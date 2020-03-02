package com.itoaxaca.yurta.ui.pedidos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.adapter.ApiAdapter;
import com.itoaxaca.yurta.adapter.InviteContactAdapter;
import com.itoaxaca.yurta.adapter.MaterialAdapter;
import com.itoaxaca.yurta.pojos.Material;
import com.itoaxaca.yurta.preferences.Preferences;
import com.itoaxaca.yurta.response.Materiales;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MaterialActivity extends AppCompatActivity implements View.OnClickListener {
    private InviteContactAdapter materialAdapter;
    private RecyclerView recyclerView;
    private List<Material> materialArrayList;
    private CardView cardViewPetreos,cardViewMet,cardViewSin,cardViewOrg;
    private List<Material> listPetreos;
    private List<Material> listMetal;
    private List<Material> listSinteticos;
    private List<Material> listOrganicos;
    private TextView tvcategories;
    private List<Material> listAllMaterials;
    private Chip chipPetreo,chipOrg,chipTodo,chipSint,chipMet;

    public List<String> mEmailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        recyclerView = findViewById(R.id.rvMateriales);
        materialArrayList = new ArrayList<>();
        listMetal = new ArrayList<>();
        listPetreos = new ArrayList<>();
        listSinteticos = new ArrayList<>();
        listOrganicos = new ArrayList<>();
        tvcategories = findViewById(R.id.tvcategories);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getMateriales();



        //materialAdapter = new MaterialAdapter(this,materialArrayList);
        //recyclerView.setAdapter(materialAdapter);



        cardViewOrg = findViewById(R.id.cvCategoriaOrg);
        cardViewMet = findViewById(R.id.cvCategoriaMetal);
        cardViewPetreos = findViewById(R.id.cvCategoriaPetreos);
        cardViewSin = findViewById(R.id.cvCategoriaSint);

        chipMet = findViewById(R.id.chipMet);
        chipSint = findViewById(R.id.chipSint);
        chipOrg = findViewById(R.id.chipOrg);
        chipPetreo = findViewById(R.id.chipPetreos);
        chipTodo = findViewById(R.id.chipTodo);

        chipMet.setOnClickListener(this);
        chipSint.setOnClickListener(this);
        chipOrg.setOnClickListener(this);
        chipPetreo.setOnClickListener(this);
        chipTodo.setOnClickListener(this);

        /* cardViewOrg.setOnClickListener(this);
        cardViewMet.setOnClickListener(this);
        cardViewPetreos.setOnClickListener(this);
        cardViewSin.setOnClickListener(this);
        tvcategories.setOnClickListener(this);*/
    }


    private void getMateriales() {
        String api_token= Preferences.getPeferenceString(this,Preferences.PREFERENCE_API_TOKEN);
        Call<Materiales> materialCall = ApiAdapter
                .getApiService().getMateriales(api_token);
        materialCall.enqueue(new MaterialCall());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Buscar material");
        // searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
               //-- materialAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                //--materialAdapter.getFilter().filter(query);
                if (materialAdapter != null) {
                    materialAdapter.getFilter().filter(query);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onClick(View view) {
        materialAdapter.all=false;
        switch (view.getId()){
            case R.id.chipPetreos:
                materialAdapter.categoria = "Pétreos";
                break;
            case R.id.chipMet:
                materialAdapter.categoria = "Metálicos";
                break;
            case R.id.chipSint:
                materialAdapter.categoria = "Sintéticos";
                break;
            case R.id.chipOrg:
                materialAdapter.categoria = "Orgánicos";
                break;
            case R.id.chipTodo:
                materialAdapter.all = true;
                break;
        }
    }

    private void setAdapter(){
        if (!materialArrayList.isEmpty()) {
            materialAdapter = new InviteContactAdapter(this, materialArrayList);
            recyclerView.setAdapter(materialAdapter);
            materialAdapter.setOnItemClickListener(new InviteContactAdapter.onItemClickListener() {
                @Override
                public void onClick(Material contact) {
                    mEmailList.add(contact.getDescripcion());
                }
            });
        } else {
           Log.i("vacia","vacia");
        }
    }
    
    private class MaterialCall implements retrofit2.Callback<Materiales> {
        @Override
        public void onResponse(Call<Materiales> call, Response<Materiales> response) {
            if(response.isSuccessful()){
                Material material;
                ArrayList<com.itoaxaca.yurta.response.Material> list = response.body().getMateriales();
                for(com.itoaxaca.yurta.response.Material m: list){
                    material = new Material(m.getId(),m.getDescripcion(),m.getUnidad(),
                            m.getTipo(),m.getMarca(),m.getPrecio_unitario(),m.getUrl_imagen(),false);
                    materialArrayList.add(material);
                    Log.i("LISTA","tam: "+m.getDescripcion());

                }

                setAdapter();
               // materialAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<Materiales> call, Throwable t) {
            Log.i("FAILURE","ERROR: "+t.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_confirmar:
                Intent intent = new Intent(this, ConfirmacionPedidoActivity.class);
                ArrayList<Material> list = new ArrayList<>();
                pedidos(list);
                intent.putExtra("LIST", list);
                this.startActivity(intent);
                break;
        }
        return true;
    }
    private void pedidos(ArrayList<Material> list){
        for(Material m:materialArrayList){
            if(m.isSelected()){
                list.add(m);
                Log.i("XCANT",m.getCantidadSolicitada()+"");
            }
        }
    }


    private void categories(){
        for(Material m:materialArrayList){
            if(m.getTipo().equals("Pétreos")){
                listPetreos.add(m);
                Log.i("PETREO"," --");
            }else if(m.getTipo().equals("Metálicos")){
                Log.i("METAL"," --");
                listMetal.add(m);
            }else if(m.getTipo().equals("Orgánicos")){
                listOrganicos.add(m);
            }else if(m.getTipo().equals("Sintéticos")){
                listSinteticos.add(m);
            }
        }
    }

    private void mostrar(List<Material> l){
        for(Material m:l){
            Log.i("M--","m " +m.getTipo());
        }
    }
}
