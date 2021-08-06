package com.aoslec.honey_all.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SEARCH.db"; // 데이터베이스 명
    public static final String TABLE_NAME = "SAVESEARCH"; // 테이블 명

    // 테이블 항목
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SEARCH_TEXT";
    public static final String COL_3 = "SEARCH_DATE";
    public static final String COL_4 = "Address";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(INDEX_NUM INTEGER PRIMARY KEY AUTOINCREMENT, SEARCH_TEXT, SEARCH_DATE, SEARCH_DELETE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    // 데이터베이스 추가하기 insert

    public boolean insertData(String mSearch, String mDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,mSearch);
        contentValues.put(COL_3,mDate);
        long result = db.insert(TABLE_NAME, null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    //데이터베이스 항목 읽어오기 Read
    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);

        return  res;
    }

    // 데이터베이스 삭제하기
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID = ? ",new String[]{id});
    }

    public void delete_item(String SEARCH_TEXT){
        Log.v("logm", SEARCH_TEXT);
//        SQLiteDatabase DB = this.getWritableDatabase();
//        try {
//            String query = "UPDATE "+TABLE_NAME+" SET SEARCH_DELETE = 'delete' WHERE SEARCH_TEXT = '"+SEARCH_TEXT+"' ";
//            DB.execSQL(query);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
    //데이터베이스 수정하기
    public boolean updateData(String id, String name, String phone, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,phone);
        contentValues.put(COL_4,address);
        db.update(TABLE_NAME,contentValues,"ID = ?", new String[] { id });
        return true;
    }
}