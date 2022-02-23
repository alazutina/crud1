package com.anna.crud.repository.jdbc;
import com.anna.crud.model.Post;
import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Tag;
import com.anna.crud.model.Writer;
import com.anna.crud.repository.WriterRepository;
import com.anna.crud.util.JdbcUtils;
import com.anna.crud.util.SQLQuery;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class JdbcWriterRepositoryImpl implements WriterRepository {


    @Override
    public void deleteById(Long id)  {
        String sql =  "DELETE FROM writer_post WHERE ID_WRITER = "+id+";";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
               sql =  "DELETE FROM writers WHERE id = "+id+";";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Writer save(Writer w) {
        String sql ;//= String.format(SQLQuery.WRITER_INSERT_QUERY.getValue(), w.getName());
        sql= "      INSERT INTO writers (ID, NAME) VALUES (0, '"+w.getName()+"');";

    try (                PreparedStatement stmt = JdbcUtils.getPreparedStatement(sql);) {
            stmt.executeUpdate();
        } catch (Throwable e) {
        }

        sql="select max(id) max_id from writers;";
                try (                PreparedStatement stmt = JdbcUtils.getPreparedStatement(sql);) {
                       ResultSet resultSet1 = stmt.executeQuery();
                        resultSet1.first();
            long i  =resultSet1.getLong("max_id");
                    w.setId(i);
                 for(Post p: w.getPosts()){
                String sql1="INSERT INTO writer_post (ID_WRITER, ID_POST) VALUES( "+i+","+p.getId()+");";
                try (                       PreparedStatement stmt1 = JdbcUtils.getPreparedStatement(sql1);) {
                    stmt1.executeUpdate();
                } catch (Throwable e) {
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return w;
    }

    @Override
    public Writer update(Writer w)  {
        String sql =  "Update writers Set Name='"+w.getName()+"' Where ID = "+w.getId()+";";
        try (                PreparedStatement stmt = JdbcUtils.getPreparedStatement(sql);) {
            stmt.executeUpdate();
        } catch (Throwable e) {
        }

        sql =  "delete from writer_post where id_writer = "+w.getId()+";";
        try (                PreparedStatement stmt = JdbcUtils.getPreparedStatement(sql);) {
            stmt.executeUpdate();
          } catch (Throwable e) {
        }

        List<Post> posts = w.getPosts();
        for(Post p: posts) {
            String sql1 = "INSERT INTO writer_post (ID_writer, ID_POST)  VALUES(" + w.getId() + "," + p.getId() + ") " +
                    "ON DUPLICATE KEY UPDATE ID_WRITER=ID_WRITER, ID_POST=ID_POST ;";
            try (                    PreparedStatement stmt1 = JdbcUtils.getPreparedStatement(sql1);) {
                stmt1.executeUpdate();
            } catch (Throwable e) {
            }
        }

//        String sql2="SELECT ID,CONTENT, STATUS FROM post LEFT JOIN  writer_post ON post.ID = writer_post.ID_post WHERE writer_post.ID_WRITER = "+w.getId()+";";
//        try (                PreparedStatement stmt1 = JdbcUtils.getPreparedStatement(sql2);) {
//            ResultSet resultSet1 = stmt1.executeQuery();
//            List<Post> postsInDataBase = new ArrayList<>();
//            while (resultSet1.next()) {                //      List<Post> postsInDataBase = new ArrayList<>();
//                Post p = new Post();
//                long id = resultSet1.getLong(1);
//                p.setId(id);
//                String content = resultSet1.getString(2);
//                p.setContent(content);
//                String status = resultSet1.getString(3);
//                PostStatus ps = PostStatus.ACTIVE;
//                if (status.equals("DELETED")) ps = PostStatus.DELETED;
//                p.setStatus(ps);
//                postsInDataBase.add(p);
//            }
//            for (Post pDB : postsInDataBase) {
//                int flag = 0; // пост есть в BD но в новом списке его нет
//                for (Post p : w.getPosts()) {
//                    if (p.equals(pDB)) flag = 1;
//                }
//                if (flag == 0) {
//                    String sql3 = "delete  FROM writer_post WHERE id_post = " + pDB.getId() + ";";
//                    try (                            PreparedStatement stmt3 = JdbcUtils.getPreparedStatement(sql3);) {
//                        stmt1.executeUpdate();
//                    } catch (Throwable e) {
//                    }
//                }
//            }
//            for (Post pW : w.getPosts()) { // таг в обновленном посте
//                int flag = 0; //
//                for (Post pDB : postsInDataBase) {
//                    if (pW.equals(pDB)) flag = 1;
//                }
//                if (flag == 0) {
//                           String sql3 = "INSERT INTO writer_post (ID_WRITER, ID_POST)  VALUES(" + w.getId() + "," + pW.getId() + "); ";// +
//                    try (
//                            PreparedStatement stmt3 = JdbcUtils.getPreparedStatement(sql3);) {
//                        stmt1.executeUpdate();
//                    } catch (Throwable e) {
//                    }
//                }
//            }
//        }catch(Throwable e){
//        }
        return w;
    }

    @Override
    public List<Writer> getAll() {
        List <Writer> listWriter = new ArrayList<>();        //Writer(id, name, List<Post> posts)
         String sql = "SELECT * FROM  writers; ";
        try (           PreparedStatement stmt = JdbcUtils.getPreparedStatement(sql);) {
            ResultSet resultSet = stmt.executeQuery();
            List<Post> postsInDataBase = new ArrayList<>();

        while (resultSet.next()) {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);

            String sql1="SELECT ID,CONTENT, STATUS FROM posts LEFT JOIN  writer_post ON posts.ID = writer_post.ID_post WHERE writer_post.ID_WRITER = "+id+";";
            try ( PreparedStatement stmt1 = JdbcUtils.getPreparedStatement(sql1);) {
                ResultSet resultSet1 = stmt1.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (resultSet1.next()) {
                Post p=new Post();
                long idP = resultSet1.getLong(1);
                p.setId(idP);
                String content = resultSet1.getString(2);
                p.setContent(content);
                String status = resultSet1.getString(3);
                PostStatus ps = PostStatus.ACTIVE;
                if(status.equals("DELETED")) ps = PostStatus.DELETED;
                p.setStatus(ps);

                String sql2="SELECT ID,NAME FROM tags, tag_post WHERE tags.ID = tag_post.ID_TAG AND tag_post.ID_POST = " + idP+"; ";
                try (                        PreparedStatement stmt2 = JdbcUtils.getPreparedStatement(sql2);) {
                             ResultSet resultSet2 = stmt2.executeQuery();
                List<Tag> tags = new ArrayList<>();

                while (resultSet2.next()) {
                    long idT= resultSet2.getLong(1);
                    String nameT = resultSet2.getString(2);
                    tags.add(new Tag(idT,nameT));
                }
                p.setTags(tags);
                posts.add(p);
                resultSet2.close();
            }catch (SQLException e) {
                    e.printStackTrace();
                      }
        }           Writer w = new Writer();
                w.setId(id);
                w.setPosts(posts);
                w.setName(name);
                listWriter.add(w);
resultSet1.close();
    }   catch (SQLException e) {
                e.printStackTrace();
            }
    }resultSet.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listWriter;
    }

    @Override
    public Writer getById(Long id) {
        Writer newWriter = new Writer(); //            Tag newTag=new Tag();
        String sql;        //= String.format(SQLQuery.WRITER_GET_BY_ID_QUERY.getValue(), id);
        sql = "SELECT * FROM writers WHERE id = "+id+";";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long idT = resultSet.getLong(1);
                String name = resultSet.getString(2);
                newWriter.setId(idT);
                newWriter.setName(name);
            }
            String sql1 = "SELECT ID,CONTENT, STATUS FROM posts LEFT JOIN  writer_post ON posts.ID = writer_post.ID_post WHERE writer_post.ID_WRITER = " + id + ";";
            try (PreparedStatement preparedStatement1 = JdbcUtils.getPreparedStatement(sql1)) {
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                List<Post> posts = new ArrayList<>();
                while (resultSet1.next()) {
                    Post p = new Post();
                    long idP = resultSet1.getLong(1);
                    p.setId(idP);
                    String content = resultSet1.getString(2);
                    p.setContent(content);
                    String status = resultSet1.getString(3);
                    PostStatus ps = PostStatus.ACTIVE;
                    if (status.equals("DELETED")) ps = PostStatus.DELETED;
                    p.setStatus(ps);
                    String sql2 = "SELECT ID,NAME FROM tags, tag_post WHERE tags.ID = tag_post.ID_TAG AND tag_post.ID_POST = " + idP + "; ";
                    try (PreparedStatement preparedStatement2 = JdbcUtils.getPreparedStatement(sql2)) {
                        ResultSet resultSet2 = preparedStatement2.executeQuery();
                        List<Tag> tags = new ArrayList<>();
                        while (resultSet2.next()) {
                            long idT = resultSet2.getLong(1);
                            String nameT = resultSet2.getString(2);
                            tags.add(new Tag(idT, nameT));
                        }
                        p.setTags(tags);
                                     } catch (SQLException e) {
                        e.printStackTrace();
                               }
                    posts.add(p);
                }
                newWriter.setPosts(posts);
            } catch (SQLException e) {
                e.printStackTrace();
                    }
        } catch (SQLException e) {
            e.printStackTrace();
             }
        return newWriter;
    }
    }

