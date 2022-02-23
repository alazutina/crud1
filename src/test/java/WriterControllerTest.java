import com.anna.crud.controller.TagController;
import com.anna.crud.controller.WriterController;
import com.anna.crud.controller.PostController;
import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Tag;
import com.anna.crud.model.Writer;
import com.anna.crud.model.Post;
import com.anna.crud.util.JdbcUtils;
import org.junit.Test;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WriterControllerTest {


    WriterController instance;// = new TagController();

    public WriterControllerTest(){
        JdbcUtils.setConnection();
        instance = new WriterController();

    }

    @Test
    public void save() {
        Writer w = new Writer();
        w.setName("D");
        List<Post> posts = new ArrayList<>();
        PostController postController = new PostController();
        posts.add(postController.getById(1l));
        w.setPosts(posts);

        Writer actual = instance.save(w.getName(),w.getPosts());
       Writer expected = instance.getById(5l);
        assertEquals(actual.toString(),expected.toString());
    }

    @Test
    public void update() {
        Long id = 1l;
        String name = "D";
        List<Post> posts = new ArrayList<>();
        PostController postController = new PostController();
        posts.add(postController.getById(1l));
        Writer actual = instance.update(id,name,posts);
        Writer expected = instance.getById(1l);
        assertEquals(actual.toString(),expected.toString());
    }

    @Test
    public void getById() {
        Writer actual = instance.getById(3l);
        Writer expected = new Writer();

        Long id = 3L;
        String name ="D";
        List<Post> posts = new ArrayList<>();
        PostController postController = new PostController();
        posts.add(postController.getById(1l));
        expected.setId(id);
        expected.setName(name);
        expected.setPosts(posts);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void deleteById() {
        instance.deleteById(4l);
        Writer actual = instance.getById(4l);
        Long expectedID = null;
        String expectedName = null;
        List<Post > expectedPosts = new ArrayList<>();
        assertEquals(expectedID, actual.getId());
        assertEquals(expectedName, actual.getName());
        assertEquals(expectedPosts,actual.getPosts());
    }

    @Test
    public void getAll() {
        List<Writer> writers = new ArrayList<>();
        writers = instance.getAll();
        int actual = writers.size();
        int expected = 4;
        assertEquals(expected, actual);
    }
}