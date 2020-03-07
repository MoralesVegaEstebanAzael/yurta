package com.itoaxaca.yurta.pojos;

public class DetallePedido {
    private String cantidad;
    private String id_pedido;
    private String id_material;
    private String descripcion;
    private String unidad;
    private String marca;
    private String url_imagen;

    public DetallePedido(String cantidad, String id_pedido, String id_material, String descripcion, String unidad, String marca, String url_imagen) {
        this.cantidad = cantidad;
        this.id_pedido = id_pedido;
        this.id_material = id_material;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.marca = marca;
        this.url_imagen = url_imagen;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getId_material() {
        return id_material;
    }

    public void setId_material(String id_material) {
        this.id_material = id_material;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
