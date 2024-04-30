package com.example.aquawalkers.models;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    private String name;
    private String email;
    @JsonIgnore
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
    @ManyToMany
    private List<Shoe> carrito;
   /*@OneToMany(mappedBy = "id")
    private ArrayList<Comment> comentariosEscritos;*/

    public User(){}
    public User(String nombre, String correo, String contrasena, List<String> roles) {
        super();
        this.name = nombre;
        this.email = correo;
        this.password = contrasena;
        this.roles = roles;
        this.carrito = new ArrayList<Shoe>();
        /*this.comentariosEscritos = new ArrayList<Comment>();*/
    }
    public User(String nombre, String correo, String contrasena, String rol) {
        super();
        this.name = nombre;
        this.email = correo;
        this.password = contrasena;
        this.roles = Collections.singletonList(rol);
        this.carrito = new ArrayList<Shoe>();
        /*this.comentariosEscritos = new ArrayList<Comment>();*/
    }

    /*public User() {
        this.id = 1L;
        this.nombre = "Invitado";
        this.email = "invitado@aquawalkers.es";
        this.password = "";
        this.carrito = new ArrayList<Shoe>();
        /*this.comentariosEscritos = new ArrayList<Comment>();*/
   // }

    public String getName() {
        return name;
    }
    public String getPassword(){
        return this.password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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

    public void setName(String nombre) {
        this.name = nombre;
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
                ", nombre='" + name + '\'' +
                ", correo='" + email + '\'' +
                ", contrasena='" + password + '\'' +
                ", carrito=" + carrito +
                /*", comentariosEscritos=" + comentariosEscritos*/ +
                '}';
    }
}
