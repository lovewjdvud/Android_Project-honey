package com.aoslec.honey_all.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.aoslec.honey_all.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    TextView tv_next;
    public static String myIP;
    public static String cId;
    public static String hostRootAddr;

    WebView wv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        tv_next = findViewById(R.id.tv_next);
        MainActivity.myIP = "192.168.2.9";
        MainActivity.cId = "";
        getHashKey();

        startLoading();

        MainActivity.hostRootAddr = "http://" + MainActivity.myIP + ":8080/honey/";

        wv_main = findViewById(R.id.wv_main);
        wv_main.loadDataWithBaseURL(null,getMainImage(), "text/html", "utf-8", null);

//        tv_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MainCategoryActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    ///////////////////// 해시 키 얻어오기 위한 메소드  //////////
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
    ///////////////////// 해시 키 얻어오기 위한 메소드  //////////

    private void startLoading(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MainCategoryActivity.class);
                startActivity(intent);
//                fhinish();
            }
        }, 1000);
    }

    public String getMainImage() {
        String selectFoodImage = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:410px; height:700px; src=\"http://" + MainActivity.myIP + ":8080/honey/img/mainImg.png\">" +
                "</body>" +
                "</html>";
        return selectFoodImage;
    }

}