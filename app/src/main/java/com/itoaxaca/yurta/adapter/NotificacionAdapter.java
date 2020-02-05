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

    public NotificacionAdapter(Context context, ArrayList<Notificacion> notificacionList) {
        this.context = context;
        this.notificacionList = notificacionList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_notificacion, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNombre.setText(notificacionList.get(position).getTitulo());
    }

    @Override
    public int getItemCount() {
        return notificacionList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivNotificacion;
        private TextView tvNombre;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivNotificacion = itemView.findViewById(R.id.ivNotificacion);
            tvNombre = itemView.findViewById(R.id.tvNotificationNombre);

        }
    }
}
