package com.anna.crud.view;

import java.sql.SQLException;
import java.util.Scanner;

public class MainView {
    private final TagView tagView = new TagView();
    private final PostView postView = new PostView();
    private final WriterView writerView = new WriterView();
    private final Scanner sc = new Scanner(System.in);
    final private  String ERROR_MESS = "неверный ввод";

    public void start() throws ClassNotFoundException, SQLException {

        int num;
        boolean end  = false;

        do {
            System.out.println("Главное меню:"+"\n1 - работа с Writer"+
                    "\n2 - работа с Post"+
                    "\n3 - работа с Tag"+
                    "\n4 - завершить работу\n");
            while (true) {
                try {
                    num = sc.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println(ERROR_MESS);
                }
            }

            if (num == 1) {
               writerView.startViewWriters();
            }
            if (num == 2) {
              postView.startPosts();
            }
            if (num == 3) {
              tagView.startTags();
            }
            if (num == 4) {
                end = true;
            }
        }
        while (!end);
    }
}
