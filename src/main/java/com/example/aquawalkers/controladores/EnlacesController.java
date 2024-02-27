package com.example.aquawalkers.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EnlacesController {

    @GetMapping("/inicio")
    public String inicio(Model model){
        return "inicio";
    }


    @GetMapping("/zapatilla")
    public String zapatilla(Model model){
        return "zapatilla";
    }

    @GetMapping("/register")
    public String register(Model model){
        return "index";
    }
}