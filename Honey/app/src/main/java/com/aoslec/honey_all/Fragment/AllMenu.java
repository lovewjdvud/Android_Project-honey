package com.aoslec.honey_all.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.aoslec.honey_all.Activity.GoogleMapAPIActivity_s;
import com.aoslec.honey_all.Activity.MainActivity;
import com.aoslec.honey_all.Activity.foodsearch;
import com.aoslec.honey_all.Adapter.ViewPagerAdapter;
import com.aoslec.honey_all.R;

import java.util.Timer;
import java.util.TimerTask;

public class AllMenu extends Fragment {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    WebView wv_main_image1, wv_main_image2, wv_main_image3, wv_main_image4;
    Button main_btn;
    EditText et_main_search;


    Integer[] imageId = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    String[] imagesName = {"image1", "image2", "image3"};

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_all_menu, container, false);

        main_btn = view.findViewById(R.id.main_btn_s);
        main_btn.setOnClickListener(onClickListener);
        et_main_search = view.findViewById(R.id.et_main_search);

        viewPager = view.findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getActivity(), imageId, imagesName);
        viewPager.setAdapter(adapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == 3) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        wv_main_image1 = view.findViewById(R.id.wv_main_image1);
        wv_main_image1.loadDataWithBaseURL(null,getMainImage1(), "text/html", "utf-8", null);


        et_main_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), foodsearch.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private String getMainImage1(){
        String image = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:350px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/mainimg-1.png\">" +
                "</body>" +
                "</html>";
        return image;
    }

    private String getMainImage2(){
        String image = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; '>" +
                "<img style=width:100px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/mainimage2.png\">" +
                "</body>" +
                "</html>";
        return image;
    }

    private String getMainImage3(){
        String image = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; '>" +
                "<img style=width:100px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/mainimage3.png\">" +
                "</body>" +
                "</html>";
        return image;
    }

    private String getMainImage4(){
        String image = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:100px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/mainimage3.png\">" +
                "</body>" +
                "</html>";
        return image;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent1 = new Intent(v.getContext(), GoogleMapAPIActivity_s.class);
            startActivity(intent1);
        }
    };


}