package com.aoslec.honey_all.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import com.aoslec.honey_all.Bean.FoodListBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask_m extends AsyncTask<Integer, String, Object> {


    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<FoodListBean> foodListBean;

    String CRUD = null;

    public NetworkTask_m(Context context, String mAddr, String CRUD) {
        this.context = context;
        this.mAddr = mAddr;
        this.foodListBean = foodListBean;
        this.foodListBean = new ArrayList<FoodListBean>();
        this.CRUD = CRUD;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("GEreGETGET.......!!");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
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
                if (CRUD.equals("select")) {
                    parserSelect(stringBuffer.toString());
                    //리턴값없고
                } else {
                    result = parserAction(stringBuffer.toString());
                    //리턴값있어
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (CRUD.equals("select")) {
            return foodListBean;
        } else {
            return result;
        }
    }

    private String parserAction(String str) {
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    private void parserSelect(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("kfood"));
            foodListBean.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String mCode = jsonObject1.getString("mCode");
                String mCategory = jsonObject1.getString("mCategory");
                String mName = jsonObject1.getString("mName");
                String mImagePath = jsonObject1.getString("mImagePath");
                String mAddDay = jsonObject1.getString("mAddDay");
                String mImage2 = jsonObject1.getString("mImage2");

                FoodListBean FoodListBean2 = new FoodListBean(mCode,mCategory,mName,mImagePath,mAddDay,mImage2);
//                Address_information_Bean address_information_beans2 = new Address_information_Bean(index, user_photo, first_name, last_name, telnumber);
                foodListBean.add(FoodListBean2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
