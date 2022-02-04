package com.anna.crud.view;
import com.anna.crud.controller.TagController;
import com.anna.crud.controller.WriterController;
import com.anna.crud.model.Post;
import com.anna.crud.model.Writer;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class WriterView {

    private final PostView postView = new PostView();

    private final WriterController writerController = new WriterController();
    private final  Scanner sc = new Scanner(System.in);
    private final  String ERROR_MESS="неверный ввод";

    public  void startViewWriters()  throws ClassNotFoundException, SQLException{

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
//            if (num == 6) {
//                System.out.println("ВВедите id автора:");
//                Long id_writer ;
//                while (true) {
//                    try {
//                        id_writer = sc.nextLong();
//                        break;
//                    } catch (Exception e) {
//                        System.out.println(ERROR_MESS);
//                    }
//                }
//                postView.startPosts(id_writer);
//            }
                if (num == 6) {
                    end = true;
                }
            }
            while (!end) ;
        }



    private void printAllWriters()   throws ClassNotFoundException, SQLException {
        for (Writer w :writerController.getAll()) {
            System.out.println(w);
        }
    }

    private void  printMenu() throws ClassNotFoundException, SQLException{
        System.out.println("Меню:" +
                "\n1 - добавить автора;" +
                "\n2 - удалить автора;" +
                "\n3 - изменить автора;" +
                "\n4 - список всех авторов;" +
                "\n5 - информация об авторе по id;"  +
     //           "\n6 - добавить пост автору;"  +
                "\n6 - завершение работы");
    }

    private void addNewWriter() throws ClassNotFoundException, SQLException{
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

            private void deleteWriter() throws ClassNotFoundException, SQLException{

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


                    private void updateWriter() throws ClassNotFoundException, SQLException{
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

    private void  getWriterById() throws ClassNotFoundException, SQLException{

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
