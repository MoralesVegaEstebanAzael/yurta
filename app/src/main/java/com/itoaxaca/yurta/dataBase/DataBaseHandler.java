package com.itoaxaca.yurta.dataBase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static DataBaseHandler dbInstance;

    public static synchronized DataBaseHandler getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new DataBaseHandler(context.getApplicationContext());
        }
        return dbInstance;
    }

    private DataBaseHandler(@Nullable Context context,
                           @Nullable String name,
                           @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private DataBaseHandler(Context context){
       super(context, Constantes.DATA_BASE_NAME , null, Constantes.DATA_BASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_USUARIO);
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_OBRAS);
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_TIPO);
        //sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_MATERIAL);
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_PEDIDO);
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_DET_PEDIDO);
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_MATERIALES_OBRA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS favoritos");
        onCreate(sqLiteDatabase);
    }


    //consultas
    public void insertUser(String id,String name,String email,String telefono,
                         String puesto,String api_token){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT OR IGNORE INTO usuario VALUES(?, ?, ?,? ,? ,?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,name);
        statement.bindString(3,email);
        statement.bindString(4,telefono);
        statement.bindString(5,puesto);
        statement.bindString(6,api_token);
        statement.executeInsert();
    }


    public void insertObra(String id,String descripcion,String lat,String lng,
                           String fech_ini,String dependencia,String encargado,
                           String tipo_obra){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT OR IGNORE INTO obra VALUES(?, ?, ?,? ,? ,? ,?,?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,descripcion);
        statement.bindString(3,lat);
        statement.bindString(4,lng);
        statement.bindString(5,fech_ini);
        statement.bindString(6,dependencia);
        statement.bindString(7,encargado);
        statement.bindString(8,tipo_obra);
        statement.executeInsert();
    }


    public void insertTipoObra(String id,String descripcion){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT OR IGNORE INTO tipo VALUES(?, ?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,descripcion);
    }


    public void insertMaterial(String id,String descripcion,String unidad,String tipo,
                           String marca,String existencias,String precio_unitario,String proveedor){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT OR IGNORE INTO material VALUES(?, ?, ?,? ,? ,?,?,?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,descripcion);
        statement.bindString(3,unidad);
        statement.bindString(4,tipo);
        statement.bindString(5,marca);
        statement.bindString(6,existencias);
        statement.bindString(7,precio_unitario);
        statement.bindString(8,proveedor);
        statement.executeInsert();
    }

    public void insertPedido(String id,String fecha_p,String fecha_conf,String estado,String obra){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT OR IGNORE INTO pedido VALUES(?, ?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,fecha_p);
        statement.bindString(3,fecha_conf);
        statement.bindString(4,estado);
        statement.bindString(5,obra);
        statement.executeInsert();
    }

    public void insertDetPedido(String cantidad,String id_pedido,String ped_material){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT OR IGNORE INTO det_ped VALUES(?, ?,?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,cantidad);
        statement.bindString(2,id_pedido);
        statement.bindString(3,ped_material);
        statement.executeInsert();
    }


    // materiales obra
    public void insertAlmacen(String obra,String id,String descripcion,String unidad,String tipo,String marca
        ,String cantidad){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT OR IGNORE INTO materiales_obra VALUES(?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,obra);
        statement.bindString(2,id);
        statement.bindString(3,descripcion);
        statement.bindString(4,unidad);
        statement.bindString(5,tipo);
        statement.bindString(6,marca);
        statement.bindString(7,cantidad);
        statement.executeInsert();
    }


}
