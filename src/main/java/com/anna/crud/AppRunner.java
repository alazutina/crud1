package com.anna.crud;
import com.anna.crud.controller.TagController;
import com.anna.crud.controller.WriterController;
import com.anna.crud.controller.PostController;
import com.anna.crud.model.Post;
import com.anna.crud.model.Tag;
import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Writer;
//import com.anna.crud.view.MainView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AppRunner {

            public static void main(String[] args) throws ClassNotFoundException, SQLException {

                final TagController tagController = new TagController();
                tagController.save("Moscow");
                tagController.save("Sochi");
                System.out.println(                tagController.getAll());
                tagController.deleteById(1l);
                System.out.println(tagController.getById(2l));


                final PostController postController = new PostController();
                List<Tag> tags = new ArrayList<>();
                tags.add(tagController.getById(2l));
                //tags.add(new Tag(0l,"Article"));
                postController.save("News", tags,PostStatus.ACTIVE);
                System.out.println(postController.getAll());
                postController.deleteById(3l                );
                System.out.println(postController.getAll());
                System.out.println(postController.getById(4l));

                final WriterController writerController = new WriterController();

                List<Post> list = new ArrayList<>();
                list.add(postController.getById(11l));
                list.add(postController.getById(12l));
                System.out.println(list);

                writerController.save("Alina", list);
             System.out.println(writerController.getAll());
                writerController.deleteById(1l                );
                System.out.println(writerController.getAll());
                System.out.println(writerController.getById(5l));
                Writer w = writerController.getById(5l);
                writerController.update(w.getId(),"Marina",list);
                System.out.println(writerController.getById(5l));




            }
}
