package com.example.aquawalkers.models;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    private Long id;
    @OneToOne
    private Shoe shoe;
    //private Long idComment;
    @Column
    private String comment;
    public Comment(){

    }

    public Comment(/*Long id*/ String comment) {
        //this.idComment = id;

        this.comment = comment;
    }

    /*public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }
*/

    public void setComment(String comment) {
        this.comment = comment;
    }

    /*public Long getIdComment() {
        return idComment;
    }*/

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return " " + comment;
    }
}
