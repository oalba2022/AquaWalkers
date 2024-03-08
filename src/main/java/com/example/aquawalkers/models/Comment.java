package com.example.aquawalkers.models;

public class Comment {
    //private Long idComment;
    private String comment;

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
