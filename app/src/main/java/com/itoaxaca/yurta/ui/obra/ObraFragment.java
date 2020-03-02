package com.itoaxaca.yurta.ui.obra;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.dataBase.DataBaseHandler;
import com.itoaxaca.yurta.pojos.Obra;
import com.itoaxaca.yurta.preferences.Preferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


public class ObraFragment extends Fragment  implements OnMapReadyCallback{
    //api AIzaSyDOoifiSc2LnrhQwCJLy7xuaYgEo3xAE5s
    private GoogleMap map;
    private Obra obra;
    private View root;
    private TextView textViewDescripcion,textViewFecha,textViewDependencia,tvCountMateriales;
    private long countMateriales;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_obra, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)
                this.getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync((OnMapReadyCallback) this);
        return root;
    }

    private void init(){
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        HorizontalCalendar horizontalCalendar =
                new HorizontalCalendar.Builder(getActivity(), R.id.calendarView)
                        .range(startDate, endDate)
                        .datesNumberOnScreen(5)
                        .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

                Log.i("DIA",""+sdf.format(date.getTime()));
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        AsyncTaskLoadDB asyncTaskLoadDB = new AsyncTaskLoadDB();
        asyncTaskLoadDB.execute();
        map = googleMap;
    }


    public class AsyncTaskLoadDB extends AsyncTask<Void,Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            init();
            textViewFecha = root.findViewById(R.id.tvObraFecha);
            textViewDescripcion = root.findViewById(R.id.tvObraDescripcion);
            textViewDependencia = root.findViewById(R.id.tvObraDependencia);
            tvCountMateriales = root.findViewById(R.id.tvCountMateriales);
        }
        @Override
        protected Boolean doInBackground(Void... voids) {//acceso a la BD local en segundo plano

            Cursor cursor = DataBaseHandler
                    .getInstance(getContext())
                    .getObraID(Preferences.getPeferenceString(getContext(),Preferences.PREFERENCE_OBRA_ID));

            while(cursor.moveToNext()){
                obra = new Obra(cursor.getString(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),cursor.getString(8));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(obra!=null){
                Double lat = Double.parseDouble(obra.getLat());
                Double lng = Double.parseDouble(obra.getLng());
                LatLng latlng = new LatLng(lat,lng);
                map.addMarker(new MarkerOptions()
                        .position(latlng)
                        .title("UBICACION DE LA OBRA"));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(lat, lng))
                        .zoom(17)
                        .build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                textViewFecha.setText(obra.getFech_ini());
                textViewDescripcion.setText(obra.getDescripcion());
                textViewDependencia.setText(obra.getDependencia());
                countMateriales=DataBaseHandler.getInstance(getContext()).countMateriales(obra.getId());
                tvCountMateriales.setText(countMateriales+" Materiales de obra");
            }
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public static ObraFragment newInstance() {
        return new ObraFragment();
    }
}
