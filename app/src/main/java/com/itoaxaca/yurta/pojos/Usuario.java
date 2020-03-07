package com.itoaxaca.yurta.pojos;

public class Usuario {
    private String id;
    private String name;
    private String email;
    private String telefono;
    private String puesto;
    private String api_token;
    private String url_avatar;
    private String fcm_token;
    public String getUrl_avatar() {
        return url_avatar;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPuesto() {
        return puesto;
    }

    public String getApi_token() {
        return api_token;
    }

    public String getFcm_token() {
        return fcm_token;
    }
}
