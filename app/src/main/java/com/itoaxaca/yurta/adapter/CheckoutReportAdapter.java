package com.itoaxaca.yurta.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.pojos.Almacen;

import java.util.ArrayList;
import java.util.List;
public class CheckoutReportAdapter extends RecyclerView.Adapter<CheckoutReportAdapter.ViewHolder> {
    private List<Almacen> arrayList;
    private Context context;

    public CheckoutReportAdapter(Context context,List<Almacen> almacenArrayList){
        this.arrayList = almacenArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_reporte_check, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Almacen a = arrayList.get(position);

        holder.tvDescripcion.setText(a.getDescripcion());
        holder.tvExistencias.setText(a.getCantidad()+" "+ a.getUnidad());
        holder.tvUtilizados.setText(a.getUtilizado()+"");


        Glide.with(context)
                .load(a.getUrl_imagen())
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_cloud_off_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.ivMaterial);
        holder.ivActionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(position);
                Log.i("ELIM"," eliminando");
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescripcion;
        private TextView tvExistencias;
        private ImageView ivMaterial;
        private ImageView ivActionDelete;
        private TextView tvUtilizados;
        public ViewHolder(View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tv_report_check_descrip);
            tvExistencias = itemView.findViewById(R.id.tv_report_check_exis);
            ivMaterial = itemView.findViewById(R.id.iv_report_check_img);
            ivActionDelete = itemView.findViewById(R.id.ic_report_delete);
            tvUtilizados = itemView.findViewById(R.id.tv_report_check_util);
        }
    }

    public void removeAt(int position) {
        arrayList.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, arrayList.size());
    }
}
