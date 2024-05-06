package com.example.aquawalkers.service;

import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.models.Comment;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.commons.io.IOUtils;


@Component
public class DatabaseInitializer {
    @Autowired
    private ShoeService shoeService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() throws IOException, SQLException {

        userService.save(new User("admin", passwordEncoder.encode("adminpass"), "ADMIN", "admin@admin.com"));
        userService.save(new User ("user", passwordEncoder.encode("password"), "USER", "user@user.com"));


        File img1 = new File("images/tn.png");
        FileInputStream input1 = new FileInputStream(img1);
        MultipartFile multipartFile1 = new MockMultipartFile("fileItem", img1.getName(), "image/png", IOUtils.toByteArray(input1));

        File img2 = new File("images/samba.png");
        FileInputStream input2 = new FileInputStream(img2);
        MultipartFile multipartFile2 = new MockMultipartFile("fileItem", img2.getName(), "image/png", IOUtils.toByteArray(input2));

        File img3 = new File("images/crocs3.png");
        FileInputStream input3 = new FileInputStream(img3);
        MultipartFile multipartFile3 = new MockMultipartFile("fileItem", img3.getName(), "image/png", IOUtils.toByteArray(input3));

        Shoe shoe1 = new Shoe("TN","Nike","Muy baratas",50,28,120,null);
        shoeService.save(shoe1,multipartFile1);
        Shoe shoe2 = new Shoe("Adidas 300","Adidas","Muy bonitas y chulas",50,30,50,null);
        shoeService.save(shoe2,multipartFile2);
        Shoe shoe3 = new Shoe("Crocks fran de la jungla","Crocs","Tela de javad entro",50,40,1,null);
        shoeService.save(shoe3,multipartFile3);
    }


}
