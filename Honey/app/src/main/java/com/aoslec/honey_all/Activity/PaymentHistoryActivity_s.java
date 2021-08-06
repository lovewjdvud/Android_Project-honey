package com.aoslec.honey_all.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aoslec.honey_all.Adapter.PaymentHistoryRecyclerAdapter_s;
import com.aoslec.honey_all.Bean.PaymentHistory_s;
import com.aoslec.honey_all.NetworkTask.PaymentHistoryNetworkTask_s;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

public class PaymentHistoryActivity_s extends AppCompatActivity {

    String urlAddr = null;
    ArrayList<PaymentHistory_s> history;
    PaymentHistoryRecyclerAdapter_s rAdapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
//    RecyclerView.Adapter adapter;

    LinearLayout history_layout1_s, history_layout2_s;
    Button history_goBack_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history_s);

        recyclerView = findViewById(R.id.payment_history_recycler_view_s);

        history_layout1_s = findViewById(R.id.history_layout1_s);
        history_layout2_s = findViewById(R.id.history_layout2_s);

        history_goBack_btn = findViewById(R.id.history_goBack_btn_s);

        history_goBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    public void connectGetData(){
        try{

            urlAddr = MainActivity.hostRootAddr + "Payment_History_Select_Info.jsp?Client_cId=" + MainActivity.cId;

            PaymentHistoryNetworkTask_s networkTask = new PaymentHistoryNetworkTask_s(PaymentHistoryActivity_s.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            history = (ArrayList<PaymentHistory_s>) obj;

            if (history.isEmpty() == true){
                history_layout2_s.setVisibility(View.VISIBLE);
                history_layout1_s.setVisibility(View.INVISIBLE);
            }else {
                history_layout2_s.setVisibility(View.INVISIBLE);
                history_layout1_s.setVisibility(View.VISIBLE);

                layoutManager = new LinearLayoutManager(PaymentHistoryActivity_s.this);
                recyclerView.setLayoutManager(layoutManager);

                rAdapter = new PaymentHistoryRecyclerAdapter_s(PaymentHistoryActivity_s.this, R.layout.payment_history_card_layout_s, history);
                recyclerView.setAdapter(rAdapter);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}