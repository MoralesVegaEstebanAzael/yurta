package com.itoaxaca.yurta.pojos;

public class Notificacion {
    private String id;
    private String notifiable_id;
    private String titulo;
    private String tipo; //1-> pedido,2-> stock material
    private String mensaje;
    private String obra;

    public Notificacion(String id, String notifiable_id, String titulo, String tipo, String mensaje, String obra) {
        this.id = id;
        this.notifiable_id = notifiable_id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.obra = obra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotifiable_id() {
        return notifiable_id;
    }

    public void setNotifiable_id(String notifiable_id) {
        this.notifiable_id = notifiable_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }
}
