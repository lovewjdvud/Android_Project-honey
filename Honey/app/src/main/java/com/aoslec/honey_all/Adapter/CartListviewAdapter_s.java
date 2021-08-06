package com.aoslec.honey_all.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoslec.honey_all.Activity.MainActivity;
import com.aoslec.honey_all.Bean.Cart_s;
import com.aoslec.honey_all.Interface.CartClickListener_s;
import com.aoslec.honey_all.NetworkTask.CartNetworkTask_s;
import com.aoslec.honey_all.R;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartListviewAdapter_s extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Cart_s> data = null;
    private LayoutInflater inflater = null;
    Intent intent = null;
    String urlAddr;
    int EA;
    DecimalFormat myFormatter = new DecimalFormat("###,###");
    private CartClickListener_s cartClickListener;

    public CartListviewAdapter_s(Context mContext, int layout, ArrayList<Cart_s> data, CartClickListener_s cartClickListener) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cartClickListener = cartClickListener;
    }

    @Override
    public int getCount() {
        return data.size();//카운트까지 돌아
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getCartCode();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(this.layout, parent, false);

        ImageView image = convertView.findViewById(R.id.cart_menu_iv_s);
        TextView tv_mName = convertView.findViewById(R.id.cart_menuTitle_tv_s);
        TextView tv_iFullName = convertView.findViewById(R.id.cart_ingredientTitle_tv_s);
        TextView tv_iPrice = convertView.findViewById(R.id.cart_ingredientPrice_tv_s);
        TextView tv_iTotalPrice = convertView.findViewById(R.id.cart_ingredientTotalPrice_tv_s);
        EditText et_cartEA = convertView.findViewById(R.id.cart_ingredientEA_et_s);
        ImageButton cart_ingredientMinus_btn = convertView.findViewById(R.id.cart_ingredientMinus_btn_s);
        ImageButton cart_ingredientPlus_btn = convertView.findViewById(R.id.cart_ingredientPlus_btn_s);

        Glide.with(convertView)
                .load(MainActivity.hostRootAddr + "img/" +  data.get(position).getmImagePath())
                .into(image);
        tv_mName.setText(data.get(position).getmName());
        tv_iFullName.setText(data.get(position).getiName() + data.get(position).getiCapacity() + data.get(position).getiUnit());
        tv_iPrice.setText(myFormatter.format(data.get(position).getiPrice()) + "원");
        tv_iTotalPrice.setText(myFormatter.format(data.get(position).getiPrice() * data.get(position).getCartEA()) + "원");
        et_cartEA.setText(Integer.toString(data.get(position).getCartEA()));

        cart_ingredientMinus_btn.setTag(data.get(position).getCartCode());
        cart_ingredientPlus_btn.setTag(data.get(position).getCartCode());

        cart_ingredientMinus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EA = Integer.parseInt(String.valueOf(et_cartEA.getText()));
                if (EA == 1){
                    Snackbar.make(v, "최소 구매 갯수입니다.", Snackbar.LENGTH_SHORT).show();
                }else {
                    EA -= 1;
                    et_cartEA.setText(String.valueOf(EA));
                    connectEAData((Integer) cart_ingredientMinus_btn.getTag(), EA);
                    cartClickListener.onCartClickAction(true);
                }

            }

        });

        cart_ingredientPlus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EA = Integer.parseInt(String.valueOf(et_cartEA.getText()));
                if (EA == 10){
                    Snackbar.make(v, "최대 구매 갯수입니다.", Snackbar.LENGTH_SHORT).show();
                }else {
                    EA += 1;
                    et_cartEA.setText(String.valueOf(EA));
                    connectEAData((Integer) cart_ingredientPlus_btn.getTag(), EA);
                    cartClickListener.onCartClickAction(true);
                }
            }
        });
        return convertView;
    }

    private String connectEAData(int position, int EA){
        urlAddr = MainActivity.hostRootAddr + "Cart_ingredientEA_Update_Return.jsp?cartCode=" + position + "&cartEA=" + EA;
        String result = null;
        try {
            CartNetworkTask_s networkTask = new CartNetworkTask_s(mContext, urlAddr, "ea");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
        //잘끝났으면 1 아니면 에러
    }
}
