package com.viewpagermvvm.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }



    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
        notifyDataSetChanged();
    }

    public void addFragments(ArrayList<Fragment> fragment) {
        this.fragments.addAll(fragment);
    }
}
