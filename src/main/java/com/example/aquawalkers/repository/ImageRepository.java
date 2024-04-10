package com.example.aquawalkers.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.aquawalkers.models.Image;
import com.example.aquawalkers.models.Shoe;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

}