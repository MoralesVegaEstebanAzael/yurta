package com.itoaxaca.yurta.ui.pedidos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.adapter.ApiAdapter;
import com.itoaxaca.yurta.adapter.InviteContactAdapter;
import com.itoaxaca.yurta.adapter.MaterialAdapter;
import com.itoaxaca.yurta.network.Network;
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
    private ProgressBar progressBar;
    private NestedScrollView scrollView;
    public List<String> mEmailList = new ArrayList<>();
    public static final int REQUEST_CODE =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(Network.isOnline(getApplicationContext())){
            Log.i("RED"," red habilidata");
            init();
        }else{
            Log.i("RED"," verifique su conexion a internet");
            dialogDanger();

        }
    }

    private void init(){
        recyclerView = findViewById(R.id.rvMateriales);
        materialArrayList = new ArrayList<>();
        listMetal = new ArrayList<>();
        listPetreos = new ArrayList<>();
        listSinteticos = new ArrayList<>();
        listOrganicos = new ArrayList<>();
        tvcategories = findViewById(R.id.tvcategories);
        progressBar = findViewById(R.id.progress);
        scrollView = findViewById(R.id.nestedS);

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
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
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
                View parentLayout = findViewById(R.id.fl_material);

                Intent intent = new Intent(this, ConfirmacionPedidoActivity.class);
                ArrayList<Material> list = new ArrayList<>();
                pedidos(list);
                if(!list.isEmpty()){
                    intent.putExtra("LIST", list);
                    this.startActivityForResult(intent, REQUEST_CODE);
                }else{
                    Snackbar.make(parentLayout, R.string.snackbar_materiales, Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
        }
        return true;
    }
    private void pedidos(ArrayList<Material> list){
        for(Material m:materialArrayList){
            if(m.isSelected() && m.getCantidadSolicitada()>0){
                list.add(m);
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






    public AlertDialog.Builder buildDialog(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Sin conexión a Internet.");
        builder.setMessage("No tienes conexión a Internet");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        return builder;
    }

    public void dialogDanger() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MaterialActivity.this);
        View view1 = getLayoutInflater().inflate(R.layout.dialog_danger,null);
        Button btnOk = view1.findViewById(R.id.btn_dialog_red_ok);
        Button btnConfiguracion = view1.findViewById(R.id.btn_dialog_red_conf);
        mBuilder.setView(view1);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                this.finish();

            }
        }
    }
}
