package com.anna.crud.view;
import com.anna.crud.model.Post;
import com.anna.crud.model.PostStatus;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.PostRepository;
import com.anna.crud.repository.TagRepository;
import com.anna.crud.repository.gson.GsonPostRepositoryImpl;
import com.anna.crud.repository.gson.GsonTagRepositoryImpl;
import com.anna.crud.controller.TagController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TagView {
    private final TagController tagController = new TagController();
   private final Scanner sc = new Scanner(System.in);
   private final  String ERROR_MESS="неверный ввод";


    public void startTags(){


        System.out.println("Список всех тагов: ");

        printAllTags();


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

                addNewTag();

            }
            if (num == 2) {
                deleteTag();

            }
            if (num == 3) {
               updateTag();
            }

            if (num == 4) {
                printAllTags();


            }
            if (num == 5) {

               tagById();


            }
            if (num == 6) {

                end = true;
            }

        }
        while (!end) ;
    }

    private void  printMenu(){
        System.out.println("Меню для Tags:" +
                "\n1 - добавить новый Tag ;" +
                "\n2 - удалить Tag;" +
                "\n3 - изменить Tag;" +
                "\n4 - список всех Tags;" +
                "\n5 - ввести Tag by id;"  +
                "\n6 - завершение работы с Tags");

    }

    private void printAllTags(){
          List<Tag> tags = tagController.getAll();
                System.out.println(tags);
    }

    private void addNewTag(){

        String name;
        System.out.print("Введите name of Tag: ");

        while (true) {
            try {
                name = sc.next();
                break;
            } catch (Exception e) {
                System.out.println("что-то пошло не так с вводом.");
            }
        }
              tagController.save(name);
            }

    private void deleteTag(){
        System.out.print("Введите id of Tag, который удалить: ");
        Long i ;

        while (true) {
            try {
                i = sc.nextLong();
                break;
            } catch (Exception e) {
                System.out.println(ERROR_MESS);
            }
        }
               tagController.deleteById(i);
            }

            private void updateTag(){

                    System.out.print("Введите id изменяемого Tag: ");
                Long i ;

                while (true) {
                    try {
                        i = sc.nextLong();
                        break;
                    } catch (Exception e) {
                        System.out.println(ERROR_MESS);
                    }
                }

                System.out.print("Введите текст Tag: ");
                String s;

                while (true) {
                    try {
                        s = sc.nextLine();
                        break;
                    } catch (Exception e) {
                        System.out.println(ERROR_MESS);
                    }
                }
               tagController.update(i,s);
            }

            private void tagById() {
                Long i;
                boolean flag = true;


                //     do {
                System.out.print("Введите id of Tag: ");

                while (true) {
                    try {
                        i = sc.nextLong();
                        break;
                    } catch (Exception e) {
                        System.out.println(ERROR_MESS);
                    }
                }

                System.out.println(tagController.getById(i));
            }

                public List<Tag> returnPostTags(){

                    Long i;
                    boolean flag = true;
                    List<Tag> writerPosts = new ArrayList<>();

                    System.out.print("All of Tags:");
                    printAllTags();

                    do {

                        System.out.print("Введите id of Tags для Post: ");


                        while (true) {
                            try {
                                i = sc.nextLong();
                                break;
                            } catch (Exception e) {
                                System.out.println(ERROR_MESS);
                            }
                        }

                        writerPosts.add(tagController.getById(i));

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



