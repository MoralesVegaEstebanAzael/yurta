package com.itoaxaca.yurta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.pojos.Material;

import java.util.ArrayList;

public class CheckoutPedidoAdapter extends RecyclerView.Adapter<CheckoutPedidoAdapter.ViewHolder> {
    private ArrayList<Material> arrayList;
    private Context context;

    public CheckoutPedidoAdapter(Context context,ArrayList<Material> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_det_pedido, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Material m = arrayList.get(position);

        holder.tvDescripcion.setText(m.getDescripcion());
        holder.tvCantidad.setText(m.getCantidadSolicitada()+" "+m.getUnidad());
        Glide.with(context)
                .load(m.getUrl_imagen())
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_cloud_off_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.ivMaterial);

        holder.ivMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete item
                Toast.makeText(context,"delete",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescripcion;
        private TextView tvCantidad;
        private ImageView ivMaterial;
        private ImageView ivActionDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tv_mat_descrip);
            tvCantidad = itemView.findViewById(R.id.tv_mat_cantidad);
            ivMaterial = itemView.findViewById(R.id.iv_material);
            ivActionDelete = itemView.findViewById(R.id.ic_delete);
        }
    }
}
