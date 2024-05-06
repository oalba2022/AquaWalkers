package com.example.aquawalkers.controllers;

import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.UserRepository;
import com.example.aquawalkers.security.jwt.UserLoginService;
import com.example.aquawalkers.service.ShoeService;
import com.example.aquawalkers.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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

    @PostMapping("/index")
    public String newUser (Model model, @Valid User user){
        User newUser = userService.save(user);
        model.addAttribute("userId", newUser.getId());
        return "redirect:/inicio";
    }

    @GetMapping("/carrito")
    public String carrito (Model model, HttpServletRequest request){
        User user = this.userService.findByName(request.getUserPrincipal().getName()).get();
        List<Shoe> shop = user.getCarrito();
        float total = this.userService.precio(user);
        model.addAttribute("shop", shop);
        model.addAttribute("total", total);
        return "carrito";
    }

    @PostMapping("/addcarrito/{id}")
    public String addCarrito(Model model, @PathVariable long id, HttpServletRequest request) throws SQLException, IOException {
        Shoe zapato = shoeService.findById(id);
        User user = userService.findByName(request.getUserPrincipal().getName()).get();
        model.addAttribute("zapato", zapato);
        shoeService.addUser(id, user);
        return "redirect:/zapatilla/"+zapato.getId();
    }

    @PostMapping("/comprar")
    public String comprar(Model model, HttpServletRequest request){
        User user = userService.findByName(request.getUserPrincipal().getName()).get();
        this.userService.comprar(user);
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
        if(id != 1) {
            if(userService.exist(id)){
                userService.delete(id);
            }
        }
        return "redirect:/users";
    }
    @GetMapping("/deleteme")
    public String deleteme(Model model, HttpServletRequest request) throws ServletException {
            userService.deleteme(request.getUserPrincipal().getName());
            request.logout();
        return "redirect:/login";
    }



    @GetMapping("/users")
    public String allusers(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }
    @PostMapping("/modifyuser")
    public String modificarUsuario(User user, Model model, HttpServletRequest request) throws ServletException {
        User newuser=userService.modifyUser(user, request.getUserPrincipal().getName());
        request.logout();
        model.addAttribute("user", newuser);
        return "redirect:/inicio";
    }
    @GetMapping("/modifyuser")
    public String modificar(Model model, HttpServletRequest request){
        User user = userService.findByName(request.getUserPrincipal().getName()).get();
        model.addAttribute("user", user);
        return "modifyUser";
    }
}
