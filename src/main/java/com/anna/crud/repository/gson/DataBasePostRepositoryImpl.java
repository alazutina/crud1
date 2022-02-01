package com.anna.crud.repository.gson;
import com.anna.crud.model.Post;
import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.PostRepository;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import static java.lang.Class.*;

public class DataBasePostRepositoryImpl implements PostRepository {
    /**
     * JDBC Driver and database url
     */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/test";
    /**
     * User and Password
     */
    static final String USER = "test";
    static final String PASSWORD = "123456";

    @Override
    public void deleteById(Long id) throws SQLException, ClassNotFoundException{
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String sql =  "DELETE FROM tag_post WHERE ID_post = "+id+";";
        statement.executeUpdate(sql);
        sql =  "DELETE FROM post WHERE id = "+id+";";
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
    }

    @Override
    public Post save(Post p) throws SQLException, ClassNotFoundException {
        writeToDataBase(p);
       return p;
    }

    @Override
    public Post update(Post post) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        String sql =  "Update post content = '"+post.getContent()+"', status = '"+post.getStatus()+
                "' Where ID = "+post.getId()+";";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.executeUpdate();

        List<Tag> tags = post.getTags();
        for(Tag t: tags) {
            String sql1 = "INSERT INTO tag_post (ID_TAG, ID_POST)  VALUES(" + t.getId() + "," + post.getId() + ") " +
                    "ON DUPLICATE KEY UPDATE ID_TAG=ID_TAG, ID_POST=ID_POST ;";
            PreparedStatement stmt1 = connection.prepareStatement(sql1);
            stmt1.executeUpdate();
            stmt1.close();
        }

                List<Tag> tagsInDataBase = new ArrayList<>();

           sql="SELECT ID,NAME FROM tag LEFT JOIN  tag_post ON tag.ID = tag_post.ID_TAG WHERE tag_post.ID_POST = "+post.getId()+";";
        Statement statement1 = connection.createStatement();
        ResultSet resultSet1 = statement1.executeQuery(sql);
        while (resultSet1.next()) {
            long id = resultSet1.getLong(1);
            String name = resultSet1.getString(2);
            tagsInDataBase.add(new Tag(id,name));
        }
        resultSet1.close();
        statement1.close();

        for (Tag tDB: tagsInDataBase ){
            int flag = 0; // пост есть в BD но в новом списке его нет
            for(Tag t: post.getTags()){
                if (t.equals(tDB)) flag=1;
            }
            if(flag==0){
                String sql1 = "delete  FROM tag_post WHERE id_TAG = "+tDB.getId()+");";
                PreparedStatement stmt1 = connection.prepareStatement(sql1);
                stmt1.executeUpdate();
                stmt1.close();

            }
        }


     //   sql="SELECT ID,NAME FROM tag LEFT JOIN  tag_post ON tag.ID = tag_post.ID_TAG WHERE tag_post.ID_POST = "+post.getId()+";";
//        Statement statement1 = connection.createStatement();
//        ResultSet resultSet1 = statement1.executeQuery(sql);
//        while (resultSet1.next()) {
//            long id = resultSet1.getLong(1);
//            String name = resultSet1.getString(2);
//            tagsInDataBase.add(new Tag(id,name));
//        }
//        resultSet1.close();
//        statement1.close();
//        for (Tag t: post.getTags()){
//            int flag = 0; // пока такого tag нет
//            for(Tag tDB: tagsInDataBase){
//                if (t.equals(tDB)) flag=1;
//            }
//            if(flag==0){
//                String sql1 =  "INSERT INTO tag_post (ID_TAG, ID_POST)  VALUES("+t.getId()+","+post.getId()+");";
//                PreparedStatement stmt1 = connection.prepareStatement(sql1);
//                stmt.executeUpdate();
//                stmt1.close();
//            }
//        }
//
//
//
//        for (Tag tDB: tagsInDataBase ){
//            int flag = 0; // пост есть в BD но в новом списке его нет
//            for(Tag t: post.getTags()){
//                if (t.equals(tDB)) flag=1;
//            }
//            if(flag==0){
//                String sql1 = "delete  FROM tag_post WHERE id_TAG = "+tDB.getId()+");";
//                PreparedStatement stmt1 = connection.prepareStatement(sql1);
//                stmt.executeUpdate();
//                stmt1.close();
//
//            }
//        }
        stmt.close();

        connection.close();
        return post;
    }

    @Override
    public List<Post> getAll() throws SQLException, ClassNotFoundException{
        return getAllPostsInternal();
    }

    public Post getById(Long id)throws SQLException, ClassNotFoundException {
        return getAll().stream().filter(t->t.getId().equals(id)).findFirst().orElse(null);
    }

    private List<Post> getAllPostsInternal() throws SQLException, ClassNotFoundException{
        List<Post> listPost = new ArrayList<>();

        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        String sql = "SELECT * FROM  post; ";
        Statement statement = connection.createStatement();
        Statement statement1 = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            Post p = new Post();
            long id = resultSet.getLong(1);
            p.setId(id);
            String content = resultSet.getString(2);
            p.setContent(content);
            String status = resultSet.getString(3);
           PostStatus st;
            if(status.equals("ACTIVE")){ st = PostStatus.ACTIVE;} else {st = PostStatus.DELETED;}
            p.setStatus(st);

           String sql1="SELECT ID,NAME FROM tag LEFT JOIN  tag_post ON tag.ID = tag_post.ID_TAG WHERE tag_post.ID_POST = "+id+";";
           ResultSet resultSet1 = statement1.executeQuery(sql1);

            List<Tag> postTags = new ArrayList<>();
            while (resultSet1.next()) {
                long idT = resultSet1.getLong(1);
                String nameT = resultSet1.getString(2);
                postTags.add(new Tag(idT,nameT));
            }

            p.setTags(postTags);
            resultSet1.close();
            listPost.add(p);
        }

        resultSet.close();
        statement.close();
        statement1.close();
        connection.close();
        return listPost;
    }

    void writeToDataBase(Post p) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        String sql = "INSERT INTO post (ID,content, status) VALUES (0, '"+p.getContent()+"', '"+p.getStatus()+"" +
                "') ON DUPLICATE KEY UPDATE ID=ID, CONTENT=CONTENT, STATUS=STATUS;";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.executeUpdate();
        sql="select max(id) max_id from post;";
        PreparedStatement stmt1 = connection.prepareStatement(sql);
        ResultSet resultSet1 = stmt1.executeQuery(sql);

resultSet1.first();
       long i  =resultSet1.getLong("max_id");


//        if(resultSet1.next()) {
//            i = resultSet1.getInt("max_id");}

        List<Tag> Tags = p.getTags();

        for( Tag t: Tags ){
            sql = "INSERT INTO tag (ID, NAME)  VALUES("+t.getId()+",'"+t.getName()+"')" +
                    " ON DUPLICATE KEY UPDATE ID=ID, NAME=NAME;";//System.out.println(sql);
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();


            sql="select max(id) max_id from tag;";
            stmt1 = connection.prepareStatement(sql);
            resultSet1 = stmt1.executeQuery(sql);

            resultSet1.first();
            long j  =resultSet1.getLong("max_id");

            sql = "INSERT INTO tag_post (ID_TAG, ID_POST)  VALUES("+j+","+i+")" +
                    " ON DUPLICATE KEY UPDATE ID_TAG=ID_TAG, ID_POST=ID_POST" +
                    ";";
           // System.out.println(sql);
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        }
        stmt.close();
        stmt1.close();

        resultSet1.close();
        connection.close();
    }

}
