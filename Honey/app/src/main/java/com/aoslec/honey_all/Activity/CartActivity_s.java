package com.aoslec.honey_all.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aoslec.honey_all.Adapter.CartListviewAdapter_s;
import com.aoslec.honey_all.Bean.Cart_s;
import com.aoslec.honey_all.Interface.CartClickListener_s;
import com.aoslec.honey_all.NetworkTask.CartNetworkTask_s;
import com.aoslec.honey_all.R;
import com.aoslec.honey_all.Swife.SwipeDismissListViewTouchListener_s;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity_s extends AppCompatActivity implements CartClickListener_s {

    String deleteResult, allDeleteResult;
    String urlAddr = null;
    ArrayList<Cart_s> cartS;
    CartListviewAdapter_s adapter;
    ListView listView;
    LinearLayout trueLinearLayout, falseLinearLayout;
    TextView cart_priceResult_tv, cart_delivery_tv, cart_deliveryTip_tv;
    ImageButton cart_deliveryTip_ib;
    Button cart_allDel_btn, cart_deliveryOrder_btn, cart_goBack_btn;
    DecimalFormat myFormatter = new DecimalFormat("###,###");
    ProgressBar cart_priceResult_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_s);

        cart_priceResult_pb = findViewById(R.id.cart_priceResult_pb_s);

        listView = findViewById(R.id.cart_cart_lv_s);
        falseLinearLayout = findViewById(R.id.cart_contain_layout_s);
        trueLinearLayout = findViewById(R.id.cart_notContain_layout_s);
        cart_priceResult_tv = findViewById(R.id.cart_priceResult_tv_s);
        cart_delivery_tv = findViewById(R.id.cart_delivery_tv_s);
        cart_deliveryTip_tv = findViewById(R.id.cart_deliveryTip_tv_s);

        cart_deliveryTip_ib = findViewById(R.id.cart_deliveryTip_ib_s);
        cart_allDel_btn = findViewById(R.id.cart_allDel_btn_s);
        cart_deliveryOrder_btn = findViewById(R.id.cart_deliveryOrder_btn_s);
        cart_goBack_btn = findViewById(R.id.cart_goBack_btn_s);

        SwipeDismissListViewTouchListener_s touchListener =
                new SwipeDismissListViewTouchListener_s(listView,
                        new SwipeDismissListViewTouchListener_s.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    deleteResult = connectDeleteData(cartS.get(position).getCartCode());
                                    cartS.remove(cartS.get(position));
                                    if(deleteResult.equals("1")){
                                        Snackbar.make(listView, "장바구니에서 삭제했습니다.", Snackbar.LENGTH_SHORT).show();
                                    }else {
                                        Snackbar.make(listView, "장바구니에서 삭제 실패했습니다.", Snackbar.LENGTH_SHORT).show();
                                    }
                                    connectGetData();
                                }
                                adapter.notifyDataSetChanged();
                            }

                        });

        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());

        cart_deliveryTip_ib.setOnClickListener(onClickListener);
        cart_allDel_btn.setOnClickListener(onClickListener);
        cart_deliveryOrder_btn.setOnClickListener(onClickListener);
        cart_goBack_btn.setOnClickListener(onClickListener);


    }

    //수정되면 또 실행함 꼭 필요
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData(){

        urlAddr = MainActivity.hostRootAddr + "Cart_All_List.jsp?" + "cId=" + MainActivity.cId;

        try{
            CartNetworkTask_s networkTask = new CartNetworkTask_s(CartActivity_s.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            cartS = (ArrayList<Cart_s>) obj;

            if (cartS.isEmpty() == true){
                trueLinearLayout.setVisibility(View.VISIBLE);
                falseLinearLayout.setVisibility(View.INVISIBLE);
            }else {
                falseLinearLayout.setVisibility(View.INVISIBLE);
                falseLinearLayout.setVisibility(View.VISIBLE);
                adapter = new CartListviewAdapter_s(CartActivity_s.this, R.layout.cart_card_layout_s, cartS, this);
                listView.setAdapter(adapter);

                cart_priceResult_tv.setText(myFormatter.format(cartTotalPrice()) + "원");
                cart_priceResult_pb.setProgress(cartTotalPrice());

                if (cartTotalPrice()<10000){
                    cart_delivery_tv.setText(myFormatter.format(10000-cartTotalPrice())+"원 더 담아주세용");
                    cart_delivery_tv.setTextColor(0xFFFF0000);
                    cart_deliveryOrder_btn.setEnabled(false);
                    cart_deliveryOrder_btn.setText("최소 주문 금액을 채워주세요");
                    cart_deliveryTip_tv.setText("3,000원");
                }else if(cartTotalPrice()>=10000 && cartTotalPrice()<30000){
                    cart_delivery_tv.setText(myFormatter.format(30000-cartTotalPrice())+"원 더 담으면 무료배송");
                    cart_delivery_tv.setTextColor(0xFF0000FF);
                    cart_deliveryOrder_btn.setEnabled(true);
                    cart_deliveryOrder_btn.setText(cartTotalEA() + "개 " + myFormatter.format(cartTotalPrice()+3000) + "원 주문하러 가기");
                    cart_deliveryTip_tv.setText("3,000원");
                }else {
                    cart_delivery_tv.setText("무료배송");
                    cart_delivery_tv.setTextColor(0xFF000000);
                    cart_deliveryOrder_btn.setEnabled(true);
                    cart_deliveryOrder_btn.setText(cartTotalEA() + "개 " + myFormatter.format(cartTotalPrice()) + "원 주문하러 가기");
                    cart_deliveryTip_tv.setText("무료배송");
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // total price 구하기
    private int cartTotalPrice(){
        int totalPrice = 0;

        for(int i=0; i<cartS.size(); i++){
            totalPrice += cartS.get(i).getiPrice()*cartS.get(i).getCartEA();
        }
        return totalPrice;
    }

    // total price 구하기
    private int cartTotalEA(){
        int totalEA = 0;
        for(int i=0; i<cartS.size(); i++){
            totalEA += cartS.get(i).getCartEA();
        }
        return totalEA;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cart_deliveryTip_ib_s:
                    new AlertDialog.Builder(v.getContext())
                            .setMessage("배달팁 안내!\n\n주문금액                                                  "+
                                    "배달팁\n30,000원~                                            무료배송\n" +
                                    "10,000원~30,000원                             3,000원\n" +
                                    "*최소주문금액은 10,000원입니다.")
                            .setCancelable(true)
                            .show();
                    break;
                case R.id.cart_allDel_btn_s:
                    new AlertDialog.Builder(v.getContext())
                            .setMessage("장바구니를 비우시겠습니까?")
                            .setCancelable(false)
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    allDeleteResult = connectAllDeleteData();
                                    if(!allDeleteResult.equals(null)){
                                        Snackbar.make(v, "장바구니를 비웠습니다.", Snackbar.LENGTH_SHORT).show();
                                    }else{
                                        Snackbar.make(v, "장바구니를 비우지못했습니다.", Snackbar.LENGTH_SHORT).show();
                                    }
                                    connectGetData();
                                }
                            })
                            .show();
                    break;
                case R.id.cart_deliveryOrder_btn_s:
                    Intent intent = new Intent(CartActivity_s.this, BuyActivity_s.class);
                    intent.putExtra("TotalPrice", cartTotalPrice());
                    startActivity(intent);
                    break;

                case R.id.cart_goBack_btn_s:
                    finish();
                    break;
            }
        }
    };

    private String connectDeleteData(int cartCode){
        urlAddr = MainActivity.hostRootAddr + "Cart_Deletedate_Update_Return.jsp?" + "cartCode=" + cartCode;
        String result = null;
        try {
            CartNetworkTask_s networkTask = new CartNetworkTask_s(CartActivity_s.this, urlAddr, "delete");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
        //잘끝났으면 1 아니면 에러
    }
    private String connectAllDeleteData(){
        urlAddr = MainActivity.hostRootAddr + "Cart_AllDeletedate_Update_Return.jsp?Client_cId=" + MainActivity.cId;
        String result = null;
        try {
            CartNetworkTask_s networkTask = new CartNetworkTask_s(CartActivity_s.this, urlAddr, "delete");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
        //잘끝났으면 1 아니면 에러
    }

    @Override
    public void onCartClickAction(boolean isSelected) {
        if(isSelected){
            connectGetData();
        }
    }

}