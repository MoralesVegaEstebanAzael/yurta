package com.itoaxaca.yurta.response;

/**MATERIALES DE ALMACEN DE OBRA**/
public class Almacen {
    private String id_obra;
    private String material_id;
    private String material_descripcion;
    private String material_unidad;
    private String material_tipo;
    private String material_marca;
    private String material_cantidad;
    private String url_imagen;


    public String getId_obra() {
        return id_obra;
    }

    public void setId_obra(String id_obra) {
        this.id_obra = id_obra;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getMaterial_descripcion() {
        return material_descripcion;
    }

    public void setMaterial_descripcion(String material_descripcion) {
        this.material_descripcion = material_descripcion;
    }

    public String getMaterial_unidad() {
        return material_unidad;
    }

    public void setMaterial_unidad(String material_unidad) {
        this.material_unidad = material_unidad;
    }

    public String getMaterial_tipo() {
        return material_tipo;
    }

    public void setMaterial_tipo(String material_tipo) {
        this.material_tipo = material_tipo;
    }

    public String getMaterial_marca() {
        return material_marca;
    }

    public void setMaterial_marca(String material_marca) {
        this.material_marca = material_marca;
    }

    public String getMaterial_cantidad() {
        return material_cantidad;
    }

    public void setMaterial_cantidad(String material_cantidad) {
        this.material_cantidad = material_cantidad;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }
}
