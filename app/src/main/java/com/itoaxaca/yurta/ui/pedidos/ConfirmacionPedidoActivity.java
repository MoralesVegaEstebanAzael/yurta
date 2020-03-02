package com.itoaxaca.yurta.ui.pedidos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.adapter.CheckoutPedidoAdapter;
import com.itoaxaca.yurta.pojos.Material;

import java.util.ArrayList;

public class ConfirmacionPedidoActivity extends AppCompatActivity {
    private ArrayList<Material> listMatPed;
    private RecyclerView recyclerView;
    private CheckoutPedidoAdapter adapter;
    private LinearLayout llAceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_pedido);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
         listMatPed= (ArrayList<Material>) i
                .getSerializableExtra("LIST");
            init();

    }



    private void init(){
        recyclerView = findViewById(R.id.rvDetPedido);
        llAceptar = findViewById(R.id.llAceptar);

        llAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if(!listMatPed.isEmpty()){
            adapter = new CheckoutPedidoAdapter(this,listMatPed);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            //
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
