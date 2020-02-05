package com.itoaxaca.yurta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.pojos.Obra;

import java.util.ArrayList;

public class ObraAdapter extends RecyclerView.Adapter<ObraAdapter.ViewHolder>{
    private ArrayList<Obra> obrasList;
    private Context context;

    public ObraAdapter(Context context, ArrayList<Obra> obrasList) {
        this.context = context;
        this.obrasList = obrasList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_obra_menu, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNombre.setText(obrasList.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return obrasList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivObra;
        private TextView tvNombre;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivObra = itemView.findViewById(R.id.ivObra);
            tvNombre = itemView.findViewById(R.id.tvNombreObra);

        }
    }
}
