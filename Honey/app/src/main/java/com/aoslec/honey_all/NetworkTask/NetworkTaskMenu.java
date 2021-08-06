package com.aoslec.honey_all.NetworkTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.aoslec.honey_all.Bean.MenuBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTaskMenu extends AsyncTask<Integer, String, Object> {
    Context context = null;
    String mAddr = null;
    ArrayList<MenuBean> Menu = null;

    String where;

    public NetworkTaskMenu(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.Menu = new ArrayList<MenuBean>();
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
            Log.v("inputStream", String.valueOf(httpURLConnection.getInputStream()));
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                Log.v("inputStream", String.valueOf(httpURLConnection.getInputStream()));
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
            return Menu;
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
//        Log.v("method", "parserSelect");
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("food"));
            Log.v("strLine-parserSelect", str);
            Menu.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String code = jsonObject1.getString("code");
                String category = jsonObject1.getString("category");
                String name = jsonObject1.getString("name");
                String image1 = jsonObject1.getString("image1");

                MenuBean menu = new MenuBean(code, category, name, image1);
                Menu.add(menu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
