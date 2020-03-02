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
import com.itoaxaca.yurta.pojos.Material;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InviteContactAdapter extends RecyclerView.Adapter<InviteContactAdapter.ItemViewHolder> implements Filterable {
    private List<Material> mContactList = new ArrayList<>();
    private List<Material> mContectFilter = new ArrayList<>();
    private Context mContext;
    private CustomFilter mFilter;
    public List<String> mEmailList = new ArrayList<>();
    public static  String categoria="";
    public static boolean all;

    public InviteContactAdapter(Context mContext, List<Material> mContactList) {
        mContext = mContext;
        this.mContactList = mContactList;
        this.mContectFilter = mContactList;
        mFilter = new CustomFilter();
    }

    public onItemClickListener onItemClickListener;

    public void setOnItemClickListener(InviteContactAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.view_material, viewGroup, false);
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
        final Material material = mContectFilter.get(i);
        itemViewHolder.tvDetalleDescripcion.setText(material.getDescripcion());
        itemViewHolder.tvDetalleUnidad.setText(material.getUnidad());
        itemViewHolder.tvDetalleMarca.setText(material.getMarca());

        if (material.isSelected())
            itemViewHolder.checkBox.setButtonDrawable(R.drawable.ic_check_box_black_24dp);
        else
            itemViewHolder.checkBox.setButtonDrawable(R.drawable.ic_check_box_outline_blank_black_24dp);

        if(material.getCantidadSolicitada()!=0)
            itemViewHolder.button.setText(material.getCantidadSolicitada()+"");
        else
            itemViewHolder.button.setText("0");


        itemViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    itemViewHolder.checkBox.setButtonDrawable(R.drawable.ic_check_box_black_24dp);
                }else{
                    itemViewHolder.checkBox.setButtonDrawable(R.drawable.ic_check_box_outline_blank_black_24dp);
                }
                material.setSelected(isChecked);

            }
        });


           Glide.with(itemViewHolder.ivMaterial.getContext())
                .load(material.getUrl_imagen())
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
                numberPicker.setMaxValue(100);
                numberPicker.setMinValue(0);
                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        itemViewHolder.button.setText(i1+"");
                        material.setCantidadSolicitada(i1);
                        cantidad[0] = i1;

                        Log.i("CANTIDAD",material.getCantidadSolicitada()+"");

                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(itemViewHolder.button.getContext()).setView(numberPicker);
                builder.setTitle("Unidades")
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
        return mContectFilter.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDetalleDescripcion;
        private TextView tvDetalleUnidad;
        private TextView tvDetalleMarca;
        private TextView tvDetalleCantidad;
        private CheckBox checkBox;
        private View view;
        private Button button;
        private ImageView ivMaterial;
        public ItemViewHolder(View itemView) {
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

    public List<String> getEmail() {
        mEmailList.clear();
        for (Material contact : mContectFilter) {
            if (contact.isSelected()) {
                mEmailList.add(contact.getDescripcion());
            }
        }
        return mEmailList;
    }

    /**
     * this class for filter data.
     */
    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence != null && charSequence.length() > 0) {
                ArrayList<Material> filters = new ArrayList<>();
                charSequence = charSequence.toString().toUpperCase();
                for (int i = 0; i < mContactList.size(); i++) {
                    if(all){
                        if (mContactList.get(i).getDescripcion().toUpperCase().contains(charSequence)
                                || mContactList.get(i).getMarca().toUpperCase().contains(charSequence)) {
                            Material m = mContactList.get(i);
                            filters.add(m);

                        }
                    }else{
                        if ( (mContactList.get(i).getDescripcion().toUpperCase().contains(charSequence)
                                || mContactList.get(i).getMarca().toUpperCase().contains(charSequence)) &&
                            mContactList.get(i).getTipo().toUpperCase().contains(categoria.toUpperCase())) {
                            Material m = mContactList.get(i);
                            filters.add(m);
                        }
                    }
                }
                results.count = filters.size();
                results.values = filters;

            } else {
                results.count = mContactList.size();
                results.values = mContactList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mContectFilter = (ArrayList<Material>) filterResults.values;
            notifyDataSetChanged();
        }
    }
    public Material findMaterial(String id,List<Material> list) {
        for(Material m : list) {
            if(m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }
}