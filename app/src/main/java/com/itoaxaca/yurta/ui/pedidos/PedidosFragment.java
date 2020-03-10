package com.itoaxaca.yurta.ui.pedidos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.adapter.PedidoAdapter;
import com.itoaxaca.yurta.dataBase.DataBaseHandler;
import com.itoaxaca.yurta.pojos.Pedido;
import com.itoaxaca.yurta.preferences.Preferences;

import java.util.ArrayList;

public class PedidosFragment extends Fragment {
    private RecyclerView rvPedidos;
    private PedidoAdapter adapterPedidos;
    private ArrayList<Pedido> pedidoArrayList;
    private CoordinatorLayout coordinatorLayout;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_pedidos, container, false);

        init();

        return root;
    }

    private void init(){


        FloatingActionButton fab1 = (FloatingActionButton) root.findViewById(R.id.fab_add_pedido);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override  public void onClick(View view) {
                Intent intent = new Intent(getContext(), MaterialActivity.class);
                getContext().startActivity(intent);
            }});




        rvPedidos = root.findViewById(R.id.rvPedidos);
        pedidoArrayList = new ArrayList<>();
        coordinatorLayout = root.findViewById(R.id.coordinatorLayout);


        getPedidos();
        Log.i("SIZE"," "+pedidoArrayList.size());
        try{
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvPedidos.setLayoutManager(layoutManager);
            adapterPedidos = new PedidoAdapter(getContext(),pedidoArrayList);
            rvPedidos.setAdapter(adapterPedidos);
            adapterPedidos.notifyDataSetChanged();
        }catch (Exception e){
            Log.i("ERROR-",""+e.getMessage());
        }

        if(pedidoArrayList.size()==0){
            coordinatorLayout.setVisibility(View.VISIBLE);
        }else{
            coordinatorLayout.setVisibility(View.GONE);
        }

    }

    private void getPedidos(){
        Cursor cursor = DataBaseHandler
                .getInstance(getContext())
                .getPedidos(Preferences.getPeferenceString(getContext(),Preferences.PREFERENCE_OBRA_ID));

        Pedido pedido;
        while(cursor.moveToNext()){
            pedido = new Pedido(cursor.getString(0),cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),
                    cursor.getString(4));
            pedidoArrayList.add(pedido);
        }
    }


    /*@Override
    public void onResume() {
        super.onResume();
        *//*getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){


                    return true;
                }
                return false;
            }
        });*//*


        PedidosFragment fragment = (PedidosFragment)
                getFragmentManager().findFragmentById(R.id.nav_host_fragment);
        getFragmentManager().beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
    }*/
}