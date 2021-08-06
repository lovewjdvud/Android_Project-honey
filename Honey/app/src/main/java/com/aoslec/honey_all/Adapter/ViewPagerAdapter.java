package com.aoslec.honey_all.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.aoslec.honey_all.R;


public class ViewPagerAdapter extends PagerAdapter {

    private Context context = null;
    private Integer[] imagesArray;
    private String[] namesArray;

    public ViewPagerAdapter(Context context, Integer[] imagesArray, String[] namesArray) {
        this.context = context;
        this.imagesArray = imagesArray;
        this.namesArray = namesArray;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = null;

        if(context != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.page, container, false);

            ImageView imageView = view.findViewById(R.id.title);
            imageView.setImageResource(imagesArray[position]);
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    @Override
    public int getCount() {
        return imagesArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, Object object) {
        return view == object;
    }
}
