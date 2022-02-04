package com.anna.crud.controller;
import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.PostRepository;
import com.anna.crud.repository.gson.DataBasePostRepositoryImpl;
import com.anna.crud.model.Post;
import java.sql.SQLException;
import java.util.List;

public class PostController {

    private final PostRepository postRepository = new DataBasePostRepositoryImpl();

    public Post save(String content, List<Tag> tags, PostStatus postStatus) throws SQLException, ClassNotFoundException{
        Post post = new Post();
        post.setContent(content);
        post.setTags(tags);
        post.setStatus(postStatus);
        return postRepository.save(post);
    }

    public
    Post update(Long id, String content, List<Tag> tags, PostStatus postStatus) throws SQLException, ClassNotFoundException {
        Post post = new Post();
        post.setId(id);
        post.setContent(content);
        post.setTags(tags);
        post.setStatus(postStatus);
        return postRepository.update(post);
    }

    public Post getById(Long id) throws SQLException, ClassNotFoundException {
        return postRepository.getById(id);
    }

    public void deleteById(Long id) throws SQLException, ClassNotFoundException {
        postRepository.deleteById(id);
    }

    public List<Post> getAll() throws SQLException, ClassNotFoundException {
        return postRepository.getAll();
    }


}
