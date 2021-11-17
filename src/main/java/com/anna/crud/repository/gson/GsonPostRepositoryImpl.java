package com.anna.crud.repository.gson;

import com.anna.crud.model.Post;
//import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.PostRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GsonPostRepositoryImpl implements PostRepository {

    private final static String TAG_FILE_PATH = "/home/anya/IdeaProjects/anna/src/main/resources/post.json";

    @Override
    public void deleteById(Long id) {
        List<Post> currentPosts = getAll();
        currentPosts.removeIf(t -> t.getId().equals(id));
        writePostsToJsonFile(currentPosts);

    }




    @Override
    public Post save(Post p) {
        List<Post> currentPosts = getAll();
        Long newMaxId = getIdNext(currentPosts);
        p.setId(newMaxId);
        currentPosts.add(p);
        writePostsToJsonFile(currentPosts);
        return p;
    }



    private Long getIdNext(List<Post> currentPosts){
       Post maxIdPost = currentPosts.stream().max(Comparator.comparing(Post::getId)).orElse(null);
        return maxIdPost == null ? 1L : maxIdPost.getId() + 1;
    }


    @Override
    public Post update(Post post) {
        List<Post> currentPosts = getAll();

        currentPosts.forEach(p -> {
            if (p.getId().equals(post.getId())) {

                p.setContent(post.getContent());
                p.setPostStatus(post.getPostStatus());
                p.setTags(post.getTags());

            }
        });

        writePostsToJsonFile(currentPosts);

        return post;
    }


    void writePostsToJsonFile(List<Post> posts) {
        try (FileWriter fw = new FileWriter(TAG_FILE_PATH);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String jsonPost = new Gson().toJson(posts);
            bw.write(jsonPost);

        } catch (IOException e) {
            System.out.println(e);
        }

    }





    @Override
    public List<Post> getAll() {
        return getAllPostsInternal();
    }


    public Post getById(Long id) {
        return getAll().stream().filter(t->t.getId().equals(id)).findFirst().orElse(null);
    }





    private List<Post> getAllPostsInternal() {
        List<Post> listPost = new ArrayList<>();
        File f = new File(TAG_FILE_PATH);
        if (f.exists()) {
            try (FileReader fr = new FileReader(TAG_FILE_PATH);
                 BufferedReader br = new BufferedReader(fr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                   Post[] p = new GsonBuilder().create().fromJson(line, Post[].class);
                    Collections.addAll(listPost, p);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return listPost;
    }
}
