package com.example.aquawalkers.controllers;

import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.security.jwt.JwtTokenProvider;
import com.example.aquawalkers.security.jwt.LoginRequest;
import com.example.aquawalkers.security.jwt.Token;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.example.aquawalkers.service.UserService;
import com.example.aquawalkers.security.jwt.UserLoginService;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class LoginWebController {

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity loginProcess(User user) throws SQLException, IOException {
        UserDetails userDetails = (UserDetails) user;
        LoginRequest loginRequest = new LoginRequest(user.getName(), user.getPassword());
        Token newtoken = jwtTokenProvider.generateToken(userDetails);
        Token refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
       return userLoginService.login(loginRequest,newtoken.getTokenValue(),refreshToken.getTokenValue());
    }
    @RequestMapping("/loginerror")
    public String loginerror() {
        return "loginerror";
    }
}
