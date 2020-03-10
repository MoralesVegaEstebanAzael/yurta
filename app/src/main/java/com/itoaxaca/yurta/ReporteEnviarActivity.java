package com.itoaxaca.yurta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.Api;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.itoaxaca.yurta.adapter.ApiAdapter;
import com.itoaxaca.yurta.adapter.CheckoutReportAdapter;
import com.itoaxaca.yurta.dataBase.DataBaseHandler;
import com.itoaxaca.yurta.pojos.Almacen;
import com.itoaxaca.yurta.pojos.Material;
import com.itoaxaca.yurta.pojos.Usuario;
import com.itoaxaca.yurta.preferences.Preferences;
import com.itoaxaca.yurta.response.AlmacenResponse;
import com.itoaxaca.yurta.ui.pedidos.ConfirmacionPedidoActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReporteEnviarActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Almacen> almacenList;
    private CheckoutReportAdapter reporteAdapter;
    private LinearLayout linearLayout;
    private String api_token;
    private String obra;
    private String userID;

    private ArrayList<String> idList;
    private ArrayList<String> cantidadesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_enviar);
        init();
    }

    private void init(){
        api_token = Preferences.getPeferenceString(this,Preferences.PREFERENCE_API_TOKEN);
        obra = Preferences.getPeferenceString(this,Preferences.PREFERENCE_OBRA_ID);
        userID = Preferences.getPeferenceString(this,Preferences.PREFERENCE_USER_ID);

        Intent i = getIntent();
        almacenList= (ArrayList<Almacen>) i
                .getSerializableExtra("LISTALMACEN");
        recyclerView =  findViewById(R.id.rv_report_mat);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        reporteAdapter = new CheckoutReportAdapter(getApplicationContext(),almacenList);
        recyclerView.setAdapter(reporteAdapter);
        reporteAdapter.notifyDataSetChanged();
        linearLayout = findViewById(R.id.llEnviar);

        idList = new ArrayList<>();
        cantidadesList =new ArrayList<>();


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idsCantidades();
                AlertDialog.Builder mBuilder =
                        new AlertDialog.Builder(ReporteEnviarActivity.this);
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
                    public void onClick(View view2) {

                        String email = Preferences
                                .getPeferenceString(
                                        ReporteEnviarActivity.this,Preferences.PREFERENCE_USER_EMAIL);
                        String fcm_token = FirebaseInstanceId.getInstance().getToken(); ;
                        Call<Usuario> userCall = ApiAdapter
                                .getApiService().getLogin(email,textInputEditText.getText().toString().trim(),fcm_token);
                        userCall.enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                if(response.isSuccessful()){
                                    dialog.dismiss();
                                    sendUpdate();
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
    }


    private void idsCantidades(){
        for(Almacen a:almacenList){
            idList.add(a.getId());
            int existencias = Integer.parseInt(a.getCantidad());
            int update = existencias - a.getUtilizado();
            cantidadesList.add(update+"");
        }
    }

    ArrayList<com.itoaxaca.yurta.response.Almacen> almacenUpdate = new ArrayList<>();
    private void sendUpdate(){
        Call<String> call = ApiAdapter.getApiService().sendReport(api_token,obra,idList,cantidadesList);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Log.i("RESPUESTA", response.body().toString());
                    getUpdate();
                }
                Log.i("REPORTE",response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("ERROR",t.getMessage());
            }
        });

    }

    private void getUpdate(){
        Call<AlmacenResponse> almacenCall = ApiAdapter.getApiService()
                .getAlmacenObra(api_token,userID);

        almacenCall.enqueue(new Callback<AlmacenResponse>() {
            @Override
            public void onResponse(Call<AlmacenResponse> call, Response<AlmacenResponse> response) {
                if(response.isSuccessful()){
                    almacenUpdate = response.body().getAlmacenes();
                    updateLocalDB();
                    dialogSucces();
                }

                Log.i("UPDATE",response.toString());
            }

            @Override
            public void onFailure(Call<AlmacenResponse> call, Throwable t) {
                Log.i("ERROR",t.getMessage());
            }
        });
    }

    public void dialogSucces() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ReporteEnviarActivity.this);
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


    private void updateLocalDB(){
        DataBaseHandler.getInstance(this).insertAlmacen(almacenUpdate);
    }
}
