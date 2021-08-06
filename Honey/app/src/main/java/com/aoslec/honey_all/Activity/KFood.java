package com.aoslec.honey_all.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.aoslec.honey_all.Adapter.BsAdapter;
import com.aoslec.honey_all.Bean.FoodListBean;
import com.aoslec.honey_all.NetworkTask.NetworkTask_m;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KFood#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KFood extends Fragment {

    String urlAddr = null; // 리스트 서버 유알엘
    ArrayList<FoodListBean> members; // 요리 리스트
    BsAdapter adapter; // 요리리스트 어뎁터
    ListView listView; // 요리리스트뷰
    String searchVlaue=""; // 검색값
    String IPaddress = "192.168.55.217"; // 아이피
    EditText search_information;
    LinearLayout  list_item_set;

    //마이페이지
    Button buttonMyPage;
    TextView textViewUesrId;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KFood() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment Contact_information.
     */
    // TODO: Rename and change types and number of parameters
    public static KFood newInstance(String param1, String param2) {
        KFood fragment = new KFood();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    /** onCreateView 실제 실행 구간 **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_k_food, container, false);
//        listView = v.findViewById(R.id.listView_foodList);
        urlAddr = "http://" + IPaddress + ":8080/honny_tip_m/honny_tip_kfood_Select_m.jsp?searchVlaue="+searchVlaue;
        // 선언
//        add_infomation = v.findViewById(R.id.add_information);
//        delete_infomation = v.findViewById(R.id.delete_information);
        search_information = v.findViewById(R.id.editText_search);
        buttonMyPage = v.findViewById(R.id.buttonMyPage);
        textViewUesrId = v.findViewById(R.id.textViewUesrId);

        // 클릭 기능
//        add_infomation.setOnClickListener(onClickListener);
//        delete_infomation.setOnClickListener(onClickListener);
        search_information.setOnClickListener(onClickListener);
        buttonMyPage.setOnClickListener(onClickListener);
        return v;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.editText_search:
                    intent = new Intent(getActivity(), foodsearch.class);
                    intent.putExtra("IPaddress", IPaddress);
                    startActivity(intent);
                    break;

                case R.id.buttonMyPage:
                    textViewUesrId.setText("test1");
                    String userId = textViewUesrId.getText().toString();
                    intent = new Intent(getActivity(), MyPage.class);
                    intent.putExtra("iPaddress", IPaddress);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    //수정되면 또 실행함 꼭 필요
    public void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData() {
        try {
            NetworkTask_m networkTask = new NetworkTask_m(getActivity(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            members = (ArrayList<FoodListBean>) obj;

            adapter = new BsAdapter(getActivity(), R.layout.kfood_list_item_m, members);
            listView.setAdapter(adapter);

//            listView.setOnItemClickListener(onItemClickListener);
//            listView.setOnItemLongClickListener(onItemLongClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}