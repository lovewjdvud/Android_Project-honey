package com.aoslec.honey_all.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.aoslec.honey_all.Bean.MyPageCartBean_m;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//0625 해당 페이지 추가함
public class MyPageCartTask_m extends AsyncTask<Integer, String, Object> {

    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<MyPageCartBean_m> myPageCartBean_ms;
    StringBuffer stringBuffer = new StringBuffer();

    String CRUD = null;

    public MyPageCartTask_m(Context context, String mAddr, String CRUD) {
        this.context = context;
        this.mAddr = mAddr;
        this.myPageCartBean_ms = myPageCartBean_ms;
        this.myPageCartBean_ms = new ArrayList<MyPageCartBean_m>();
        this.CRUD = CRUD;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("get.......!!");
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
            return myPageCartBean_ms;
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
            JSONArray jsonArray = new JSONArray(jsonObject.getString("MypageCart"));
            myPageCartBean_ms.clear();


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String Client_cId = jsonObject1.getString("Client_cId");
                String mCode = jsonObject1.getString("mCode");
                String mName = jsonObject1.getString("mName");
                String tipContent = jsonObject1.getString("tipContent");
                String tipAddDay = jsonObject1.getString("tipAddDay");//0626 tip day 추가

                MyPageCartBean_m address_information_beans2 = new MyPageCartBean_m(Client_cId, mCode, mName, tipContent, tipAddDay);//0626 tip day 추가
//                Address_information_Bean address_information_beans2 = new Address_information_Bean(index, user_photo, first_name, last_name, telnumber);
                myPageCartBean_ms.add(address_information_beans2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
