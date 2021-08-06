package com.aoslec.honey_all.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.aoslec.honey_all.Bean.IngredientBean;
import com.aoslec.honey_all.Interface.SwifeListener;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

public class MenuSelectRecyclerAdapter_y extends RecyclerView.Adapter<MenuSelectRecyclerAdapter_y.ViewHolder> implements SwifeListener {
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<IngredientBean> ingredient = null;
    private LayoutInflater inflater = null;
    public static ArrayList<String> selectCode = new ArrayList<>();
    public static ArrayList<String> selectName = new ArrayList<>();

    int checkCount = 0;
    boolean isCheck = true;

    public MenuSelectRecyclerAdapter_y() {
    }

    public MenuSelectRecyclerAdapter_y(Context mContext, int layout, ArrayList<IngredientBean> ingredient) {
        this.mContext = mContext;
        this.layout = layout;
        this.ingredient = ingredient;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_menu_select_ingredient;
        public TextView tv_menu_select_ingredient_price;
        public CheckBox chk_select;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_menu_select_ingredient = itemView.findViewById(R.id.tv_menu_select_ingredient);
            tv_menu_select_ingredient_price = itemView.findViewById(R.id.tv_menu_select_ingredient_price);
            chk_select = itemView.findViewById(R.id.chk_select);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_select_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.wv_menu_image.loadDataWithBaseURL(null,imagePath(data.get(position).getImage1()), "text/html", "utf-8", null);
//        holder.tv_menu_name.setText(data.get(position).getName());
        checkCount = holder.getAdapterPosition();
        String name = ingredient.get(position).getiName();
        String Capacity = ingredient.get(position).getiCapacity();
        String Unit = ingredient.get(position).getiUnit();
        String price = ingredient.get(position).getiPrice();

        String fullName = name + " " + Capacity + " " + Unit;
        String fullPrice = price + "원";

        holder.tv_menu_select_ingredient.setText(fullName);
        holder.tv_menu_select_ingredient_price.setText(fullPrice);

        holder.chk_select.setChecked(ingredient.get(position).isSelected());
//        Log.v("check", "holder.chk_select.setChecked(ingredient.get(position).isSelected());");
        holder.chk_select.setTag(ingredient.get(position));
//        Log.v("check", "holder.chk_select.setTag(ingredient.get(position));");
        holder.chk_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    String code = ingredient.get(position).getiCode();
                    String name = ingredient.get(position).getiName();

                    Log.v("check", name);
                    selectCode.add(code);
                    selectName.add(name);
                } else {
                    String code = ingredient.get(position).getiCode();
                    String name = ingredient.get(position).getiName();
                    selectCode.remove(code);
                    selectName.remove(name);
                }
            }
        });
//        holder.chk_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkCount = holder.getAdapterPosition();
//                Log.v("checkCount", Integer.toString(checkCount));
//
////                holder.getCheckedItemPositions();
//                String test = Integer.toString(checkCount);
//                Log.v("check", test);
//
//                CheckBox checkBox = (CheckBox) v;
//                IngredientBean ingredientBean = (IngredientBean) checkBox.getTag();
//                ingredientBean.setSelected(checkBox.isSelected());
//                ingredient.get(position).setSelected(checkBox.isSelected());
//
//
//
//                String code = ingredient.get(position).getiCode();
//                String name = ingredient.get(position).getiName();
//
//                Log.v("check", name);
//                selectCode.add(code);
//                selectName.add(name);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }


    @Override
    public boolean onItemMove(int from_position, int to_position) {
        return false;
    }

    @Override
    public void onItemSwipe(int position) {
//        int iCode = Integer.parseInt(ingredient.get(position).getiCode());
//        selectCode.add(iCode);
//        notifyItemChanged(position);
//        notifyItemRemoved(position);
    }

    @Override
    public void onLeftClick(int position, RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {

    }

}