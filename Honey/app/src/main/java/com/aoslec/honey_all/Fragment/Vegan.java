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


public class Vegan extends Fragment {

    WebView wv_vegan_y;
    RecyclerView recycle_vegan_y;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    ArrayList<MenuBean> menu = null;
    String urlAddr = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vegan, container, false);

        wv_vegan_y = view.findViewById(R.id.wv_vegan_y);
        recycle_vegan_y = view.findViewById(R.id.recycle_vegan_y);


        // web setting
        WebSettings webSettings = wv_vegan_y.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // web image
        wv_vegan_y.loadDataWithBaseURL(null,getVegantImage(), "text/html", "utf-8", null);

        urlAddr = "http://" + MainActivity.myIP + ":8080/honey/jsp/menu_select_vegan.jsp";

        getListData();

        return view;
    }


    public String getVegantImage() {
        String Image = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:300px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/vegan.png\">" +
                "</body>" +
                "</html>";
        return Image;
    }

    private void getListData() {
        try {
            NetworkTaskMenu networkTask = new NetworkTaskMenu(getActivity(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            menu = (ArrayList<MenuBean>) obj;

            // recycler view
            layoutManager = new LinearLayoutManager(getActivity());
            recycle_vegan_y.setLayoutManager(layoutManager);

            adapter = new KoreanFoodRecyclerAdapter(getActivity(), R.layout.menu_layout, menu);
            recycle_vegan_y.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}