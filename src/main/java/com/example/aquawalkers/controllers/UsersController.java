package com.example.aquawalkers.controllers;

import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.service.ShoeService;
import com.example.aquawalkers.service.UserService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private ShoeService shoeService;
    @GetMapping("/usercard")
    public String usercard (Model model){
        User invitado = this.userService.inv;
        model.addAttribute("invitado", invitado);
        return "usercard";
    }

    @GetMapping("/carrito")
    public String carrito (Model model){
        User invitado = this.userService.inv;
        ArrayList<Shoe> shop = this.userService.inv.getCarrito();
        float total = this.userService.precio();
        model.addAttribute("shop", shop);
        model.addAttribute("total", total);
        return "carrito";
    }

    @PostMapping("/addcarrito/{id}")
    public String addCarrito(Model model, @PathVariable long id) throws ShoeNotFoundException {
        Optional<Shoe> zapato = shoeService.findById(id);
        model.addAttribute("zapato", zapato.get());
        userService.addPrecioCarrito(zapato.get());
        return "redirect:/zapatilla/"+zapato.get().getId();
    }

}
