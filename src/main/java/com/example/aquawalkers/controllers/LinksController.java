package com.example.aquawalkers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LinksController {

    @GetMapping("/inicio")
    public String inicio(Model model){
        return "greeting-page";
    }


    @GetMapping("/zapatilla")
    public String zapatilla(Model model){
        return "shoe";
    }

    @GetMapping("/")
    public String register(Model model){
        return "greeting-page";
    }
    @GetMapping("/Sobre-Nosotros")
    public String sobreNosotros(Model model){return "about-us";}
    //No esta la pagina
}