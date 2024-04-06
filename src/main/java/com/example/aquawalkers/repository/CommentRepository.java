package com.example.aquawalkers.repository;

import com.example.aquawalkers.models.Comment;
import com.example.aquawalkers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
