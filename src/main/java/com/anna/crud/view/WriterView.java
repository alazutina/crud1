package com.anna.crud.view;

import com.anna.crud.controller.TagController;
import com.anna.crud.controller.WriterController;
import com.anna.crud.model.Post;
import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.WriterRepository;
import com.anna.crud.repository.gson.GsonWriterRepositoryImpl;
import com.anna.crud.model.Writer;
import com.anna.crud.view.PostView;

import java.util.List;
import java.util.Scanner;


public class WriterView {


    private final WriterController writerController = new WriterController();
    private final  Scanner sc = new Scanner(System.in);
    private final  String ERROR_MESS="неверный ввод";

    public  void startViewWriters() {

        System.out.println("Список авторов: ");
        printAllWriters();
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
                addNewWriter();
            }
            if (num == 2) {
                deleteWriter();
            }
            if (num == 3) {
                updateWriter();
            }
                if (num == 4) {
                    printAllWriters();
                }
                if (num == 5) {
                    getWriterById();
                }
                if (num == 6) {
                    end = true;
                }
            }
            while (!end) ;
        }



    private void printAllWriters(){
        for (Writer w :writerController.getAll()) {
            System.out.println(w);
        }
    }

    private void  printMenu(){
        System.out.println("Меню:" +
                "\n1 - добавить автора;" +
                "\n2 - удалить автора;" +
                "\n3 - изменить автора;" +
                "\n4 - список всех авторов;" +
                "\n5 - информация об авторе по id;"  +
                "\n6 - завершение работы");
    }

    private void addNewWriter() {
        String name;
        List<Post> posts;

        System.out.print("Введите имя автора: ");

        while (true) {
            try {
                name = sc.next();
                break;
            } catch (Exception e) {
                System.out.println("что-то пошло не так с вводом.");
            }
        }

        PostView pv = new PostView(name);
        writerController.save(name, pv.returnWriterPosts());
    }

            private void deleteWriter(){

                System.out.print("Введите id of Writer, которого удалить: ");
                Long i ;

                while (true) {
                    try {
                        i = sc.nextLong();
                        break;
                    } catch (Exception e) {
                        System.out.println(ERROR_MESS);
                    }
                }
                writerController.deleteById(i);
                    }


                    private void updateWriter(){
                        System.out.print("Введите id изменяемого Writer: ");
                        Long i ;

                        while (true) {
                            try {
                                i = sc.nextLong();
                                break;
                            } catch (Exception e) {
                                System.out.println(ERROR_MESS);
                            }
                        }
                        String name;
                        List<Post> posts;

                        System.out.print("Введите имя автора: ");

                        while (true) {
                            try {
                                name = sc.next();
                                break;
                            } catch (Exception e) {
                                System.out.println("что-то пошло не так с вводом.");
                            }
                        }

                        PostView pv = new PostView(name);

                        writerController.update(i,name,pv.returnWriterPosts());

                    }

    private void  getWriterById(){

        System.out.print("Введите id of Writer: ");
        Long i ;

        while (true) {
            try {
                i = sc.nextLong();
                break;
            } catch (Exception e) {
                System.out.println(ERROR_MESS);
            }
        }

        System.out.println(writerController.getById(i));

    }


}
