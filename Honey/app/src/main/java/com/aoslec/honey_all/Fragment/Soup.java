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

import java.util.ArrayList;

public class Soup extends Fragment {

    WebView wv_soup_y;
    RecyclerView recycle_soup_y;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    ArrayList<MenuBean> menu = null;
    String urlAddr = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_soup, container, false);

        wv_soup_y = view.findViewById(R.id.wv_soup_y);
        recycle_soup_y = view.findViewById(R.id.recycle_soup_y);

        // web setting
        WebSettings webSettings = wv_soup_y.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // web image
        wv_soup_y.loadDataWithBaseURL(null,getSoupImage(), "text/html", "utf-8", null);

        urlAddr = "http://" + MainActivity.myIP + ":8080/honey/jsp/menu_select_soup.jsp";

        getListData();


        return view;
    }

    public String getSoupImage() {
        String soupImage = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:300px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/soup.png\">" +
                "</body>" +
                "</html>";
        return soupImage;
    }

    private void getListData() {
        try {
            NetworkTaskMenu networkTask = new NetworkTaskMenu(getActivity(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            menu = (ArrayList<MenuBean>) obj;

            // recycler view
            layoutManager = new LinearLayoutManager(getActivity());
            recycle_soup_y.setLayoutManager(layoutManager);

            adapter = new KoreanFoodRecyclerAdapter(getActivity(), R.layout.menu_layout, menu);
            recycle_soup_y.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}