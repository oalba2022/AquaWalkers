package com.example.aquawalkers.controllers;

//import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.service.ShoeService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import com.example.aquawalkers.controllers.CustomErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class LinksController {

    @Autowired
    private ShoeService shoeService;

    /*@Autowired
    private CustomErrorController customErrorController*/

    @GetMapping("/inicio")

    public String inicio(Model model,Integer from, Integer to,String marca, Float precio) {
        if(this.shoeService.findAll( from, to, marca, precio).size() >= 3){
            Shoe zapa1 = this.shoeService.findById(1L);
            model.addAttribute("zapa1",zapa1);
            Shoe zapa2 = this.shoeService.findById(2L);
            model.addAttribute("zapa2",zapa2);
            Shoe zapa3 = this.shoeService.findById(3L);
            model.addAttribute("zapa3",zapa3);
        }
        return "greeting-page";
    }

    @GetMapping("/index")
    public String index(Model model){
        return "index";
    }

    @GetMapping("/zapatilla")
    public String zapatilla(Model model){
        return "shoe";
    }

    @GetMapping("/")
    public String greeting(Model model,Integer from, Integer to,String marca, Float precio) {
        if(this.shoeService.findAll(from,to,marca,precio).size() >= 3){
            Shoe zapa1 = this.shoeService.findById(1L);
            model.addAttribute("zapa1",zapa1);
            Shoe zapa2 = this.shoeService.findById(2L);
            model.addAttribute("zapa2",zapa2);
            Shoe zapa3 = this.shoeService.findById(3L);
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

    @GetMapping("/error")
    public String handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null && Integer.valueOf(status.toString()) == HttpStatus.NOT_FOUND.value()){
            return "404";
        }

        return "error";
    }


}