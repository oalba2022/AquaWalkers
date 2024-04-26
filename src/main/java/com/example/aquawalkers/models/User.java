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
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
    @ManyToMany
    private List<Shoe> carrito;
   /*@OneToMany(mappedBy = "id")
    private ArrayList<Comment> comentariosEscritos;*/

    public User(String nombre, String correo, String contrasena) {
        super();
        this.nombre = nombre;
        this.email = correo;
        this.password = contrasena;
        this.carrito = new ArrayList<Shoe>();
        /*this.comentariosEscritos = new ArrayList<Comment>();*/
    }

    public User() {
        this.id = 1L;
        this.nombre = "Invitado";
        this.email = "invitado@aquawalkers.es";
        this.password = "";
        this.carrito = new ArrayList<Shoe>();
        /*this.comentariosEscritos = new ArrayList<Comment>();*/
    }

    public String getNombre() {
        return nombre;
    }


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public void setEmail(String correo) {
        this.email = correo;
    }

    public void setPassword(String contrasena) {
        this.password = contrasena;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + email + '\'' +
                ", contrasena='" + password + '\'' +
                ", carrito=" + carrito +
                /*", comentariosEscritos=" + comentariosEscritos*/ +
                '}';
    }
}
