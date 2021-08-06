package com.aoslec.honey_all.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aoslec.honey_all.Bean.BuyNoBankBook_s;
import com.aoslec.honey_all.NetworkTask.BuyNoBankNetworkTask_s;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

public class BuyNoBankBookActivity_s extends AppCompatActivity {

    String urlAddr = null;
    ArrayList<BuyNoBankBook_s> noBank;
    String BuyPriceResult, BuyDeliveryPrice, BuyDeliveryTip, BuyNumber;
    Intent intent;
    TextView NB_priceResult_tv, NB_deliveryTip_tv, NB_deliveryPrice_tv, noBankBook_buyAll_tv, noBankBook_buyAddr_tv, noBankBook_buyRequest_tv;
    Button noBankBook_orderDetail_btn, noBankBook_shopping_btn;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_no_bank_book_s);

        NB_priceResult_tv = findViewById(R.id.noBankBook_priceResult_tv_s);
        NB_deliveryTip_tv = findViewById(R.id.noBankBook_deliveryTip_tv_s);
        NB_deliveryPrice_tv = findViewById(R.id.noBankBook_deliveryPrice_tv_s);

        noBankBook_buyAll_tv = findViewById(R.id.noBankBook_buyAll_tv_s);
        noBankBook_buyAddr_tv = findViewById(R.id.noBankBook_buyAddr_tv_s);
        noBankBook_buyRequest_tv = findViewById(R.id.noBankBook_buyRequest_tv_s);

        noBankBook_shopping_btn =findViewById(R.id.noBankBook_shopping_btn_s);
        noBankBook_orderDetail_btn = findViewById(R.id.noBankBook_orderDetail_btn_s);

        noBankBook_shopping_btn.setOnClickListener(onClickListener);
        noBankBook_orderDetail_btn.setOnClickListener(onClickListener);

        intent = getIntent();
        BuyPriceResult = intent.getStringExtra("BuyPriceResult");
        BuyDeliveryTip = intent.getStringExtra("BuyDeliveryTip");
        BuyDeliveryPrice = intent.getStringExtra("BuyDeliveryPrice");
        BuyNumber = intent.getStringExtra("BuyNumber");

        NB_priceResult_tv.setText(BuyPriceResult);
        NB_deliveryTip_tv.setText(BuyDeliveryTip);
        NB_deliveryPrice_tv.setText(BuyDeliveryPrice);
    }
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData(){

        urlAddr = MainActivity.hostRootAddr + "Buy_Order_Select_Info.jsp?" + "buyNum=" + BuyNumber;

        try{
            BuyNoBankNetworkTask_s networkTask = new BuyNoBankNetworkTask_s(BuyNoBankBookActivity_s.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            noBank = (ArrayList<BuyNoBankBook_s>) obj;

            int conunt = Integer.parseInt(noBank.get(0).getCount());

            noBankBook_buyAll_tv.setText(noBank.get(0).getiName() + noBank.get(0).getiCapacity() + noBank.get(0).getiUnit() + "외 " + (conunt-1) + "개");
            noBankBook_buyAddr_tv.setText(noBank.get(0).getBuyPostNum() + ", "+ noBank.get(0).getBuyAddress1() + " " + noBank.get(0).getBuyAddress2());
            noBankBook_buyRequest_tv.setText(noBank.get(0).getBuyRequests());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.noBankBook_orderDetail_btn_s:
                    Intent intent = new Intent(BuyNoBankBookActivity_s.this, PaymentHistoryActivity_s.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.noBankBook_shopping_btn_s:
                    Intent intent1 = new Intent(BuyNoBankBookActivity_s.this, MainCategoryActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
            }
        }
    };

}