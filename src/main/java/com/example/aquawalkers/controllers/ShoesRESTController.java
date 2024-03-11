package com.example.aquawalkers.controllers;

import com.example.aquawalkers.exceptions.ShoeNotFoundException;
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

   @GetMapping("/")
    public List<Shoe> getShoes(){
        return this.shoeService.findAll();
    }

    @PostMapping("/")
    public Shoe saveShoe(@RequestBody Shoe shoe, MultipartFile img){
        return this.shoeService.save(shoe, img);
    }

    @DeleteMapping(path="/{id}")
    public void deleteShoe(@PathVariable("id") Long id){
        shoeService.delete(id);
    }

    @GetMapping(path="/{id}")
    public Optional<Shoe> findShoe(@PathVariable("id") Long id) throws ShoeNotFoundException {
        return shoeService.findById(id);
    }

    @PutMapping(path="/{id}")
    public Shoe modifyShoe(@RequestBody Shoe shoe,@PathVariable("id") Long id) throws ShoeNotFoundException {
        return shoeService.modify(shoe, id);
    }




}
