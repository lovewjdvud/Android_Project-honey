package com.aoslec.honey_all.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.honey_all.Activity.MainActivity;
import com.aoslec.honey_all.Bean.PaymentDetail_s;
import com.aoslec.honey_all.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentDetailRecyclerAdapter_s extends RecyclerView.Adapter<PaymentDetailRecyclerAdapter_s.ViewHolder> {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<PaymentDetail_s> data = null;
    Intent intent = null;
    String urlAddr;
    String result;
    DecimalFormat myFormatter = new DecimalFormat("###,###");

    public PaymentDetailRecyclerAdapter_s(Context mContext, int layout, ArrayList<PaymentDetail_s> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView pd_menuTitle_tv;
        public TextView pd_ingredientTitle_tv;
        public TextView pd_ingredientPrice_tv;
        public TextView pd_ingredientTotalEA_tv;
        public TextView pd_ingredientTotalPrice_tv;
        public ImageView pd_menu_iv;


        public ViewHolder(View itemView) {
            super(itemView);
            pd_menuTitle_tv = itemView.findViewById(R.id.pd_menuTitle_tv_s);
            pd_ingredientTitle_tv = itemView.findViewById(R.id.pd_ingredientTitle_tv_s);
            pd_ingredientPrice_tv = itemView.findViewById(R.id.pd_ingredientPrice_tv_s);
            pd_ingredientTotalEA_tv = itemView.findViewById(R.id.pd_ingredientTotalEA_tv_s);
            pd_ingredientTotalPrice_tv = itemView.findViewById(R.id.pd_ingredientTotalPrice_tv_s);
            pd_menu_iv = itemView.findViewById(R.id.pd_menu_iv_s);
        }
    }

    @NonNull
    @Override
    // 뷰홀더 만드는거
    public PaymentDetailRecyclerAdapter_s.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_detail_card_layout_s, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    //뷰홀더 쓰는거
    @Override
    public void onBindViewHolder(PaymentDetailRecyclerAdapter_s.ViewHolder holder, int position) {
        holder.pd_menuTitle_tv.setText(data.get(position).getmName());
        holder.pd_ingredientTitle_tv.setText(data.get(position).getiName() + data.get(position).getiCapacity()
                + data.get(position).getiUnit());
        holder.pd_ingredientPrice_tv.setText(myFormatter.format(Integer.parseInt(data.get(position).getiPrice())) + "원");
        holder.pd_ingredientTotalEA_tv.setText(data.get(position).getBuyEA() + "개");
        holder.pd_ingredientTotalPrice_tv.setText(myFormatter.format(Integer.parseInt(data.get(position).getiPrice()) * data.get(position).getBuyEA()) + "원");
        Glide.with(holder.pd_menu_iv)
                .load(MainActivity.hostRootAddr + "img/" +  data.get(position).getmImagePath())
                .into(holder.pd_menu_iv);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
