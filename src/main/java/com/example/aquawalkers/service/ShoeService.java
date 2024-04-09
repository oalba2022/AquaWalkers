package com.example.aquawalkers.service;

import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
//import com.example.aquawalkers.repositories.ShoesRepository;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.CommentRepository;
import com.example.aquawalkers.repository.ShoeRepository;
import com.example.aquawalkers.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
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
    @Autowired
    private CommentService commentService;

    public Optional<Shoe> findById(long id) throws ShoeNotFoundException{
        if(!this.exist(id)) {
            throw new ShoeNotFoundException("No tenemos esa zapa");
        }
        return shoeRepository.findById(id);
    }

    public boolean exist(long id){
        return this.shoeRepository.existsById(id);

    }

    public List<Shoe> findAll() {
        return shoeRepository.findAll();
    }

    public Shoe save(@Valid Shoe shoe, MultipartFile imageField){
        if (imageField != null && !imageField.isEmpty()){
            String path = imageService.createImage(imageField);
            shoe.setImage(path);
        }
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

    public void anadirComentario(Shoe shoe, Comment comment){
        commentService.save(comment);
        shoe.getComentarios().add(comment);
        shoeRepository.save(shoe);
    }

    public Shoe modify(Shoe shoe, long id) throws ShoeNotFoundException{
        Shoe zapa = shoeRepository.findById(id).get();
        zapa.setNombre(shoe.getNombre());
        zapa.setMarca(shoe.getMarca());
        zapa.setDescripcion(shoe.getDescripcion());
        zapa.setStock(shoe.getStock());
        zapa.setTalla(shoe.getTalla());
        zapa.setPrecio(shoe.getPrecio());
        shoeRepository.save(zapa);
        return zapa;
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
