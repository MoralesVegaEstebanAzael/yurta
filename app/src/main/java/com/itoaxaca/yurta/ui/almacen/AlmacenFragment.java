package com.itoaxaca.yurta.ui.almacen;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.itoaxaca.yurta.Constantes;
import com.itoaxaca.yurta.R;
import com.itoaxaca.yurta.adapter.SeccionAdapter;
import com.itoaxaca.yurta.response.Almacen;
import com.itoaxaca.yurta.response.AlmacenResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlmacenFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private View view;
    private AppBarLayout appBarLayout;
    TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Almacen> almacenArrayList;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_almacen, container, false);
        View parent = container.getRootView();
        //View parent =(View)container.getParent();
        if(Constantes.rotation==0){
            if(appBarLayout==null){
                appBarLayout = (AppBarLayout)parent.findViewById(R.id.appBarLayout);
                //appBarLayout.setBackground(R.color.colorAppBar);
                //appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorAppBar));
                tabLayout=new TabLayout(getActivity());

                appBarLayout.addView(tabLayout);

                viewPager = root.findViewById(R.id.viewPagerAlmacen);
                addViewPager(viewPager);
                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                });
                tabLayout.setupWithViewPager(viewPager);

                //try{
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_wall);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_categories_metalicos);
                tabLayout.getTabAt(2).setIcon(R.drawable.ic_categories_organic);
                tabLayout.getTabAt(3).setIcon(R.drawable.ic_format_color_fill_black_24dp);


               // tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
                //tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));

              // tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
                tabLayout.getTabAt (0) .getIcon (). setColorFilter (getResources (). getColor (android.R.color.white), PorterDuff.Mode.SRC_IN) ;
                tabLayout.getTabAt (1) .getIcon (). setColorFilter (getResources (). getColor (android.R.color.white), PorterDuff.Mode.SRC_IN) ;
                tabLayout.getTabAt (2) .getIcon (). setColorFilter (getResources (). getColor (android.R.color.white), PorterDuff.Mode.SRC_IN) ;
                tabLayout.getTabAt (3) .getIcon (). setColorFilter (getResources (). getColor (android.R.color.white), PorterDuff.Mode.SRC_IN) ;

            }
        }else{
            Constantes.rotation=1;
        }
        return root;
    }
    private void addViewPager(ViewPager viewPager){
        SeccionAdapter adapter=new SeccionAdapter(getFragmentManager());
        adapter.addFragment(new AlmacenTabFragment1(),"");
        adapter.addFragment(new AlmacenTabFragment2(),"");
        adapter.addFragment(new AlmacenTabFragment3(),"");
        adapter.addFragment(new AlmacenTabFragment3(),"");
        viewPager.setAdapter(adapter);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(Constantes.rotation==0)
        appBarLayout.removeView(tabLayout);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}