package com.example.aquawalkers.controllers;

import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.repository.ShoeRepository;
import com.example.aquawalkers.service.ShoeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ShoesRESTController {

    @Autowired
    private ShoeService shoeService ;
    @Autowired
    private ShoeRepository shoeRepository;

   /*@GetMapping("/zapatillas")
   public ResponseEntity <List<Shoe>> getShoes(Integer from, Integer to, String marca,Float precio){
       List<Shoe> shoes =shoeService.findAll(from,to,marca,precio);
       return ResponseEntity.ok(shoes);
   }
   */
   @GetMapping("/zapatillas")
   public ResponseEntity<List<Shoe>> getShoes(@RequestParam(required = false) Integer from,
                                              @RequestParam(required = false) Integer to,
                                              @RequestParam(required = false) String marca,
                                              @RequestParam(required = false) Float precio) {
       try {
           List<Shoe> shoes = shoeService.findAll(from, to, marca, precio);
           return ResponseEntity.ok(shoes);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
   }

    /*@PostMapping("/zapatilla")
    public ResponseEntity<Shoe> saveShoe(@RequestBody Shoe shoe, MultipartFile img) throws SQLException, ShoeNotFoundException, IOException {
        Shoe savedShoe = this.shoeService.save(shoe, img);
        return new ResponseEntity<>(savedShoe, HttpStatus.CREATED);
    }
    */
    @PostMapping("/zapatilla")
    public ResponseEntity<Shoe> addShoe(@Valid Shoe shoe, @RequestParam("image") MultipartFile image) {
        try {
            Shoe savedShoe = shoeService.save(shoe,image);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedShoe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping(path="/zapatilla/{id}")
    public ResponseEntity<Void> deleteShoe(@PathVariable("id") Long id){
        shoeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(path="/zapatilla/{id}")
    public Shoe findShoe(@PathVariable("id") Long id) throws ShoeNotFoundException {
        return shoeService.findById(id);
    }

    @PutMapping(path="/zapatilla/{id}")
    public ResponseEntity<Shoe> modifyShoe(@RequestBody Shoe shoe,@PathVariable("id") Long id) throws ShoeNotFoundException {
        Shoe modifiedShoe = shoeService.modify(shoe, id);
        return new ResponseEntity<>(modifiedShoe, HttpStatus.OK);
    }

    @PostMapping("/zapatilla/{id}/image")
    public ResponseEntity<Void> insertImage(@PathVariable Shoe shoe, @RequestBody MultipartFile img) throws ShoeNotFoundException, SQLException, IOException {
        shoeService.insertImage(shoe,img);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

  /*  @PostMapping("/zapatilla/{id}/comment")
    public ResponseEntity<Void> addComment(@PathVariable Long id, @RequestBody String comentario) throws ShoeNotFoundException {
        Comment comment = new Comment(comentario);
        Optional<Shoe> shoe = shoeService.findById(id);
        shoeService.anadirComentario(shoe.get(), comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
*/

}
