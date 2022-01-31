package com.anna.crud.controller;
import java.sql.SQLException;
import java.util.List;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.TagRepository;
import com.anna.crud.repository.gson.DataBaseTagRepositoryImpl;

public class TagController {

    private final TagRepository tagRepository = new DataBaseTagRepositoryImpl();

    public Tag save(String name) throws SQLException, ClassNotFoundException{
        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }

    public Tag update(Long id, String name)throws ClassNotFoundException, SQLException {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        return tagRepository.update(tag);
    }

    public Tag getById(Long id) throws ClassNotFoundException, SQLException{
        return tagRepository.getById(id);
    }

    public void deleteById(Long id) throws SQLException, ClassNotFoundException{
        tagRepository.deleteById(id);
    }

    public List<Tag> getAll() throws SQLException, ClassNotFoundException{

        return tagRepository.getAll();
    }
}

