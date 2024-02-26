package com.example.aquawalkers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class EnalcesController {

    @GetMapping("/inicio")
    public String inicio(Model model){
        return "index";
    }
}
