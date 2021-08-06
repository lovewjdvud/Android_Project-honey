package com.aoslec.honey_all.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.aoslec.honey_all.Bean.MyPageBean;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

public class MyPageAdapter extends BaseAdapter{

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<MyPageBean> data = null;
    private LayoutInflater inflater = null;

    public MyPageAdapter(Context mContext, int layout, ArrayList<MyPageBean> data) {
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
        return data.get(position).getUserName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(this.layout, parent, false);
        TextView tv_code = convertView.findViewById(R.id.userPw);
        TextView tv_name = convertView.findViewById(R.id.userName);
        TextView textview_id = convertView.findViewById(R.id.textview_id);
        TextView tv_dept = convertView.findViewById(R.id.userTel);
        TextView tv_phone = convertView.findViewById(R.id.userEmaile);
        TextView textview_cart_count = convertView.findViewById(R.id.textview_cart_count);

        tv_name.setText(data.get(position).getUserPw());
        tv_code.setText(data.get(position).getUserName());
        textview_id.setText(data.get(position).getUserName());
        tv_dept.setText(data.get(position).getUserTel());
        tv_phone.setText(data.get(position).getUserEmail());
        textview_cart_count.setText(data.get(position).getCartCount());

        return convertView;
    }
}
