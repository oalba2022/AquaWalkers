package com.example.aquawalkers.models;
import jakarta.persistence.*;

@Entity
@Table(name= "comentario")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    @ManyToOne
    private Shoe shoe;
    private String comment;
    /*@ManyToOne
    private User user;*/
    public Comment(){
    }

    public Comment(String comment) {

        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
