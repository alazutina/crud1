package com.anna.crud.view;
import com.anna.crud.controller.PostController;
import com.anna.crud.controller.TagController;
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

    public PostView(String name) {
        this.name = name;
    }

    public PostView() {
    }

    final private PostController postController = new PostController();
    final private  Scanner sc = new Scanner(System.in);
    final private  String ERROR_MESS = "неверный ввод";

    public void startPosts() {
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
        }
        while (!end);
    }


    private void printMenu() {
        System.out.println("Меню постов:" +
                "\n1 - добавить пост;" +
                "\n2 - удалить пост из списка;" +
                "\n3 - изменить пост из списка;" +
                "\n4 - список всех постов;" +
                "\n5 - введите id of Post;" +
                "\n6 - завершение работы с постами");
    }

    private void printAllPosts() {
        for (Post p : postController.getAll()) {
            System.out.println(p);
        }  //getAll

    }

    private void addNewPost() {

        Post p = new Post();
        String content;
        List<Tag> tags;
        PostStatus status = PostStatus.ACTIVE;
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
                if (str.equals("ACTIVE")) status = PostStatus.ACTIVE;
                if (str.equals("DELETED")) status = PostStatus.DELETED;
                break;
            } catch (Exception e) {
                System.out.println("что-то пошло не так с вводом.");
            }
        }

        p.setPostStatus(status);
        p.setContent(content);

        TagView t = new TagView();
        tags = t.returnPostTags();

        postController.save(content,tags,status);
    }

    private void deletePost() {

        System.out.print("Введите id of Post, который удалить: ");
        Long i;

        while (true) {
            try {
                i = sc.nextLong();
                break;
            } catch (Exception e) {
                System.out.println(ERROR_MESS);
            }
        }
        postController.deleteById(i);
    }

    private void updatePost() {
        System.out.print("Введите id изменяемого Post: ");
        Long i;
        while (true) {
            try {
                i = sc.nextLong();
                break;
            } catch (Exception e) {
                System.out.println(ERROR_MESS);
            }
        }
        List<Tag> tags;
        PostStatus status = PostStatus.ACTIVE;
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
                if (str.equals("ACTIVE")) status = PostStatus.ACTIVE;
                if (str.equals("DELETED")) status = PostStatus.DELETED;
                break;
            } catch (Exception e) {
                System.out.println("что-то пошло не так с вводом.");
            }
        }
        TagView t = new TagView();
        tags = t.returnPostTags();
        postController.update(i,c,tags,status);
    }

    private void postGetById() {
        Long i;
        boolean flag = true;
            System.out.print("Введите id of Post: ");
            while (true) {
                try {
                    i = sc.nextLong();
                    break;
                } catch (Exception e) {
                    System.out.println(ERROR_MESS);
                }
            }
            System.out.println(postController.getById(i));
        }

    public List<Post> returnWriterPosts(){
        Long i;
        boolean flag = true;
        List<Post> writerPosts = new ArrayList<>();
        System.out.print("All of Posts:");
        printAllPosts();
        do {
        System.out.print("Введите id of Post для Writer: ");
        while (true) {
            try {
                i = sc.nextLong();
                break;
            } catch (Exception e) {
                System.out.println(ERROR_MESS);
            }
        }

        writerPosts.add(postController.getById(i));
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
        return writerPosts;
    }
    }

