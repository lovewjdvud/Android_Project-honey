package com.aoslec.honey_all.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoslec.honey_all.Activity.MainActivity;
import com.aoslec.honey_all.Bean.FoodListBean;
import com.aoslec.honey_all.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class BsAdapter extends BaseAdapter {

    private static boolean mClick;
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<FoodListBean> data = null;
    private LayoutInflater inflater = null;


    public BsAdapter(Context mContext, int layout, ArrayList<FoodListBean> data) {
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
        return data.get(position).getmCode();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(this.layout, parent, false);


        ImageView imageVIew_kfood_mImage2 = convertView.findViewById(R.id.imageVIew_kfood_mImage2);
        TextView textVIew_kfood_mName = convertView.findViewById(R.id.textVIew_kfood_mName);
        TextView imageVIew_kfood_mImagePath = convertView.findViewById(R.id.imageVIew_kfood_mImagePath);
        TextView textVIew_kfood_mCode = convertView.findViewById(R.id.textVIew_kfood_mCode);
        TextView textVIew_kfood_mAddDay = convertView.findViewById(R.id.textVIew_kfood_mAddDay);
        TextView textVIew_kfood_mCategory = convertView.findViewById(R.id.textVIew_kfood_mCategory);


//        imageVIew_kfood_mImage2.setText(data.get(position).getmImage2());
//      0625이미지를 글라이드로 바꿈.
        Glide.with(convertView)
                .load(MainActivity.hostRootAddr + "img/" + data.get(position).getmImagePath())
                .into(imageVIew_kfood_mImage2);

        textVIew_kfood_mName.setText(data.get(position).getmName());
        imageVIew_kfood_mImagePath.setText(data.get(position).getmImagePath());
        textVIew_kfood_mCode.setText(data.get(position).getmCode());
        textVIew_kfood_mAddDay.setText(data.get(position).getmAddDay());
        textVIew_kfood_mCategory.setText(data.get(position).getmCategory());


        return convertView;
    }


}
