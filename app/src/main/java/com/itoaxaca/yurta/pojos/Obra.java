package com.itoaxaca.yurta.pojos;

public class Obra {
    private String id;
    private String descripcion;
    private String lat;
    private String lng;
    private String fech_ini;
    private String fech_fin;
    private String dependencia;
    private String tipo_obra;
    private String tipo_descripcion;

    public Obra(String id, String descripcion, String lat, String lng, String fech_ini, String fech_fin, String dependencia, String tipo_obra, String tipo_descripcion) {
        this.id = id;
        this.descripcion = descripcion;
        this.lat = lat;
        this.lng = lng;
        this.fech_ini = fech_ini;
        this.fech_fin = fech_fin;
        this.dependencia = dependencia;
        this.tipo_obra = tipo_obra;
        this.tipo_descripcion = tipo_descripcion;
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getFech_ini() {
        return fech_ini;
    }

    public void setFech_ini(String fech_ini) {
        this.fech_ini = fech_ini;
    }

    public String getFech_fin() {
        return fech_fin;
    }

    public void setFech_fin(String fech_fin) {
        this.fech_fin = fech_fin;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getTipo_obra() {
        return tipo_obra;
    }

    public void setTipo_obra(String tipo_obra) {
        this.tipo_obra = tipo_obra;
    }

    public String getTipo_descripcion() {
        return tipo_descripcion;
    }

    public void setTipo_descripcion(String tipo_descripcion) {
        this.tipo_descripcion = tipo_descripcion;
    }
}
