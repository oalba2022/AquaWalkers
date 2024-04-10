/*package com.example.aquawalkers.controllers;

import com.example.aquawalkers.models.Image;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.service.ImageService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ImageController {
    @Autowired
    private ImageService2 imageService;

    // display image
    @GetMapping("/shoe/{id}/display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") long id) throws IOException, SQLException {
        Image image = imageService.viewById(id);
        byte[] imageBytes = null;
        imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    // view All images
   /* @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("index");
        List<Image> imageList = imageService.viewAll();
        mv.addObject("imageList", imageList);
        return mv;
    } */
/*
    // add image - get
    @GetMapping("/add")
    public ModelAndView addImage(){
        return new ModelAndView("addimage");
    }

    // add image - post
    @PostMapping("/add")
    public String addImagePost(Shoe shoe, MultipartFile image) throws IOException, SerialException, SQLException
    {
        byte[] imageData = image.getBytes();
        Blob imageBlob = new SerialBlob(imageData);
        shoe.setImageData(imageBlob);
        shoe.setDate(LocalDate.now());
        shoe.save(forum);
        return "forums/saved_forum";
    }

}
*/