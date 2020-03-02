package com.itoaxaca.yurta.pojos;

import java.io.Serializable;

public class Material implements Serializable {
    private String id;
    private String descripcion;
    private String unidad;
    private String tipo;
    private String marca;
    private String precio_unitario;
    private String url_imagen;
    private boolean selected;
    private int cantidadSolicitada;
    public Material(String id, String descripcion, String unidad, String tipo, String marca, String precio_unitario, String url_imagen, boolean selected) {
        this.id = id;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.tipo = tipo;
        this.marca = marca;
        this.precio_unitario = precio_unitario;
        this.url_imagen = url_imagen;
        this.selected = selected;
        cantidadSolicitada=0;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
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

    public String getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(String precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
