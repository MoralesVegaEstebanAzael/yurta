package com.itoaxaca.yurta.pojos;

public class Almacen {
    private String obra;
    private String id;
    private String descripcion;
    private String unidad;
    private String tipo;
    private String marca;
    private String cantidad;
    private String url_imagen;

    public Almacen(String obra, String id, String descripcion, String unidad, String tipo, String marca, String cantidad, String url_imagen) {
        this.obra = obra;
        this.id = id;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.tipo = tipo;
        this.marca = marca;
        this.cantidad = cantidad;
        this.url_imagen = url_imagen;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
