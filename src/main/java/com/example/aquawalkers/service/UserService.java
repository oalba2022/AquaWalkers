package com.example.aquawalkers.service;

//import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.UserRepository;
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
    } //añdadido bbdd

    public boolean delete(long id){
        if (this.exist(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    } //añadido bbdd



    public User modify(User user, long id) {
        long newId = id;
        this.delete(id);
        user.setId(newId);
        userRepository.save(user);
        return user;
    }
    /*public void addPrecioCarrito(Shoe shoe){
        this.inv.addCarrito(shoe);
    }*/

    public float precio(){
        float suma = 0;
        User inv = findById(1L).get();
        List<Shoe> carrito = inv.getCarrito();
        for(int i = 0; i < carrito.size(); i++){
            suma += carrito.get(i).getPrecio();
        }
        return suma;
    }

    public void comprar(){
        /*List<Shoe> carrito = this.inv.getCarrito();
        for(int i = 0; i < carrito.size(); i++){
            carrito.get(i).setStock(carrito.get(i).getStock()-1);
        }*/
        User inv = findById(1L).get();
        List<Shoe> nuevo_carrito = new ArrayList<>();
        inv.setCarrito(nuevo_carrito);
        userRepository.save(inv);
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
    /*public boolean validateUser(String username, String password) {
        Optional<User> user = userRepository.findByName(username);
        return user.isPresent() && user.get().getEncodedPassword().equals(password);
    }*/
    /*public User getInv() {
        return inv;
    }*/
}