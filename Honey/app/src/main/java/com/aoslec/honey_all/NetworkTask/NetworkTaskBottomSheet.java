package com.aoslec.honey_all.NetworkTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.aoslec.honey_all.Bean.BottomSheetBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTaskBottomSheet extends AsyncTask<Integer, String, Object> {
    Context context = null;
    String mAddr = null;
    ArrayList<BottomSheetBean> bottomSheetArr = null;


    String where;

    public static String bottomSheet_userName;
    public static String bottomSheet_menuCode;
    public static String bottomSheet_buyDay;


    public NetworkTaskBottomSheet(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.bottomSheetArr = new ArrayList<>();
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

                if (where.equals("select")) {
                    Log.v("strLine", stringBuffer.toString());
                    parserSelect(stringBuffer.toString());
                } else {
                    result = parserAction(stringBuffer.toString()); // 리턴값을 받아야함~
                }

            }
        } catch (Exception e){
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
            return bottomSheetArr;
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

    private void parserSelect(String str) {
        Log.v("method", "parserSelect");
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("bottomsheet"));
            Log.v("bottomsheet", str);
//            ingredient.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String name = jsonObject1.getString("name");
                String code = jsonObject1.getString("code");
                String mName = jsonObject1.getString("mname");
                Log.v("task_name", name);

                BottomSheetBean bottomSheet = new BottomSheetBean(name, code, mName);
                bottomSheetArr.add(bottomSheet);

//                IngredientBean ingredientBean = new IngredientBean(isChecked, code, name, capacity, unit, price);
//                ingredient.add(ingredientBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
