package com.anna.crud.controller;

import java.util.ArrayList;
import java.util.List;


import com.anna.crud.model.Tag;
import com.anna.crud.repository.TagRepository;
import com.anna.crud.repository.gson.GsonTagRepositoryImpl;

public class TagController {
    private final TagRepository tagRepository = new GsonTagRepositoryImpl();


    public Tag save(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }

    public Tag update(Long id, String name) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        return tagRepository.update(tag);
    }

    public Tag getById(Long id) {
        return tagRepository.getById(id);
    }

    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }

    public List<Tag> getAll() {

        return tagRepository.getAll();
    }
}

