package com.aoslec.honey_all.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.aoslec.honey_all.Adapter.TipAdapter;
import com.aoslec.honey_all.Bean.TipBean_y;
import com.aoslec.honey_all.Interface.DeleteOnClickListener;
import com.aoslec.honey_all.NetworkTask.NetworkTaskTip_y;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

public class TipActivity_y extends AppCompatActivity implements DeleteOnClickListener {

    WebView wv_tip_menu_image_y, wv_tip_title_y;
    TextView tv_tip_menu_name_y;
    String code, name, content;

    EditText et_tip_content;

    Button btn_tip_add_y, btn_tip_add_cancel_y, btn_tip_insert_y;

    RecyclerView recycle_tip_list_y;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    String urlAddr, insertUrl;
    ArrayList<TipBean_y> tips = null;

    Dialog insertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_y);

        Intent intent = getIntent();
        code = intent.getStringExtra("mCode");
        name = intent.getStringExtra("mName");
        Log.v("tip의", code.toString());

        tv_tip_menu_name_y = findViewById(R.id.tv_tip_menu_name_y);
        tv_tip_menu_name_y.setText(name.toString());

        wv_tip_menu_image_y = findViewById(R.id.wv_tip_menu_image_y);
        wv_tip_menu_image_y.loadDataWithBaseURL(null,getTipMenuImage(), "text/html", "utf-8", null);

        wv_tip_title_y = findViewById(R.id.wv_tip_title_y);
        wv_tip_title_y.loadDataWithBaseURL(null,getTipTitleImage(), "text/html", "utf-8", null);

        recycle_tip_list_y = findViewById(R.id.recycle_tip_list_y);
        btn_tip_add_y = findViewById(R.id.btn_tip_add_y);
        btn_tip_add_y.setOnClickListener(btnOnClickListener);

        urlAddr = "http://" + MainActivity.myIP + ":8080/honey/jsp/tip_select.jsp?mCode=" + code;



    }

    public String getTipMenuImage() {
        String image = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:72px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/foodcode" + code + ".png\">" +
                "</body>" +
                "</html>";
        return image;
    }


    public String getTipTitleImage() {
        String image = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:300px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/tip_title.png\">" +
                "</body>" +
                "</html>";
        return image;
    }


    @Override
    public void onResume() {
        Log.v("resume", "resume");
        super.onResume();
        getListData();
    }

    @Override
    protected void onPause() {
        Log.v("resume", "onPause");
        super.onPause();
        getListData();
    }

    public void getListData() {
        try {
            NetworkTaskTip_y networkTask = new NetworkTaskTip_y(TipActivity_y.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            tips = (ArrayList<TipBean_y>) obj;
//            Log.v("bbb", tips.get(2).getcName());
            layoutManager = new LinearLayoutManager(TipActivity_y.this);
            recycle_tip_list_y.setLayoutManager(layoutManager);

            Log.v("bbb", "2");
            adapter = new TipAdapter(TipActivity_y.this, R.layout.tip_item, tips, this);
            recycle_tip_list_y.setAdapter(adapter);

            Log.v("bbb", "3");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (MainActivity.cId.length() == 0) {
                Toast.makeText(TipActivity_y.this, "로그인 해주세요!", Toast.LENGTH_SHORT).show();
            } else {
                insertDialog = new Dialog(TipActivity_y.this);
                insertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                insertDialog.setContentView(R.layout.dialog);

                WindowManager.LayoutParams lpDown = new WindowManager.LayoutParams();
                lpDown.copyFrom(insertDialog.getWindow().getAttributes());
                lpDown.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lpDown.height = WindowManager.LayoutParams.WRAP_CONTENT;

                Window window = insertDialog.getWindow();
                window.setAttributes(lpDown);

                insertDialog.show();
                Log.v("tip", code + "/" + MainActivity.cId + "/");
                btn_tip_add_cancel_y = insertDialog.findViewById(R.id.btn_tip_add_cancel_y);
                btn_tip_insert_y = insertDialog.findViewById(R.id.btn_tip_insert_y);
                et_tip_content = insertDialog.findViewById(R.id.et_tip_content);

                btn_tip_add_cancel_y.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertDialog.dismiss();
                    }
                });

                btn_tip_insert_y.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String result = null;
                        content = et_tip_content.getText().toString();
                        insertUrl = "http://" + MainActivity.myIP + ":8080/honey/jsp/tip_insert.jsp?mCode=" + code + "&cId=" + MainActivity.cId + "&tipContent=" + content;
                        Log.v("tip", insertUrl);
                        try {
                            NetworkTaskTip_y networkTask = new NetworkTaskTip_y(TipActivity_y.this, insertUrl, "insert");
                            Object obj = networkTask.execute().get();

                            result = (String) obj;

                            if (result.equals("1")) {
                                Toast.makeText(TipActivity_y.this, "꿀팁 등록성공", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TipActivity_y.this, "혹시 로그인 안하셨나요?", Toast.LENGTH_SHORT).show();
                            }
//                        TipActivity_y.this.notify();
                            insertDialog.dismiss();
                            onResume();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    };

    @Override
    public void onDeleteClickListener(boolean isSelected) {
        if (isSelected) {
            getListData();
        }
    }
}