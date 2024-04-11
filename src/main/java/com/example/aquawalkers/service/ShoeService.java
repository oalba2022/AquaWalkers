package com.example.aquawalkers.service;

//import com.example.aquawalkers.exceptions.ShoeExceptionHandler;
//import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
//import com.example.aquawalkers.repositories.ShoesRepository;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.CommentRepository;
import com.example.aquawalkers.repository.ShoeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;

import javax.sql.rowset.serial.SerialBlob;

@Service
@Component
public class ShoeService {

    @Autowired
    private ShoeRepository shoeRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;
    /*@Autowired
    private ShoeExceptionHandler shoeExceptionHandler;*/
    public Shoe findById(long id){
        if(!shoeRepository.existsById(id)){

        }
        return shoeRepository.findById(id).orElse(null);
    }

    public boolean exist(long id){
        return this.shoeRepository.existsById(id);

    }

    /*public List<Shoe> findAll() {
        return shoeRepository.findAll();
    }*/
    private boolean isNotEmptyFieldFloat(Float f){return f != null && f>0;}
    private boolean isNotEmptyField(String field) {
        return field != null && !field.isEmpty();
    }
    public List<Shoe> findAll(Integer from, Integer to, String marca,Float precio) {
        String query = "SELECT * FROM shoe";
        if( (from != null && to != null) || isNotEmptyFieldFloat(precio)) {
            query+=" WHERE";
        }
        if(from != null && to != null) {
            query+=" precio BETWEEN "+from+" AND "+to;
        }
        if( from != null && to != null && isNotEmptyField(marca)) {
            query+=" AND";
        }
        if(isNotEmptyField(marca)) {
            query+=" marca='"+marca+"'";
        }

        return (List<Shoe>) entityManager.createNativeQuery(query, Shoe.class).getResultList();
    }



 

    public Shoe save(@Valid Shoe shoe, MultipartFile imageField) {

        insertImage(shoe,imageField);
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
        commentService.save(comment, shoe);
        shoe.addComment(comment);
        shoeRepository.save(shoe);
    }


    public Shoe modify(Shoe shoe, long id){
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

    public MultipartFile insertImage(Shoe shoe, MultipartFile image)throws SQLException, IOException {
        byte[] imageData = image.getBytes();
        Blob imageBlob = new SerialBlob(imageData);
        shoe.setImage(imageBlob);
        shoe.setDate(LocalDate.now());
        return image;
    }
    public void addUser(User u,Long id){
        Shoe shoe=this.findById(id);
        shoe.adduser(u);
    }
    public List<Shoe> findAll1() {
        return shoeRepository.findAll();
    }


}
