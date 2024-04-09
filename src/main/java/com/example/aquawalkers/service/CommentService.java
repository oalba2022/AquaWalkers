package com.example.aquawalkers.service;


import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
//import com.example.aquawalkers.repositories.ShoesRepository;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.CommentRepository;
import com.example.aquawalkers.repository.ShoeRepository;
import com.example.aquawalkers.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Component
public class CommentService {


    @Autowired
    private CommentRepository commentRepository;

    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    public boolean exist(long id){
        return this.commentRepository.existsById(id);

    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment save(Comment comment){
        commentRepository.save(comment);
        return comment;
    }

    public boolean delete(long id){
        if (this.exist(id)){
                commentRepository.deleteById(id);
            return true;
        }
        return false;
    }



    public Comment modify(Comment comment, long id) throws ShoeNotFoundException{
        long newId = id;
        this.delete(id);
        comment.setId(newId);
        commentRepository.save(comment);
        return comment;
    }


}
