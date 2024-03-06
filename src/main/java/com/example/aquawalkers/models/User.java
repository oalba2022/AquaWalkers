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
}
