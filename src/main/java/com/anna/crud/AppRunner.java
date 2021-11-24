package com.anna.crud;

        import com.anna.crud.model.*;
        import com.anna.crud.model.Post;
        import com.anna.crud.repository.TagRepository;
        import com.anna.crud.repository.PostRepository;
        import com.anna.crud.repository.WriterRepository;
        import com.anna.crud.repository.gson.GsonPostRepositoryImpl;
        import com.anna.crud.repository.gson.GsonTagRepositoryImpl;
        import com.anna.crud.repository.gson.GsonWriterRepositoryImpl;
        import com.anna.crud.view.WriterView;
        import com.anna.crud.view.MainView;


public class AppRunner {

            public static void main(String[] args) {
            MainView mainView = new MainView();
            mainView.start();
        }

}
