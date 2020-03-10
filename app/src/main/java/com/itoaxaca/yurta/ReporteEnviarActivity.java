package com.itoaxaca.yurta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.itoaxaca.yurta.pojos.Almacen;
import com.itoaxaca.yurta.pojos.Material;

import java.util.ArrayList;
import java.util.List;

public class ReporteEnviarActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Almacen> almacenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_enviar);

    }

    private void init(){
        Intent i = getIntent();
        almacenList= (ArrayList<Almacen>) i
                .getSerializableExtra("LISTALMACEN");


       /* recyclerView =  findViewById(R.id.rvReporte);
        almacenList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        reporteAdapter = new ReporteAdapter(getApplicationContext(),almacenList);
        recyclerView.setAdapter(reporteAdapter);*/
    }
}
