package com.itoaxaca.yurta.dataBase;

public class Constantes {

    public static final String DATA_BASE_NAME="yurta_app.db";
    public static final int DATA_BASE_VERSION=1;


    //tabla usuario
    public static final String CREATE_TABLE_USUARIO="CREATE TABLE IF NOT EXISTS usuario " +
            "(id STRING PRIMARY KEY, name STRING, email STRING,telefono STRING,puesto STRING,api_token STRING)";
    //tabla obras
    public static final String CREATE_TABLE_OBRAS="CREATE TABLE IF NOT EXISTS obras "+
            "(id STRING PRIMARY KEY,descripcion STRING,lat STRING,lng STRING,fech_ini STRING,dependencia STRING," +
            "encargado STRING,tipo_obra STRING)";




    //table tipo de obra
    public static final String CREATE_TABLE_TIPO="CREATE TABLE IF NOT EXISTS tipo " +
            "(id STRING PRIMARY KEY,descripcion STRING";
    //tabla pedido
    public static final String CREATE_TABLE_PEDIDO="CREATE TABLE IF NOT EXISTS pedido "+
            "(id STRING PRIMARY KEY,fecha_p STRING,fecha_conf STRING,estado STRING,obra STRING)";
    //tabla detalle de pedido
    public static final String CREATE_TABLE_DET_PEDIDO= "CREATE TABLE IF NOT EXISTS det_ped "+
            "(cantidad STRING,id_pedido STRING,ped_material STRING)";
    //tabla material
    public static final String CREATE_TABLE_MATERIAL="CREATE TABLE IF NOT EXISTS material "+
            "(id STRING PRIMARY KEY,descripcion STRING,unidad STRING,tipo STRING,marca STRING," +
            "existencias STRING,precio_unitario STRING,proveedor STRING)";
    //tabla materiales obra(ALMACEN)
    public static final String CREATE_TABLE_MATERIALES_OBRA = "CREATE TABLE IF NOT EXISTS materiales_obra "+
            "(id STRING,descripcion STRING,unidad STRING,tipo STRING,marca STRING,cantidad STRING)";




}

