package com.anna.crud.repository.gson;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.TagRepository;
import java.util.ArrayList;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.*;

public class DataBaseTagRepositoryImpl implements TagRepository {
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
    private Tag t;

    public Tag getById(Long id) throws ClassNotFoundException, SQLException {
        return getAllTagsInternal().stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Tag> getAll() throws ClassNotFoundException, SQLException{
        return getAllTagsInternal();
    }

    public Tag update(Tag t) throws SQLException, ClassNotFoundException{
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        String sql =  "Update tag Set Name='"+t.getName()+"' Where ID = "+t.getId()+";";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
        return t;
    }

    public Tag save(Tag tag) throws SQLException, ClassNotFoundException {
         tag.setId(writeToDataBase(tag));
         return tag;
    }

    @Override
    public void deleteById(Long id) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String sql =  "DELETE FROM tag_post WHERE ID_TAG = "+id+";";
        statement.executeUpdate(sql);
        sql =  "DELETE FROM tag WHERE id = "+id+";";
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
    }

//    private Long getIdNext(List<Tag> currentTags){
//        Tag maxIdTag = currentTags.stream().max(Comparator.comparing(Tag::getId)).orElse(null);
//        return maxIdTag == null ? 1L : maxIdTag.getId() + 1;
//    }

    private List<Tag> getAllTagsInternal() throws ClassNotFoundException, SQLException{
        List <Tag> listTag = new ArrayList<>();
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        String sql = "SELECT * FROM  tag; ";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            listTag.add(new Tag(id,name));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return listTag;
    }

    void writeToDataBase(Tag t) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        String sql = "INSERT INTO tag (ID, NAME) VALUES (0, '"+t.getName()+"')";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
}