package com.aoslec.honey_all.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.aoslec.honey_all.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetNonClient_y extends BottomSheetDialogFragment {

    WebView wv_bottomsheet_img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_bottom_sheet_non_client_y, container, false);
        wv_bottomsheet_img = view.findViewById(R.id.wv_bottomsheet_img);
        wv_bottomsheet_img.loadDataWithBaseURL(null,getBottomSheetImage(), "text/html", "utf-8", null);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getView().findViewById(R.id.btn_detail_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(v.getContext(), DetailShowActivity_y.class);
                startActivity(intent);
            }
        });

    }

    private String getBottomSheetImage() {
        String bottomSheetImg = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:400px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/bottomsheet.png\">" +
                "</body>" +
                "</html>";
        return bottomSheetImg;
    }
}