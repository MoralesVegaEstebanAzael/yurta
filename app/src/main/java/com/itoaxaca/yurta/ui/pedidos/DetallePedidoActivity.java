package com.itoaxaca.yurta.ui.pedidos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.adapter.DetallePedAdapter;
import com.itoaxaca.yurta.dataBase.DataBaseHandler;
import com.itoaxaca.yurta.pojos.DetallePedido;
import com.itoaxaca.yurta.pojos.Pedido;

import java.util.ArrayList;

public class DetallePedidoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<DetallePedido> detallePedidoArrayList;
    private DetallePedAdapter detallePedAdapter;
    private Pedido pedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){


        pedido = (Pedido) getIntent().getSerializableExtra("OBJETO_PEDIDO");

        recyclerView = findViewById(R.id.rvDetalleMateriales);
        detallePedidoArrayList = new ArrayList<>();
        getDetalles();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        detallePedAdapter = new DetallePedAdapter(this,detallePedidoArrayList);
        recyclerView.setAdapter(detallePedAdapter);
        detallePedAdapter.notifyDataSetChanged();
    }


    private void getDetalles(){
        Cursor cursor = DataBaseHandler
                .getInstance(this)
                .getDetallePedido(pedido.getId());
        DetallePedido detalle;
        while(cursor.moveToNext()){
            detalle = new DetallePedido(cursor.getString(0),cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5));
            detallePedidoArrayList.add(detalle);
            Log.i("TAG-","OBTENIENDO DETALLES");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }

}
