package com.example.aquawalkers.repository;

import com.example.aquawalkers.models.Shoe;
import com.example.aquawalkers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeRepository extends JpaRepository<Shoe, Long> {
}
