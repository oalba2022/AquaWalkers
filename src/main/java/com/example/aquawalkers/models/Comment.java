package com.example.aquawalkers.models;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    @ManyToOne
    private Shoe shoe;
    private String text;
    @ManyToOne
    private User user;

    public Comment(){}

    public Comment(String comment, User user) {
        this.text = comment;
        this.user = user;
    }
    public String getText(){
        return this.text;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public void setText(String comment) {
        this.text = comment;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser(){
        return user = this.user;
    }
    public Long getId(){
        return this.id;
    }
}
