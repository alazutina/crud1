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


public class GsonTagRepositoryImpl implements TagRepository {

    private final static String TAG_FILE_PATH = "/home/anya/IdeaProjects/anna/src/main/resources/tag.json";

    public Tag getById(Long id) {
        return getAllTagsInternal().stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Tag> getAll() {

        return getAllTagsInternal();
    }


    public Tag update(Tag t) {
        List<Tag> currentTags = getAllTagsInternal();

        currentTags.forEach(tag -> {
            if (tag.getId().equals(t.getId())) {
                tag.setName(t.getName());
            }
        });

        writeToJsonFile(currentTags);

        return t;
    }


    public Tag save(Tag t) {
        List<Tag> currentTags = getAllTagsInternal();
        Long newMaxId = getIdNext(currentTags);
        t.setId(newMaxId);
        t.setName(t.getName());
        currentTags.add(t);
        writeToJsonFile(currentTags);
        return t;
    }


    public void deleteById(Long id) {
        List<Tag> currentTags = getAllTagsInternal();
        currentTags.removeIf(t -> t.getId().equals(id));
        writeToJsonFile(currentTags);
    }

    private Long getIdNext(List<Tag> currentTags){
        Tag maxIdTag = currentTags.stream().max(Comparator.comparing(Tag::getId)).orElse(null);
        return maxIdTag == null ? 1L : maxIdTag.getId() + 1;
    }

    private List<Tag> getAllTagsInternal() {
        List <Tag> listTag = new ArrayList<>();
        File f = new File(TAG_FILE_PATH);
        if (f.exists()) {
            try (FileReader fr = new FileReader(TAG_FILE_PATH);
                 BufferedReader br = new BufferedReader(fr)) {
                String line;
                while ((line = br.readLine()) != null) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    Tag[] t = new GsonBuilder().create().fromJson(line, Tag[].class);
                    Collections.addAll(listTag, t);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return listTag;
    }

    void writeToJsonFile(List<Tag> tags) {
        try (FileWriter fw = new FileWriter(TAG_FILE_PATH);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String jsonTag = new Gson().toJson(tags);
            bw.write(jsonTag);

        } catch (IOException e) {
            System.out.println(e);
        }

    }

}