package com.example.aquawalkers.controladores;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


public class EnlacesController {

    @GetMapping("/inicio")
    public String inicio(Model model){
        return "index";
    }


    @GetMapping("/zapatilla")
    public String zapatilla(Model model){
        return "zapatilla";
    }
}

