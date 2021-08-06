package com.aoslec.honey_all.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.honey_all.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity_j extends AppCompatActivity {
    Button btn;
   //gradlew signingReport sha 1 얻는법
    public static String macIP = MainActivity.myIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_s);
        getHashKey();
        btn = findViewById(R.id.btn);
        Log.v("start", "여기는 MainActivity");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Log.v("start", "여기는 MainActivity");

             ///   로구인으로 보내기
                Intent intent = null;
                intent = new Intent(MainActivity_j.this, Login_s.class);
                intent.putExtra("macIP",macIP);
                startActivity(intent);
//                ///   로구인으로 보내기
//                Intent  intent2= null;
//                 intent2 = new Intent(MainActivity.this, SignUp_j.class);
//                intent2.putExtra("macIP",macIP);
//                startActivity(intent2);

           }
        });

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

}