package com.aoslec.honey_all.Adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aoslec.honey_all.Fragment.AllMenu;
import com.aoslec.honey_all.Fragment.FusionFood;
import com.aoslec.honey_all.Fragment.Han;
import com.aoslec.honey_all.Fragment.Instant;
import com.aoslec.honey_all.Fragment.SchoolFood;
import com.aoslec.honey_all.Fragment.Soup;
import com.aoslec.honey_all.Fragment.Vegan;


public class TopTabPagerAdapter extends FragmentPagerAdapter {
    //                          FragmentPagerAdapter 상속받는다.

    int tabCount; // TabBar에 몇개인지 나타내기위해서


    public TopTabPagerAdapter(FragmentManager fm, int behavior) { // 따로 생성자를 만들어줘야한다.
        super(fm, behavior);
        this.tabCount = behavior;

    }

    @Override
    public Fragment getItem(int position) { // Tab을 눌렀을 때마다의 보여줘야하는 화면
        switch (position) {
            case 0:
                AllMenu allMenu = new AllMenu();
                return allMenu;
            case 1:
                Han han = new Han();
                return han;
            case 2:
                SchoolFood schoolFood = new SchoolFood();
                return schoolFood;
            case 3:
                Soup soup = new Soup();
                return soup;
            case 4:
                Instant instant = new Instant();
                return instant;
            case 5:
                FusionFood fusionFood = new FusionFood();
                Log.v("dessert", "topAdapter");
                return fusionFood;
            case 6:
                Vegan vegan = new Vegan();
                return vegan;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
