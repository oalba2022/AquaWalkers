package com.example.aquawalkers.controllers;

import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.service.ShoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;


@Controller
public class LinksController {

    @Autowired
    private ShoeService shoeService;

    @GetMapping("/inicio")

    public String inicio(Model model) throws ShoeNotFoundException {
        if(this.shoeService.findAll().size() >= 3){
            Shoe zapa1 = this.shoeService.findById(1L).get();
            model.addAttribute("zapa1",zapa1);
            Shoe zapa2 = this.shoeService.findById(2L).get();
            model.addAttribute("zapa2",zapa2);
            Shoe zapa3 = this.shoeService.findById(3L).get();
            model.addAttribute("zapa3",zapa3);
        }
        return "greeting-page";
    }


    @GetMapping("/zapatilla")
    public String zapatilla(Model model){
        return "shoe";
    }

    @GetMapping("/")
    public String greeting(Model model) throws ShoeNotFoundException {
        if(this.shoeService.findAll().size() >= 3){
            Shoe zapa1 = this.shoeService.findById(1L).get();
            model.addAttribute("zapa1",zapa1);
            Shoe zapa2 = this.shoeService.findById(2L).get();
            model.addAttribute("zapa2",zapa2);
            Shoe zapa3 = this.shoeService.findById(3L).get();
            model.addAttribute("zapa3",zapa3);
        }
            return "greeting-page";
    }

    @GetMapping("/sobre-nosotros")
    public String sobreNosotros(Model model){return "about-us";}

    @GetMapping("/allShoes")
    public String allShoes(Model model){
        return "allshoes";
    }


}