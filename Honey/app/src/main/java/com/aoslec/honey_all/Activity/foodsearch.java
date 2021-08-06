package com.aoslec.honey_all.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.honey_all.Adapter.BsAdapter;
import com.aoslec.honey_all.Adapter.searchListAdapter;
import com.aoslec.honey_all.Bean.FoodListBean;
import com.aoslec.honey_all.NetworkTask.NetworkTask_m;
import com.aoslec.honey_all.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class foodsearch extends AppCompatActivity {

    EditText editTextSearch;
    Button imageViewSearchButton;
    LinearLayout layoutSearchList, layoutSearchListMenu;
    Button button_searchitem_delete;

    /**
     * DB
     **/
    DatabaseHelper searchDB;
    long mNow = 0;
    Date mDate = null;
    SimpleDateFormat mFormat = null;
    String stringDate = null, IPaddress = null;
    ListView listView, listView_menu;
    String urlAddr;
    //Cursor라는 그릇에 목록을 담아주기
    Cursor cursor = null;
    ArrayList<FoodListBean> foodListBeans; // 요리 리스트
    BsAdapter adapter; // 요리리스트 어뎁터

    @Override
    //수정되면 또 실행함 꼭 필요
    public void onResume() {
        super.onResume();
        connectGetData();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsearch);

        editTextSearch = findViewById(R.id.editTextSearch);
        imageViewSearchButton = findViewById(R.id.imageViewSearchButton);
        layoutSearchList = findViewById(R.id.layoutSearchList);
        layoutSearchListMenu = findViewById(R.id.layoutSearchListMenu);
        listView = findViewById(R.id.searchListView);
        listView_menu = findViewById(R.id.searchListView_menu);

        /******** Search Data Save Start (make by KMJ )*********/
        searchDB = new DatabaseHelper(this);

        Intent intent = getIntent();
        IPaddress = intent.getStringExtra("IPaddress");

        editTextSearch.setOnClickListener(onClickListener);
        imageViewSearchButton.setOnClickListener(onClickListener);


        listView.setOnItemClickListener(onItemClickListener1);
        listView_menu.setOnItemClickListener(onItemClickListener2);   //0625 수정

        editTextSearch.clearComposingText();
        displayList();

        /** 타이핑 실시간처리 -> afterTextChanged **/
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override//
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override//키를 누르면 변경
            public void afterTextChanged(Editable s) {
                layoutSearchList.setVisibility(View.VISIBLE);
                layoutSearchListMenu.setVisibility(View.INVISIBLE);
            }
        });
    }


    /**
     * 검색데이터 추가하기
     **/
    final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editTextSearch:
                    /**검색입력시 검색리스트 보이기**/
                    layoutSearchList.setVisibility(View.VISIBLE);
                    layoutSearchListMenu.setVisibility(View.INVISIBLE);
                    break;
                case R.id.imageViewSearchButton:
                    /**검색어 저장**/
                    searchSave();
                    break;
                default:
                    break;
            }
        }
    };


    searchListAdapter searchAdapter = new searchListAdapter();

    /**
     * 저장된 검색어 리스트 ListView
     **/
    public void displayList() {
        searchAdapter.list.clear();
        //Dbhelper의 읽기모드 객체를 가져와 SQLiteDatabase에 담아 사용준비
        searchDB = new DatabaseHelper(this);
        SQLiteDatabase database = searchDB.getReadableDatabase();

        //Cursor라는 그릇에 목록을 담아주기
        cursor = database.rawQuery("SELECT INDEX_NUM, SEARCH_TEXT,MAX(SEARCH_DATE) FROM SAVESEARCH WHERE SEARCH_DELETE is NULL GROUP BY SEARCH_TEXT ORDER BY SEARCH_DATE DESC", null);

        //리스트뷰에 목록 채워주는 도구인 adapter준비

        //목록의 개수만큼 순회하여 adapter에 있는 list배열에 add
        while (cursor.moveToNext()) {
            //num 행은 가장 첫번째에 있으니 0번이 되고, name은 1번
            searchAdapter.addItemToList(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }

        //리스트뷰의 어댑터 대상을 여태 설계한 adapter로 설정
        listView.setAdapter(searchAdapter);
    }


    /**
     * 검색어리스트 클릭시 기능
     **/
    AdapterView.OnItemClickListener onItemClickListener1 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            editTextSearch.setText(searchAdapter.list.get(position).getSearch_text());
        }
    };
    /**
     * 검색후 메뉴리스 클릭시 기능 0625 추가
     **/
    AdapterView.OnItemClickListener onItemClickListener2 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(foodsearch.this, SelectMenuActivity.class);
            intent.putExtra("mCode",foodListBeans.get(position).getmCode());
            intent.putExtra("mName",foodListBeans.get(position).getmName());
            startActivity(intent);
        }
    };



    /**
     * 검색어를 저장하기위한 시간 가저오기 (검색 데이터에 시간 넣어주기위해 필요)
     **/
    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        stringDate = mFormat.format(mDate);
        return stringDate;
    }
    /**
     * 검색어 SQLite 저장
     **/
    private void searchSave() {
        layoutSearchListMenu.setVisibility(View.VISIBLE);
        layoutSearchList.setVisibility(View.INVISIBLE);
        getTime();
        boolean isInserted = searchDB.insertData(editTextSearch.getText().toString(), stringDate);
        if (isInserted == true || !editTextSearch.getText().toString().equals("")) {
            Toast.makeText(foodsearch.this, "검색 성공", Toast.LENGTH_SHORT).show();
            displayList();
        } else {
            Toast.makeText(foodsearch.this, "검색 실패 \n", Toast.LENGTH_SHORT).show();
        }
        //0625 URL IP주소 바꿈.
        urlAddr = "http://" + MainActivity.myIP + ":8080/honny_tip_m/honny_tip_kfood_Select_m.jsp?searchVlaue=" + editTextSearch.getText().toString();
        connectGetData();
    }

    /**
     * 검색 후 검색된  '메뉴 보여주기'  기능
     * 0625 BsAdaptet 어뎁터에 출력되는 이미지 출력되는 부분 TextView  ->  imageView 로 변경
     **/
    private void connectGetData() {
        try {
            displayList();
            NetworkTask_m networkTask = new NetworkTask_m(foodsearch.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            foodListBeans = (ArrayList<FoodListBean>) obj;

            adapter = new BsAdapter(foodsearch.this, R.layout.kfood_list_item_m, foodListBeans);
            listView_menu.setAdapter(adapter);

//            listView.setOnItemClickListener(onItemClickListener);
//            listView.setOnItemLongClickListener(onItemLongClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}