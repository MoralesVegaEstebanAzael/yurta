package com.itoaxaca.yurta;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.itoaxaca.yurta.adapter.ObraAdapter;
import com.itoaxaca.yurta.pojos.Obra;
import com.itoaxaca.yurta.ui.almacen.AlmacenFragment;

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

import java.util.ArrayList;

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
    private TextView tvNavHeaderObra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_almacen, R.id.nav_pedidos, R.id.nav_ayuda,
                R.id.nav_about, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);

       // AsyncTaskLoadDB asyncTaskLoadDB = new AsyncTaskLoadDB();
        //asyncTaskLoadDB.execute();
        init();
    }
    @SuppressWarnings("StatementWithEmptyBody")


    private void init(){
        obrasList = new ArrayList<>();
        tvNavHeaderObra = header.findViewById(R.id.tvNavHeaderObra);
        rvNavHeader = header.findViewById(R.id.rvNavHeader);
        final ImageView imageView = header.findViewById(R.id.actionDowm);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNavHeader.setLayoutManager(layoutManager);
        Obra obra1= new Obra("Obra de carretera");
        Obra obra2= new Obra("Obra de agua potable");
        obrasList.add(obra1);
        obrasList.add(obra2);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
}
