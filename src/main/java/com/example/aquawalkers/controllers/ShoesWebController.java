package com.example.aquawalkers.controllers;

import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.service.ShoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Controller
public class ShoesWebController {

    @Autowired
    private ShoeService shoeService;

    @GetMapping("/zapatillas")
    public String showAllShoes(Model model){
        model.addAttribute("zapatillas", shoeService.findAll());
        return "allshoes";
    }

    @GetMapping("/zapatilla/{id}")
    public String showShoe(Model model, @PathVariable long id){
        Optional<Shoe> zapatilla = shoeService.findById(id);
        if(zapatilla.isPresent()){
            model.addAttribute("zapatilla", zapatilla.get());
            return "shoe";
        }else{
            return "allshoes";
        }
    }

    @GetMapping("/newshoe")
    public String newShoe(Model model){
        return "newshoe";
    }

    @PostMapping("/newshoe")
    public String newShoeProcess(Model model, Shoe shoe){
        Shoe newShoe = shoeService.save(shoe);
        model.addAttribute("shoeId", newShoe);
        return "redirect:/zapatilla/"+newShoe.getId();
    }

    @GetMapping("/deleteshoe/{id}")
    public String deleteShoe(Model model, @PathVariable long id){
        Optional<Shoe> zapatilla = shoeService.findById(id);
        if(zapatilla.isPresent()){
            shoeService.delete(id);
            return "deleteshoe";
        }
        return "allshoes";
    }

}
