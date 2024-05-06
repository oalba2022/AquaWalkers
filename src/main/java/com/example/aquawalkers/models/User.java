package com.example.aquawalkers.models;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name="USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String name;
    private String mail;
    @JsonIgnore
    private String encodedPassword;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
    @ManyToMany
    private List<Shoe> carrito;
    @OneToMany
    @JsonIgnore
    private List<Comment> comentariosEscritos;

    public User(){}
    public User(String name, String encodedPassword, String roles, String mail) {
        super();
        this.name = name;
        this.encodedPassword = encodedPassword;
        this.roles = List.of(roles);
        this.mail = mail;
        this.carrito = new ArrayList<Shoe>();
        this.comentariosEscritos = new ArrayList<Comment>();
    }
    public String getName() {
        return name;
    }
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEncodedPassword(){
        return encodedPassword;
    }
    public void setPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public List<Shoe> getCarrito() {
        return carrito;
    }

    public List<Comment> getComentariosEscritos() {
        return comentariosEscritos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCarrito(List<Shoe> carrito) {
        this.carrito = carrito;
    }

   /* public void setComentariosEscritos(ArrayList<Comment> comentariosEscritos) {
        this.comentariosEscritos = comentariosEscritos;
    }*/
    public void addComment(Comment comment){
        this.comentariosEscritos.add(comment);
    }

    public void deleteComment(Comment comment){
        this.comentariosEscritos.remove(comment);
    }

    public void addCarrito(Shoe shoe){
        this.carrito.add(shoe);
    }
   /* @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                ", contrasena='" + password + '\'' +
                ", carrito=" + carrito +
                /*", comentariosEscritos=" + comentariosEscritos*/ //+
                //'}';
    //}
}
