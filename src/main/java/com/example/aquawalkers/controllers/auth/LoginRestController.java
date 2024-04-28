package com.example.aquawalkers.controllers.auth;

import com.example.aquawalkers.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/api/login")
public class LoginRestController {

    private final UserService userService;

    public LoginRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials,
                                   HttpSession session, HttpServletResponse response) throws IOException {
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

}