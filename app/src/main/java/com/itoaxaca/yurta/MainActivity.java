package com.itoaxaca.yurta;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.itoaxaca.yurta.Interface.ApiService;
import com.itoaxaca.yurta.adapter.ApiAdapter;
import com.itoaxaca.yurta.adapter.ObraAdapter;
import com.itoaxaca.yurta.dataBase.DataBaseHandler;
import com.itoaxaca.yurta.pojos.Obra;
import com.itoaxaca.yurta.pojos.Usuario;
import com.itoaxaca.yurta.preferences.Preferences;
import com.itoaxaca.yurta.response.CountNotificaciones;
import com.itoaxaca.yurta.ui.NotificacionesActivity;
import com.itoaxaca.yurta.ui.almacen.AlmacenFragment;
import com.itoaxaca.yurta.ui.obra.ObraFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements AlmacenFragment.OnFragmentInteractionListener {
    private RecyclerView rvNavHeader;
    private ObraAdapter obraAdapter;
    private ArrayList<Obra> obrasList;
    private AppBarConfiguration mAppBarConfiguration;
    private View header;
    private NavigationView navigationView;
    private int height;
    private RelativeLayout relativeLayout;
    private TextView tvNavHeaderObra,tvNavHeaderUsuario,tvNavHeaderEmail;
    private String userName;
    private String userEmail;
    private String obraNombre;
    private ImageView imageViewPerfil;
    private TextView textNotifItemCount;
    String api_token;
    String id_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateFcm_token();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_obra,R.id.nav_almacen, R.id.nav_pedidos, R.id.nav_ayuda,
                R.id.nav_about, R.id.nav_share, R.id.nav_salir)
                .setDrawerLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /*CustomNavigationUI.setupWithNavController(navigationView, navController, item -> {
            switch (item.getItemId()){
                case R.id.nav_salir:
                    Toast.makeText(this,"Salir",Toast.LENGTH_SHORT).show();;
                    break;
            }
            return true;
        });
*/

        header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);


        // AsyncTaskLoadDB asyncTaskLoadDB = new AsyncTaskLoadDB();
        //asyncTaskLoadDB.execute();
        init();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_salir).setOnMenuItemClickListener(menuItem -> {
            logout();
            return true;
        });
    }

    private void logout() {
        Preferences.savePreferenceBoolean(MainActivity.this,false,
                Preferences.PREFERENCE_SESION);
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")


    private void init(){
        api_token = Preferences
                .getPeferenceString(getApplicationContext(),Preferences.PREFERENCE_API_TOKEN);
        id_usuario = Preferences
                .getPeferenceString(getApplicationContext(),Preferences.PREFERENCE_USER_ID);

        obrasList = new ArrayList<>();
        tvNavHeaderObra = header.findViewById(R.id.tvNavHeaderObra);
        tvNavHeaderUsuario = header.findViewById(R.id.tvNavHeaderUser);
        tvNavHeaderEmail = header.findViewById(R.id.tvNavHeaderEmail);
        imageViewPerfil = header.findViewById(R.id.imageViewPerfil);
        userName= Preferences.getPeferenceString(this,Preferences.PREFERENCE_USER_NAME);
        userEmail =Preferences.getPeferenceString(this,Preferences.PREFERENCE_USER_EMAIL);
        obraNombre=Preferences.getPeferenceString(this,Preferences.PREFERENCE_OBRA_NOMBRE);


        if(obraNombre!=null){
            tvNavHeaderObra.setText(obraNombre);
        }

        tvNavHeaderUsuario.setText(userName);
        tvNavHeaderEmail.setText(userEmail);
        rvNavHeader = header.findViewById(R.id.rvNavHeader);
        final ImageView imageView = header.findViewById(R.id.actionDowm);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNavHeader.setLayoutManager(layoutManager);

        Glide.with(this)
                .load(Preferences.getPeferenceString(this,Preferences.PREFERENCE_USER_IMG))
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_cloud_off_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageViewPerfil);

        obrasFromDB();
        obraAdapter = new ObraAdapter(this,obrasList);
        rvNavHeader.setAdapter(obraAdapter);

        relativeLayout = header.findViewById(R.id.expandibleMenu);
        height = relativeLayout.getMeasuredHeight();

        relativeLayout.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        relativeLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                        relativeLayout.setVisibility(View.GONE);
                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        relativeLayout.measure(widthSpec, heightSpec);
                        height = relativeLayout.getMeasuredHeight();
                        return true;
                    }
                });

        LinearLayout linearLayout = header.findViewById(R.id.linearAction);
        linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (relativeLayout.getVisibility() == View.GONE) {
                    expand(relativeLayout, height);
                    navigationView.getMenu().setGroupVisible(R.id.group_1, false);
                    navigationView.getMenu().setGroupVisible(R.id.group_2, false);
                    imageView.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);

                } else {
                    collapse(relativeLayout);
                    navigationView.getMenu().setGroupVisible(R.id.group_1, true);
                    navigationView.getMenu().setGroupVisible(R.id.group_2, true);
                    imageView.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        /*Preferences.savePreferenceBoolean(MainActivity.this
                ,false,Preferences.PREFERENCE_SESION);
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            */
        getMenuInflater().inflate(R.menu.main, menu);
        //getMenuInflater().inflate(R.menu.toolbar_menu_paquete, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_notifications);
        View actionView= MenuItemCompat.getActionView(menuItem);
        textNotifItemCount = actionView.findViewById(R.id.notify_badge_red);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        //int count = dataBaseHandler.getCountShoppingCart();


        Call<CountNotificaciones> notificacionesCall = ApiAdapter
                .getApiService().getCountNotificaciones(api_token,id_usuario);
        notificacionesCall.enqueue(new Callback<CountNotificaciones>() {
            @Override
            public void onResponse(Call<CountNotificaciones> call, Response<CountNotificaciones> response) {
                if(response.isSuccessful()){
                    Log.i("COUNT",response.body().getUnread());
                    textNotifItemCount.setText(response.body().getUnread());

                }
                Log.i("RESPUESTA",response.toString());
            }
            @Override
            public void onFailure(Call<CountNotificaciones> call, Throwable t) {
                Log.i("ERROR: ",t.getMessage());
            }
        });



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.action_notifications:
                //Toast.makeText(this,"Notificaciones",Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), NotificacionesActivity.class);
                startActivity(intent);
                break;
            case R.id.action_qr:
                Toast.makeText(this,"QR CODE",Toast.LENGTH_SHORT).show();

                intent = new Intent(getApplicationContext(), QRScanActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class AsyncTaskLoadDB extends AsyncTask<Void,Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            init();
        }
        @Override
        protected Boolean doInBackground(Void... voids) {//acceso a la BD local en segundo plano
           // cargarDatosLocalDB();
            publishProgress(1);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            obraAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }


    private void expand(RelativeLayout layout, int layoutHeight) {
        layout.setVisibility(View.VISIBLE);
        //ValueAnimator animator = slideAnimator(layout, 0, layoutHeight);
        //animator.start();
    }

    private void collapse(final RelativeLayout layout) {
        layout.setVisibility(View.GONE);
       /* int finalHeight = layout.getHeight();
        ValueAnimator mAnimator = slideAnimator(layout, finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                layout.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();*/
    }

    private ValueAnimator slideAnimator(final RelativeLayout layout, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
                layoutParams.height = value;
                layout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }


    private void obrasFromDB(){
        Cursor cursor = DataBaseHandler
                .getInstance(getApplicationContext())
                .getObrasUserID(Preferences.getPeferenceString(this,Preferences.PREFERENCE_USER_ID));
        Obra obra;
        while(cursor.moveToNext()){
            obra = new Obra(cursor.getString(0),cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),cursor.getString(6),
                    cursor.getString(7),cursor.getString(8));
            obrasList.add(obra);
            Log.i("WHILE"," RECUPERA");
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        countNotif();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        countNotif();
    }
    private void updateFcm_token(){
        String fcm_token = FirebaseInstanceId.getInstance().getToken();
        Call<Usuario> userCall = ApiAdapter
                .getApiService().updateFCMtoken(api_token,id_usuario,fcm_token);

        userCall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Log.i("FCM ",response.body().getFcm_token());
                }else{
                    Log.i("CASI",response.toString());
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.i("FCMERROR",": "+t.getMessage());
            }
        });
    }
    private void countNotif(){
        Call<CountNotificaciones> notificacionesCall = ApiAdapter
                .getApiService().getCountNotificaciones(api_token,id_usuario);
        notificacionesCall.enqueue(new Callback<CountNotificaciones>() {
            @Override
            public void onResponse(Call<CountNotificaciones> call,
                                   Response<CountNotificaciones> response) {
                if(response.isSuccessful()){
                    Log.i("COUNT",response.body().getUnread());
                    if(response.body().getUnread()==null)
                        textNotifItemCount.setText("0");
                    else
                        textNotifItemCount.setText(response.body().getUnread()+"");
                }
                Log.i("RESPUESTA",response.toString());
            }
            @Override
            public void onFailure(Call<CountNotificaciones> call, Throwable t) {
                Log.i("ERROR: ",t.getMessage());
            }
        });
    }
}

/*
* close sesion
* Preferences.savePreferenceBoolean(HomeActivity.this,false,Preferences.PREFERENCE_ESTADO_SESION);
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
* */