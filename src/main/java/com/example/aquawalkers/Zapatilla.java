package com.example.aquawalkers;

public class Zapatilla {
    private int id;
    private String nombre;
    private String marca;
    private String descripción;
    private int stock;
    private int talla;
    private float precio;

    public Zapatilla(int id, String nombre, String marca, String descripción, int stock, int talla, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.descripción = descripción;
        this.stock = stock;
        this.talla = talla;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
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

    @Override
    public String toString() {
        return "Zapatilla{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", descripción='" + descripción + '\'' +
                ", stock=" + stock +
                ", talla=" + talla +
                ", precio=" + precio +
                '}';
    }
}
