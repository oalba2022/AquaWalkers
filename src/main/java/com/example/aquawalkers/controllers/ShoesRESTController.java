package com.example.aquawalkers.controllers;

import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.service.ShoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ShoesRESTController {

    @Autowired
    private ShoeService shoeService;

   @GetMapping("/zapatillas")
   public ResponseEntity<List<Shoe>> getShoes(){
       List<Shoe> shoes = this.shoeService.findAll();
       return new ResponseEntity<>(shoes, HttpStatus.OK);
   }

    @PostMapping("/zapatilla")
    public ResponseEntity<Shoe> saveShoe(@RequestBody Shoe shoe, MultipartFile img){
        Shoe savedShoe = this.shoeService.save(shoe, img);
        return new ResponseEntity<>(savedShoe, HttpStatus.CREATED);
    }

    @DeleteMapping(path="/zapatilla/{id}")
    public ResponseEntity<Void> deleteShoe(@PathVariable("id") Long id){
        shoeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(path="/zapatilla/{id}")
    public Optional<Shoe> findShoe(@PathVariable("id") Long id) throws ShoeNotFoundException {
        return shoeService.findById(id);
    }

    @PutMapping(path="/zapatilla/{id}")
    public ResponseEntity<Shoe> modifyShoe(@RequestBody Shoe shoe,@PathVariable("id") Long id) throws ShoeNotFoundException {
        Shoe modifiedShoe = shoeService.modify(shoe, id);
        return new ResponseEntity<>(modifiedShoe, HttpStatus.OK);
    }

    @PostMapping("/zapatilla/{id}/image")
    public ResponseEntity<Void> insertImage(@PathVariable Long id, @RequestBody MultipartFile img) throws ShoeNotFoundException {
        shoeService.insertImage(id,img);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/zapatilla/{id}/comment")
    public ResponseEntity<Void> addComment(@PathVariable Long id, @RequestBody String comentario) throws ShoeNotFoundException {
        Comment comment = new Comment(comentario);
        Optional<Shoe> shoe = shoeService.findById(id);
        shoeService.anadirComentario(shoe.get(), comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
