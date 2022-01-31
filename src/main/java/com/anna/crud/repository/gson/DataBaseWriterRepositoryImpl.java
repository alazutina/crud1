package com.anna.crud.repository.gson;
//import com.anna.crud.model.Post;
//import com.anna.crud.model.PostStatus;
//import com.anna.crud.model.Tag;
//import com.anna.crud.model.Writer;
//import com.anna.crud.repository.WriterRepository;
//import java.util.ArrayList;
//import java.util.List;
//import java.sql.*;
//
//public class DataBaseWriterRepositoryImpl implements WriterRepository {
//    /**
//     * JDBC Driver and database url
//     */
//    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//    static final String DATABASE_URL = "jdbc:mysql://localhost/test";
//
//    /**
//     * User and Password
//     */
//    static final String USER = "test";
//    static final String PASSWORD = "123456";
//
//    @Override
//    public void deleteById(Long id) throws SQLException, ClassNotFoundException {
//
//        Connection connection = null;
//        Class.forName("com.mysql.jdbc.Driver");
//        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
//        Statement statement = connection.createStatement();
//        String sql  = "DELETE FROM tag_post WHERE ID_POST IN (select id from post where ID_WRITER ="+id+");";
//       statement.executeUpdate(sql);
//        sql =  "DELETE FROM writer_post WHERE ID_WRITER = "+id+";";
//        statement.executeUpdate(sql);
//        sql =  "DELETE FROM writer WHERE id = "+id+";";
//        statement.executeUpdate(sql);
//        statement.close();
//        connection.close();
//    }
//
//    @Override
//    public Writer save(Writer w) throws SQLException, ClassNotFoundException {
//        writeToDataBase(w);
//        return w;
//    }
//
//    @Override
//    public Writer update(Writer w) throws SQLException, ClassNotFoundException {
//        Connection connection = null;
//
//        Class.forName("com.mysql.jdbc.Driver");
//
//        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
//
//        String sql =  "Update writer Set Name='"+w.getName()+"' Where ID = "+w.getId()+";";
//
//        PreparedStatement stmt = connection.prepareStatement(sql);
//
//        stmt.executeUpdate();
//
//        stmt.close();
//
//        for(Post p: w.getPosts()){
//            sql="INSERT INTO post (ID, CONTENT, STATUS) VALUES( "+p.getId()+
//                    ",'"+p.getContent()+"','"+p.getStatus()+"')"+
//                    "ON DUPLICATE KEY UPDATE ID=ID, CONTENT=CONTENT, STATUS=STATUS;";
//            stmt = connection.prepareStatement(sql);
//            stmt.executeUpdate();
//            stmt.close();
//        }
//
//        connection.close();
//
//        return w;
//
//    }
//
//    @Override
//    public List<Writer> getAll() throws SQLException, ClassNotFoundException {
//        return getAllWritersInternal();
//    }
//
//    @Override
//    public Writer getById(Long id) throws SQLException, ClassNotFoundException {
//        return getAll().stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
//    }
//
//    private List<Writer> getAllWritersInternal() throws SQLException, ClassNotFoundException {
//        List <Writer> listWriter = new ArrayList<>();        //Writer(id, name, List<Post> posts)
//        Connection connection = null;
//        Class.forName("com.mysql.jdbc.Driver");
//        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
//        String sql = "SELECT * FROM  writer; ";
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//
//        while (resultSet.next()) {
//            long id = resultSet.getLong(1);
//            String name = resultSet.getString(2);
//            List<Post> posts = new ArrayList<>();             //  Post(id, content, List<Tag> tags, PostStatus status)
//            sql="SELECT * FROM  post WHERE id_writer =" + id+"; ";
//            Statement statement1 = connection.createStatement();
//            ResultSet resultSet1 = statement1.executeQuery(sql);
//
//            while (resultSet1.next()) {
//                long idP = resultSet1.getLong(1);
//                String content = resultSet1.getString(2);
//                String status = resultSet1.getString(3);
//                long idW = resultSet1.getLong(4);
//                PostStatus st=PostStatus.DELETED;
//                if(status.equals("ACTIVE")) st = PostStatus.ACTIVE;
//
//              //  sql="SELECT ID,NAME FROM tag, tag_post WHERE tag.ID = tag_post.ID_TAG AND tag_post.ID_POST = " + idP+"; ";
//               sql="SELECT ID,NAME FROM tag LEFT JOIN  tag_post ON tag.ID = tag_post.ID_TAG WHERE tag_post.ID_POST = "+ idP+";";
//                Statement statement2 = connection.createStatement();
//                ResultSet resultSet2 = statement2.executeQuery(sql);
//                List<Tag> tags = new ArrayList<>();
//
//                while (resultSet2.next()) {
//                    long idT= resultSet2.getLong(1);
//                    String nameT = resultSet2.getString(2);
//                    tags.add(new Tag(idT,nameT));
//                       }
//                statement2.close();
//                resultSet2.close();
//                                   posts.add(new Post(idP, content, tags,st,idW));
//                        }
//            listWriter.add(new Writer(id,name,posts));
//            statement1.close();
//            resultSet1.close();
//
//        }
//        statement.close();
//        resultSet.close();
//        connection.close();
//        return listWriter;
//   }
//
//
//    void writeToDataBase(Writer w) throws ClassNotFoundException, SQLException {
//        //Writer(id, name, List<Post> posts)
//        Connection connection = null;
//        Class.forName("com.mysql.jdbc.Driver");
//        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
//        String sql = "INSERT INTO writer (ID, NAME) VALUES (0, '"+w.getName()+"')";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        stmt.executeUpdate();
//        stmt.close();
//        sql="select max(id) max_id from writer;";
//        PreparedStatement stmt1 = connection.prepareStatement(sql);
//        ResultSet resultSet1 = stmt1.executeQuery(sql);
//
//        resultSet1.first();
//        long i  =resultSet1.getLong("max_id");
//
//        for(Post p: w.getPosts()){
//
//            sql="INSERT INTO post (ID, CONTENT, STATUS) VALUES( "+p.getId()+
//                    ",'"+p.getContent()+"','"+p.getStatus()+"')"+
//                    "ON DUPLICATE KEY UPDATE ID=ID, CONTENT=CONTENT, STATUS=STATUS " +                    ";";
//            stmt = connection.prepareStatement(sql);
//            stmt.executeUpdate();
//
//
//            sql="select max(id) max_id from post;";
//            stmt1 = connection.prepareStatement(sql);
//            resultSet1 = stmt1.executeQuery(sql);
//
//            resultSet1.first();
//            long j  =resultSet1.getLong("max_id");
//
//            sql="INSERT INTO writer_post (ID_WRITER, ID_POST) VALUES( "+i+
//                    ",'"+j+";";
//            stmt = connection.prepareStatement(sql);
//            stmt.executeUpdate();
//
//
//            stmt.close();
//        }
//
//
//        connection.close();
//    }
//    }
