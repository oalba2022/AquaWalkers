package com.example.aquawalkers.controllers;

//import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.service.ShoeService;
import com.example.aquawalkers.service.UserService;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private ShoeService shoeService;
    @GetMapping("/usercard/{id}")
    public String usercard (Model model, @PathVariable Long id){
        Optional<User> user = this.userService.findById(id);
        User u = user.get();
        model.addAttribute("user", u);
        return "usercard";
    }
    @PostMapping("/index")
    public String newUser (Model model, @Valid User user){
        User newUser = userService.save(user);
        model.addAttribute("userId", newUser.getId());
        return "redirect:/inicio";
    }
    @GetMapping("/carrito")
    public String carrito (Model model){
        User invitado = this.userService.findById(1L).get();
        List<Shoe> shop = invitado.getCarrito();
        float total = this.userService.precio();
        model.addAttribute("shop", shop);
        model.addAttribute("total", total);
        return "carrito";
    }

    @PostMapping("/addcarrito/{id}")
    public String addCarrito(Model model, @PathVariable long id) throws SQLException, IOException {
        Shoe zapato = shoeService.findById(id);
        model.addAttribute("zapato", zapato);
        shoeService.addUser(id);
       // shoeService.addUser(userService.getInv(), id);
        return "redirect:/zapatilla/"+zapato.getId();
    }

    @PostMapping("/comprar")
    public String comprar(Model model){
        this.userService.comprar();
        return "redirect:/carrito";
    }
}
