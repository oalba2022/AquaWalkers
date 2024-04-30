package com.example.aquawalkers.controllers;

import java.security.Principal;

import com.example.aquawalkers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> me(HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if(principal != null) {
            return ResponseEntity.ok(userRepository.findByName(principal.getName()).orElseThrow());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestBody User user){
        User newUser = this.userService.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
