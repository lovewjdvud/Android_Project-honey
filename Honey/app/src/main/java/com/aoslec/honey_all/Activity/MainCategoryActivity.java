package com.aoslec.honey_all.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aoslec.honey_all.Adapter.TopTabPagerAdapter;
import com.aoslec.honey_all.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MainCategoryActivity extends AppCompatActivity {

    BottomSheetClient_y bottomSheetClienty;
    BottomSheetNonClient_y bottomSheetNonClient_y;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);


        bottomNavigationView = findViewById(R.id.bottom_nabigetion);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.tab_cart:
                        if(MainActivity.cId.length() == 0){
                            Snackbar.make(findViewById(android.R.id.content), "로그인 해주세요!", Snackbar.LENGTH_SHORT).show();
//                            Toast.makeText(MainCategoryActivity.this, "로그인 해주세요!", Toast.LENGTH_SHORT).show();
                        } else {
                            intent = new Intent(MainCategoryActivity.this, CartActivity_s.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.tab_buyHistory:
                        // 결제내역  // 추가@@
                        if(MainActivity.cId.length() == 0){
                            Snackbar.make(findViewById(android.R.id.content), "로그인 해주세요!", Snackbar.LENGTH_SHORT).show();
//                            Toast.makeText(MainCategoryActivity.this, "로그인 해주세요!", Toast.LENGTH_SHORT).show();
                        } else {
                            intent = new Intent(MainCategoryActivity.this, PaymentHistoryActivity_s.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.tab_mypage:
                        // 마이페이지
                        if (MainActivity.cId.length() == 0) {
                            intent = new Intent(MainCategoryActivity.this, Login_s.class);
                            startActivity(intent);
                        }else{
                            intent = new Intent(MainCategoryActivity.this, MyPage_cart_m.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.tab_search:
                        // 검색
                        intent = new Intent(MainCategoryActivity.this, foodsearch.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });


        if (MainActivity.cId.isEmpty() == true) {
            bottomSheetNonClient_y = new BottomSheetNonClient_y();
            bottomSheetNonClient_y.show(getSupportFragmentManager(), bottomSheetNonClient_y.getTag());
        } else {
            bottomSheetClienty = new BottomSheetClient_y();
            bottomSheetClienty.show(getSupportFragmentManager(), bottomSheetClienty.getTag());
        }

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // content_tab_layout.xml 과 연결
        tabLayout.addTab(tabLayout.newTab().setText("HOME").setIcon(R.drawable.ic_baseline_home_24));
        tabLayout.addTab(tabLayout.newTab().setText("한식").setIcon(R.drawable.icon_koreanfood));
        tabLayout.addTab(tabLayout.newTab().setText("분식").setIcon(R.drawable.icon_schoolfood));
        tabLayout.addTab(tabLayout.newTab().setText("탕요리").setIcon(R.drawable.icon_soup));
        tabLayout.addTab(tabLayout.newTab().setText("인스턴트").setIcon(R.drawable.icon_instant));
        tabLayout.addTab(tabLayout.newTab().setText("디저트").setIcon(R.drawable.icon_dessert));
        tabLayout.addTab(tabLayout.newTab().setText("채식").setIcon(R.drawable.icon_vegan));

        final int selectColor = ContextCompat.getColor(this, R.color.select);
        final int unSelectColor = ContextCompat.getColor(this,R.color.unSelect);

        tabLayout.getTabAt(0).getIcon().setColorFilter(selectColor, PorterDuff.Mode.SRC_IN);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).getIcon().setColorFilter(unSelectColor, PorterDuff.Mode.SRC_IN);
        }

        ViewPager viewPager = findViewById(R.id.pager);
        // activity_main.xml 의 tab 별 내용 연결
        PagerAdapter pagerAdapter = new TopTabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        // TabPagerAdapter 와 연결 및 TabPagerAdapter의 생성자 파라미터(메니져, 위에 구한 getTabCount)
        viewPager.setAdapter(pagerAdapter);
        // TabPagerAdapter 셋팅


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // viewPager 는 말그대로 보여지는 곳. 그곳에 대한 이벤트.

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // 이게 탭에서 클릭을 했을 경우 내용이 보여지는 곳.
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                // viewPager 에 TabPagerAdapter 의 getItem 실행
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