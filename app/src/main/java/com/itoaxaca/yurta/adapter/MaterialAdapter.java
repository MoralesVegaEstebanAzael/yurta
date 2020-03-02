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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.chip.ChipGroup;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.pojos.Material;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MaterialAdapter  extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Material> materialArrayList;
    private List<Material> list;
    public static  String categoria="";
    public static boolean all;
    private List<Material> listSelect;

    public MaterialAdapter(Context context, List<Material> materialArrayList) {
        this.context = context;
        this.materialArrayList = materialArrayList;
        this.list = materialArrayList;
        listSelect = new ArrayList<>();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_material, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Material material = materialArrayList.get(position);
        holder.tvDetalleDescripcion.setText(material.getDescripcion());
        holder.tvDetalleUnidad.setText(material.getUnidad());
        holder.tvDetalleMarca.setText(material.getMarca());

        holder.checkBox.setOnCheckedChangeListener(null);
        //if true, your checkbox will be selected, else unselected
        holder.checkBox.setChecked(material.isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    holder.checkBox.setButtonDrawable(R.drawable.ic_check_box_black_24dp);

                    if(findMaterial(material.getId(),listSelect)==null)
                        listSelect.add(material);
                }else{
                    if(findMaterial(material.getId(),listSelect)!=null) {
                        for (Iterator<Material> iterator = listSelect.iterator(); iterator.hasNext(); ) {
                            if (iterator.next().getId().equals(material.getId()))
                                iterator.remove();
                        }
                    }
                    holder.checkBox.setButtonDrawable(R.drawable.ic_check_box_outline_blank_black_24dp);
                }
                Toast.makeText(context,"id: " + material.getId(),Toast.LENGTH_SHORT).show();
                material.setSelected(isChecked);

            }
        });
        Glide.with(context)
                .load(material.getUrl_imagen())
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_cloud_off_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.ivMaterial);


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int m = Integer.parseInt("10");
                NumberPicker numberPicker=  new NumberPicker(context);
                numberPicker.setMaxValue(m);
                numberPicker.setMinValue(0);
                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        holder.button.setText(i1+"");
                        Log.i("PICKER",""+i);
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(numberPicker);
                builder.setTitle("Unidades")
                        .setIcon(R.drawable.ic_assignment_black_24dp);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.button.setText("0");
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return materialArrayList.size();
    }

    public Material findMaterial(String id,List<Material> list) {
        for(Material m : list) {
            if(m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }
    private void numberPicker(String max){
        int m = Integer.parseInt(max);
        NumberPicker numberPicker=  new NumberPicker(context);
        numberPicker.setMaxValue(m);
        numberPicker.setMinValue(0);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(numberPicker);
        builder.setTitle("Cantidad")
                .setIcon(R.drawable.ic_almacen);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    materialArrayList = list;
                } else {
                    List<Material> filteredList = new ArrayList<>();
                    for (Material row : list) {
                        Log.i("FILTER"," all:" + all + " cat:" +categoria);

                        if(all){
                            if (row.getDescripcion().toLowerCase().contains(charString.toLowerCase())
                                    || row.getMarca().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }else{
                            if ((row.getDescripcion().toLowerCase().contains(charString.toLowerCase())
                                    || row.getMarca().contains(charSequence)) &&
                                    row.getTipo().toLowerCase().contains(categoria.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                    }
                    materialArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = materialArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                materialArrayList = (ArrayList<Material>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDetalleDescripcion;
        private TextView tvDetalleUnidad;
        private TextView tvDetalleMarca;
        private TextView tvDetalleCantidad;
        private CheckBox checkBox;
        private View view;
        private Button button;
        private ImageView ivMaterial;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvDetalleDescripcion = itemView.findViewById(R.id.tvMaterialDescripcion);
            tvDetalleUnidad = itemView.findViewById(R.id.tvMaterialUnidad);
            tvDetalleMarca = itemView.findViewById(R.id.tvMatMarca);
            tvDetalleCantidad = itemView.findViewById(R.id.tvMatCantidad);
            checkBox = itemView.findViewById(R.id.cbSelect);
            button = itemView.findViewById(R.id.btnCantidad);
            ivMaterial = itemView.findViewById(R.id.ivMaterial);
        }
    }
}
