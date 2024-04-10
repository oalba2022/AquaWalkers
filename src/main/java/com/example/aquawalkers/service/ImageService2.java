package com.example.aquawalkers.service;


import com.example.aquawalkers.models.Image;
import com.example.aquawalkers.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImageService2{
    @Autowired
    private ImageRepository imageRepository;


    public Image create(Image image) {
        return imageRepository.save(image);
    }

    public List<Image> viewAll() {
        return (List<Image>) imageRepository.findAll();
    }

    public Image viewById(long id) {
        return imageRepository.findById(id).get();
    }
}