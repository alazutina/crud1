package com.anna.crud.repository.jdbc;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import com.anna.crud.util.JdbcUtils;
import com.anna.crud.util.SQLQuery;

public class JdbcTagRepositoryImpl implements TagRepository {

    public Tag getById(Long id) {
        Tag newTag=new Tag();//        System.out.println("!!");
        String sql = "SELECT * FROM tags WHERE id = "+id+";";//        System.out                .println(sql);
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long idT = resultSet.getLong(1);
                String name = resultSet.getString(2);
                newTag.setId(idT);
                newTag.setName(name);
            }
                return newTag;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Tag save(Tag tag) {
        String sql="INSERT INTO tags (ID, NAME) VALUES (0, '"+tag.getName()+"');";
        try (PreparedStatement stmt = JdbcUtils.getPreparedStatement(sql);) {
                  stmt.executeUpdate();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return tag;
    }

    public Tag update(Tag t) {
        String sql = "UPDATE tags SET name='" + t.getName() + "' WHERE id = " + t.getId() + ";";
        try (
                PreparedStatement stmt = JdbcUtils.getPreparedStatement(sql);) {
            stmt.executeUpdate();
            return t;
        } catch (Throwable e) {
            return null;
        }
    }

    public List<Tag> getAll() {
        List<Tag> tags = new ArrayList<>();
        String sql= SQLQuery.TAG_GET_ALL_QUERY.getValue();
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tag newTag = new Tag();
                long idT = resultSet.getLong(1);
                String name = resultSet.getString(2);
                newTag.setId(idT);
                newTag.setName(name);
                tags.add(newTag);
            }
            return tags;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void deleteById(Long id) {
        String sql = "DELETE FROM tag_post WHERE id_tag = "+id+";";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql ="DELETE FROM tags WHERE id = "+id+";";//        sql = String.format(SQLQuery.TAG_DELETE_BY_ID_QUERY.getValue(), id);
           try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
