package com.itoaxaca.yurta.pojos;

public class DetallePedido {
    private String cantidad;
    private String id_pedido;
    private String id_material;
    private String descripcion;
    private String unidad;
    private String marca;

    public DetallePedido(String cantidad, String id_pedido, String id_material, String descripcion, String unidad, String marca) {
        this.cantidad = cantidad;
        this.id_pedido = id_pedido;
        this.id_material = id_material;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.marca = marca;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public String getId_material() {
        return id_material;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUnidad() {
        return unidad;
    }

    public String getMarca() {
        return marca;
    }
}
