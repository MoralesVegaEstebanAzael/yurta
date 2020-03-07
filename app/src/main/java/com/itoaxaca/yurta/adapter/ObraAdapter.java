package com.itoaxaca.yurta.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.itoaxaca.yurta.LoginActivity;
import com.itoaxaca.yurta.MainActivity;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.pojos.Obra;
import com.itoaxaca.yurta.preferences.Preferences;
import com.itoaxaca.yurta.ui.obra.ObraFragment;

import java.util.ArrayList;
import java.util.List;

public class ObraAdapter extends RecyclerView.Adapter<ObraAdapter.ViewHolder>{
    private ArrayList<Obra> obrasList;
    private Context context;
    private int  rowIndex=-1;
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Obra obra = obrasList.get(position);
        holder.tvNombre.setText(obra.getDescripcion());


        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //guardar id de obra activia
                Preferences.savePreferenceString(context,
                       obra.getId(),Preferences.PREFERENCE_OBRA_ID);
                Preferences.savePreferenceString(context,
                        obra.getDescripcion(),Preferences.PREFERENCE_OBRA_NOMBRE);

                Toast.makeText(context,"ID: " +obrasList.get(position).getId(),Toast.LENGTH_SHORT).show();

                View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
                DrawerLayout mDrawerLayout;
                RelativeLayout layout;
                TextView textView;
                NavigationView navigationView = rootView.findViewById(R.id.nav_view);
                ImageView imageView = rootView.findViewById(R.id.actionDowm);
                layout = rootView.findViewById(R.id.expandibleMenu);
                layout.setVisibility(View.GONE);
                textView=rootView.findViewById(R.id.tvNavHeaderObra);
                textView.setText(obra.getDescripcion());


                rowIndex=position;
                notifyDataSetChanged();


                navigationView.getMenu().setGroupVisible(R.id.group_1, true);
                navigationView.getMenu().setGroupVisible(R.id.group_2, true);
                imageView.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                mDrawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);

               // ((MainActivity) context).getSupportFragmentManager()
                 //       .beginTransaction().replace(R.id.nav_host_fragment, ObraFragment.newInstance()).commit();
                mDrawerLayout.closeDrawers();
            }
        });


        if(rowIndex==position){
            holder.row.setBackgroundColor(Color.parseColor("#DCDCDC"));
           // holder.tv1.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.row.setBackgroundColor(Color.parseColor("#ffffff"));
           // holder.tv1.setTextColor(Color.parseColor("#000000"));
        }
       /* String obraId = Preferences.getPeferenceString(context,Preferences.PREFERENCE_OBRA_ID);
        if(obra.getId().equals(obraId)){
            holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorItemSelected));
        }*/


    }

    @Override
    public int getItemCount() {
        return obrasList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivObra;
        private TextView tvNombre;
        private View view;
        private CoordinatorLayout row;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivObra = itemView.findViewById(R.id.ivObra);
            tvNombre = itemView.findViewById(R.id.tvNombreObra);
            row = itemView.findViewById(R.id.row);
        }
    }


}
