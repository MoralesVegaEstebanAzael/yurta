package com.itoaxaca.yurta.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.adapter.ApiAdapter;
import com.itoaxaca.yurta.adapter.NotificacionAdapter;
import com.itoaxaca.yurta.dataBase.DataBaseHandler;
import com.itoaxaca.yurta.pojos.Usuario;
import com.itoaxaca.yurta.preferences.Preferences;
import com.itoaxaca.yurta.response.Notificacion;
import com.itoaxaca.yurta.response.NotificacionesResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionesActivity extends AppCompatActivity {
    private RecyclerView rvNotificaciones;
    private NotificacionAdapter notificacionAdapter;
    private ArrayList<Notificacion> arrayListResponse;
    private ArrayList<com.itoaxaca.yurta.pojos.Notificacion> arrayList;
    private ArrayList<String> unreadNotifsList;
    private String api_token;
    private String id;
    private String obra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        obra =  Preferences.getPeferenceString(this,Preferences.PREFERENCE_OBRA_ID);
        rvNotificaciones = findViewById(R.id.rv_notificaciones);
        arrayListResponse = new ArrayList<>();
        arrayList= new ArrayList<>();
        unreadNotifsList = new ArrayList<>();

        notificacionAdapter = new NotificacionAdapter(getApplicationContext(),arrayList,unreadNotifsList);
        api_token = Preferences.getPeferenceString(this,Preferences.PREFERENCE_API_TOKEN);
        id = Preferences.getPeferenceString(this,Preferences.PREFERENCE_USER_ID);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNotificaciones.setLayoutManager(layoutManager);
        rvNotificaciones.setAdapter(notificacionAdapter);

        getNotificacionesAPI();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void getNotificacionesAPI(){
        Call<NotificacionesResponse> notifResponseCall = ApiAdapter.getApiService()
                .getNotificacones(api_token,id);

        notifResponseCall.enqueue(new Callback<NotificacionesResponse>() {
            @Override
            public void onResponse(Call<NotificacionesResponse> call, Response<NotificacionesResponse> response) {
                if(response.isSuccessful()){
                    arrayListResponse = response.body().getNotificaciones();
                    for(Notificacion n:arrayListResponse){
                        if(n.getData().getObra().equals(obra)){
                            com.itoaxaca.yurta.pojos.Notificacion notificacion
                                    = new com.itoaxaca.yurta.pojos.Notificacion(n.getId(),n.getNotifiable_id(),
                                    n.getData().getTitulo(),n.getData().getTipo(),n.getData().getMensaje(),
                                    n.getData().getObra());
                            arrayList.add(notificacion);
                            unreadNotifsList.add(n.getId()); //agregar a la lista de IDs
                        }
                    }
                }
                Log.i("NOTIFICACIONES",response.toString());
                saveDB();
            }

            @Override
            public void onFailure(Call<NotificacionesResponse> call, Throwable t) {
                Log.i("ERROR",t.getMessage());
                getNotificacionesDB();
            }
        });
    }

    private void markAsread(){
        if(!unreadNotifsList.isEmpty()){
            Call<Usuario> usuarioCall = ApiAdapter.getApiService()
                    .markAsReadNotifications(api_token,id,unreadNotifsList);
            usuarioCall.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if(response.isSuccessful()){
                        Log.i("LEIDAS"," marcadas como leidas");

                    }
                    Log.i("RESPUESTA:", response.toString());
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Log.i("ERROR",t.getMessage());
                }
            });
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        markAsread();
    }

    private void saveDB(){
        if(!arrayList.isEmpty())
            DataBaseHandler.getInstance(this).insertNotificaciones(arrayList);
        getNotificacionesDB();
    }
    private void getNotificacionesDB(){
       arrayList.clear();
        Cursor c = DataBaseHandler.getInstance(this)
                .getNotificaciones(obra);
        com.itoaxaca.yurta.pojos.Notificacion n;
        while(c.moveToNext()){
            n = new com.itoaxaca.yurta.pojos.Notificacion(c.getString(0),c.getString(1),
                    c.getString(2),c.getString(3),c.getString(4),c.getString(5));
            arrayList.add(n);
        }
        notificacionAdapter.notifyDataSetChanged();
    }
}
