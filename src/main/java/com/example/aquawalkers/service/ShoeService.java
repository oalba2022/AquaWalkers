package com.example.aquawalkers.service;

import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
//import com.example.aquawalkers.repositories.ShoesRepository;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.ShoeRepository;
import com.example.aquawalkers.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Component
public class ShoeService {

    @Autowired
    private ImageService imageService;
    @Autowired
    private ShoeRepository shoeRepository;

    private AtomicLong nextId = new AtomicLong(1L);
    // private ConcurrentHashMap<Long, Shoe> shoes = new ConcurrentHashMap<>();

    public Optional<Shoe> findById(long id) throws ShoeNotFoundException{
        if(!shoeRepository.existsById(id)) {
            throw new ShoeNotFoundException("No tenemos esa zapa");
        }
        return shoeRepository.findById(id);
    }

    public boolean exist(long id){
        return shoeRepository.existsById(id);
    }

    public List<Shoe> findAll() {
        return shoeRepository.findAll();
    }

    public Shoe save(@Valid Shoe shoe, MultipartFile imageField){
        if (imageField != null && !imageField.isEmpty()){
            String path = imageService.createImage(imageField);
            shoe.setImage(path);
        }

        //long id = nextId.getAndIncrement();
        //shoe.setId(id);
        //shoes.put(id, shoe);
        shoeRepository.save(shoe);
        return shoe;
    } //añdadido bbdd

    public boolean delete(long id){
        if (this.exist(id)){
            shoeRepository.deleteById(id);
            return true;
        }
        return false;
    } //añadido bbdd

    /*public void anadirComentario(Shoe shoe, Comment comment) throws ShoeNotFoundException{
        shoe.addComment(comment);
    }
*/
    public Shoe modify(Shoe shoe, long id) throws ShoeNotFoundException{
        long newId = id;
        this.delete(id);
        shoe.setId(newId);
        //shoes.put(newId, shoe);
        shoeRepository.save(shoe);
        return shoe;
    }

    public void insertImage(Long id, MultipartFile img) throws ShoeNotFoundException {
        Optional<Shoe> shoe = this.findById(id);
        String path = imageService.createImage(img);
        shoe.get().setImage(path);
    }
    public void addUser(User u,Long id)throws ShoeNotFoundException{
        Shoe shoe=this.findById(id).get();
        shoe.adduser(u);
    }


}
