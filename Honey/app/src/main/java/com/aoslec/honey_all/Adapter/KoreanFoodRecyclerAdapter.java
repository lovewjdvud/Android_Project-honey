package com.aoslec.honey_all.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.aoslec.honey_all.Activity.MainActivity;
import com.aoslec.honey_all.Activity.SelectMenuActivity;
import com.aoslec.honey_all.Bean.MenuBean;
import com.aoslec.honey_all.Interface.OnItemClick;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

public class KoreanFoodRecyclerAdapter extends RecyclerView.Adapter<KoreanFoodRecyclerAdapter.ViewHolder> {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<MenuBean> data = null;
    private LayoutInflater inflater = null;

    private OnItemClick mCallback;
    private AdapterView.OnItemClickListener listener; // 이벤트 리스너를 변수로 선언

    String mCode;

    public KoreanFoodRecyclerAdapter(Context mContext, int layout, ArrayList<MenuBean> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
//        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public WebView wv_menu_image;
        public TextView tv_menu_name;

        public ViewHolder(View itemView) {
            super(itemView);
            wv_menu_image = itemView.findViewById(R.id.wv_menu_image);
            tv_menu_name = itemView.findViewById(R.id.tv_menu_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(v.getContext(), SelectMenuActivity.class);
                    intent.putExtra("mCode", data.get(getAdapterPosition()).getCode());
                    intent.putExtra("mName", data.get(getAdapterPosition()).getName());

                    mContext.startActivity(intent);
//                    Toast.makeText(context, data.get(getAdapterPosition()).getCode(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.wv_menu_image.loadDataWithBaseURL(null,imagePath(data.get(position).getImage1()), "text/html", "utf-8", null);
        holder.tv_menu_name.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private String imagePath(String imageName) {
        String path = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
                        "<html><head>"+
                        "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"+
                        "<head><body>"+
                        "<img src=\"http://"+ MainActivity.myIP  +":8080/honey/img/";
        path += imageName + "\" alt=\"이미지\" width=\"100%\" height=\"100%\"></body></html>";
        return path;
    }

}

