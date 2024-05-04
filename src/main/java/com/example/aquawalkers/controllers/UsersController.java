package com.example.aquawalkers.controllers;

//import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import ch.qos.logback.classic.encoder.JsonEncoder;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.UserRepository;
import com.example.aquawalkers.service.ShoeService;
import com.example.aquawalkers.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @GetMapping("/usercard")
    public String usercard (Model model, HttpServletRequest request){
        User user = userService.findByName(request.getUserPrincipal().getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        return "usercard";
    }
   /* @GetMapping("/private")
    public String privatePage(Model model, HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        User user = userService.findByName(name).orElseThrow();
        model.addAttribute("username", user.getName());
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        return "private";
    }*/

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

    @PostMapping("/register")
    public String registerUser(@Valid User user, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(user, password);
            redirectAttributes.addFlashAttribute("success", "Usuario registrado exitosamente.");
            return "redirect:/inicio";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/deleteuser/{id}")
    public String deleteShoe(Model model, @PathVariable long id){
        if(userService.exist(id)){
            userService.delete(id);
            return "users";
        }
        return "users";
    }

    @GetMapping("/users")
    public String allusers(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }
}
