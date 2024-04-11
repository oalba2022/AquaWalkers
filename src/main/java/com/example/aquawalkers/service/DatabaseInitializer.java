package com.example.aquawalkers.service;

import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.repository.ShoeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@Component
public class DatabaseInitializer {
    @Autowired
    private ShoeService shoeService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @PostConstruct
    public void init() throws IOException, SQLException {

        // Create some shops
        User user1= new User();
        userService.save(user1);



        // Create some books
        Shoe shoe1 = new Shoe("TN","Nike","Muy baratas",50,28,120,null);
        shoeService.save1(shoe1);
        Shoe shoe2 = new Shoe("Adidas 300","Adidas","Muy bonitas y chulas",50,30,50,null);
        shoeService.save1(shoe2);
        Shoe shoe3 = new Shoe("Crocks fran de la jungla","Crocs","Tela de javad entro",50,40,1,null);
        shoeService.save1(shoe3);

        Comment comment1 = new Comment();
        comment1.setText("Hola, cuanto valen");
        shoe1.addComment(comment1);
        Comment comment2 = new Comment();
        comment2.setText("No me gustan ");
        shoe2.addComment(comment2);
        Comment comment3 = new Comment();
        comment3.setText("Ande vas truan con las crocs esas");
        shoe2.addComment(comment3);


    }


}
