package com.aoslec.honey_all.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoslec.honey_all.Activity.DatabaseHelper;
import com.aoslec.honey_all.Activity.foodsearch;
import com.aoslec.honey_all.Bean.SearchListBean;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

public class searchListAdapter extends BaseAdapter {

    public ArrayList<SearchListBean> list = new ArrayList<SearchListBean>();
    public ArrayList<String> arrlist = new ArrayList<String>();
    SQLiteDatabase DB;
    foodsearch foodsearch;
    @Override
    public int getCount() {
        return list.size(); //그냥 배열의 크기를 반환하면 됨
    }

    @Override
    public Object getItem(int i) {
        return list.get(i); //배열에 아이템을 현재 위치값을 넣어 가져옴
    }

    @Override
    public long getItemId(int i) {
        return i; //그냥 위치값을 반환해도 되지만 원한다면 아이템의 num 을 반환해도 된다.
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Context context = viewGroup.getContext();
        //리스트뷰에 아이템이 인플레이트 되어있는지 확인한후
        //아이템이 없다면 아래처럼 아이템 레이아웃을 인플레이트 하고 view객체에 담는다.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_listview_text, viewGroup, false);
        }
        //이제 아이템에 존재하는 텍스트뷰 객체들을 view객체에서 찾아 가져온다
        TextView search_index = (TextView) view.findViewById(R.id.textView_searchList_index);
        TextView search_text = (TextView) view.findViewById(R.id.textView_searchList_text);
        TextView search_date = (TextView) view.findViewById(R.id.textView_searchList_date);

        //현재 포지션에 해당하는 아이템에 글자를 적용하기 위해 list배열에서 객체를 가져온다.
        SearchListBean listdata = list.get(i);

        //가져온 객체안에 있는 글자들을 각 뷰에 적용한다
        search_index.setText(listdata.getSearch_index()); //원래 int형이라 String으로 형 변환
        search_text.setText(listdata.getSearch_text());
        search_date.setText(listdata.getSearch_date());


        ImageView imageView_search_delete = view.findViewById(R.id.imageView_search_delete);
        imageView_search_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(v.getContext());
                DB = databaseHelper.getWritableDatabase();
                String query = "UPDATE SAVESEARCH SET SEARCH_DELETE = 'delete' WHERE SEARCH_TEXT = '" + list.get(i).getSearch_text() + "';";
                DB.execSQL(query);

                list.remove(list.get(i));
                notifyDataSetChanged();
            }
        });

        return view;
    }

    //ArrayList로 선언된 list 변수에 목록을 채워주기 위함 다른방시으로 구현해도 됨
    public void addItemToList(int index, String text, String date) {
        SearchListBean listdata = new SearchListBean();

        listdata.setSearch_index(Integer.toString(index));
        listdata.setSearch_text(text);
        listdata.setSearch_date(date);

        //값들의 조립이 완성된 listdata객체 한개를 list배열에 추가
        list.add(listdata);
        arrlist.add(text);
    }

}
