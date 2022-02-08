package com.anna.crud.repository.jdbc;
import com.anna.crud.model.Post;
import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.PostRepository;
import com.anna.crud.util.JdbcUtils;
import com.anna.crud.util.SQLQuery;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class JdbcPostRepositoryImpl implements PostRepository {


    public void deleteById(Long id) {
        String sql ;//= String.format(SQLQuery.TAG_POST_DELETE_BY_ID_POST_QUERY.getValue(), id);
        sql =  "DELETE  FROM tag_post WHERE id_post ="+id+ ";";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "DELETE  FROM posts WHERE id ="+id+";";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Post save(Post p) {
        String sql = "INSERT INTO posts (ID,content, status) VALUES (0, '" + p.getContent() + "', '" + p.getStatus() + "" +
                "') ON DUPLICATE KEY UPDATE ID=ID, CONTENT=CONTENT, STATUS=STATUS;";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "SELECT max(id) max_id from posts;";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.first();
            long i = resultSet.getLong("max_id");
            List<Tag> Tags = p.getTags();
            System.out.println(Tags);

            for (Tag t : Tags) {
                String sql1 = "INSERT INTO tag_post (ID_TAG, ID_POST)  VALUES(" + t.getId() + "," + i + ")" +
                        " ON DUPLICATE KEY UPDATE ID_TAG=ID_TAG, ID_POST=ID_POST" +
                        ";";
                 try (PreparedStatement preparedStatement1 = JdbcUtils.getPreparedStatement(sql1)) {
                    preparedStatement1.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            p.setId(i);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return p;
    }

    @Override
    public Post update(Post post) {
        String sql = "UPDATE posts set content = '" + post.getContent() + "', status = '" + post.getStatus() +
                "' Where ID = " + post.getId() + ";";
        try (
                PreparedStatement stmt = JdbcUtils.getPreparedStatement(sql);) {
            stmt.executeUpdate();
            List<Tag> tags = post.getTags();
            List<Tag> tagsInDataBase = new ArrayList<>();
            String sql1 = "SELECT ID,NAME FROM tag LEFT JOIN  tag_post ON tag.ID = tag_post.ID_TAG WHERE tag_post.ID_POST = " + post.getId() + ";";
             try (
                    PreparedStatement stmt1 = JdbcUtils.getPreparedStatement(sql1);) {
                ResultSet resultSet1 = stmt1.executeQuery(sql1);
                while (resultSet1.next()) {
                    long id = resultSet1.getLong(1);
                    String name = resultSet1.getString(2);
                    tagsInDataBase.add(new Tag(id, name));
                    for (Tag tDB : tagsInDataBase) {
                        int flag = 0; // пост есть в BD но в новом списке его нет
                        for (Tag t : post.getTags()) {
                            if (t.equals(tDB)) flag = 1;
                        }
                        if (flag == 0) {
                            String sql2 = "delete  FROM tag_post WHERE id_TAG = " + tDB.getId() + ";";
                            try (
                                    PreparedStatement stmt2 = JdbcUtils.getPreparedStatement(sql2);) {
                                stmt2.executeUpdate();
                            } catch (Throwable e) {
                            }
                        }
                    }
                    for (Tag t : post.getTags()) { // таг в обновленном посте
                        int flag = 0; //
                        for (Tag tDB : tagsInDataBase) {
                            if (t.equals(tDB)) flag = 1;
                        }
                        if (flag == 0) {                                //     String sql1 = "INSERT INTO tag_post (ID_TAG, ID_POST)  VALUES(" + t.getId() + "," + post.getId() + ") " +
                            String sql2 = "INSERT INTO tag_post (ID_TAG, ID_POST)  VALUES(" + t.getId() + "," + post.getId() + "); ";// +
                            try (                                    PreparedStatement stmt2 = JdbcUtils.getPreparedStatement(sql2);) {
                                stmt2.executeUpdate();
                            } catch (Throwable e) {
                            }
                        }
                    }
                }

            }

        } catch (Throwable e) {
            return null;
        }
        return post;
    }


    @Override
    public List<Post> getAll()  {
        return getAllPostsInternal();
    }

    public Post getById(Long id) {
        Post p = new Post();
        String sql ;//= String.format(SQLQuery.POST_GET_BY_ID_QUERY.getValue(), id);
        sql = "SELECT * FROM posts WHERE id = "+id+";";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long idP = resultSet.getLong(1);
                p.setId(id);
                String content = resultSet.getString(2);
                p.setContent(content);
                String status = resultSet.getString(3);
                PostStatus st;
                if (status.equals("ACTIVE")) {
                    st = PostStatus.ACTIVE;
                } else {
                    st = PostStatus.DELETED;
                }
                p.setStatus(st);
                String sql2 = "SELECT ID,NAME FROM tags LEFT JOIN  tag_post ON tags.ID = tag_post.ID_TAG WHERE tag_post.ID_POST = " + id + ";";
                    try (PreparedStatement preparedStatement2 = JdbcUtils.getPreparedStatement(sql2)) {
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    List<Tag> postTags = new ArrayList<>();
                    while (resultSet2.next()) {
                        long idT = resultSet2.getLong(1);
                        String nameT = resultSet2.getString(2);
                        postTags.add(new Tag(idT, nameT));
                    }
                    p.setTags(postTags);
                    resultSet2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    private List<Post> getAllPostsInternal(){
        List<Post> listPost = new ArrayList<>();
        String sql = "SELECT * FROM  posts; ";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Post p = new Post();
                long id = resultSet.getLong(1);
                p.setId(id);
                String content = resultSet.getString(2);
                p.setContent(content);
                String status = resultSet.getString(3);
                PostStatus st;
                if (status.equals("ACTIVE")) {
                    st = PostStatus.ACTIVE;
                } else {
                    st = PostStatus.DELETED;
                }
                p.setStatus(st);
                String sql1 = "SELECT ID,NAME FROM tags LEFT JOIN  tag_post ON tags.ID = tag_post.ID_TAG WHERE tag_post.ID_POST = " + id + ";";
                try (PreparedStatement preparedStatement1 = JdbcUtils.getPreparedStatement(sql1)) {
                       ResultSet resultSet1 = preparedStatement1.executeQuery();
                        List<Tag> postTags = new ArrayList<>();
                        while (resultSet1.next()) {
                            long idT = resultSet1.getLong(1);
                            String nameT = resultSet1.getString(2);
                            postTags.add(new Tag(idT, nameT));
                        }
                        p.setTags(postTags);
                listPost.add(p);
                    resultSet1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPost;
    }
}
