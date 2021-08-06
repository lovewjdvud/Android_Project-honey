package com.aoslec.honey_all.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.aoslec.honey_all.Activity.MainActivity;
import com.aoslec.honey_all.Bean.TipBean_y;
import com.aoslec.honey_all.Interface.DeleteOnClickListener;
import com.aoslec.honey_all.Interface.OnItemClick;
import com.aoslec.honey_all.NetworkTask.NetworkTaskTip_y;
import com.aoslec.honey_all.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<TipBean_y> data = null;
    private LayoutInflater inflater = null;

    private OnItemClick mCallback;
    private AdapterView.OnItemClickListener listener; // 이벤트 리스너를 변수로 선언

    private DeleteOnClickListener deleteOnClickListener;

    String tipCode, deleteUrl, dialogName;

    Dialog deleteDialog;
    TextView tv_dialog_delete_content;
    Button btn_dialog_delete, btn_dialog_cancel;

    public TipAdapter(Context mContext, int layout, ArrayList<TipBean_y> data, DeleteOnClickListener deleteOnClickListener) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.deleteOnClickListener = deleteOnClickListener;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tip_title;
        public TextView tv_tip_id;
        public TextView tv_tip_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_tip_title = itemView.findViewById(R.id.tv_tip_title);
            tv_tip_id = itemView.findViewById(R.id.tv_tip_id);
            tv_tip_name = itemView.findViewById(R.id.tv_tip_name);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Context context = v.getContext();
                    String id = data.get(getAdapterPosition()).getcId();
                    tipCode = data.get(getAdapterPosition()).getTipCode();

                    if (id.equals(MainActivity.cId)) {
                        deleteDialog = new Dialog(v.getContext());
                        deleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        deleteDialog.setContentView(R.layout.tip_delete_dialog);

                        WindowManager.LayoutParams lbDown = new WindowManager.LayoutParams();
                        lbDown.copyFrom(deleteDialog.getWindow().getAttributes());
                        lbDown.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        lbDown.height = WindowManager.LayoutParams.WRAP_CONTENT;

                        Window window = deleteDialog.getWindow();
                        window.setAttributes(lbDown);

                        deleteDialog.show();
                        tv_dialog_delete_content = deleteDialog.findViewById(R.id.tv_dialog_delete_content);
                        btn_dialog_cancel = deleteDialog.findViewById(R.id.btn_dialog_cancel);
                        btn_dialog_delete = deleteDialog.findViewById(R.id.btn_dialog_delete);

                        tv_dialog_delete_content.setText("정말 삭제 하실껀가요..? \n다른 분들도 " + dialogName + "님의 꿀팁 궁금해할텐데..?");

                        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteDialog.dismiss();
                            }
                        });

                        btn_dialog_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = tipDeleted();
                                if (result.equals("1")) {
                                    deleteOnClickListener.onDeleteClickListener(true);
                                    Toast.makeText(mContext, "꿀재료는 꿀팁을 사랑하지만, 삭제 완료", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, "아싸 삭제 실패!", Toast.LENGTH_SHORT).show();
                                }
                                deleteDialog.dismiss();
                            }
                        });

                    } else {
                        Snackbar.make(v, "내가 쓴 꿀팁만 삭제 가능해요!", Snackbar.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(v.getContext(), SelectMenuActivity.class);
//                    intent.putExtra("mCode", data.get(getAdapterPosition()).getCode());
//                    intent.putExtra("mName", data.get(getAdapterPosition()).getName());
//
//                    mContext.startActivity(intent);
//                    Toast.makeText(context, data.get(getAdapterPosition()).getCode(), Toast.LENGTH_LONG).show();
//                }
//            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tip_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String id = data.get(position).getcId().substring(0,2);
        String tipId = id + "***";

        dialogName = data.get(position).getcName();
        String name = data.get(position).getcName().substring(0,1);
        String tipName = name + "**";

        Log.v("adapter", tipId);
        Log.v("adapter", tipName);

        holder.tv_tip_id.setText(tipId.toString());
        holder.tv_tip_name.setText(tipName.toString());
        holder.tv_tip_title.setText(data.get(position).getTipContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private String tipDeleted() {
        deleteUrl = "http://" + MainActivity.myIP + ":8080/honey/jsp/tip_delete.jsp?code=" + tipCode;
        String result = null;
        try {
            NetworkTaskTip_y networkTask = new NetworkTaskTip_y(mContext, deleteUrl, "delete");
            Object obj = networkTask.execute().get();
            result = (String)obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("result", result);
        return result;
    }

}
