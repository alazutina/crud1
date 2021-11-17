package com.anna.crud.model;

import java.util.List;

public class Writer {

    Long id;
    String name;
    List<Post> posts;

    public Writer() {
    }

    public Writer(Long id, String name, List<Post> posts ) {
        this.id = id;
        this.name = name;
        this.posts = posts;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setPosts(List<Post> posts) {
        this.posts=posts;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Post> getPosts() {
        return posts;
    }


    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", posts=" + posts +
                '}';
    }


}
