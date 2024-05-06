package com.example.aquawalkers.service;

import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
        if (commentRepository.existsById(id) && user.getId() == 1L){
            User useraux = commentRepository.findById(id).get().getUser();
            useraux.deleteComment(commentRepository.findById(id).get());
            commentRepository.deleteById(id);
            return true;
        }else if(commentRepository.existsById(id) && user.getId().equals(commentRepository.findById(id).get().getUser().getId())){
            user.deleteComment(commentRepository.findById(id).get());
            commentRepository.deleteById(id);
            return true;
        }
        return false;
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
