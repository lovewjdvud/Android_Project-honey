package com.aoslec.honey_all.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.aoslec.honey_all.Fragment.Id_find_fragment_j;
import com.aoslec.honey_all.Fragment.Pw_find_frgment_j;
import com.aoslec.honey_all.Fragment.Signup_Agree_fragment_j;
import com.aoslec.honey_all.NetworkTask.NetworkTask_j;
import com.aoslec.honey_all.R;
import com.aoslec.honey_all.Bean.Signup_Bean;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;

public class Login_s extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    /// 구글 로그인을 위한 객채 시작  //



    public static String client_id;  // 로그인시 값 변환
    private SignInButton btn_google_j; //구글 로그인 버튼
    private FirebaseAuth auth_j; // 파이어 베이스 인증 객체
    private GoogleApiClient googleApiClient_j; // 구글 API 클라이언트 객체
    private  static final int REO_SIGN_GOOGLE = 100; //구글 로그인 결과 코드

    // 6/26 편집 한 부분    // 자동 로그인
    ListView list_login_id_j;    // 자동로그인  정보 넣기

    ArrayList<String> items_auto_id_j;        // id데이터 들어가는 곳
    ArrayAdapter<String> adapter_auto_id_j;

    ListView list_login_pw_j;

    ArrayList<String> items_auto_pw_j;        // pw데이터 들어가는 곳
    ArrayAdapter<String> adapter_auto_pw_j;


    public static  String auto_loginId_j;
    public static String auto_loginPw_j;   // 자동 로그인 정보 저장
    // 6/26 편집 한 부분    // 자동 로그인


    LinearLayout linearLayout;


//
//    Intent intent = getIntent();
//    String macIP = intent.getStringExtra("macIP");

    String macIP = MainActivity.myIP;
    String id_confirm_urlAddr = "http://" + macIP + ":8080/honey/honey_login_confirm_j.jsp?";  //아이디 중복체크

    ///////////////////////// 카카오 로그인을 위한 객채  ////////////////////////////
    private ISessionCallback mSeesingCallback_j;
    LinearLayout layout_j;


    ///////////////////////// 아이디 및 비밀번호 찾기  ////////////////////////////

    TextView id_find_j;
    TextView pw_find_j;
    TextView login_join_j;
    ///////////////////////// 로그인 ////////////////////////////
    ArrayList<Signup_Bean> idpw_confirm_result;
    EditText ed_login_id_j, ed_login_pw_j ;
    Button btn_login_j;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ///////////////////////// ////// 자동 로그인   //////////////////////////


        // 아이디 부분
        items_auto_id_j = new ArrayList<String>();
        adapter_auto_id_j = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,items_auto_id_j);
        list_login_id_j= findViewById(R.id.list_login_id_j);


        // 비밀번호 부분
        items_auto_pw_j= new ArrayList<String>();
        adapter_auto_pw_j = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,items_auto_pw_j);
        list_login_pw_j= findViewById(R.id.list_login_pw_j);


        SharedPreferences auto = getSharedPreferences("honey", MainActivity.MODE_PRIVATE);

        //   자동로그인  6/26 수정부분
        Login_s.auto_loginId_j = auto.getString("auto_Id",null);
        Login_s.auto_loginPw_j = auto.getString("auto_Pw",null);

        // 아이디
        list_login_id_j.setVisibility(View.INVISIBLE);
        // 비번

        list_login_pw_j.setVisibility(View.INVISIBLE);

        if(Login_s.auto_loginId_j !=null && Login_s.auto_loginPw_j != null){
            list_login_id_j.setAdapter(adapter_auto_id_j);
            items_auto_id_j.add(auto_loginId_j);
            items_auto_pw_j.add(auto_loginPw_j);
            list_login_pw_j.setAdapter(adapter_auto_pw_j);
        }
        //   자동로그인  6/26 수정부분




        ed_login_id_j=findViewById(R.id.login_edit_id_j);
        ed_login_pw_j =findViewById(R.id.login_edit_pw_j);

        //클릭시 edittext에 값 넣어주기
        list_login_id_j.setOnItemClickListener(mItemClickListener_id);
        list_login_pw_j.setOnItemClickListener(mItemClickListener_pw);

        // 클릭시 리스트뷰 소환
        ed_login_id_j.setOnTouchListener(ed_click_id);
        ed_login_pw_j.setOnTouchListener(ed_click_pw);


        ///////////////////////// ////// 자동 로그인   //////////////////////////





        ///////////////////////// 카카오  ////////////////////////////


        layout_j = findViewById(R.id.kaka);


        LoginButton kakao_btn ;

        mSeesingCallback_j = new ISessionCallback() {
            @Override
            public void onSessionOpened() {

                //로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback()
                {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        //로그인 실패시
                        Toast.makeText(Login_s.this, "로그인 시도 중 오류가 발생했습니다다", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {

                        //세션이 닫힘
                        Toast.makeText(Login_s.this, "세션이 닫혔습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    //로그인 성공시 result로 정보를 가져옴
                    public void onSuccess(MeV2Response result ) {

                        //로그인 성공
                        Intent intent = new Intent(Login_s.this, ResultActivity.class);
                        intent.putExtra("Name", result.getKakaoAccount().getProfile().getNickname());
                        intent.putExtra("profileImg",result.getKakaoAccount().getProfile().getProfileImageUrl());
                        intent.putExtra("cId", result.getKakaoAccount().getEmail());

                        startActivity(intent);
                        Toast.makeText(Login_s.this, " 환영합니다", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Toast.makeText(Login_s.this, "세션이 닫혔습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();

            }
        };



        Session.getCurrentSession().addCallback(mSeesingCallback_j);
        Session.getCurrentSession().checkAndImplicitOpen();

        ///////////////////////// 카카오 로그인을 위한 객채  ////////////////////////////




        ///////////////////////// 아이디 및 비밀번호 찾기 위한 리스너 시잗   ////////////////////////////

        id_find_j = findViewById(R.id.login_id_find);
        pw_find_j = findViewById(R.id.login_pw_find);

        id_find_j.setOnTouchListener(click);
        pw_find_j.setOnTouchListener(click);
        ///////////////////////// 아이디 및 비밀번호 찾기 위한  끝  ////////////////////////////



        ///////////////////////// 회원가입  텍스트뷰 불러오기  ////////////////////////////
        login_join_j=findViewById(R.id.login_join);
        login_join_j.setOnTouchListener(joinclick);



        ///////////////////////// 카카오 로그인을 위한 객채  ////////////////////////////


        ////////////////////////////////////// ////////////// 구글 로그인 연동 과정  시작    //////////////////////////////////////////////////////////

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient_j = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        auth_j = FirebaseAuth.getInstance();  //파이어베이스 인증 객체 초기화

        btn_google_j =findViewById(R.id.login_btn_google);
        btn_google_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient_j);
                startActivityForResult(intent, REO_SIGN_GOOGLE);
            }
        });



        /////////////////        login 부분     ///////////////
        btn_login_j = findViewById(R.id.login_btn_justlogin);
        btn_login_j.setOnClickListener(login_click);


















    }///////////       ///////        ;ONCREATE           ///////////        ONCREATE        ///////        ONCREATE




    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) { //구글 로그인 인증을 요청했을 때 결과 값을 되돌려 받는 곳
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REO_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {  //인증결과가 성공적이면
                GoogleSignInAccount account = result.getSignInAccount();//account 라는 데이터는 구글 로그인 정보를 담고 있습니다(닉네임 , 프로필 ,사진 주소등등)
                resultLogin(account); //로그인 결과 값 출력 수행하라는 메소드

            }
        }
    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth_j.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        String google = "google";
                        if (task.isSuccessful()){  //로그인 성공하면
                            Toast.makeText(Login_s.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                            intent.putExtra("division",google);
                            intent.putExtra("cId" , account.getEmail());
                            intent.putExtra("photoUrl", String.valueOf(account.getPhotoUrl()));  //String.valueof 특정 자료형을 string 형태로 변환
                            startActivity(intent);
                        }else{   //로그인 실패하면
                            Toast.makeText(Login_s.this, "로그인 실패", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    ////////////////////////////////////// ////////////// 구글 로그인 연동 과정 마지막 부분      //////////////////////////////////////////////////////////




    ////////////////////////////////////// //////////////    아이디 및 비밀번호를 찾기 위한 리스너 시작  //////////////////////////////////////////////////////////

    View.OnTouchListener click = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()){
                case R.id.login_id_find :
                    Toast.makeText(Login_s.this, "id를 찾으시겠습니까?", Toast.LENGTH_SHORT).show();

                    linearLayout= findViewById(R.id.login_linearlayout);

                    linearLayout.setVisibility(View.INVISIBLE);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Id_find_fragment_j id_find_fragment_j = new Id_find_fragment_j();
                    transaction.replace(R.id.ly_login_framelayout, id_find_fragment_j);
                    transaction.commit();



                    break;
                case R.id.login_pw_find :

                    linearLayout= findViewById(R.id.login_linearlayout);
                    linearLayout.setVisibility(View.INVISIBLE);
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    Pw_find_frgment_j pw_find_fragment_j = new Pw_find_frgment_j();
                    transaction2.replace(R.id.ly_login_framelayout, pw_find_fragment_j);
                    transaction2.commit();

                    break;
            }
            return true;
        }
    };

    ////////////////////////////////////// //////////////    아이디 및 비밀번호를 찾기 위한 리스너 끝  //////////////////////////////////////////////////////////


    ////////////////////////////////////// //////////////    회원가입을 위한 클릭리스너 시작 //////////////////////////////////////////////////////////
    View.OnTouchListener joinclick = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            linearLayout= findViewById(R.id.login_linearlayout);
//            linearLayout.setVisibility(View.INVISIBLE);
//
//            FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
//            Signup_Agree_fragment_j signup_agree_fragment_j = new Signup_Agree_fragment_j();
//
//          transaction3.replace(R.id.ly_login_framelayout,signup_agree_fragment_j );
//          transaction3.commit();

            Intent intent = new Intent(Login_s.this, SignUp_j.class);
            startActivity(intent);




            return true;
        }
    };

    ////////////////////////////////////// //////////////    회원가입을 위한 클릭리스너 끝  //////////////////////////////////////////////////////////





    ////////////////////////////////////// //////////////    로그인 버튼  //////////////////////////////////////////////////////////



    View.OnClickListener login_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String login_id =  ed_login_id_j.getText().toString();
            String login_pw = ed_login_pw_j.getText().toString();

            SharedPreferences auto = getSharedPreferences("honey", MainActivity.MODE_PRIVATE);
            auto_loginId_j = auto.getString("auto_Id",null);
            auto_loginPw_j = auto.getString("auto_Pw",null);


            int result =  connectGetData(login_id,login_pw); // 로그인 성공
            Log.v("message", "여기는= connectGetData의 결과 값이야 result = " + result);



            if (result == 1){

                ////////////////////////         자동 로그인 부분            ////////

                if(auto_loginId_j == null || auto_loginPw_j ==null){
                    SharedPreferences autos = getSharedPreferences("honey", MainActivity.MODE_PRIVATE);
                    SharedPreferences.Editor autoLogin = auto.edit();
                    // 저장
                    autoLogin.putString("auto_Id", ed_login_id_j.getText().toString());
                    autoLogin.putString("auto_Pw", ed_login_pw_j.getText().toString());
                    autoLogin.commit();


                    String auto_login_id_j = auto.getString("auto_Id","실패");
                    String auto_login_pw_j = auto.getString("auto_Pw","실패");

                    Toast.makeText(Login_s.this, auto_login_id_j, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Login_s.this, auto_login_pw_j, Toast.LENGTH_SHORT).show();







                } else if(auto_loginId_j != null || auto_loginPw_j ==null){

                    String auto_login_data_j = auto.getString("auto_Id","실패");
                    String ed_login_pw_j = auto.getString("auto_Pw","실패");

                    Toast.makeText(Login_s.this, auto_login_data_j + "비번은 = "+ed_login_pw_j, Toast.LENGTH_SHORT).show();


                }
                ////////////////////////         자동 로그인 부분            ////////


                Toast.makeText(Login_s.this, "로그인 성공! id = " +MainActivity.cId, Toast.LENGTH_SHORT).show();
                MainActivity.cId = login_id;


            }else if (result==0){

                Toast.makeText(Login_s.this, "로그인에 실패 했습니다 아이디와 비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
            }


            id_confirm_urlAddr = "http://" + MainActivity.myIP + ":8080/honey/honey_login_confirm_j.jsp?";
        }


    };



    private int connectGetData(String client_write_id, String client_write_pw) {
        id_confirm_urlAddr = id_confirm_urlAddr + "cId=" + client_write_id + "&cPw=" + client_write_pw;

        String id = "";
        String pw = "";
        int result = 0;



        try {
            NetworkTask_j networkTask = new NetworkTask_j(Login_s.this, id_confirm_urlAddr, "login");
            Object obj = networkTask.execute().get();

            idpw_confirm_result = (ArrayList<Signup_Bean>) obj;

            id = idpw_confirm_result.get(0).getId();
            pw = idpw_confirm_result.get(0).getPw();

            Log.v("message", "여기는 로그인 값이 돌아오는 곳 id=" + id + "/  pw=" + pw);

            if (id.equals(client_write_id) && pw.equals(client_write_pw)) {
                Intent intent= new Intent(Login_s.this, ResultActivity.class);   // 로그인 성공시 저기로 보낸다
                intent.putExtra("cId", id);
                MainActivity.cId = id;
                startActivity(intent);
                result = 1;

            } else {
                Log.v("message", "여기는 로그인 값이 돌아오는 곳 id=" + id + "pw=" + pw);
                result = 0;
            }
            Log.v("message", "여기는 아이디 중복체크하는  urlAddr 확인하는거=" + id);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

        //////////////////////    로그인 끝  ////////////////////////





    }//////////////////////     자동  로그인 id, pw   ////////////////////////

    AdapterView.OnItemClickListener mItemClickListener_id = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String auto_login_id_j = items_auto_id_j.get(position);;
            ed_login_id_j.setText(auto_login_id_j);
            list_login_id_j.setVisibility(View.INVISIBLE);

        }
    };
    AdapterView.OnItemClickListener mItemClickListener_pw = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String auto_login_pw_j = items_auto_pw_j.get(position);;
            ed_login_pw_j.setText(auto_login_pw_j);
            list_login_pw_j.setVisibility(View.INVISIBLE);

        }
    };
    View.OnTouchListener ed_click_id = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {



            if(Login_s.auto_loginId_j !=null){
                list_login_id_j.setVisibility(View.VISIBLE);
            }


            return false;
        }
    };
    View.OnTouchListener ed_click_pw = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(Login_s.auto_loginPw_j !=null){
                list_login_pw_j.setVisibility(View.VISIBLE);
            }
            return false;
        }
    };


} //MAIN