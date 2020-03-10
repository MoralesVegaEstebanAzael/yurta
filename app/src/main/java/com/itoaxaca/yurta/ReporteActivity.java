package com.itoaxaca.yurta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.snackbar.Snackbar;
import com.itoaxaca.yurta.adapter.ReporteAdapter;
import com.itoaxaca.yurta.dataBase.DataBaseHandler;
import com.itoaxaca.yurta.pojos.Almacen;
import com.itoaxaca.yurta.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import static com.itoaxaca.yurta.ui.pedidos.MaterialActivity.REQUEST_CODE;

public class ReporteActivity extends AppCompatActivity {
    private List<Almacen> almacenList;
    private ReporteAdapter reporteAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        init();
    }

    private void init(){
        recyclerView =  findViewById(R.id.rvReporte);
        almacenList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        reporteAdapter = new ReporteAdapter(getApplicationContext(),almacenList);
        recyclerView.setAdapter(reporteAdapter);


        AsyntaskLoadDB asyncTaskLoadDB = new AsyntaskLoadDB();
        asyncTaskLoadDB.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Buscar material");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (reporteAdapter != null) {
                    reporteAdapter.getFilter().filter(query);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_confirmar:
                View parentLayout = findViewById(R.id.fl_material);

                Intent intent = new Intent(this, ReporteEnviarActivity.class);
                ArrayList<Almacen> list = new ArrayList<>();
                reportAlmacen(list);
                if(!list.isEmpty()){
                    intent.putExtra("LISTALMACEN", list);
                    this.startActivityForResult(intent, REQUEST_CODE);
                }else{
                    Snackbar.make(parentLayout, R.string.snackbar_alamen, Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
        }
        return true;
    }

    private class AsyntaskLoadDB extends AsyncTask<Void,Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            String obra= Preferences.getPeferenceString(getApplicationContext(),Preferences.PREFERENCE_OBRA_ID);
            Cursor c = DataBaseHandler.getInstance(getApplicationContext()).getAllAlmacen(obra);
            while (c.moveToNext()){
                Log.i("CONSULTA"," consulta sql");
                Almacen a = new Almacen(c.getString(0),c.getString(1)
                        ,c.getString(2),c.getString(3),c.getString(4),
                        c.getString(5),c.getString(6),c.getString(7));
                almacenList.add(a);
            }

            Log.i("TAM","  s"+almacenList.size());
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            reporteAdapter.notifyDataSetChanged();
        }
        @Override
        protected void onCancelled() {
        }
    }

    private void reportAlmacen(ArrayList<Almacen> list){
        for(Almacen m:almacenList){
            if(m.getUtilizado()!=0){
                list.add(m);
            }
        }
    }
}
