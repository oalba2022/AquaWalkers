package com.example.aquawalkers.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginWebController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/loginerror")
    public String loginerror() {
        return "loginerror";
    }

    @RequestMapping("/logout")
    public String logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Si la autenticación no es nula y está autenticada, invalidarla
        if (auth != null && auth.isAuthenticated()) {
            // Invalidar la autenticación actual
            SecurityContextHolder.getContext().setAuthentication(null);

            // Redirigir al usuario a una página deslogueo
            return "redirect:/logout-success";
        }

        // Redirigir a una página de error
        return "redirect:/logout-error";
    }



}