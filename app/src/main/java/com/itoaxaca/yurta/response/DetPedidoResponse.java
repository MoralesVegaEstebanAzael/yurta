package com.itoaxaca.yurta.response;

public class DetPedidoResponse {
    private String cantidad;
    private String id_pedido;
    private String id_material;
    private String descripcion;
    private String unidad;
    private String marca;
    private String url_imagen;

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

    public String getUrl_imagen() {
        return url_imagen;
    }
}
