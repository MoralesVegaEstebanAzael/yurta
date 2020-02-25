package com.itoaxaca.yurta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputEditText;
import com.itoaxaca.yurta.preferences.Preferences;
import com.itoaxaca.yurta.pojos.Usuario;
import com.itoaxaca.yurta.response.Obra;
import com.itoaxaca.yurta.response.Obras;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText email;
    private TextInputEditText password;
    private Button btnLogin;
    private RadioButton radioButton;
    private boolean isCheckedRB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //verificar si ya esta logeado
        if(Preferences.getPeferenceBoolean(this,Preferences.PREFERENCE_ESTADO_SESION)){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        init();
    }

    private void init(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        radioButton = findViewById(R.id.RBSesion);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        isCheckedRB= radioButton.isChecked();
        //radio button mantener sesion
        radioButton.setOnClickListener(this);
    }
    private void login(){
        try {
            String correo = email.getText().toString().trim();
            String contraseña = password.getText().toString().trim();
            Call<Usuario> userCall = ApiAdapter
                    .getApiService().getLogin(correo,contraseña);
            userCall.enqueue(new UserCallback());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("ERROR",e.getMessage());
        }
    }

    private void insertDB(){

        Call<Obras> obrasCall = ApiAdapter.getApiService().getObras("1");
        obrasCall.enqueue(new Callback<Obras>() {
            @Override
            public void onResponse(Call<Obras> call, Response<Obras> response) {
                if(response.isSuccessful()){
                    Obras obras = response.body();
                    List<Obra> ob = obras.getObras();
                    for(Obra obr: ob){
                        Log.i("DES",obr.getDescripcion());
                    }
                }
                Log.i("RES",response.toString());
            }
            @Override
            public void onFailure(Call<Obras> call, Throwable t) {
                Log.i("ERROR",t.getLocalizedMessage());
            }
        });


    }
    private void obras(){
        try {
            Call<Obras> userCall = ApiAdapter
                    .getApiService().getObras("1");
            userCall.enqueue(new ObrasCallback());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("ERROR",e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                login();
                break;
            case R.id.RBSesion:
                if(isCheckedRB){
                    radioButton.setChecked(false);
                }
                isCheckedRB = radioButton.isChecked();
                break;
        }
    }

    class ObrasCallback implements Callback<Obras> {
        @Override
        public void onResponse(Call<Obras> call, Response<Obras> response) {
            if(response.isSuccessful()){
                Obras obras = response.body();
                List<Obra> ob = obras.getObras();
                for(Obra obr: ob){
                    Log.i("DES",obr.getDescripcion());
                }
            }
            Log.i("RES",response.toString());
        }

        @Override
        public void onFailure(Call<Obras> call, Throwable t) {
            Log.i("ERROR",t.getLocalizedMessage());
        }
    }

    class UserCallback implements Callback<Usuario>{
        @Override
        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
            if(response.isSuccessful()){
                Preferences.savePreferenceBoolean(LoginActivity.this,radioButton.isChecked(),
                        Preferences.PREFERENCE_ESTADO_SESION);


                Usuario usuario = response.body();
                Log.i("USER",usuario.getName() + " correo: " +usuario.getEmail());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            Log.i("RES",response.toString());
        }

        @Override
        public void onFailure(Call<Usuario> call, Throwable t) {
            Log.i("ERROR",t.getLocalizedMessage());
        }
    }

}
