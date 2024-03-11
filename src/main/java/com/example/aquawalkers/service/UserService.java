package com.example.aquawalkers.service;

import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
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
    /*private AtomicLong nextId = new AtomicLong(1L);
    private ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();

    public Optional<User> findById(long id) {
        if(this.users.containsKey(id)) {
            return Optional.of(this.users.get(id));
        }
        return Optional.empty();
    }

    public boolean exist(long id){
        return this.users.containsKey(id);
    }

    public List<User> findAll() {
        return this.users.values().stream().toList();
    }

    public User save(User user){
        long id = nextId.getAndIncrement();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    public boolean delete(long id){
        if (this.exist(id)){
            this.users.remove(id);
            return true;
        }
        return false;
    }
    public User modify(User user, long id){
        long newId = id;
        this.delete(id);
        user.setId(newId);
        users.put(newId, user);
        return user;
    }*/
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

}