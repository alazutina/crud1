import com.anna.crud.controller.PostController;
import com.anna.crud.controller.TagController;
import com.anna.crud.model.Post;
import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Tag;
import com.anna.crud.util.JdbcUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PostControllerTest {

    PostController instance;// = new TagController();

    public PostControllerTest(){
        JdbcUtils.setConnection();
        instance = new PostController();

    }


    @Test
    public void save() {
        PostStatus postStatus = PostStatus.ACTIVE;
        String content = "News";
        List<Tag> tagList = new ArrayList<>();
        TagController tagController = new TagController();
        tagList.add(tagController.getById(1l));
        Post actual = instance.save(content,tagList,postStatus);
        Post expected = instance.getById(5l);
        assertEquals(actual.toString(),expected.toString());

    }

    @Test
    public void update() {
        Long id = 3l;
        String content = "Article";
        PostStatus postStatus = PostStatus.ACTIVE;
        List<Tag> tagList = new ArrayList<>();
        TagController tagController = new TagController();
        tagList.add(tagController.getById(1l));
        Post actual = instance.update(id,content,tagList,postStatus);
        Post expected = instance.getById(3l);
        assertEquals(actual.toString(),expected.toString());
    }


    @Test
    public void getById() {

       Post actual = instance.getById(3l);
       Post expected = new Post();

       Long id = 3L;
       String content ="Article";
       PostStatus postStatus = PostStatus.ACTIVE;
        List<Tag> tagList = new ArrayList<>();
        TagController tagController = new TagController();
        tagList.add(tagController.getById(1l));
        expected.setTags(tagList);
        expected.setContent(content);
        expected.setStatus(postStatus);
        expected.setId(id);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void deleteById() {
        instance.deleteById(4l);
        Post actual = instance.getById(4l);
        Long expectedID = null;
        String expectedContent = null;
        List<Tag > expectedTags = new ArrayList<>();
        PostStatus expectedStatus = null;
        assertEquals(expectedID, actual.getId());
        assertEquals(expectedContent, actual.getContent());
        assertEquals(expectedStatus,actual.getStatus());
        assertEquals(expectedTags,actual.getTags());
    }

    @Test
    public void getAll() {
        List<Post> postArrayList = new ArrayList<>();
        postArrayList = instance.getAll();
        int actual = postArrayList.size();
        int expected = 2;
        assertEquals(expected, actual);
    }
}