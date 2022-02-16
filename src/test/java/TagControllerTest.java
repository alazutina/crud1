import com.anna.crud.model.Tag;
import com.anna.crud.util.JdbcUtils;
import org.junit.Test;
import com.anna.crud.controller.TagController;
import java.util.List;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TagControllerTest {

    TagController instance;// = new TagController();

    public TagControllerTest(){
        JdbcUtils.setConnection();
        instance = new TagController();

    }

    @Test
    public void save() {
        Tag t  = new Tag();
        t.setName("Roma");
        Tag actual = instance.save(t.getName());
        Tag expected = instance.getById(8l);
        assertEquals(actual.toString(),expected.toString());

    }

    @Test
    public void update() {
        Tag t  = new Tag();
        t.setId(1l);
        t.setName("Moscow");
        Tag actual = instance.update(t.getId(),t.getName());
        Tag expected = instance.getById(1l);
        assertEquals(actual.toString(),expected.toString());
    }

    @Test
    public void getById() {
      Tag expected = instance.getById(3l);
      Tag actual = new Tag();
        actual.setId(3l);
        actual.setName("Paris");
        assertEquals(expected.toString(), actual.toString());

    }

    @Test
    public void deleteById() {
        instance.deleteById(2l);
        Tag actual = instance.getById(2l);
        Long expectedID = null;
        String expectedName = null;
        assertEquals(expectedID, actual.getId());
        assertEquals(expectedName, actual.getName());
            }

    @Test
    public void getAll() {
        List<Tag> tagArrayList = new ArrayList<>();
        tagArrayList = instance.getAll();
        int actual = tagArrayList.size();
        int expected = 6;
        assertEquals(expected, actual);
    }
}