package com.aoslec.honey_all.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.honey_all.Activity.MainActivity;
import com.aoslec.honey_all.Adapter.KoreanFoodRecyclerAdapter;
import com.aoslec.honey_all.Bean.MenuBean;
import com.aoslec.honey_all.NetworkTask.NetworkTaskMenu;
import com.aoslec.honey_all.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Han extends Fragment {

    WebView wv_koreanfood;
    RecyclerView recycle_koreanfood;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    ArrayList<MenuBean> menu = null;
    String urlAddr = null;

    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_han, container, false);

        wv_koreanfood = view.findViewById(R.id.wv_koreanfood);
        recycle_koreanfood = view.findViewById(R.id.recycle_koreanfood);

        // web setting
        WebSettings webSettings = wv_koreanfood.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // web image
        wv_koreanfood.loadDataWithBaseURL(null,getKoreanFoodImage(), "text/html", "utf-8", null);

        urlAddr = "http://" + MainActivity.myIP + ":8080/honey/jsp/menu_select_koreanfood.jsp";

        getListData();

//        bottomNavigationView = view.findViewById(R.id.bottom_nabigetion);
//        bottomNavigationView.setVisibility(View.VISIBLE);

        return view;
    }

    public String getKoreanFoodImage() {
        String koreanFoodImage = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:300px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/korean.png\">" +
                "</body>" +
                "</html>";
        return koreanFoodImage;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getListData();
//    }

    private void getListData() {
        try {
            NetworkTaskMenu networkTask = new NetworkTaskMenu(getActivity(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            menu = (ArrayList<MenuBean>) obj;

            // recycler view
            layoutManager = new LinearLayoutManager(getActivity());
            recycle_koreanfood.setLayoutManager(layoutManager);

            adapter = new KoreanFoodRecyclerAdapter(getActivity(), R.layout.menu_layout, menu);
            recycle_koreanfood.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




//    @Override
//    public void onClick(View v, int position) {
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
////        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        FusionFood fusionFood = new FusionFood();
//        transaction.replace(R.id.pager,fusionFood);
//        transaction.commit();
//    }
}