package com.example.aquawalkers.models;



import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    private String nombre;
    private String correo;
    private String contrasena;
    @ManyToMany
    private List<Shoe> carrito;
   /*@OneToMany(mappedBy = "id")
    private ArrayList<Comment> comentariosEscritos;*/

    public User(String nombre, String correo, String contrasena) {
        super();
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.carrito = new ArrayList<Shoe>();
        /*this.comentariosEscritos = new ArrayList<Comment>();*/
    }

    public User() {
        this.id = 1L;
        this.nombre = "Invitado";
        this.correo = "invitado@aquawalkers.es";
        this.contrasena = "";
        this.carrito = new ArrayList<Shoe>();
        /*this.comentariosEscritos = new ArrayList<Comment>();*/
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

    public List<Shoe> getCarrito() {
        return carrito;
    }

    /*public ArrayList<Comment> getComentariosEscritos() {
        return comentariosEscritos;
    }*/

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

    public void setCarrito(List<Shoe> carrito) {
        this.carrito = carrito;
    }

   /* public void setComentariosEscritos(ArrayList<Comment> comentariosEscritos) {
        this.comentariosEscritos = comentariosEscritos;
    }*/

    public void addCarrito(Shoe shoe){
        this.carrito.add(shoe);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", carrito=" + carrito +
                /*", comentariosEscritos=" + comentariosEscritos*/ +
                '}';
    }
}
