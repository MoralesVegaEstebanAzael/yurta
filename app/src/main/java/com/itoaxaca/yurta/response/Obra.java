package com.itoaxaca.yurta.response;

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
    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getFech_ini() {
        return fech_ini;
    }

    public String getDependencia() {
        return dependencia;
    }

    public String getTipo_obra() {
        return tipo_obra;
    }

    public String getFech_fin() {
        return fech_fin;
    }

    public String getTipo_descripcion() {
        return tipo_descripcion;
    }
}
