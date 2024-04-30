package com.example.aquawalkers.controllers;

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
}