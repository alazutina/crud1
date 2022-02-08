package com.anna.crud;
import com.anna.crud.controller.TagController;
import com.anna.crud.controller.WriterController;
import com.anna.crud.controller.PostController;
import com.anna.crud.model.Post;
import com.anna.crud.model.Tag;
import com.anna.crud.model.PostStatus;
import com.anna.crud.util.JdbcUtils;
import com.anna.crud.model.Writer;
import com.anna.crud.view.MainView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AppRunner {

            public static void main(String[] args) throws ClassNotFoundException, SQLException {

//                MainView mn = new
//                        MainView();
//                mn.start();


                JdbcUtils.setConnection();
                                MainView mn = new
                        MainView();
                mn.start();
//               final TagController tagController = new TagController();
//               tagController.save("Moscow"); //save
//               tagController.save("Sochi");
//                System.out.println(                tagController.getAll()); //getAll
//                tagController.deleteById(3l); //deleteByID
//                System.out.println(tagController.getById(2l));
//                System.out.println(                tagController.getAll());
//                Tag t = tagController.getById(2l); //getById
//                                t.setName("London");
//                                tagController.update(t.getId(),t.getName()); //update
//                System.out.println(tagController.getById(2l));
////
////
//       final PostController postController = new PostController();
//               List<Tag> tags = new ArrayList<>();
//                tags.add(tagController.getById(20l));
//          //      tags.add(new Tag(0l,"Article"));
//
//            postController.save("News!", tags,PostStatus.ACTIVE);//save
//          System.out.println(postController.getAll()); //getall
//          postController.deleteById(11l                ); //geleteById
//                //update
//                Post post = postController.getById(31l);
//                System.out.println("31"+postController.getById(31l)); //getById
//                post.setStatus(PostStatus.DELETED);
//                postController.update(post.getId(),post.getContent(),post.getTags(),post.getStatus());
//                System.out.println("31"+postController.getById(31l)); //getById
//          System.out.println(postController.getAll());
//               System.out.println(postController.getById(30l)); //getById
//
//                final WriterController writerController = new WriterController();
//
//                List<Post> list = new ArrayList<>();
//                //list.add(postController.getById(11l));
//                list.add(postController.getById(12l));
//                System.out.println(list);
//
//                writerController.save("Alina", list);
//                System.out.println("!"+writerController.getById(30l));
//            System.out.println("!"+writerController.getAll());
//            writerController.deleteById(1l                );
//           System.out.println(writerController.getAll());
//
//                System.out.println(writerController.getById(5l));
//
//                Writer w = writerController.getById(5l);
//
//                writerController.update(w.getId(),"Marina",list);
//                w=writerController.getById(5l);
//                System.out.println(w);




            }
}
