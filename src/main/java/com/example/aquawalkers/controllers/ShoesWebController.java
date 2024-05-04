package com.example.aquawalkers.controllers;

//import com.example.aquawalkers.exceptions.ShoeExceptionHandler;
//import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
//import com.example.aquawalkers.service.ImageService;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.CommentRepository;
import com.example.aquawalkers.repository.ShoeRepository;
import com.example.aquawalkers.service.CommentService;
import com.example.aquawalkers.service.FileService;
import com.example.aquawalkers.service.ShoeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.aquawalkers.service.UserService;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
public class ShoesWebController {

    @Autowired
    private ShoeService shoeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;


    @GetMapping("/zapatillas")
    public String showAllShoes(Model model, Integer from, Integer to, String marca, HttpServletRequest request){
        model.addAttribute("zapatillas", shoeService.findAll(from,to,marca));
                model.addAttribute("admin", request.isUserInRole("ADMIN"));
        return "allshoes";
    }

    @GetMapping("/zapatilla/{id}")
    public String showShoe(Model model, @PathVariable long id, HttpServletRequest request) {
        Shoe zapa = shoeService.findById(id);
        List<Comment> comentarios = zapa.getComentarios();
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        if(shoeService.exist(id)){
            model.addAttribute("zapatilla", zapa);

            //model.addAttribute("comentarios", zapa.getComentarios());

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
    public String newShoeProcess(Model model,@Valid Shoe shoe, MultipartFile file) throws SQLException, IOException {
        Shoe newShoe = shoeService.save(shoe, file);
        model.addAttribute("shoeId", newShoe.getId());
        return "redirect:/zapatilla/" + newShoe.getId();
    }

    @GetMapping("/deleteshoe/{id}")
    public String deleteShoe(Model model, @PathVariable long id){
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
    public String newComment(Model model, String comment, @PathVariable long id, HttpServletRequest request) {
        Shoe zapatilla = shoeService.findById(id);
        User user = this.userService.findByName(request.getUserPrincipal().getName()).get();
        Comment comentario = new Comment(comment, user);
        shoeService.anadirComentario(zapatilla, comentario,user);
        model.addAttribute(comentario.getText());
        return "redirect:/zapatilla/"+id;
   }
    @GetMapping("/deletecomment/{id}")
    public String deleteComment(Model model, @PathVariable long id, HttpServletRequest request){
        User user = this.userService.findByName(request.getUserPrincipal().getName()).get();
        commentService.delete(id, user);
        return "redirect:/zapatillas";
    }


    @GetMapping("/modifyshoe/{id}")
    public String modifyShoe(Model model, @PathVariable long id){
        Shoe zapatilla = shoeService.findById(id);
        model.addAttribute("zapatilla", zapatilla);
        return "modifyshoe";
    }

    @PostMapping("/modifyshoe/{id}")
    public String modifyShoePost(Model model, Shoe shoe, @PathVariable long id){
        Shoe newShoe = shoeService.modify(shoe, id);
        model.addAttribute("shoeId", newShoe);
        return "redirect:/zapatilla/"+newShoe.getId();
    }

    @GetMapping("/zapatilla/{id}/image")
    public void downloadImage(@PathVariable long id, HttpServletResponse response)  {
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

    @PostMapping("/uploadfile")
    public String uploadfile(MultipartFile filename) throws IOException {
        shoeService.uploadData(filename);
        return "greeting-page";
    }

}
