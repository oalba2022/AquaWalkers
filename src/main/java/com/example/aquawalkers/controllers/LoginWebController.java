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

        if (auth != null && auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(null);
            return "redirect:/logout-success";
        }
        return "redirect:/logout-error";
    }
    @RequestMapping("/register")
    public String register() {
        return "register";
    }
}