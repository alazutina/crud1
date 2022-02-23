package com.anna.crud.controller;

import com.anna.crud.repository.jdbc.JdbcTagRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import com.anna.crud.model.Tag;
import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;

public class TagControllerTestMock {
    @Mock
    JdbcTagRepositoryImpl tagRepository;

    TagController tagController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tagController = new TagController();
    }
    @Test
    public void getById() {
        ReflectionTestUtils.setField(tagController,"tagRepository",tagRepository);
        Tag t = new Tag();
        t.setName("news");
        t.setId(100l);
        Mockito.when(tagRepository.getById(100l)).thenReturn(t);
        assertEquals(t.toString(), tagController.getById(100l).toString());
    }

        @Test
    public void getAll(){
            ReflectionTestUtils.setField(tagController,"tagRepository",tagRepository);
            List<Tag> tags = new ArrayList<>();
            Tag tag = new Tag();
            tags.add(tag);

        Mockito.when(tagRepository.getAll()).thenReturn(tags);
        assertEquals(tags.toString(), tagController.getAll().toString());
    }


    @Test
    public void deleteById() {
        ReflectionTestUtils.setField(tagController,"tagRepository",tagRepository);
        doNothing().when(tagRepository).deleteById(1l);
        tagController.deleteById(1l);
        Mockito.verify(tagRepository,Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    public void save() {

        ReflectionTestUtils.setField(tagController, "tagRepository", tagRepository);
        Tag t = new Tag();
        t.setName("news");
        Mockito.when(tagRepository.save(Mockito.any(Tag.class))).thenReturn(t);
        assertEquals(t.toString(), tagController.save("news").toString());
    }

    @Test
    public void update() {
        ReflectionTestUtils.setField(tagController, "tagRepository", tagRepository);
        Tag t = new Tag();
        t.setName("news");
        Mockito.when(tagRepository.update(Mockito.any(Tag.class))).thenReturn(t);
        assertEquals(t.toString(), tagController.update(Mockito.anyLong(),"news").toString());
    }




    }





