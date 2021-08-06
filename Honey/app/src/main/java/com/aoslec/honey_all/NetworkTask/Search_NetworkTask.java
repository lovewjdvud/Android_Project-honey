package com.aoslec.honey_all.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.aoslec.honey_all.Activity.DatabaseHelper;
import com.aoslec.honey_all.Bean.SearchListBean;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Search_NetworkTask extends AsyncTask<Integer, String, Object> {


    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<SearchListBean> searchListBean;

    String CRUD = null;
    DatabaseHelper searchDB;


    public Search_NetworkTask(Context context, String CRUD) {

        this.context = context;
        this.mAddr = mAddr;
        this.searchListBean = searchListBean;
        this.searchListBean = new ArrayList<SearchListBean>();
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
        Log.v("logMessage", CRUD );
        try {
            Log.v("logMessage", "1" + CRUD);
                if (CRUD.equals("select")) {
                    Log.v("logMessage",  "6" + CRUD );
                    parserSelect(stringBuffer.toString());
                    //리턴값없고
                } else {
                    result = parserAction(stringBuffer.toString());
                    //리턴값있어
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
            return searchListBean;
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
//            JSONObject jsonObject = new JSONObject();
//            JSONArray jsonArray = new JSONArray(jsonObject.getString("searchList"));

            Cursor res = searchDB.getAllData();

            while(res.moveToNext()){
                String mSearchIndex = res.getString(0);
                String mSearchText = res.getString(1);
                String mSearchDate = res.getString(2);
                SearchListBean searchListBean2 = new SearchListBean(mSearchIndex, mSearchText, mSearchDate);
                searchListBean.add(searchListBean2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
