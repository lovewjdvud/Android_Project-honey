package com.aoslec.honey_all.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.aoslec.honey_all.Activity.MyPage;
import com.aoslec.honey_all.Bean.MyPageBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyPageTask extends AsyncTask<Integer, String, Object> {

    public String userId = null;
    public String userPw = null;
    public String userName = null;
    public String userTel = null;
    public String userPostNum = null;
    public String userAddress1 = null;
    public String userAddress2 = null;
    public String userEmail = null;
    public String cartCount = null;

    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<MyPageBean> myPage1;
    MyPage getmyPage = null;
    String CRUD = null;

    public MyPageTask(Context context, String mAddr, String CRUD) {
        this.context = context;
        this.mAddr = mAddr;
        this.myPage1 = myPage1;
        this.myPage1 = new ArrayList<MyPageBean>();
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
            return myPage1;
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

    public void parserSelect(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("myPage"));
            myPage1.clear();

            JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
            userId = jsonObject1.getString("userId");
            userPw = jsonObject1.getString("userPw");
            userName = jsonObject1.getString("userName");
            userTel = jsonObject1.getString("userTel");
            userPostNum = jsonObject1.getString("userPostNum");
            userAddress1 = jsonObject1.getString("userAddress1");
            userAddress2 = jsonObject1.getString("userAddress2");
            userEmail = jsonObject1.getString("userEmail");
            cartCount = jsonObject1.getString("cartCount");

            MyPageBean myPage2 = new MyPageBean(userPw, userName, userTel, userEmail, cartCount);
            myPage1.add(myPage2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}