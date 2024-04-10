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
    /*@ManyToOne
    private User user;*/
    public Comment(){}

    /*public Comment(String comment) {
        this.comment = comment;
    }*/
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
}
