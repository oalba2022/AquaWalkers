package com.example.aquawalkers.controllers.auth;

import com.example.aquawalkers.models.User;
import com.example.aquawalkers.security.RepositoryUserDetailsService;
import com.example.aquawalkers.security.jwt.AuthResponse;
import com.example.aquawalkers.security.jwt.JwtTokenProvider;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.aquawalkers.security.jwt.LoginRequest;
import com.example.aquawalkers.security.jwt.UserLoginService;

@Controller
public class LoginController {

    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RepositoryUserDetailsService repositoryUserDetailsService;
        @RequestMapping("/login")
        public String login() {
            return "login";
        }

        @RequestMapping("/loginerror")
        public String loginerror() {
            return "loginerror";
        }
}