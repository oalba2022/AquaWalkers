package com.example.aquawalkers.service;

import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Component
public class UserService {

    private AtomicLong nextId = new AtomicLong(1L);
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
    }
}