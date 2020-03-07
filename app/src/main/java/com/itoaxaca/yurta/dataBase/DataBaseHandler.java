package com.itoaxaca.yurta.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itoaxaca.yurta.pojos.DetallePedido;
import com.itoaxaca.yurta.pojos.Material;
import com.itoaxaca.yurta.response.Almacen;
import com.itoaxaca.yurta.response.DetPedidoResponse;
import com.itoaxaca.yurta.response.Obra;
import com.itoaxaca.yurta.response.PedidoResponse;
import com.itoaxaca.yurta.response.TipoObra;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static DataBaseHandler dbInstance;

    public static  DataBaseHandler getInstance(Context context) {
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

   /* private DataBaseHandler(Context context){
       super(context, Constantes.DATA_BASE_NAME , null, Constantes.DATA_BASE_VERSION);
    }*/

    public DataBaseHandler(Context context){
        super(context, Constantes.DATA_BASE_NAME , null, Constantes.DATA_BASE_VERSION);
        //database = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_USUARIO);
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_OBRAS);
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_TIPO);
        //sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_MATERIAL);
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_PEDIDO);
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_DET_PEDIDO);
        sqLiteDatabase.execSQL(Constantes.CREATE_TABLE_ALMACEN_OBRA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS favoritos");
        onCreate(sqLiteDatabase);
    }


    //consultas
    public void insertUser(String id,String name,String email,String telefono,
                         String puesto,String api_token,String url_avatar){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT OR IGNORE INTO usuario VALUES(?, ?, ?,? ,? ,?,?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,name);
        statement.bindString(3,email);
        statement.bindString(4,telefono);
        statement.bindString(5,puesto);
        statement.bindString(6,api_token);
        statement.bindString(7,url_avatar);
        statement.executeInsert();
    }


    public void insertObra(String id,String descripcion,String lat,String lng,
                           String fech_ini,String fech_fin,String dependencia,String encargado,
                           String tipo_obra,String tipo_descripcion){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT OR IGNORE INTO obras VALUES(?, ?, ?,? ,? ,? ,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(query);

        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,descripcion);
        statement.bindString(3,lat);
        statement.bindString(4,lng);
        statement.bindString(5,fech_ini);
        statement.bindString(6,fech_fin);
        statement.bindString(7,dependencia);
        statement.bindString(8,encargado);
        statement.bindString(9,tipo_obra);
        statement.bindString(10,tipo_descripcion);
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
        String query = "INSERT OR IGNORE INTO pedido VALUES(?, ?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,fecha_p);
        statement.bindString(3,fecha_conf);
        statement.bindString(4,estado);
        statement.bindString(5,obra);
        statement.executeInsert();
    }

    public void insertDetPedido(String cantidad,String id_pedido,String id_material,String descripcion,
                                String unidad,String marca){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT OR IGNORE INTO detalle_pedido VALUES(?, ?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,cantidad);
        statement.bindString(2,id_pedido);
        statement.bindString(3,id_material);
        statement.bindString(4,descripcion);
        statement.bindString(5,unidad);
        statement.bindString(6,marca);
        statement.executeInsert();
    }
    // materiales obra
    public void insertAlmacen(String obra,String id,String descripcion,String unidad,String tipo,String marca
        ,String cantidad,String url_imagen){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT OR IGNORE INTO materiales_obra VALUES(?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1,obra);
        statement.bindString(2,id);
        statement.bindString(3,descripcion);
        statement.bindString(4,unidad);
        statement.bindString(5,tipo);
        statement.bindString(6,marca);
        statement.bindString(7,cantidad);
        statement.bindString(8,url_imagen);
        statement.executeInsert();
    }

    public void insertObras(ArrayList<Obra> obraArrayList,String encargado){
        SQLiteDatabase db = getReadableDatabase();
        for (Obra o:obraArrayList){
            Log.i("ENTRA","FOR");
            upsertObras(o.getId(),o.getDescripcion(),o.getLat(),o.getLng(),o.getFech_ini(),
                    o.getFech_fin(),o.getDependencia(),encargado,o.getTipo_obra(),
                    o.getTipo_descripcion(),db);
        }
    }

    public void insertAlmacen(ArrayList<Almacen> almacenArrayList){
        SQLiteDatabase db = getReadableDatabase();
        for(Almacen a:almacenArrayList){
            upsertAlmacen(a.getId_obra(),a.getMaterial_id(),a.getMaterial_descripcion(),
                    a.getMaterial_unidad(),a.getMaterial_tipo(),a.getMaterial_marca()
                    ,a.getMaterial_cantidad(),a.getUrl_imagen(),db);
        }
    }
    public void insertPedidos(ArrayList<PedidoResponse> pedidoResponseArrayList){
        SQLiteDatabase db = getReadableDatabase();
        for(PedidoResponse p:pedidoResponseArrayList){
            upsertPedido(p.getId(),p.getFecha_p(),p.getFecha_conf(),p.getEstado(),p.getObra(),db);
        }
    }

    public void insertDetallesPedido(ArrayList<DetPedidoResponse> detPedidoArrayList){
        for(DetPedidoResponse d: detPedidoArrayList){
            insertDetPedido(d.getCantidad(),d.getId_pedido(),d.getId_material(),d.getDescripcion(),
                    d.getUnidad(),d.getMarca());
        }
    }
    public void insertTiposObra(ArrayList<TipoObra> tipoObraArrayList){
        SQLiteDatabase db = getReadableDatabase();
        for (TipoObra t:tipoObraArrayList){
            upsertTipoObra(t.getId(),t.getDescripcion(),db);
        }
    }

    //buscar obras por ID de usuario
    public Cursor getObrasUserID(String idEncargado){
        SQLiteDatabase db = getReadableDatabase();
        //String sql = "SELECT * FROM obras where encargado = ''";
        Cursor c = db.rawQuery("SELECT * FROM obras WHERE TRIM(encargado) = '"+idEncargado.trim()+"'", null);
      //  SQLiteDatabase db = getReadableDatabase();
        return c;
    }

    //buscar obra por id
    public Cursor getObraID(String idObra){
        SQLiteDatabase db = getReadableDatabase();
        //String sql = "SELECT * FROM obras where id = ''";
        Cursor c = db.rawQuery("SELECT * FROM obras WHERE TRIM(id) = '"+idObra.trim()+"'", null);
        //  SQLiteDatabase db = getReadableDatabase();
        return c;
    }


    public long countMateriales(String idObra){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement s = db.compileStatement( "select count(*) from materiales_obra where obra='" + idObra + "';" );
        long count = s.simpleQueryForLong();
        return count;
    }

    //obtener pedidos de obra
    public Cursor getPedidos(String obra){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM pedido WHERE TRIM(obra)" +
                " = '"+obra.trim()+"'", null);
        return c;
    }
    //obtener detalles de pedido
    public Cursor getDetallePedido(String pedido){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM detalle_pedido WHERE TRIM(id_pedido) = '"+pedido.trim()+"'", null);
        return c;
    }

    public Cursor getAlmacen(String obra,String categoria){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM materiales_obra WHERE TRIM(obra) " +
                "= '"+obra.trim()+"' AND TRIM(tipo) = '"+categoria.trim()+"'", null);
        return c;
    }


    public void upsertAlmacen(String obra,String _id,String descripcion,String unidad,String tipo,
                              String marca,String cantidad,String url_imagen,SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("obra",obra);
        values.put("id",_id);
        values.put("descripcion",descripcion);
        values.put("unidad",unidad);
        values.put("tipo",tipo);
        values.put("marca",marca);
        values.put("cantidad",cantidad);
        values.put("url_imagen",url_imagen);
        int id = getIDAlmacen(_id,db);
        Log.i("BUSCANDOID",";"+id);
        if(id==-1){
            db.insert("materiales_obra", null, values);
        }
        else{
            db.update("materiales_obra", values
                    , "id=?", new String[]{Integer.toString(id)});
        }
    }

    private int getIDAlmacen(String id,SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT * FROM materiales_obra " +
                "WHERE TRIM(id) = '"+id.trim()+"'", null);

        if (c.moveToFirst()) //if the row exist then return the id
            return c.getInt(c.getColumnIndex("id"));
        return -1;
    }

    public void upsertObras(String _id,String descripcion,String lat,String lng,
                            String fech_ini,String fech_fin,String dependencia,String encargado,
                            String tipo_obra,String tipo_descripcion,SQLiteDatabase db){

        ContentValues values = new ContentValues();
        values.put("id",_id);
        values.put("descripcion",descripcion);
        values.put("lat",lat);
        values.put("lng",lng);
        values.put("fech_ini",fech_ini);
        values.put("fech_fin",fech_fin);
        values.put("dependencia",dependencia);
        values.put("encargado",encargado);
        values.put("tipo_obra",tipo_obra);
        values.put("tipo_descripcion",tipo_descripcion);

        int id = getIDobra(_id,db);

        if(id==-1){
            db.insert("obras", null, values);
        }
        else{
            db.update("obras", values
                    , "id=?", new String[]{Integer.toString(id)});
        }
    }

    private int getIDobra(String id,SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT * FROM obras " +
                "WHERE TRIM(id) = '"+id.trim()+"'", null);

        if (c.moveToFirst()) //if the row exist then return the id
            return c.getInt(c.getColumnIndex("id"));
        return -1;
    }

    public void upsertTipoObra(String _id,String descripcion,SQLiteDatabase db){
        Log.i("ENTRA","ENTRA");
        ContentValues values = new ContentValues();
        values.put("id",_id);
        values.put("descripcion",descripcion);
        int id = getIDtipoObra(_id,db);
        Log.i("IDTIPO","id"+id);
        if(id==-1){
            db.insert("tipo", null, values);
        }
        else{
            db.update("tipo", values
                    , "id=?", new String[]{Integer.toString(id)});
        }
    }

    private int getIDtipoObra(String id,SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT * FROM tipo " +
                "WHERE TRIM(id) = '"+id.trim()+"'", null);
        if (c.moveToFirst())
            return c.getInt(c.getColumnIndex("id"));
        return -1;
    }


    public void upsertPedido(String _id,String fecha_p,String fecha_conf,String estado,String obra,SQLiteDatabase db){
        //Log.i("ENTRA","ENTRA");
        ContentValues values = new ContentValues();
        values.put("id",_id);
        values.put("fecha_p",fecha_p);
        values.put("fecha_conf",fecha_conf);
        values.put("estado",estado);
        values.put("obra",obra);

        int id = getIDpedido(_id,db);
        Log.i("IDPEDIDO","id"+id);
        if(id==-1){
            db.insert("pedido", null, values);
        }
        else{
            db.update("pedido", values
                    , "id=?", new String[]{Integer.toString(id)});
        }
    }

    private int getIDpedido(String id,SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT * FROM pedido " +
                "WHERE TRIM(id) = '"+id.trim()+"'", null);
        if (c.moveToFirst())
            return c.getInt(c.getColumnIndex("id"));
        return -1;
    }


    public void upsertDetPedido(String _id,String fecha_p,String fecha_conf,String estado,String obra,SQLiteDatabase db){
        //Log.i("ENTRA","ENTRA");
        ContentValues values = new ContentValues();
        values.put("id",_id);
        values.put("fecha_p",fecha_p);
        values.put("fecha_conf",fecha_conf);
        values.put("estado",estado);
        values.put("obra",obra);

        int id = getIDpedido(_id,db);
        Log.i("IDPEDIDO","id"+id);
        if(id==-1){
            db.insert("pedido", null, values);
        }
        else{
            db.update("pedido", values
                    , "id=?", new String[]{Integer.toString(id)});
        }
    }

    public void insert_pedido(String _id,String fecha_p,String fecha_conf,String estado,String obra){
        SQLiteDatabase db = getReadableDatabase();
        //Log.i("ENTRA","ENTRA");
        ContentValues values = new ContentValues();
        values.put("id",_id);
        values.put("fecha_p",fecha_p);
        values.put("fecha_conf",fecha_conf);
        values.put("estado",estado);
        values.put("obra",obra);

        db.insert("pedido", null, values);
    }



    public void insertDetallesP(ArrayList<Material> detallesLis,String pedido){
        SQLiteDatabase db = getReadableDatabase();
        for (Material d:detallesLis) {
            insert_detalle_pedido(d.getCantidadSolicitada()+"",pedido,d.getId(),
                    d.getDescripcion(),d.getUnidad(),d.getMarca(),d.getUrl_imagen(),db);
        }
    }
    public void insert_detalle_pedido(String cantidad,String idpedido,String idmaterial,String descripcion
            , String unidad,String marca,String url_imagen,SQLiteDatabase db){

        ContentValues values = new ContentValues();
        values.put("cantidad",cantidad);
        values.put("id_pedido",idpedido);
        values.put("id_material",idmaterial);
        values.put("descripcion",descripcion);
        values.put("unidad",unidad);
        values.put("marca",marca);
        values.put("url_imagen",url_imagen);
        db.insert("detalle_pedido", null, values);
    }


    public void upsertDetallePedido(String cantidad,String idpedido,String idmaterial,String descripcion
            , String unidad,String marca,String url_imagen,SQLiteDatabase db){

        ContentValues values = new ContentValues();
        values.put("cantidad",cantidad);
        values.put("id_pedido",idpedido);
        values.put("id_material",idmaterial);
        values.put("descripcion",descripcion);
        values.put("unidad",unidad);
        values.put("marca",marca);
        values.put("url_imagen",url_imagen);

        int id = getIDdetallePedido(idpedido,idmaterial,cantidad,db);
        Log.i("IDDETALLEPEDIDO","id"+id);
        if(id==-1){
            db.insert("detalle_pedido", null, values);
        }else{
           // db.update("detalle_pedido", values
             //       , "id=?", new String[]{Integer.toString(id)});
        }

       // db.insert("detalle_pedido", null, values);
    }

    private int getIDdetallePedido(String id_pedido,String id_material,String cantidad,SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT * FROM detalle_pedido " +
                "WHERE TRIM(id_pedido) = '"+id_pedido.trim()+"' " +
                "AND TRIM(id_material) = '"+id_material+"' AND TRIM(cantidad)= '"+cantidad+"'", null);
        if (c.moveToFirst())
            return c.getInt(c.getColumnIndex("id_pedido"));
        return -1;
    }


    public void upsertDetallePedido(ArrayList<DetPedidoResponse> arrayList){
        SQLiteDatabase db = getReadableDatabase();
        for(DetPedidoResponse d:arrayList){
                upsertDetallePedido(d.getCantidad(),d.getId_pedido(),d.getId_material(),d.getDescripcion(),
                        d.getUnidad(),d.getMarca(),d.getUrl_imagen(),db);
        }
    }
}


