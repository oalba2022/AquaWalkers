package com.example.aquawalkers.controllers;

import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.service.ImageService;
import com.example.aquawalkers.service.ShoeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
@Controller
public class ShoesWebController {

    @Autowired
    private ShoeService shoeService;

    @Autowired
    private ImageService imageService;


    @GetMapping("/zapatillas")
    public String showAllShoes(Model model){
        model.addAttribute("zapatillas", shoeService.findAll());
        return "allshoes";
    }

    @GetMapping("/zapatilla/{id}")
    public String showShoe(Model model, @PathVariable long id) throws ShoeNotFoundException {
        Optional<Shoe> zapatilla = shoeService.findById(id);
        Shoe zapa = zapatilla.get();
        ArrayList<Comment> comentarios = zapa.getComentarios();
        if(zapatilla.isPresent()){
            model.addAttribute("zapatilla", zapa);
            model.addAttribute("comentario", comentarios);

            return "shoe";
        }else{
            return "allshoes";
        }
    }

    @GetMapping("/newshoe")
    public String newShoe(Model model){
        return "newshoe";
    }

    @PostMapping("/newshoe")
    public String newShoeProcess(Model model,@Valid Shoe shoe,  MultipartFile imageField){
        Shoe newShoe = shoeService.save(shoe, imageField);
        model.addAttribute("shoeId", newShoe.getId());
        return "redirect:/zapatilla/" + newShoe.getId();
    }

    @GetMapping("/deleteshoe/{id}")
    public String deleteShoe(Model model, @PathVariable long id) throws  ShoeNotFoundException{
        Optional<Shoe> zapatilla = shoeService.findById(id);
        if(zapatilla.isPresent()){
            shoeService.delete(id);
            return "deleteshoe";
        }
        return "deleteshoe";
    }

    @GetMapping("/zapatilla/{id}/escribirComentario")
    public String escribirComentario(Model model){
        return"escribirComentario";
    }
    @PostMapping("/zapatilla/{id}/escribirComentario")
    public String newComment(Model model, Comment comment, @PathVariable long id) throws ShoeNotFoundException{
        Optional<Shoe> zapatilla = shoeService.findById(id);
        shoeService.anadirComentario(zapatilla.get(), comment);
        return "redirect:/zapatilla/"+id;
   }

    @GetMapping("/modifyshoe/{id}")
    public String modifyShoe(Model model, @PathVariable long id) throws ShoeNotFoundException{
        Optional<Shoe> zapatilla = shoeService.findById(id);
        model.addAttribute("zapatilla", zapatilla.get());
        return "modifyshoe";
    }

    @PostMapping("/modifyshoe/{id}")
    public String modifyShoePost(Model model, Shoe shoe, @PathVariable long id) throws ShoeNotFoundException{
        Shoe newShoe = shoeService.modify(shoe, id);
        model.addAttribute("shoeId", newShoe);
        return "redirect:/zapatilla/"+newShoe.getId();
    }

    @GetMapping("/shoe/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException, ShoeNotFoundException {

        Optional<Shoe> op = shoeService.findById(id);

        if(op.isPresent()) {
            Shoe shoe = op.get();
            Resource poster = imageService.getImage(shoe.getImage());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg","image/png").body(poster);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found");
        }
    }

}
