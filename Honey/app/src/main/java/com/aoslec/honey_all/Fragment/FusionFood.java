package com.aoslec.honey_all.Fragment;

import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

public class FusionFood extends Fragment {

    WebView wv_dessert_y;
    RecyclerView recycle_dessert_y;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    ArrayList<MenuBean> menu = null;
    String urlAddr = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fusion_food, container, false);
        Log.v("dessert", "fragment");
        wv_dessert_y = view.findViewById(R.id.wv_dessert_y);
        recycle_dessert_y = view.findViewById(R.id.recycle_dessert_y);


        // web setting
        WebSettings webSettings = wv_dessert_y.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // web image
        wv_dessert_y.loadDataWithBaseURL(null,getDessertImage(), "text/html", "utf-8", null);

        urlAddr = "http://" + MainActivity.myIP + ":8080/honey/jsp/menu_select_dessert.jsp";

        getListData();

        return view;
    }

    public String getDessertImage() {
        String Image = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:300px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/dessert.png\">" +
                "</body>" +
                "</html>";
        return Image;
    }

    private void getListData() {
        try {
            NetworkTaskMenu networkTask = new NetworkTaskMenu(getActivity(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            menu = (ArrayList<MenuBean>) obj;
            Log.v("dessert", "task");
            // recycler view
            layoutManager = new LinearLayoutManager(getActivity());
            recycle_dessert_y.setLayoutManager(layoutManager);

            adapter = new KoreanFoodRecyclerAdapter(getActivity(), R.layout.menu_layout, menu);
            recycle_dessert_y.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}