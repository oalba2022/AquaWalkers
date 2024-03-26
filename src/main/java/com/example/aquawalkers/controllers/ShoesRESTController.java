package com.example.aquawalkers.controllers;

import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.service.ShoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ShoesRESTController {

    @Autowired
    private ShoeService shoeService;

   @GetMapping("/zapatillas")
    public List<Shoe> getShoes(){
        return this.shoeService.findAll();
    }

    @PostMapping("/zapatilla")
    public Shoe saveShoe(@RequestBody Shoe shoe, MultipartFile img){
        return this.shoeService.save(shoe, img);
    }

    @DeleteMapping(path="/zapatilla/{id}")
    public void deleteShoe(@PathVariable("id") Long id){
        shoeService.delete(id);
    }

    @GetMapping(path="/zapatilla/{id}")
    public Optional<Shoe> findShoe(@PathVariable("id") Long id) throws ShoeNotFoundException {
        return shoeService.findById(id);
    }

    @PutMapping(path="/zapatilla/{id}")
    public Shoe modifyShoe(@RequestBody Shoe shoe,@PathVariable("id") Long id) throws ShoeNotFoundException {
        return shoeService.modify(shoe, id);
    }

    @PostMapping("/zapatilla/{id}/image")
    public void insertImage(@PathVariable Long id, @RequestBody MultipartFile img) throws ShoeNotFoundException {
        shoeService.insertImage(id,img);
    }

    @PostMapping("/zapatilla/{id}/comment")
    public void addComment(@PathVariable Long id, @RequestBody String comentario) throws ShoeNotFoundException {
       Comment comment = new Comment(comentario);
       Optional<Shoe> shoe = shoeService.findById(id);
       shoeService.anadirComentario(shoe.get(), comment);
    }



}
