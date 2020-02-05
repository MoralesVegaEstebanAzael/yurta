package com.itoaxaca.yurta.ui.almacen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

public class AlmacenFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private View view;
    private AppBarLayout appBarLayout;
     TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_almacen, container, false);
        View parent = container.getRootView();
        //View parent =(View)container.getParent();
        if(Constantes.rotation==0){
            if(appBarLayout==null){
                appBarLayout = (AppBarLayout)parent.findViewById(R.id.appBarLayout);
                //appBarLayout.setBackground(R.color.colorAppBar);
                appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorAppBar));
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
            }
        }else{
            Constantes.rotation=1;
        }
        return root;
    }
    private void addViewPager(ViewPager viewPager){
        SeccionAdapter adapter=new SeccionAdapter(getFragmentManager());
        adapter.addFragment(new AlmacenTabFragment1(),"TAB1");
        adapter.addFragment(new AlmacenTabFragment2(),"TAB2");
        adapter.addFragment(new AlmacenTabFragment3(),"TAB3");
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