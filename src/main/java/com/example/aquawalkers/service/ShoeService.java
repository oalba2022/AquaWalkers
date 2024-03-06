package com.example.aquawalkers.service;

import com.example.aquawalkers.models.Shoe;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ShoeService {
    private AtomicLong nextId = new AtomicLong(1L);
    private ConcurrentHashMap<Long, Shoe> shoes = new ConcurrentHashMap<>();

    public Optional<Shoe> findById(long id) {
        if(this.shoes.containsKey(id)) {
            return Optional.of(this.shoes.get(id));
        }
        return Optional.empty();
    }

    public boolean exist(long id){
        return this.shoes.containsKey(id);
    }

    public List<Shoe> findAll() {
        return this.shoes.values().stream().toList();
    }

    public Shoe save(Shoe shoe){
        long id = nextId.getAndIncrement();
        shoe.setId(id);
        shoes.put(id, shoe);
        return shoe;
    }
}
