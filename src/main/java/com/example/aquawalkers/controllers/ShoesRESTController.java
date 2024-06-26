package com.example.aquawalkers.controllers;

import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.ShoeRepository;
import com.example.aquawalkers.service.ShoeService;
import com.example.aquawalkers.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ShoesRESTController {

    @Autowired
    private ShoeService shoeService ;
    @Autowired
    private UserService userService;

    @GetMapping("/zapatillas")
    public  ResponseEntity <List<Shoe>> getShoes() throws JsonProcessingException {
        List <Shoe> shoes=shoeService.findAll1();
        return ResponseEntity.ok(shoes);
    }

    @PostMapping("/zapatilla")
    public ResponseEntity<Shoe> saveShoe(@RequestBody Shoe shoe) throws SQLException, IOException {
        Shoe savedShoe = this.shoeService.save1(shoe);
        return new ResponseEntity<>(savedShoe, HttpStatus.CREATED);
    }

    @DeleteMapping(path="/zapatilla/{id}")
    public ResponseEntity<Void> deleteShoe(@PathVariable("id") Long id){
        shoeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(path="/zapatilla/{id}")
    public Shoe findShoe(@PathVariable("id") Long id) {
        Shoe shoe = shoeService.findById(id);
        shoe.setImage(null);
        return shoe;
    }

    @PutMapping(path="/zapatilla/{id}")
    public ResponseEntity<Shoe> modifyShoe(@RequestBody Shoe shoe,@PathVariable Long id)throws SQLException, IOException {
        Shoe modifiedShoe = shoeService.modify(shoe, id);
        return new ResponseEntity<>(modifiedShoe, HttpStatus.OK);
    }

    @PostMapping("/zapatilla/{id}/image")
    public ResponseEntity<Void> insertImage(@PathVariable Long id, @RequestBody MultipartFile img) throws SQLException, IOException {
        Shoe shoe = shoeService.findById(id);
        shoeService.save(shoe,img);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/zapatilla/{id}/comment")
    public ResponseEntity<Void> addComment(@PathVariable Long id, @RequestBody String string, HttpServletRequest request){
        Comment comment = new Comment();
        comment.setText(string);
        User user = this.userService.findByName(request.getUserPrincipal().getName()).get();
        Shoe shoe = shoeService.findById(id);
        shoeService.anadirComentario(shoe, comment, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
