package com.itoaxaca.yurta.adapter;

import android.content.Context;
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
import com.itoaxaca.yurta.pojos.DetallePedido;

import java.util.ArrayList;

public class DetallePedAdapter extends RecyclerView.Adapter<DetallePedAdapter.ViewHolder>{
    private ArrayList<DetallePedido> detallePedidoArrayList;
    private Context context;

    public DetallePedAdapter(Context context, ArrayList<DetallePedido> detallePedidos) {
        this.context = context;
        this.detallePedidoArrayList = detallePedidos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_detalle_pedido, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetallePedido detallePedido = detallePedidoArrayList.get(position);

        holder.tvDetalleDescripcion.setText(detallePedido.getDescripcion());
        holder.tvDetalleUnidades.setText(detallePedido.getCantidad());
        holder.tvDetalleMarca.setText(detallePedido.getMarca());
        holder.tvDetalleUn.setText(detallePedido.getUnidad());
        Glide.with(context)
                .load(detallePedido.getUrl_imagen())
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_cloud_off_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.ivImagen);
    }

    @Override
    public int getItemCount() {
        return detallePedidoArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDetalleDescripcion;
        private TextView tvDetalleUnidades;
        private TextView tvDetalleMarca;
        private TextView tvDetalleUn;
        private ImageView ivImagen;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvDetalleDescripcion = itemView.findViewById(R.id.tv_det_descrip);
            tvDetalleUnidades = itemView.findViewById(R.id.tv_det_unidades);
            tvDetalleMarca = itemView.findViewById(R.id.tv_det_marca);
            tvDetalleUn = itemView.findViewById(R.id.tv_det_un);
            ivImagen = itemView.findViewById(R.id.iv_det_img);
        }
    }
}
