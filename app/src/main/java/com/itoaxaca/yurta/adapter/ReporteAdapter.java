package com.itoaxaca.yurta.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.pojos.Almacen;
import com.itoaxaca.yurta.pojos.Material;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReporteAdapter extends
        RecyclerView.Adapter<ReporteAdapter.ItemViewHolder> implements Filterable {
    private List<Almacen> almacenList = new ArrayList<>();
    private List<Almacen> almacenFilter = new ArrayList<>();
    private Context mContext;
    private CustomFilter mFilter;
    public static  String categoria="";
    public static boolean all;



    public ReporteAdapter(Context mContext, List<Almacen> almacenList) {
        mContext = mContext;
        this.almacenList = almacenList;
        this.almacenFilter = almacenList;
        mFilter = new CustomFilter();
    }

    public onItemClickListener onItemClickListener;

    public void setOnItemClickListener(ReporteAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.view_reporte, viewGroup, false);
        return new ItemViewHolder(view);
    }

    public interface onItemClickListener {
        void onClick(Material contact);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, int i) {
        final Almacen almacen = almacenFilter.get(i);
        itemViewHolder.tvDetalleDescripcion.setText(almacen.getDescripcion());
        itemViewHolder.tvDetalleUnidad.setText(almacen.getUnidad());
        itemViewHolder.tvDetalleExis.setText(almacen.getCantidad());


        if(almacen.getUtilizado()!=0)
            itemViewHolder.button.setText(almacen.getUtilizado()+"");
        else
            itemViewHolder.button.setText("0");



        Glide.with(itemViewHolder.ivMaterial.getContext())
                .load(almacen.getUrl_imagen())
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_cloud_off_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(itemViewHolder.ivMaterial);
        itemViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] cantidad = {0};
                //int m = Integer.parseInt("10");
                NumberPicker numberPicker=  new NumberPicker(itemViewHolder.button.getContext());
                numberPicker.setMaxValue(Integer.parseInt(almacen.getCantidad()));
                numberPicker.setMinValue(0);
                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        itemViewHolder.button.setText(i1+"");
                        almacen.setUtilizado(i1);
                        cantidad[0] = i1;
                        Log.i("CANTIDAD",almacen.getUtilizado()+"");

                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(itemViewHolder
                        .button.getContext()).setView(
                                numberPicker);
                builder.setTitle("Unidades Utilizadas")
                        .setIcon(R.drawable.ic_assignment_black_24dp);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("PICKER",""+i);
                        // material.setCantidadSolicitada(cantidad[0]);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemViewHolder.button.setText("0");
                    }
                });

                builder.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return almacenFilter.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDetalleDescripcion;
        private TextView tvDetalleUnidad;
        private TextView tvDetalleExis;
        private Button button;
        private ImageView ivMaterial;
        public ItemViewHolder(View itemView) {
            super(itemView);
            tvDetalleDescripcion = itemView.findViewById(R.id.tv_report_descrip);
            tvDetalleUnidad = itemView.findViewById(R.id.tv_report_unidad);
            tvDetalleExis = itemView.findViewById(R.id.tv_report_exis);
            button = itemView.findViewById(R.id.btn_reporte_gasto);
            ivMaterial = itemView.findViewById(R.id.iv_report);
        }
    }



    /**
     * this class for filter data.
     */
    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence != null && charSequence.length() > 0) {
                ArrayList<Almacen> filters = new ArrayList<>();
                charSequence = charSequence.toString().toUpperCase();
                for (int i = 0; i < almacenList.size(); i++) {
                    if(all){
                        if (almacenList.get(i).getDescripcion().toUpperCase().contains(charSequence)
                                || almacenList.get(i).getMarca().toUpperCase().contains(charSequence)) {
                            Almacen m = almacenList.get(i);
                            filters.add(m);

                        }
                    }else{
                        if ( (almacenList.get(i).getDescripcion().toUpperCase().contains(charSequence)
                                || almacenList.get(i).getMarca().toUpperCase().contains(charSequence)) &&
                                almacenList.get(i).getTipo().toUpperCase().contains(categoria.toUpperCase())) {
                            Almacen m = almacenList.get(i);
                            filters.add(m);
                        }
                    }
                }
                results.count = filters.size();
                results.values = filters;

            } else {
                results.count = almacenList.size();
                results.values = almacenList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            almacenFilter = (ArrayList<Almacen>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}