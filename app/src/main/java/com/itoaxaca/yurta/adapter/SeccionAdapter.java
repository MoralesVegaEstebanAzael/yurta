package com.itoaxaca.yurta.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SeccionAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> listFragments=new ArrayList<>();
    private final ArrayList<String> listTitles=new ArrayList<>();
    public SeccionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }
    public void addFragment(Fragment fragment,String title){
        listFragments.add(fragment);
        listTitles.add(title);
    }

}
