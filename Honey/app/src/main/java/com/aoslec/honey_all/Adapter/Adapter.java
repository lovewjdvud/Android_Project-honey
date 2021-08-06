package com.aoslec.honey_all.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aoslec.honey_all.Activity.KFood;


public class Adapter extends FragmentPagerAdapter {

    int tabCount;

    public Adapter(FragmentManager fm, int behavior){
        super(fm, behavior);
        this.tabCount = behavior;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new KFood();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
