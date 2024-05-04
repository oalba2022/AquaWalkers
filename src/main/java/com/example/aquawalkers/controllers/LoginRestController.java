package com.example.aquawalkers.controllers;



import com.example.aquawalkers.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aquawalkers.security.jwt.AuthResponse;
import com.example.aquawalkers.security.jwt.AuthResponse.Status;
import com.example.aquawalkers.security.jwt.LoginRequest;
import com.example.aquawalkers.security.jwt.UserLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class LoginRestController {

    @Autowired
    private UserLoginService userService;

    /*@PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestBody LoginRequest loginRequest) {

        return userService.login(loginRequest, accessToken, refreshToken);
    }*/
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestBody LoginRequest loginRequest) {
        ResponseEntity<AuthResponse> responseEntity = userService.login(loginRequest, accessToken, refreshToken);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        } else {
            AuthResponse errorResponse = new AuthResponse(Status.FAILURE, "Error en el inicio de sesión.");
            return ResponseEntity.status(responseEntity.getStatusCode()).body(errorResponse);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        return userService.refresh(refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logOut(HttpServletRequest request, HttpServletResponse response) {

        return ResponseEntity.ok(new AuthResponse(Status.SUCCESS, userService.logout(request, response)));
    }
}
