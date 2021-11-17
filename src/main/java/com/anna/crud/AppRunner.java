
package com.anna.crud;

        import com.anna.crud.model.*;
        import com.anna.crud.model.Post;
        import com.anna.crud.repository.TagRepository;
        import com.anna.crud.repository.PostRepository;
        import com.anna.crud.repository.WriterRepository;
        import com.anna.crud.repository.gson.GsonPostRepositoryImpl;
        import com.anna.crud.repository.gson.GsonTagRepositoryImpl;
        import com.anna.crud.repository.gson.GsonWriterRepositoryImpl;


public class AppRunner {

    public static void main(String[] args) {
        TagRepository tr = new GsonTagRepositoryImpl();
        PostRepository pr = new GsonPostRepositoryImpl();
        WriterRepository wr = new GsonWriterRepositoryImpl();

        Tag t1 = new Tag();
        Tag t2 = new Tag();

        t1.setName("test");
        t1 = tr.save(t1);  // добавление

        t2.setName("test");
        t2 = tr.save(t2);  // добавление

        System.out.print("getById, id = " + 5);

        if (tr.getById(5L) != null) {
            System.out.println(" " + tr.getById(5L));
        } else {
            System.out.println(" нет такого");
        }


        Tag updateTag = new Tag(6L, "test666");  //update
        tr.update(updateTag);

        tr.deleteById(1L);  /// deleteById


        for (Tag t : tr.getAll()) {
            System.out.println(t);
        }  //getAll


        Post p1 = new Post();


        p1.setContent("news");
        p1.setPostStatus(PostStatus.ACTIVE);
        p1.setTags(tr.getAll());

        pr.save(p1);

        System.out.print("getById, id = " + 5);

        if (pr.getById(5L) != null) {
            System.out.println(" " + pr.getById(5L));
        } else {
            System.out.println(" нет такого");
        }

        Post p2 = new Post();

        p2.setContent("file");
        p2.setPostStatus(PostStatus.DELETED);
        p2.setTags(tr.getAll());

        pr.save(p2);

        System.out.println("------------------");
        for (Post p :pr.getAll()) {
            System.out.println(p);
        }  //getAll



        p2.setPostStatus(PostStatus.ACTIVE);
        pr.update(p2);

        System.out.println("------------------");
        for (Post p :pr.getAll()) {
            System.out.println(p);
        }  //getAll


        pr.deleteById(2L);


        System.out.println("------------------");
        for (Post p :pr.getAll()) {
            System.out.println(p);
        }  //getAll


        Writer w1 = new Writer();


        w1.setName("A.Pushkin");
        w1.setPosts(pr.getAll());

wr.save(w1);

        System.out.print("getById, id = " + 5);

        if (wr.getById(5L) != null) {
            System.out.println(" " + wr.getById(5L));
        } else {
            System.out.println(" нет такого");
        }

        Writer w2 = new Writer();

        w2.setName("L.Tolstoy");
        w2.setPosts(pr.getAll());

        wr.save(w2);

        System.out.println("++++++++++++++");
        for (Writer w :wr.getAll()) {
            System.out.println(w);
        }  //getAll

        w2.setName("A.Tolstoy");
        wr.update(w2);


        System.out.println("++++++++++++++");
        for (Writer w :wr.getAll()) {
            System.out.println(w);
        }  //getAll


        wr.deleteById(2L);


        System.out.println("++++++++++++++");
        for (Writer w :wr.getAll()) {
            System.out.println(w);
        }  //getAll

    }
}
