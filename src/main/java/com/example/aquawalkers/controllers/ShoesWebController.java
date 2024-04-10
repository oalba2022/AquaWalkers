package com.example.aquawalkers.controllers;

import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
//import com.example.aquawalkers.service.ImageService;
import com.example.aquawalkers.repository.CommentRepository;
import com.example.aquawalkers.repository.ShoeRepository;
import com.example.aquawalkers.service.CommentService;
import com.example.aquawalkers.service.ShoeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Controller
public class ShoesWebController {

    @Autowired
    private ShoeService shoeService;

    @Autowired
    private ShoeRepository shoeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;


    @GetMapping("/zapatillas")
    public String showAllShoes(Model model,Integer from, Integer to,String marca, Float precio){
        model.addAttribute("zapatillas", shoeService.findAll(from,to,marca,precio));
        return "allshoes";
    }

    @GetMapping("/zapatilla/{id}")
    public String showShoe(Model model, @PathVariable long id) throws ShoeNotFoundException {
        Shoe zapatilla = shoeService.findById(id);
        Shoe zapa = zapatilla;
        List<Comment> comentarios = zapa.getComentarios();
        if(shoeService.exist(id)){
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
    public String newShoeProcess(Model model,@Valid Shoe shoe, MultipartFile file) throws SQLException, IOException, ShoeNotFoundException {
       if (file.isEmpty()){
           System.out.println("holaholaholahola");
       }
        Shoe newShoe = shoeService.save(shoe, file);
        model.addAttribute("shoeId", newShoe.getId());
        return "redirect:/zapatilla/" + newShoe.getId();
    }

    @GetMapping("/deleteshoe/{id}")
    public String deleteShoe(Model model, @PathVariable long id) throws  ShoeNotFoundException{
       Shoe zapatilla = shoeService.findById(id);
        if(shoeService.exist(id)){
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
    public String newComment(Model model, String s, @PathVariable long id) throws ShoeNotFoundException{
        Optional<Shoe> zapatilla = shoeRepository.findById(id);
        Comment comment = new Comment(s);
        shoeService.anadirComentario(zapatilla.get(), s);
        model.addAttribute(comment);
        return "redirect:/zapatilla/"+id;
   }

    @GetMapping("/modifyshoe/{id}")
    public String modifyShoe(Model model, @PathVariable long id) throws ShoeNotFoundException{
        Shoe zapatilla = shoeService.findById(id);
        model.addAttribute("zapatilla", zapatilla);
        return "modifyshoe";
    }

    @PostMapping("/modifyshoe/{id}")
    public String modifyShoePost(Model model, Shoe shoe, @PathVariable long id) throws ShoeNotFoundException{
        Shoe newShoe = shoeService.modify(shoe, id);
        model.addAttribute("shoeId", newShoe);
        return "redirect:/zapatilla/"+newShoe.getId();
    }

    @GetMapping("/zapatilla/{id}/image")
    public void downloadImage(@PathVariable long id, HttpServletResponse response) throws ShoeNotFoundException {
            Shoe shoe = shoeService.findById(id);
            if (shoe == null || shoe.getImage() == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Blob imageBlob = shoe.getImage();
            try {
                response.setContentType("image/jpeg");
                response.setContentLength((int) imageBlob.length());

                StreamUtils.copy(imageBlob.getBinaryStream(), response.getOutputStream());
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

}
