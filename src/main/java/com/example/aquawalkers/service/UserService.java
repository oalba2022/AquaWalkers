package com.example.aquawalkers.service;

import com.example.aquawalkers.exceptions.ShoeNotFoundException;
import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import com.example.aquawalkers.repository.UserRepository;
import jakarta.validation.Valid;
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

    public User inv = new User();
    @Autowired
    private UserRepository userRepository;
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
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



    public User modify(User user, long id) throws ShoeNotFoundException {
        long newId = id;
        this.delete(id);
        user.setId(newId);
        userRepository.save(user);
        return user;
    }
    public void addPrecioCarrito(Shoe shoe){
        this.inv.addCarrito(shoe);
    }

    public float precio(){
        float suma = 0;
        List<Shoe> carrito = this.inv.getCarrito();
        for(int i = 0; i < carrito.size(); i++){
            suma += carrito.get(i).getPrecio();
        }
        return suma;
    }

    public void comprar(){
        List<Shoe> carrito = this.inv.getCarrito();
        for(int i = 0; i < carrito.size(); i++){
            carrito.get(i).setStock(carrito.get(i).getStock()-1);
        }
        ArrayList<Shoe> nuevo_carrito = new ArrayList<>();
        this.inv.setCarrito(nuevo_carrito);
    }

    public User getInv() {
        return inv;
    }
}