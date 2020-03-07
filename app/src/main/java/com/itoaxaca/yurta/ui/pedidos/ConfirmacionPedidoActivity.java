package com.itoaxaca.yurta.ui.pedidos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.itoaxaca.yurta.LoginActivity;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.adapter.ApiAdapter;
import com.itoaxaca.yurta.adapter.CheckoutPedidoAdapter;
import com.itoaxaca.yurta.dataBase.DataBaseHandler;
import com.itoaxaca.yurta.pojos.Material;
import com.itoaxaca.yurta.pojos.Pedido;
import com.itoaxaca.yurta.pojos.Usuario;
import com.itoaxaca.yurta.preferences.Preferences;
import com.itoaxaca.yurta.response.PedidoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmacionPedidoActivity extends AppCompatActivity {
    private ArrayList<Material> listMatPed;
    private RecyclerView recyclerView;
    private CheckoutPedidoAdapter adapter;
    private LinearLayout llAceptar;
    private ProgressBar progressBar;
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
        progressBar = findViewById(R.id.progressPedido);
        llAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) { //guardar y enviar pedido
                //savePedidoWithDetails();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ConfirmacionPedidoActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.dialog_confirm_password,null);
                TextInputEditText textInputEditText = view1.findViewById(R.id.ti_password);
                Button button = view1.findViewById(R.id.btnAceptar);
                TextInputLayout textInputLayout = view1.findViewById(R.id.til_password);
                ImageView btnClose = view1.findViewById(R.id.btn_close_dialog);

                mBuilder.setView(view1);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String email = Preferences
                                .getPeferenceString(
                                        ConfirmacionPedidoActivity.this,Preferences.PREFERENCE_USER_EMAIL);
                        String fcm_token = FirebaseInstanceId.getInstance().getToken(); ;
                        Call<Usuario> userCall = ApiAdapter
                                .getApiService().getLogin(email,textInputEditText.getText().toString().trim(),fcm_token);
                        userCall.enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                if(response.isSuccessful()){
                                    dialog.dismiss();
                                    progressBar.setVisibility(View.VISIBLE);
                                    savePedidoWithDetails();
                                }else{
                                    Snackbar.make(view2, "Contraseña incorrecta", Snackbar.LENGTH_LONG)
                                            .show();
                                    textInputLayout.setError("");
                                }
                                Log.i("CONFIR"," "+ response );
                            }
                            @Override
                            public void onFailure(Call<Usuario> call, Throwable t) {
                                Log.i("ERRORE",t.getMessage());
                            }
                        });
                    }
                });

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
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
            //ningun resultado
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


    private void savePedidoWithDetails(){
        Log.i("DETAILS","GUARDANDO DETALLES EN LOCAL");
        String api_token =  Preferences.getPeferenceString(this,Preferences.PREFERENCE_API_TOKEN);
        String fecha_conf = "2020/02/11";
        String obra = Preferences.getPeferenceString(this,Preferences.PREFERENCE_OBRA_ID) ;
        List<String> cantidades = new ArrayList<>();
        List<String> materiales = new ArrayList<>();

        for(Material m: listMatPed){
            cantidades.add(m.getCantidadSolicitada()+"");
            materiales.add(m.getId());
        }

        Call<PedidoResponse> pedidoCall =
                ApiAdapter.getApiService().savePedidoDetalles(api_token,fecha_conf,obra,
                cantidades,materiales);
        Log.i("PARAMS",": " + api_token + " " + obra + " " + fecha_conf);
        pedidoCall.enqueue(new Callback<PedidoResponse>() {
            @Override
            public void onResponse(Call<PedidoResponse> call, Response<PedidoResponse> response) {
                if(response.isSuccessful()) {
                    String idPedido = response.body().getId();
                    String fecha_p = response.body().getFecha_p();
                    String fecha_conf =response.body().getFecha_conf();
                    String estado = response.body().getEstado();


                    DataBaseHandler.getInstance(getApplicationContext())
                            .insert_pedido(idPedido,fecha_p,fecha_conf,estado,
                                    Preferences.getPeferenceString(getApplicationContext(),
                                            Preferences.PREFERENCE_OBRA_ID));
                    DataBaseHandler.getInstance(getApplicationContext())
                            .insertDetallesP(listMatPed,idPedido);
                    //progressBar.setVisibility(View.GONE);

                    dialogSucces();
                    //finish();
                }else{
                    Log.i("ERROR"," DE RED");
                }

                Log.i("RESPONSEPED ",response+"");
            }

            @Override
            public void onFailure(Call<PedidoResponse> call, Throwable t) {
                Log.e("ERROR", "ERROR" + t.getLocalizedMessage() + "-" +t.getMessage());
            }
        });

    }


    public void dialogSucces() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ConfirmacionPedidoActivity.this);
        View view1 = getLayoutInflater().inflate(R.layout.dialog_succes, null);
        Button btnOk = view1.findViewById(R.id.btn_succes_ok);
        mBuilder.setView(view1);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                setResult(RESULT_OK, null);
                finish();

            }
        });
    }
}
