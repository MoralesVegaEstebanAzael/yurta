package com.itoaxaca.yurta.pojos;

public class Notificacion {
    private int id;
    private String titulo;
    private String informacion;

    public Notificacion(int id, String titulo, String informacion) {
        this.id = id;
        this.titulo = titulo;
        this.informacion = informacion;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }
}
