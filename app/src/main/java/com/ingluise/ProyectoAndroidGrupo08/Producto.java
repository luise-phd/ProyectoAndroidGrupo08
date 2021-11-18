package com.ingluise.ProyectoAndroidGrupo08;

public class Producto {
    private String ref;
    private String nom;
    private int precio;

    public Producto() {
        this.ref = "";
        this.nom = "";
        this.precio = 0;
    }

    public Producto(String ref, String nom, int precio) {
        this.ref = ref;
        this.nom = nom;
        this.precio = precio;
    }

    public String getRef() {
        return ref;
    }

    public String getNom() {
        return nom;
    }

    public int getPrecio() {
        return precio;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
