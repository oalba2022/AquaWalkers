package com.example.aquawalkers.controllers;

import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.security.jwt.JwtTokenProvider;
import com.example.aquawalkers.security.jwt.LoginRequest;
import com.example.aquawalkers.security.jwt.Token;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.example.aquawalkers.service.UserService;
import com.example.aquawalkers.security.jwt.UserLoginService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class LoginWebController {

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session, HttpServletResponse response) throws IOException {
        String name = credentials.get("name");
        String password = credentials.get("password");

        if (userService.validateUser(name, password)) {
            session.setAttribute("USER", name);
            return ResponseEntity.ok("Correct credentials");
        } else {
            response.getWriter().write("{message: \"Bad credentials\"}");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @RequestMapping("/loginerror")
    public String loginerror() {
        return "loginerror";
    }
}
