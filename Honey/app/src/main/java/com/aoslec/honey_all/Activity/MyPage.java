package com.aoslec.honey_all.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.honey_all.Adapter.MyPageAdapter;
import com.aoslec.honey_all.Bean.MyPageBean;
import com.aoslec.honey_all.NetworkTask.MyPageTask;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

public class MyPage extends AppCompatActivity {
    EditText userId, userPw, userName, userTel, userPostNum, userAddress1, userAddress2, userEmail;
    String userIdST, userPwST, userNameST, userTelST, userPostNumST, userAddress1ST, userAddress2ST, userEmailST;
    Button mypageUpdate, btn_logout, btn_logout_logout, btn_logout_cancel;

    String getURL;

    String CRUD = "select";

    Dialog logoutDialog;

    ArrayList<MyPageBean> ArrayListMyPage;
    MyPageAdapter myPageAdapter;
    StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        userId = findViewById(R.id.userId);
        userPw = findViewById(R.id.userPw);
        userName = findViewById(R.id.userName);
        userTel = findViewById(R.id.userTel);
        userPostNum = findViewById(R.id.userPostNum);
        userAddress1 = findViewById(R.id.userAddress1);
        userAddress2 = findViewById(R.id.userAddress2);
        userEmail = findViewById(R.id.userEmaile);

        //수정버튼
        mypageUpdate = findViewById(R.id.mypageUpdate);
        mypageUpdate.setOnClickListener(onClickListener);

        //로그아웃
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(logoutOnClickListener);


        Intent intent = getIntent();
        userId.setText(intent.getStringExtra("userId"));
        //0625 아이피주소 변경
        getURL = "http://"+MainActivity.myIP+":8080/honny_tip_m/mypageSelect.jsp?userId=" + MainActivity.cId;


        connectGetData();

    }

    @Override
    //수정되면 또 실행함 꼭 필요
    public void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData() {
        try {
            MyPageTask networkTask = new MyPageTask(MyPage.this, getURL, "select");
            Object obj = networkTask.execute().get();
            ArrayListMyPage = (ArrayList<MyPageBean>) obj;

            userId.setText(networkTask.userId);
            userPw.setText(networkTask.userPw);
            userName.setText(networkTask.userName);
            userTel.setText(networkTask.userTel);
            userPostNum.setText(networkTask.userPostNum);
            userAddress1.setText(networkTask.userAddress1);
            userAddress2.setText(networkTask.userAddress2);
            userEmail.setText(networkTask.userEmail);

//            myPageAdapter = new MyPageAdapter(MyPage.this, R.layout.activity_contact_information_list, ArrayListMyPage);
//            listView.setAdapter(myPageAdapter);
//            listView.setOnItemClickListener(onItemClickListener);
//            listView.setOnItemLongClickListener(onItemLongClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String userInformation = "userPw="+userPw.getText().toString()+"&userName="+userName.getText().toString()+"&userTel="+userTel.getText().toString()+"&userPostNum="+userPostNum.getText().toString()+"&userAddress1="+userAddress1.getText().toString()+"&userAddress2="+userAddress2.getText().toString()+"&userEmail="+userEmail.getText().toString()+"&userId="+userId.getText().toString();
            //0625 URL IP주소 바꿈.
            getURL = "http://"+MainActivity.myIP+":8080/honny_tip_m/mypageUpdate.jsp?"+userInformation; //0625

            String result = connectInsertData();
            if(result.equals("1")){
//                Toast.makeText(MyPage.this, userId.getText().toString()+"가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                // 0625 아래내용으로 바꿈
                Toast.makeText(MyPage.this, MainActivity.cId+"님 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MyPage.this, "수정이 실패 하였습니다.", Toast.LENGTH_SHORT).show();
            }
            finish();
            //메인화면으로 이동하는거야
        }
    };
    private String connectInsertData(){
        String result = null;
        try {
            MyPageTask networkTask = new MyPageTask(MyPage.this, getURL, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
        //잘끝났으면 1 아니면 에러
    }


    View.OnClickListener logoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logoutDialog = new Dialog(MyPage.this);
            logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            logoutDialog.setContentView(R.layout.logout_dialog);

            WindowManager.LayoutParams lpDown = new WindowManager.LayoutParams();
            lpDown.copyFrom(logoutDialog.getWindow().getAttributes());
            lpDown.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lpDown.height = WindowManager.LayoutParams.WRAP_CONTENT;

            Window window = logoutDialog.getWindow();
            window.setAttributes(lpDown);

            logoutDialog.show();
            btn_logout_cancel = logoutDialog.findViewById(R.id.btn_logout_cancel);
            btn_logout_logout = logoutDialog.findViewById(R.id.btn_logout_logout);

            btn_logout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutDialog.dismiss();
                }
            });

            btn_logout_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyPage.this, MainCategoryActivity.class);
                    startActivity(intent);
                    MainActivity.cId = "";
                    Toast.makeText(MyPage.this, "로그아웃 완료. 다음에 만나요~", Toast.LENGTH_SHORT).show();
                }
            });


        }
    };


}