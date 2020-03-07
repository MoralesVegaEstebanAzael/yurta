package com.itoaxaca.yurta.response;

public class Notificacion {
    private String id;
    private String type;
    private String notifiable_type;
    private String notifiable_id;
    private Data data;
    private String read_at;
    private String created_at;
    private String updated_at;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotifiable_type() {
        return notifiable_type;
    }

    public void setNotifiable_type(String notifiable_type) {
        this.notifiable_type = notifiable_type;
    }

    public String getNotifiable_id() {
        return notifiable_id;
    }

    public void setNotifiable_id(String notifiable_id) {
        this.notifiable_id = notifiable_id;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getRead_at() {
        return read_at;
    }

    public void setRead_at(String read_at) {
        this.read_at = read_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
