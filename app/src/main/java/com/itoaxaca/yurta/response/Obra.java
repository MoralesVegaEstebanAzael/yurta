package com.itoaxaca.yurta.response;

public class Obra {
    private String id;
    private String descripcion;
    private String lat;
    private String lng;
    private String fech_ini;
    private String dependencia;
    private String tipo_obra;

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
}
