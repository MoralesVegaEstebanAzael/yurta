package com.itoaxaca.yurta.adapter;

import android.content.Context;
import android.util.Log;
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
        holder.tvMarca.setText(m.getMarca());
        holder.tvCategoria.setText(m.getTipo()+"");

        Glide.with(context)
                .load(m.getUrl_imagen())
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
        private TextView tvCantidad;
        private ImageView ivMaterial;
        private ImageView ivActionDelete;
        private TextView tvMarca;
        private TextView tvCategoria;
        public ViewHolder(View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tv_mat_descrip);
            tvCantidad = itemView.findViewById(R.id.tv_mat_cantidad);
            ivMaterial = itemView.findViewById(R.id.iv_material);
            ivActionDelete = itemView.findViewById(R.id.ic_delete);
            tvMarca = itemView.findViewById(R.id.tv_mat_marca);
            tvCategoria = itemView.findViewById(R.id.tv_mat_cat);
        }
    }


    public void removeAt(int position) {
        arrayList.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, arrayList.size());
    }
}
