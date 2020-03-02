package com.itoaxaca.yurta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputEditText;
import com.itoaxaca.yurta.adapter.ApiAdapter;
import com.itoaxaca.yurta.dataBase.DataBaseHandler;
import com.itoaxaca.yurta.pojos.DetallePedido;
import com.itoaxaca.yurta.preferences.Preferences;
import com.itoaxaca.yurta.pojos.Usuario;
import com.itoaxaca.yurta.response.Almacen;
import com.itoaxaca.yurta.response.AlmacenResponse;
import com.itoaxaca.yurta.response.DetPedidoResponse;
import com.itoaxaca.yurta.response.DetPedidosResponse;
import com.itoaxaca.yurta.response.Obra;
import com.itoaxaca.yurta.response.Obras;
import com.itoaxaca.yurta.response.PedidoResponse;
import com.itoaxaca.yurta.response.PedidosResponse;
import com.itoaxaca.yurta.response.TipoObra;
import com.itoaxaca.yurta.response.TiposObraResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText email;
    private TextInputEditText password;
    private Button btnLogin;
    private RadioButton radioButton;
    private boolean isCheckedRB;
    private ArrayList<Obra> obraArrayList;
    private ArrayList<TipoObra> tipoObraArrayList;
    private ArrayList<Almacen> almacenArrayList;
    private ArrayList<PedidoResponse> pedidoResponseArrayList;
    private ArrayList<DetPedidoResponse> detPedidoResponseArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //verificar si ya esta logeado
        if(Preferences.getPeferenceBoolean(this,Preferences.PREFERENCE_SESION)){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        init();
    }

    private void init(){
        obraArrayList=new ArrayList<>();
        tipoObraArrayList=new ArrayList<>();
        almacenArrayList=new ArrayList<>();
        pedidoResponseArrayList = new ArrayList<>();
        detPedidoResponseArrayList = new ArrayList<>();

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


    private void getDataFromApi(String api_token,String userId) {
        /**DEL USUARIOS ALMACENAR LOCALMENTE**/
        /**OBRAS-ALMACEN-PEDIDOS-DETALLES-TIPOSDEOBRA**/
        Observable<AlmacenResponse> observable1 = ApiAdapter.getApiService().getAlmacen(api_token,userId);
        Observable<Obras> observable2 = ApiAdapter.getApiService().getObras(api_token,userId);
        Observable<TiposObraResponse> observable3 = ApiAdapter.getApiService().getTiposObras(api_token);
        Observable<PedidosResponse> observable4 = ApiAdapter.getApiService().getPedidos(api_token,userId);
        Observable<DetPedidosResponse> observable5 = ApiAdapter.getApiService().getDetPedidos(api_token,userId);

        Observable<List<String >> result =
                Observable.zip(
                        observable1.subscribeOn(Schedulers.io()),
                        observable2.subscribeOn(Schedulers.io()),
                        observable3.subscribeOn(Schedulers.io()),
                        observable4.subscribeOn(Schedulers.io()),
                        new Function4<AlmacenResponse, Obras, TiposObraResponse, PedidosResponse, List<String>>() {
                            @Override
                            public List<String> apply(AlmacenResponse almacenResponse, Obras obras, TiposObraResponse tiposObraResponse, PedidosResponse pedidosResponse) throws Exception {
                                List<String> list = new ArrayList();
                                obraArrayList = obras.getObras();
                                tipoObraArrayList = tiposObraResponse.getTiposobra();
                                almacenArrayList = almacenResponse.getAlmacenes();
                                pedidoResponseArrayList = pedidosResponse.getPedidos();
                                list.add(obras.getObras().get(0).getDescripcion());
                                list.add(tiposObraResponse.getTiposobra().get(0).getDescripcion());
                                return list;
                            }
                        });

       /* Observable<List<String>> result =
                Observable.zip(
                        observable1.subscribeOn(Schedulers.io()),
                        observable2.subscribeOn(Schedulers.io()),
                        observable3.subscribeOn(Schedulers.io()),
                        observable4.subscribeOn(Schedulers.io()),
                        observable5.subscribeOn(Schedulers.io()),
                        new Function5<AlmacenResponse, Obras, TiposObraResponse,
                                PedidosResponse, DetPedidosResponse, List<String>>() {
                            @Override
                            public List<String> apply(AlmacenResponse almacenResponse,
                                                      Obras obras, TiposObraResponse tiposObraResponse,
                                                      PedidosResponse pedidosResponse,
                                                      DetPedidosResponse detPedidosResponse) throws Exception {
                                List<String> list = new ArrayList();

                                obraArrayList = obras.getObras();
                                tipoObraArrayList = tiposObraResponse.getTiposobra();
                                almacenArrayList = almacenResponse.getAlmacenes();
                                pedidoResponseArrayList = pedidosResponse.getPedidos();
                                detPedidoResponseArrayList = detPedidosResponse.getDet_pedidos();

                                list.add(obras.getObras().get(0).getDescripcion());
                                list.add(tiposObraResponse.getTiposobra().get(0).getDescripcion());
                                return list;
                            }
                        });*/

        result.observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(List<String> s) {
                        Log.i("OnNEXT", "<<next>>");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("OnERROR", e.getMessage() + "-"+
                                e.getLocalizedMessage() + "-"+e.getCause());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("OnCOMPLETE", "<<onComplete>>");
                        addToDB();
                        //mostrar();
                    }
                });
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

    class UserCallback implements Callback<Usuario>{
        @Override
        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
            if(response.isSuccessful()){
                Preferences.savePreferenceBoolean(LoginActivity.this,radioButton.isChecked(),
                        Preferences.PREFERENCE_SESION);
                Usuario usuario = response.body();
                Log.i("USER",usuario.getName() + " correo: " +usuario.getEmail());
                String imagen ;
                if(usuario.getUrl_avatar()==null)
                    imagen="https://firebasestorage.googleapis.com/v0/b/" +
                            "yurta-b4d1d.appspot.com/o/usuarios%2Fhombre.png?alt=media&token=544bd93c-d744-493a-a2cf-56d68f9363a1";
                else
                    imagen = usuario.getUrl_avatar();

                               //agregar usuario a la base de datos
                DataBaseHandler.getInstance(getApplicationContext())
                        .insertUser(usuario.getId(),usuario.getName()
                                ,usuario.getEmail(),usuario.getTelefono()
                                ,usuario.getPuesto(),usuario.getApi_token(),imagen);

                Preferences.savePreferenceString(LoginActivity.this,
                        usuario.getId(),Preferences.PREFERENCE_USER_ID);
                Preferences.savePreferenceString(LoginActivity.this,
                        usuario.getApi_token(),Preferences.PREFERENCE_API_TOKEN);
                Preferences.savePreferenceString(LoginActivity.this,
                        usuario.getName(),Preferences.PREFERENCE_USER_NAME);
                Preferences.savePreferenceString(LoginActivity.this,
                        usuario.getEmail(),Preferences.PREFERENCE_USER_EMAIL);
                Preferences.savePreferenceString(LoginActivity.this,
                        imagen,Preferences.PREFERENCE_USER_IMG);

                //datos from API REST
                getDataFromApi(usuario.getApi_token(),usuario.getId());
                //apitest(usuario.getApi_token(),usuario.getId());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
            Log.i("RES",response.toString());
        }
        @Override
        public void onFailure(Call<Usuario> call, Throwable t) {
            Log.i("ERROR",t.getLocalizedMessage());
        }
    }


    private void addToDB(){
        if(!obraArrayList.isEmpty()){
            DataBaseHandler.getInstance(getApplicationContext())
                    .insertObras(obraArrayList,
                            Preferences.getPeferenceString(getApplicationContext(),
                                    Preferences.PREFERENCE_USER_ID));
        }
        if(!almacenArrayList.isEmpty()){
            DataBaseHandler.getInstance(getApplicationContext())
                    .insertAlmacen(almacenArrayList);
        }
        if(!pedidoResponseArrayList.isEmpty()){
            DataBaseHandler.getInstance(getApplicationContext())
                    .insertPedidos(pedidoResponseArrayList);
        }
        if(!tipoObraArrayList.isEmpty()){
            DataBaseHandler.getInstance(getApplicationContext())
                    .insertTiposObra(tipoObraArrayList);
        }

        /*
        if(!detPedidoResponseArrayList.isEmpty()){
            DataBaseHandler.getInstance(getApplicationContext())
                    .insertDetallesPedido(detPedidoResponseArrayList);
        }*/
    }

    private void mostrar(){
        Log.i("OBRASZ",obraArrayList.size()+"");
        for(Obra o:obraArrayList){
            Log.i("OBRAS",o.getId()+"-"+o.getDescripcion());
        }
        Log.i("ALMACENZ",almacenArrayList.size()+"");
        for(Almacen a:almacenArrayList){
            Log.i("ALMACEN",a.getMaterial_marca()+"-"+a.getId_obra());
        }
        Log.i("TIPOZ",tipoObraArrayList.size()+"");
        for(TipoObra t:tipoObraArrayList){
            Log.i("TIPO",t.getId() + "-"+t.getDescripcion());
        }
        Log.i("PEDIDOZ",pedidoResponseArrayList.size()+"");
        for(PedidoResponse p:pedidoResponseArrayList){
            Log.i("PEDIDO",p.getId()+"-"+p.getFecha_p());
        }
        /*Log.i("DETALLEZ",detPedidoResponseArrayList.size()+"");
        for(DetPedidoResponse d: detPedidoResponseArrayList){
            Log.i("DETALLEP",d.getId_pedido()+"-"+d.getDescripcion());
        }*/
    }
}