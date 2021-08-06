package com.aoslec.honey_all.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.aoslec.honey_all.Bean.Cart_s;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CartNetworkTask_s extends AsyncTask<Integer, String, Object> {

    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<Cart_s> cartS;

    // Network Task를 검색, 입력, 수정, 삭제 구분없이 하나로 사용키 위해 생성자 변수 추가.
    String where = null;

    public CartNetworkTask_s(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.cartS = cartS;
        this.cartS = new ArrayList<Cart_s>();
        this.where = where;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Get.....");
        progressDialog.show();
    }

    //바뀌는 데이터... 진행중에 쓰는것
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
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

        try{
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true){
                    String strLine = bufferedReader.readLine();
                    if (strLine == null) break;
                    stringBuffer.append(strLine + "\n");
                }
                if(where.equals("select")){
                    parserSelect(stringBuffer.toString());
                    //리턴값없고
                }else{
                    result = parserAction(stringBuffer.toString());
                    //리턴값있어
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (where.equals("select")){
            return cartS;
        }else {
            return result;
        }
    }
    private String parserAction(String str){
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }

    private void parserSelect(String str){
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("carts_info"));
            cartS.clear();

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                int cartCode = jsonObject1.getInt("cartCode");
                int cartEA = jsonObject1.getInt("cartEA");
                String iName = jsonObject1.getString("iName");
                String iCapacity = jsonObject1.getString("iCapacity");
                String iUnit = jsonObject1.getString("iUnit");
                int iPrice = jsonObject1.getInt("iPrice");
                String mName = jsonObject1.getString("mName");
                String mImagePath = jsonObject1.getString("mImagePath");

                Cart_s cart = new Cart_s(cartCode, cartEA, iName, iCapacity, iUnit, iPrice, mName, mImagePath);
                cartS.add(cart);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
