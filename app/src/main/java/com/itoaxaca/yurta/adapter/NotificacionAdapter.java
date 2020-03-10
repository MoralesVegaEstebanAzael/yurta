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
import com.itoaxaca.yurta.pojos.Notificacion;
import com.itoaxaca.yurta.pojos.Obra;

import java.util.ArrayList;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.ViewHolder>{
    private ArrayList<Notificacion> notificacionList;
    private Context context;
    private ArrayList<String> unreadList;
    public NotificacionAdapter(Context context, ArrayList<Notificacion> notificacionList,
                               ArrayList<String> unreadList) {
        this.context = context;
        this.notificacionList = notificacionList;
        this.unreadList = unreadList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_notification, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notificacion n = notificacionList.get(position);
        holder.tvTitulo.setText(n.getTitulo());
        holder.tvMensaje.setText(n.getMensaje());
        holder.tvObra.setText("Obra: "+n.getObra());
        switch (n.getTipo()){
            case "1":
                holder.ivIcono.setImageResource(R.drawable.ic_caja);
                break;
            case "2":

                break;
        }

        if(!unreadList.isEmpty()){
            if(unreadList.contains(n.getId()))
                holder.viewNew.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return notificacionList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitulo;
        private TextView tvMensaje;
        private TextView tvObra;
        private ImageView ivIcono;
        private View viewNew;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tv_notificacion_titulo);
            tvMensaje = itemView.findViewById(R.id.tv_notificacion_mensaje);
            tvObra = itemView.findViewById(R.id.tv_notificacion_obra);
            ivIcono = itemView.findViewById(R.id.iv_icono);
            viewNew = itemView.findViewById(R.id.vnotif_nueva);
        }
    }

}
