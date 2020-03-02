package com.itoaxaca.yurta.response;

public class PedidoResponse {
    private String id;
    private String fecha_p;
    private String fecha_conf;
    private String estado;
    private String obra;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha_p() {
        return fecha_p;
    }

    public void setFecha_p(String fecha_p) {
        this.fecha_p = fecha_p;
    }

    public String getFecha_conf() {
        return fecha_conf;
    }

    public void setFecha_conf(String fecha_conf) {
        this.fecha_conf = fecha_conf;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }
}
