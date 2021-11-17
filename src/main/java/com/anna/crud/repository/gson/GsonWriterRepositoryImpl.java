package com.anna.crud.repository.gson;
import com.anna.crud.model.Tag;
import com.anna.crud.model.Writer;
import com.anna.crud.repository.WriterRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GsonWriterRepositoryImpl implements WriterRepository {


    private final static String WRITER_FILE_PATH = "/home/anya/IdeaProjects/anna/src/main/resources/writer.json";

    @Override
    public void deleteById(Long id) {
        List<Writer> currentWriters = getAll();
            currentWriters.removeIf(t -> t.getId().equals(id));
            writeWritersToJsonFile(currentWriters);
           }

    @Override
    public Writer save(Writer w) {
        List<Writer> currentWriters = getAll();
        Long newMaxId = getIdNext(currentWriters);
        w.setId(newMaxId);

        currentWriters.add(w);
        writeWritersToJsonFile(currentWriters);
        return w;
    }

    @Override
    public Writer update(Writer w) {
        List<Writer> currentWriters = getAll();

        currentWriters.forEach(t -> {
            if (t.getId().equals(w.getId())) {
                t.setName(w.getName());
                t.setPosts(w.getPosts());
            }
        });
        writeWritersToJsonFile(currentWriters);

        return w;
    }

    @Override
    public List<Writer> getAll() {
        return getAllWritersInternal();
    }

    @Override
    public Writer getById(Long id) {

            return getAll().stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
            }


    private Long getIdNext(List<Writer> currentWriters){
        Writer maxIdWriter = currentWriters.stream().max(Comparator.comparing(Writer::getId)).orElse(null);
        return maxIdWriter == null ? 1L : maxIdWriter.getId() + 1;
    }


    void writeWritersToJsonFile(List<Writer> writers) {
        try (FileWriter fw = new FileWriter(WRITER_FILE_PATH);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String jsonWriters = new Gson().toJson(writers);
            bw.write(jsonWriters);

        } catch (IOException e) {
            System.out.println(e);
        }

    }


    private List<Writer> getAllWritersInternal() {
        List <Writer> listWriter = new ArrayList<>();
        File f = new File(WRITER_FILE_PATH);
        if (f.exists()) {
            try (FileReader fr = new FileReader(WRITER_FILE_PATH);
                 BufferedReader br = new BufferedReader(fr)) {
                String line;
                while ((line = br.readLine()) != null) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    Writer[] w = new GsonBuilder().create().fromJson(line, Writer[].class);
                    Collections.addAll(listWriter, w);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return listWriter;
    }

}
