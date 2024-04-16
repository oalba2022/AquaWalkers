package com.example.aquawalkers.service;

//import com.example.aquawalkers.exceptions.ShoeExceptionHandler;
//import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
//import com.example.aquawalkers.repositories.ShoesRepository;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.CommentRepository;
import com.example.aquawalkers.repository.ShoeRepository;
import jakarta.persistence.TypedQuery;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private UserService userService;
    @Autowired
    private ShoeRepository shoeRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private FileService fileService;

    @Autowired
    private CommentRepository commentRepository;
    /*@Autowired
    private ShoeExceptionHandler shoeExceptionHandler;*/
    private static final Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "files");
    public Shoe findById(long id){
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
    public List<Shoe> findAll(Integer from, Integer to, String marca) {
        String sentence = new String();
        TypedQuery<Shoe> query = null;
        if(from == null && to == null && marca == null){
            sentence = "SELECT s FROM Shoe s";
            query = entityManager.createQuery(sentence, Shoe.class);
        } else if((from != null && to != null) && marca.isEmpty()) {
            sentence ="SELECT s FROM Shoe s WHERE s.precio BETWEEN :from AND :to";
            query = entityManager.createQuery(sentence, Shoe.class);
            query.setParameter("from", from);
            query.setParameter("to", to);

        } else if((from != null && to != null) && !marca.isEmpty()) {
            sentence ="SELECT s FROM Shoe s WHERE (s.precio BETWEEN :from AND :to) AND s.marca LIKE CONCAT('%', :marca, '%')";
            query = entityManager.createQuery(sentence, Shoe.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            query.setParameter("marca", marca);
        }
        return query.getResultList();
    }



 

    public Shoe save(@Valid Shoe shoe, MultipartFile imageField) throws SQLException, IOException {

        insertImage(shoe,imageField);
        shoeRepository.save(shoe);
        return shoe;
    }

    public boolean delete(long id){
        if (this.exist(id)){
            shoeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void anadirComentario(Shoe shoe, Comment comment){
        comment.setText(this.sanitize(comment.getText()));
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
    public void addUser(Long id) throws SQLException, IOException {
        Shoe shoe=this.findById(id);
        User inv = userService.findById(1L).get();
        shoe.getUsuarios().add(inv);
        inv.addCarrito(shoe);
        this.save1(shoe);
    }
    public List<Shoe> findAll1() {
        return shoeRepository.findAll();
    }
    public Shoe save1(@Valid Shoe shoe) throws SQLException, IOException {
        shoeRepository.save(shoe);
        return shoe;
    }
    public String sanitize(String string){
        String clean = Jsoup.clean(string, Safelist.basicWithImages());
        return clean;
    }
   public void uploadData(MultipartFile file) throws IOException {
        fileService.saveFile(String.valueOf(FILES_FOLDER), file, file.getOriginalFilename());
    }



}
