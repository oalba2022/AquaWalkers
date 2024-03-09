package com.example.aquawalkers.models;

import java.util.ArrayList;

public class User {
    private Long id;
    private String nombre;
    private String correo;
    private String contrasena;
    private ArrayList<Shoe> carrito;
    private ArrayList<Comment> comentariosEscritos;

    public User(Long id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.carrito = new ArrayList<Shoe>();
        this.comentariosEscritos = new ArrayList<Comment>();
    }

    public User() {
    }

    public String getNombre() {
        return nombre;
    }

    public Long getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public ArrayList<Shoe> getCarrito() {
        return carrito;
    }

    public ArrayList<Comment> getComentariosEscritos() {
        return comentariosEscritos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setCarrito(ArrayList<Shoe> carrito) {
        this.carrito = carrito;
    }

    public void setComentariosEscritos(ArrayList<Comment> comentariosEscritos) {
        this.comentariosEscritos = comentariosEscritos;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", carrito=" + carrito +
                ", comentariosEscritos=" + comentariosEscritos +
                '}';
    }
}
