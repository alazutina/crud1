package com.anna.crud.view;
import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Tag;
import com.anna.crud.model.Writer;
import com.anna.crud.repository.PostRepository;
import com.anna.crud.model.Post;
import com.anna.crud.repository.gson.GsonPostRepositoryImpl;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.TagRepository;
import com.anna.crud.repository.gson.GsonTagRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {

    String name; // current writer
    List<Post> posts = new ArrayList<>();

    public PostView(String name){
        this.name = name;

    }

    public PostView(){

    }

    PostRepository pr = new GsonPostRepositoryImpl();
    final  Scanner sc = new Scanner(System.in);
    final  String ERROR_MESS="неверный ввод";

    public List<Post> startPosts(){


        System.out.println("Список всех постов: ");

        printAllPosts();


            int num;
            boolean end = false;

            do {
                printMenu();

                while (true) {
                    try {
                        num = sc.nextInt();
                        break;
                    } catch (Exception e) {
                        System.out.println(ERROR_MESS);
                    }
                }

                if (num == 1) {

                    addNewPost();

                }
                if (num == 2) {

                    deletePost();


                }
                if (num == 3) {

                    updatePost();
                }

                    if (num == 4) {
                        printAllPosts();


                    }
                    if (num == 5) {
                        postGetById();


                    }
                    if (num == 6) {

                        end = true;
                    }

//                    if (!end) {
//                        System.out.println(ERROR_MESS);
//                    }

                }
                while (!end) ;

return posts;
            }







    private void  printMenu(){
        System.out.println("Меню постов:" +
                "\n1 - добавить пост для автора " + name+ ";"+
                "\n2 - удалить пост из списка;" +
                "\n3 - изменить пост из списка;" +
                "\n4 - список всех постов;" +
                "\n5 - ввести id of Post, которые добавить к автору;"  +
                "\n6 - завершение работы с постами");

    }

    private void printAllPosts(){
        for (Post p :pr.getAll()) {
            System.out.println(p);
        }  //getAll

    }

    private void addNewPost(){

    //    TagRepository tr = new GsonTagRepositoryImpl();
        Post p = new Post();
        String content;
        List<Tag> tags;
        PostStatus status=PostStatus.ACTIVE;

        System.out.print("Введите content: ");

        while (true) {
            try {
                content = sc.next();
                break;
            } catch (Exception e) {
                System.out.println("что-то пошло не так с вводом.");
            }
        }

        System.out.print("Введите PostStatus (ACTIVE, DELETED ): ");
        String str;

        while (true) {
            try {
                str = sc.next();
                if(str.equals("ACTIVE")) status = PostStatus.ACTIVE;
                if(str.equals("DELETED")) status = PostStatus.DELETED;
                break;
            } catch (Exception e) {
                System.out.println("что-то пошло не так с вводом.");
            }
        }

        p.setPostStatus(status);
        p.setContent(content);

        TagView t = new TagView();
        tags = t.startTags();

        p.setTags(tags);


        pr.save(p);
    }

    private void deletePost(){

        System.out.print("Введите id of Post, который удалить: ");
        Long i ;

        while (true) {
            try {
                i = sc.nextLong();
                break;
            } catch (Exception e) {
                System.out.println(ERROR_MESS);
            }
        }
        pr.deleteById(i);
    }

    private void updatePost(){
        System.out.print("Введите id изменяемого Post: ");
        Long i ;

        while (true) {
            try {
                i = sc.nextLong();
                break;
            } catch (Exception e) {
                System.out.println(ERROR_MESS);
            }
        }


        List<Tag> tags;
        PostStatus status=PostStatus.ACTIVE;


        System.out.print("Введите content of Post: ");
        String c;

        while (true) {
            try {
                c = sc.next();
                break;
            } catch (Exception e) {
                System.out.println(ERROR_MESS);
            }
        }

        String str;

        System.out.print("Введите status of Post(ACTIVE, DELETED): ");

        while (true) {
            try {
                str = sc.next();
                if(str.equals("ACTIVE")) status = PostStatus.ACTIVE;
                if(str.equals("DELETED")) status = PostStatus.DELETED;
                break;
            } catch (Exception e) {
                System.out.println("что-то пошло не так с вводом.");
            }
        }

        TagView t = new TagView();
        tags = t.startTags();

        Post updatePost = new Post();
        updatePost.setTags(tags);
        updatePost.setPostStatus(status);
        updatePost.setContent(c);
        updatePost.setId(i);

        pr.update(updatePost);

    }

    private void postGetById(){
        Long i;
        boolean flag = true;


        do {

            System.out.print("Введите id of Post: ");


            while (true) {
                try {
                    i = sc.nextLong();
                    break;
                } catch (Exception e) {
                    System.out.println(ERROR_MESS);
                }
            }

            posts.add(pr.getById(i));
            //System.out.println(tr.getById(i));
            System.out.print("Еще добавить(1 - да, 0 - нет)?: ");
            while (true) {
                try {
                    i = sc.nextLong();
                    break;
                } catch (Exception e) {
                    System.out.println(ERROR_MESS);
                }
            }
            if (i==0) flag = false;


        } while (!flag==false);


    }



}
