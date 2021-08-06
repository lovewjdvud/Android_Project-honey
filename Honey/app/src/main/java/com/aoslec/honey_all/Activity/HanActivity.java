package com.aoslec.honey_all.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.honey_all.Fragment.Han;
import com.aoslec.honey_all.R;


public class HanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_han);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_han, new Han()).commit();
    }
}