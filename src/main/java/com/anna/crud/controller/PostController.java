package com.anna.crud.controller;
import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.PostRepository;
import com.anna.crud.repository.jdbc.JdbcPostRepositoryImpl;
import com.anna.crud.model.Post;
import java.sql.SQLException;
import java.util.List;

public class PostController {

    private final PostRepository postRepository ;

    public PostController() {
        this.postRepository = new JdbcPostRepositoryImpl();
    }

    public PostController(PostRepository postController) {
        this.postRepository=  postController;
          }

    public Post save(String content, List<Tag> tags, PostStatus postStatus) {
        Post post = new Post();
        post.setContent(content);
        post.setTags(tags);
        post.setStatus(postStatus);
        return postRepository.save(post);
    }

    public
    Post update(Long id, String content, List<Tag> tags, PostStatus postStatus) {
        Post post = new Post();
        post.setId(id);
        post.setContent(content);
        post.setTags(tags);
        post.setStatus(postStatus);
        return postRepository.update(post);
    }

    public Post getById(Long id)  {
        return postRepository.getById(id);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }


}
