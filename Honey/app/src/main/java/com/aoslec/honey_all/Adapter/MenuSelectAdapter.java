package com.aoslec.honey_all.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.aoslec.honey_all.Bean.IngredientBean;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

public class MenuSelectAdapter extends BaseAdapter {
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<IngredientBean> data = null;
    private LayoutInflater inflater = null;

    public MenuSelectAdapter(Context mContext, int layout, ArrayList<IngredientBean> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getiCode();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(this.layout, parent, false);
        TextView tv_menu_select_ingredient = convertView.findViewById(R.id.tv_menu_select_ingredient);
        TextView tv_menu_select_ingredient_price = convertView.findViewById(R.id.tv_menu_select_ingredient_price);

        String name = data.get(position).getiName();
        String Capacity = data.get(position).getiCapacity();
        String Unit = data.get(position).getiUnit();
        String price = data.get(position).getiPrice();

        String fullName = name + " " + Capacity + " " + Unit;
        String fullPrice = price + "원";

        tv_menu_select_ingredient.setText(fullName);
        tv_menu_select_ingredient_price.setText(fullPrice);

        return convertView;
    }
}
