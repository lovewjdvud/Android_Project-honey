package com.aoslec.honey_all.NetworkTask;

import android.content.Context;
import android.os.AsyncTask;


import com.aoslec.honey_all.Bean.CartBean;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTaskInsertCart extends AsyncTask<Integer, String, Object> {
    Context context = null;
    String mAddr = null;
    ArrayList<CartBean> carts;

    // Network Task 를 검색, 입력, 수정, 삭제 구분없이 하나로 사용하기 위해 생성자 변수 추가.
    String where = null;


    public NetworkTaskInsertCart(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.carts = new ArrayList<>();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String result = null;

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strLine = bufferedReader.readLine();
                    if (strLine == null) break;
                    stringBuffer.append(strLine + "\n");
                }
//                if (where.equals("select")) {
//                    parserSelect(stringBuffer.toString());
//                } else { // select 만 좀 다르기때문에 나눔@@@@@@
                    result = parserAction(stringBuffer.toString()); // 리턴값을 받아야함~
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        if (where.equals("select")) {
            return carts;
        } else {
            return result;
        }
    }

    private String parserAction(String str) {
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
        }catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }

//    private void parserSelect(String str) {
//        try {
//            JSONObject jsonObject = new JSONObject(str);
//            JSONArray jsonArray = new JSONArray(jsonObject.getString("students_info"));
//            carts.clear();
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
//                String code = jsonObject1.getString("code");
//                String name = jsonObject1.getString("name");
//                String dept = jsonObject1.getString("dept");
//                String phone = jsonObject1.getString("phone");
//
//                Student member = new Student(code, name, dept, phone);
//                members.add(member);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
