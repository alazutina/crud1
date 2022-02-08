package com.anna.crud.model;

import java.util.ArrayList;
import java.util.List;

public class Post {


    private Long id;
    private String content;
    List<Tag> tags = new ArrayList<>();
    private PostStatus status;

    public Post() {
    }

    public Post(Long id, String content, List<Tag> tags, PostStatus status) {
        this.id = id;
        this.content = content;
        this.tags = tags;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", status=" + status +
                '}';
    }
}




