package com.example.aquawalkers.models;
import jakarta.persistence.*;

@Entity
@Table(name= "comentarios")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    @ManyToOne
    private Shoe shoe;
    //private Long idComment;
    @Column
    private String comment;

    @OneToOne
    private User user;
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
