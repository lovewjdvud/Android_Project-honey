package com.aoslec.honey_all.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aoslec.honey_all.Adapter.Adapter;
import com.aoslec.honey_all.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity_m extends AppCompatActivity {

    String IPaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_m);

        IPaddress = "192.168.55.217";
        TabLayout tabLayout = findViewById(R.id.Tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("한식").setIcon(android.R.drawable.ic_dialog_email));

        ViewPager viewPager = findViewById(R.id.pager);

        PagerAdapter pagerAdapter = new Adapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
//        PagerAdapter pagerAdapter1 = new TabPagerAdapter(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //기능추가
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}