package com.itoaxaca.yurta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.tvDetalleUnidad.setText(detallePedido.getUnidad());
        holder.tvDetalleMarca.setText(detallePedido.getMarca());
        holder.tvDetalleCantidad.setText(detallePedido.getCantidad());

    }

    @Override
    public int getItemCount() {
        return detallePedidoArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDetalleDescripcion;
        private TextView tvDetalleUnidad;
        private TextView tvDetalleMarca;
        private TextView tvDetalleCantidad;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvDetalleDescripcion = itemView.findViewById(R.id.tvMaterialDescripcion);
            tvDetalleUnidad = itemView.findViewById(R.id.tvMaterialUnidad);
            tvDetalleMarca = itemView.findViewById(R.id.tvMatMarca);
            tvDetalleCantidad = itemView.findViewById(R.id.tvMatCantidad);
        }
    }
}
