package com.anna.crud.controller;//package com.anna.crud.view.;
import java.util.List;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.TagRepository;
import com.anna.crud.repository.jdbc.JdbcTagRepositoryImpl;

public class TagController {



    private final TagRepository tagRepository;

    public TagController() {
        this.tagRepository = new JdbcTagRepositoryImpl();
    }

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag save(String name){
        Tag tag = new Tag();
        tag.setName(name);
        Tag r = tagRepository.save(tag);
        return r;
    }




    public Tag update(Long id, String name) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        return tagRepository.update(tag);
    }

    public Tag getById(Long id){
        return tagRepository.getById(id);
    }

    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }

    public List<Tag> getAll(){

        return tagRepository.getAll();
    }

}


