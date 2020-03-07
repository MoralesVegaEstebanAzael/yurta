package com.itoaxaca.yurta.ui.almacen;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itoaxaca.yurta.Interface.ApiService;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.adapter.AlmacenAdapter;
import com.itoaxaca.yurta.adapter.ApiAdapter;
import com.itoaxaca.yurta.dataBase.DataBaseHandler;
import com.itoaxaca.yurta.pojos.Almacen;
import com.itoaxaca.yurta.preferences.Preferences;
import com.itoaxaca.yurta.response.DetPedidoResponse;
import com.itoaxaca.yurta.response.DetPedidosResponse;
import com.itoaxaca.yurta.response.PedidoResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlmacenTabFragment1 extends Fragment {
    private List<Almacen> almacenList;
    private RecyclerView recyclerView;
    private AlmacenAdapter almacenAdapter;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
         root = inflater.inflate(R.layout.fragment_almacen_1, container, false);
         init();
         return root;
    }


    private void init(){
        recyclerView =  root.findViewById(R.id.rvAlmacen1);
        almacenList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        almacenAdapter = new AlmacenAdapter(getContext(),almacenList);
        recyclerView.setAdapter(almacenAdapter);

        AsyntaskLoadDB asyncTaskLoadDB = new AsyntaskLoadDB();
        asyncTaskLoadDB.execute();
    }

    private class AsyntaskLoadDB extends AsyncTask<Void,Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            String obra=Preferences.getPeferenceString(getContext(),Preferences.PREFERENCE_OBRA_ID);
            String categoria="Pétreos";
            Cursor c = DataBaseHandler.getInstance(getContext()).getAlmacen(obra,categoria);
            while (c.moveToNext()){
                Log.i("CONSULTA"," consulta sql");
                Almacen a = new Almacen(c.getString(0),c.getString(1)
                        ,c.getString(2),c.getString(3),c.getString(4),
                        c.getString(5),c.getString(6),c.getString(7));
                almacenList.add(a);
            }

            Log.i("TAM","  s"+almacenList.size());
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            almacenAdapter.notifyDataSetChanged();
        }
        @Override
        protected void onCancelled() {
        }
    }


}