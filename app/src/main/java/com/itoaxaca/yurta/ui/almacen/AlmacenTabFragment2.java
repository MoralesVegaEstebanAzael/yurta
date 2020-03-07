package com.itoaxaca.yurta.ui.almacen;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.adapter.AlmacenAdapter;
import com.itoaxaca.yurta.dataBase.DataBaseHandler;
import com.itoaxaca.yurta.pojos.Almacen;
import com.itoaxaca.yurta.preferences.Preferences;
import com.itoaxaca.yurta.ui.obra.ObraFragment;

import java.util.ArrayList;
import java.util.List;

public class AlmacenTabFragment2 extends Fragment {
    private RecyclerView recyclerView;
    private AlmacenAdapter almacenAdapter;
    private List<Almacen> almacenList;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.fragment_almacen_2, container, false);

        almacenList = new ArrayList<>();
        recyclerView = root.findViewById(R.id.rvAlmacen2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        almacenAdapter = new AlmacenAdapter(getContext(),almacenList);
        recyclerView.setAdapter(almacenAdapter);


        AsyntaskLoadDB asyntaskLoadDB = new AsyntaskLoadDB();
        asyntaskLoadDB.execute();

        return root;
    }


    private class AsyntaskLoadDB extends AsyncTask<Void,Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String obra = Preferences.getPeferenceString(getContext(), Preferences.PREFERENCE_OBRA_ID);
            String categoria = "Metálicos";
            Cursor c = DataBaseHandler.getInstance(getContext()).getAlmacen(obra, categoria);
            while (c.moveToNext()) {
                Almacen a = new Almacen(c.getString(0), c.getString(1)
                        , c.getString(2), c.getString(3), c.getString(4),
                        c.getString(5), c.getString(6), c.getString(7));
                almacenList.add(a);
            }
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