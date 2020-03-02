package com.itoaxaca.yurta.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.pojos.DetallePedido;
import com.itoaxaca.yurta.pojos.Pedido;
import com.itoaxaca.yurta.ui.pedidos.DetallePedidoActivity;

import java.util.ArrayList;
public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder>{
    private ArrayList<Pedido> pedidoArrayList;
    private Context context;

    public PedidoAdapter(Context context, ArrayList<Pedido> pedidoArrayList) {
        this.context = context;
        this.pedidoArrayList = pedidoArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_pedido, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pedido p = pedidoArrayList.get(position);

        int id = Integer.parseInt(p.getId());
        holder.tvPedidoID.setText("#"+String.format("%03d",id));
        holder.tvPedidoFecha.setText(p.getFecha_p());


        holder.tvEstado1.setText(p.getEstado());
        holder.tvEstado1.setText(p.getEstado());
        holder.ivInfAcction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.llInf.getVisibility() == View.GONE) {
                    holder.llInf.setVisibility(View.VISIBLE);
                    holder.ivInfAcction.setImageResource(R.drawable.ic_remove_black_24dp);

                } else {
                    holder.llInf.setVisibility(View.GONE);
                    holder.ivInfAcction.setImageResource(R.drawable.ic_add_black_24dp);
                }
            }
        });
        holder.llMoreInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetallePedidoActivity.class);
                intent.putExtra("OBJETO_PEDIDO", p);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pedidoArrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPedidoID;
        private TextView tvPedidoFecha;

        private ImageView ivInfAcction;
        private TextView tvEstado1;
        private TextView tvEstado2;
        private LinearLayout llInf;
        private LinearLayout llMoreInf;

        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvPedidoID = itemView.findViewById(R.id.tvPedidoID);
            tvPedidoFecha = itemView.findViewById(R.id.tvPedidoFecha);

            tvEstado1 = itemView.findViewById(R.id.tvEstado1);
            tvEstado2 = itemView.findViewById(R.id.tvEstado2);
            ivInfAcction = itemView.findViewById(R.id.moreInfPedido);
            llInf = itemView.findViewById(R.id.layoutInfPedido);
            llMoreInf = itemView.findViewById(R.id.layoutDetalles);
        }
    }
}


