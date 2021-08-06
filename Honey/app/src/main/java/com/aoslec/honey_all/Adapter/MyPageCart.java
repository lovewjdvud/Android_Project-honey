package com.aoslec.honey_all.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aoslec.honey_all.Bean.MyPageCartBean_m;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

//0625 해당 페이지 추가함
public class MyPageCart extends BaseAdapter {

    private static boolean mClick;
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<MyPageCartBean_m> data = null;
    private LayoutInflater inflater = null;


    public MyPageCart(Context mContext, int layout, ArrayList<MyPageCartBean_m> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();//카운트까지 돌아
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getClient_cId();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(this.layout, parent, false);
        TextView textView_tipHistory_menuCode = convertView.findViewById(R.id.textView_tipHistory_menuCode);
        TextView textView_tipHistory_menuName = convertView.findViewById(R.id.textView_tipHistory_menuName);
        TextView textView_tipHistory_menuTip = convertView.findViewById(R.id.textView_tipHistory_menuTip);
        TextView textView_tipHistory_TipAddDay = convertView.findViewById(R.id.textView_tipHistory_TipAddDay); //0626 tip day 추가

        textView_tipHistory_menuCode.setText(data.get(position).getmCode());
        textView_tipHistory_menuName.setText(data.get(position).getmName());
        textView_tipHistory_menuTip.setText(data.get(position).getTipContent());
        textView_tipHistory_TipAddDay.setText(data.get(position).getTipAddDay());//0626 tip day 추가

        return convertView;
    }
}
