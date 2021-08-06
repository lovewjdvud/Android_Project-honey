package com.aoslec.honey_all.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.honey_all.R;


public class DetailShowActivity_y extends AppCompatActivity {

    WebView wv_detail_image;
    Button btn_go_login_y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_show_y);

        wv_detail_image = findViewById(R.id.wv_detail_image);
        btn_go_login_y = findViewById(R.id.btn_go_login_y);

        wv_detail_image.loadDataWithBaseURL(null, getImage(), "text/html", "utf-8", null);

        btn_go_login_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailShowActivity_y.this, Login_s.class);
                startActivity(intent);
            }
        });
    }

    private String getImage() {
        String selectFoodImage = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:auto; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/greentest.png\">" +
                "</body>" +
                "</html>";
        return selectFoodImage;
    }
}