package com.aoslec.honey_all.NetworkTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.aoslec.honey_all.Bean.TipBean_y;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTaskTip_y extends AsyncTask<Integer, String, Object> {
    Context context = null;
    String mAddr = null;
    ArrayList<TipBean_y> tip = null;

    String where;

    public NetworkTaskTip_y(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.tip = new ArrayList<>();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String result = null;
        String count = null;

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

                if (where.equals("count")) {
                    count = parserSelectCount(stringBuffer.toString());
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

        if (where.equals("count")) {
            return count;
        }


        if (where.equals("select")) {
            return tip;
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
//        Log.v("aaa", str);
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("tip"));
            Log.v("strLine-parserSelect", str);
            tip.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String code = jsonObject1.getString("code");
                String cId = jsonObject1.getString("id");
                String cName = jsonObject1.getString("name");
                String content = jsonObject1.getString("content");


                TipBean_y tipBean = new TipBean_y(code, cId, cName, content);
                tip.add(tipBean);
//                Log.v("aaa", content);
            }
//            Log.v("aaa", tip.get(0).getTipContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String parserSelectCount(String str) {
        String countResult = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("tipcount"));
            Log.v("strLine-parserSelect", str);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                countResult = jsonObject1.getString("count");


//                Log.v("aaa", content);
            }
//            Log.v("aaa", tip.get(0).getTipContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countResult;
    }


}
