package com.example.aquawalkers.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysql.cj.conf.PropertyDefinitions;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Shoe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message= "Debes introducir una marca")
    private String marca;

    private String descripcion;

    @Getter
    @Lob
    private Blob image;

    private int stock;

    private int talla;

    private float precio;
    @ManyToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<User> usuarios;
    @OneToMany(mappedBy = "shoe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comentarios;

    public Shoe(@Valid String nombre, @Valid String marca, String descripcion, int stock,  int talla,  float precio, Blob image) {
        this.nombre = nombre;
        this.marca = marca;
        this.descripcion = descripcion;
        this.image = image;
        this.stock = stock;
        this.talla = talla;
        this.precio = precio;
        this.comentarios = new ArrayList<>();
        this.usuarios=new ArrayList<>();
    }
    public Shoe(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getTalla() {
        return talla;
    }

    public void setTalla(int talla) {
        this.talla = talla;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void addComment(Comment c){
        this.comentarios.add(c);
    }


    public List<Comment> getComentarios() {
        return comentarios;
    }


    public void  adduser(User u){
        this.usuarios.add(u);
    }

    @Override
    public String toString() {
        return "Shoe{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", image='" + image + '\'' +
                ", stock=" + stock +
                ", talla=" + talla +
                ", precio=" + precio +
                '}';
    }

    public LocalDate setDate(LocalDate now) {
        return now;
    }
}
