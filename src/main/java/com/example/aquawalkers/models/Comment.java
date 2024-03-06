package com.example.aquawalkers.models;

public class Comment {
    private Long id;
    private User user;
    private String comment;

    public Comment(Long id, User user, String comment) {
        this.id = id;
        this.user = user;
        this.comment = comment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", comment='" + comment + '\'' +
                '}';
    }
}
