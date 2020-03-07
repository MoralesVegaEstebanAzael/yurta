package com.itoaxaca.yurta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.pojos.Almacen;
import com.itoaxaca.yurta.pojos.Material;

import java.util.List;

public class AlmacenAdapter extends RecyclerView.Adapter<AlmacenAdapter.ViewHolder>{
    private List<Almacen> almacenList;
    private Context context;

    public AlmacenAdapter(Context context,List<Almacen> almacenList){
        this.almacenList = almacenList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_almacen, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Almacen m = almacenList.get(position);
        holder.tvUnidad.setText(m.getUnidad());
        holder.tvDescripcion.setText(m.getDescripcion());
        holder.tvMarca.setText(m.getMarca());
        holder.btnCantidad.setText(m.getCantidad());


        Glide.with(context)
                .load(m.getUrl_imagen())
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_cloud_off_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.ivImagen);
    }

    @Override
    public int getItemCount() {
        return almacenList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUnidad;
        private TextView tvDescripcion;
        private TextView tvMarca;
        private Button btnCantidad;
        private ImageView ivImagen;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUnidad = itemView.findViewById(R.id.tv_almacen_uni);
            tvDescripcion = itemView.findViewById(R.id.tv_almacen_descrip);
            tvMarca = itemView.findViewById(R.id.tv_almacen_marca);
            btnCantidad = itemView.findViewById(R.id.btn_almacen_cantidad);
            ivImagen = itemView.findViewById(R.id.iv_almacen_img);
        }
    }
}
