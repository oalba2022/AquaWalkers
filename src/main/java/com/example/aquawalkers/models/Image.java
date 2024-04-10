/*package com.example.aquawalkers.models;

import jakarta.persistence.*;

import jakarta.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "image_table")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    private Blob image;

    @OneToOne
    private Shoe shoe;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Blob getImage() {
        return image;
    }
    public void setImage(Blob image) {
        this.image = image;
    }
}
*/