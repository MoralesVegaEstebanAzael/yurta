package com.itoaxaca.yurta.pojos;

public class Obra {
    private int categoria;
    private String nombre;

    public Obra(String nombre){
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
