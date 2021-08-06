package com.aoslec.honey_all.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.aoslec.honey_all.Bean.BottomSheetBean;
import com.aoslec.honey_all.NetworkTask.NetworkTaskBottomSheet;
import com.aoslec.honey_all.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetClient_y extends BottomSheetDialogFragment {

    TextView tv_bottomsheet_username;
    TextView tv_bottomsheet_content;

    Button btn_bottom_nexttip;

    LinearLayout ll_bottomsheet_client, ll_bottomsheet_new_client;

    WebView wv_bottomsheet_menuimg;

    String urlAddr = null;
    String code = null;
    String mName = null;

    BottomSheetBean bottomSheet = new BottomSheetBean();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bottom_sheet, container, false);
//        connectGetData();
        tv_bottomsheet_username = view.findViewById(R.id.tv_bottomsheet_username);
        tv_bottomsheet_content = view.findViewById(R.id.tv_bottomsheet_content);
        wv_bottomsheet_menuimg = view.findViewById(R.id.wv_bottomsheet_menuimg);

        btn_bottom_nexttip = view.findViewById(R.id.btn_bottom_nexttip);

        ll_bottomsheet_client = view.findViewById(R.id.ll_bottomsheet_client);
        ll_bottomsheet_new_client = view.findViewById(R.id.ll_bottomsheet_new_client);

        wv_bottomsheet_menuimg.loadDataWithBaseURL(null, getNewClient(), "text/html", "utf-8", null);
        ll_bottomsheet_client.setVisibility(View.INVISIBLE);
        urlAddr = "http://" + MainActivity.myIP + ":8080/honey/jsp/bottom_select.jsp?cId=" + MainActivity.cId;

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.btn_bottom_nexttip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getView().findViewById(R.id.btn_bottom_gotip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(v.getContext(), TipActivity_y.class);
                intent.putExtra("mCode", code);
                intent.putExtra("mName", mName);
                startActivity(intent);
            }
        });

        getView().findViewById(R.id.btn_bottom_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override // 이거 잊지마세용@@@@@@@@@@@@@@@
    public void onResume() { // 여기서 수정되거나 무어ㅓ쩌거나 하는 정보가 담겨야지만 화면에 즉각 반응한다.
        super.onResume();
        connectGetData();
    }

    private void connectGetData() {
        try {

            NetworkTaskBottomSheet networkTask = new NetworkTaskBottomSheet(getActivity(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            ArrayList<BottomSheetBean> content = (ArrayList<BottomSheetBean>) obj;

            if(content.get(0).getName().length() > 0) {
                ll_bottomsheet_client.setVisibility(View.VISIBLE);
                ll_bottomsheet_new_client.setVisibility(View.INVISIBLE);
                tv_bottomsheet_username.setText(content.get(0).getName() + "님");
                tv_bottomsheet_content.setText("신선한 재료로 맛있는 식사하셨나요? \n" + content.get(0).getName() + "님만의 꿀팁이 있다면 등록해주세요!");

                code = content.get(0).getCode();
                mName = content.get(0).getmName();
                wv_bottomsheet_menuimg.loadDataWithBaseURL(null, getMenuImage(), "text/html", "utf-8", null);
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getMenuImage() {
        String image = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:100px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/foodcode" + code + ".png\">" +
                "</body>" +
                "</html>";
        return image;
    }

    public String getNewClient() {
        String image = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:100px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/new_client.png\">" +
                "</body>" +
                "</html>";
        return image;
    }


}