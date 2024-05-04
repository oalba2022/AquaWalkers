package com.example.aquawalkers.service;


//import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
//import com.example.aquawalkers.repositories.ShoesRepository;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.CommentRepository;
import com.example.aquawalkers.repository.ShoeRepository;
import com.example.aquawalkers.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    private UserService userService;


    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    public boolean exist(long id){
        return this.commentRepository.existsById(id);

    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public void save(Comment comment, Shoe shoe,User user){
        commentRepository.save(comment);
        comment.setShoe(shoe);
        comment.setUser(user);
    }

    public boolean delete(long id, User user){
        if (!this.exist(id) || !user.getId().equals(commentRepository.findById(id).get().getUser().getId())){

            return false;
        }else{
            user.deleteComment(commentRepository.findById(id).get());
            commentRepository.deleteById(id);
            return true;
        }
    }



    public Comment modify(Comment comment, long id, HttpServletRequest request){
        User user = this.userService.findByName(request.getUserPrincipal().getName()).get();
        long newId = id;
        this.delete(id, user);
        comment.setId(newId);
        commentRepository.save(comment);
        return comment;
    }


}
