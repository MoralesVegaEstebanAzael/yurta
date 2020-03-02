package com.itoaxaca.yurta.dataBase;

public class Constantes {

    public static final String DATA_BASE_NAME="yurta_app.db";
    public static final int DATA_BASE_VERSION=1;

    //tabla usuario
    public static final String CREATE_TABLE_USUARIO="CREATE TABLE IF NOT EXISTS usuario " +
            "(id STRING PRIMARY KEY, name STRING, email STRING,telefono STRING" +
            ",puesto STRING,api_token STRING,url_avatar STRING)";
    //tabla obras
    public static final String CREATE_TABLE_OBRAS="CREATE TABLE IF NOT EXISTS obras "+
            "(id STRING PRIMARY KEY,descripcion STRING,lat STRING,lng STRING" +
            ",fech_ini STRING,fech_fin STRING,dependencia STRING," +
            "encargado STRING,tipo_obra STRING,tipo_descripcion STRING)";
    //table tipo de obra
    public static final String CREATE_TABLE_TIPO="CREATE TABLE IF NOT EXISTS tipo " +
            "(id STRING PRIMARY KEY,descripcion STRING)";

    /**pedidos desde la app**/
    //tabla pedido
    public static final String CREATE_TABLE_PEDIDO="CREATE TABLE IF NOT EXISTS pedido "+
            "(id STRING PRIMARY KEY,fecha_p STRING,fecha_conf STRING,estado STRING,obra STRING)";

    //tabla detalle de pedido
    public static final String CREATE_TABLE_DET_PEDIDO= "CREATE TABLE IF NOT EXISTS detalle_pedido "+
            "(cantidad STRING,id_pedido STRING,id_material STRING,descripcion STRING," +
            "unidad STRING,marca STRING,url_imagen STRING)";
   /* //tabla material
    public static final String CREATE_TABLE_MATERIAL="CREATE TABLE IF NOT EXISTS material "+
            "(id STRING PRIMARY KEY,descripcion STRING,unidad STRING,tipo STRING,marca STRING," +
            "existencias STRING,precio_unitario STRING,proveedor STRING)";*/
    //tabla materiales obra(ALMACEN)
    public static final String CREATE_TABLE_ALMACEN_OBRA = "CREATE TABLE IF NOT EXISTS materiales_obra "+
            "(obra String,id STRING PRIMARY KEY,descripcion STRING,unidad STRING,tipo STRING" +
            ",marca STRING,cantidad STRING,url_imagen STRING)";




}

