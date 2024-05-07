package com.example.aquawalkers.service;

//import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.CommentRepository;
import com.example.aquawalkers.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Component
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public boolean exist(long id){
        return this.userRepository.existsById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(@Valid User user){
        userRepository.save(user);
        return user;
    }

    public boolean delete(long id){
        if (this.exist(id)){
            User user = userRepository.findById(id).get();
            List<Comment> lista = new ArrayList<Comment>();
            List<Comment> listacomment = user.getComentariosEscritos();

            for(int i = 0; i < listacomment.size(); i++){
                Comment commentid = commentRepository.findById(listacomment.get(i).getId()).get();
                user.deleteComment(commentid);
                commentRepository.delete(commentid);
            }
            user.setComentariosEscritos(lista);
            userRepository.save(user);
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteme(String name){
       User user = userRepository.findByName(name).get();
       userRepository.delete(user);
       return true;
    }

    public User modifyUser(User user, String name){
        User user1 = findByName(name).get();
        user1.setName(user.getName());
        if(!user.getEncodedPassword().equals(user1.getEncodedPassword())){
            user1.setPassword(passwordEncoder.encode(user.getEncodedPassword()));
        }
        user1.setMail(user.getMail());
        userRepository.save(user1);
        return user1;
    }

    public float precio(User user){
        float suma = 0;
        List<Shoe> carrito = user.getCarrito();
        for(int i = 0; i < carrito.size(); i++){
            suma += carrito.get(i).getPrecio();
        }
        return suma;
    }

    public void comprar(User user){
        List<Shoe> carrito = user.getCarrito();
        for(int i = 0; i < carrito.size(); i++){
            carrito.get(i).setStock(carrito.get(i).getStock()-1);
        }
        List<Shoe> nuevo_carrito = new ArrayList<>();
        user.setCarrito(nuevo_carrito);
        userRepository.save(user);
    }

    public User registerUser(User user, String password) {
        if (findByName(user.getName()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        List<String> roles = user.getRoles();
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add("USER");
        user.setRoles(roles);

        return save(user);
    }

    public void saveComment(Comment comment, User user){
        commentRepository.save(comment);
    }
}