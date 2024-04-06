package com.example.aquawalkers.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.ArrayList;
@Entity

public class Shoe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message= "Debes introducir una marca")
    private String marca;
    @Column
    private String descripcion;
    @Column
    private String image;
    @Column
    private int stock;
    @Column
    private int talla;
    @Column
    private float precio;
    @OneToMany
    @JoinColumn
    private ArrayList<User> usuarios;
    @OneToMany
    @JoinColumn
    private ArrayList<Comment> comentarios;

    public Shoe(@Valid String nombre, @Valid String marca, String descripcion, int stock,  int talla,  float precio) {
        this.nombre = nombre;
        this.marca = marca;
        this.descripcion = descripcion;
        this.image = new String();
        this.stock = stock;
        this.talla = talla;
        this.precio = precio;
       this.comentarios = new ArrayList<Comment>();
        this.usuarios=new ArrayList<User>();
    }
    public Shoe(){

    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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


    public ArrayList<Comment> getComentarios() {
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
}
