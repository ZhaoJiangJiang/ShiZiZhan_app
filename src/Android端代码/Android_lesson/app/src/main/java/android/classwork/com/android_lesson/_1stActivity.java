package android.classwork.com.android_lesson;

import android.classwork.com.android_lesson.util.HttpUtil;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 赵江江 on 2018/11/12.
 */

public class _1stActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String WEB_NAV_USER = HttpUtil.IP+":8080/android_lesson_work/bcz_user_get_username.jsp";
    private static final String WEB_GET_INSISTDAY = HttpUtil.IP+":8080/android_lesson_work/bcz_user_get_insistday.jsp";
    private static final String WEB_GET_PLANNUM = HttpUtil.IP+":8080/android_lesson_work/bcz_user_get_plannum.jsp";
    private static final String WEB_SET_PLANNUM = HttpUtil.IP+":8080/android_lesson_work/bcz_user_set_plannum.jsp";
    private static final String WEB_GET_SELECTKIND = HttpUtil.IP+":8080/android_lesson_work/bcz_user_get_selectkind.jsp";

    private DrawerLayout mDrawerLayout;

    private TextView nav_header_username;
    private RequestBody body;

    private LinearLayout search;

    private TextView tv_InsistDay;
    private TextView tv_PlanNum;
    private TextView tv_SelectKind;
    private TextView tv_ChangePlan;
    private TextView tv_toVocbList;
    private TextView tv_toWordTv;

    private Button btn_begin;

    private String Account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1st);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id._1st_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_icon);
        }

        // 接收由登录界面传入的account
        Intent intent = getIntent();
        String nav_account = intent.getStringExtra("account");
        Account = nav_account;
        // 将nav_header中的account和username设置成登录用户的信息
        // 找nav_menu和nav_header中的控件，要一层一层找！
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        TextView nav_header_account = (TextView) nav.getHeaderView(0).findViewById(R.id.nav_account);
        nav_header_account.setText("账号："+nav_account);

        nav_header_username = (TextView) nav.getHeaderView(0).findViewById(R.id.nav_username);
        tv_InsistDay = (TextView) findViewById(R.id._1st_info_insistDay);
        tv_PlanNum = (TextView) findViewById(R.id._1st_info_planNum);
        tv_SelectKind = (TextView) findViewById(R.id._1st_info_selectKind);
        tv_ChangePlan = (TextView) findViewById(R.id._1st_info_changePlan);
        tv_toVocbList = (TextView) findViewById(R.id._1st_info_vocabularyList);
        tv_toWordTv = (TextView) findViewById(R.id._1st_info_toWordTv);
        btn_begin = (Button) findViewById(R.id._1st_info_begin);


        // 显示Nav_header_username
        body = new FormBody.Builder().add("account", nav_account).build();
        SetNavHeaderUsername();

        // 显示坚持天数
        showInsistDay();

        // 显示今日要背的个数
        showPlanNum();

        // 显示背的单词类型
        showSelectKind();

        // NavigationItem点击事件
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_menu_quit:
                        // 提示->是否要退出当前用户
                        AlertDialog.Builder dialog = new AlertDialog.Builder(_1stActivity.this);
                        dialog.setTitle("提示");
                        dialog.setMessage("是否要退出当前用户");
                        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent quitIntent = new Intent(_1stActivity.this, LoginActivity.class);
                                startActivity(quitIntent);
                                finish();
                            }
                        });
                        dialog.setNegativeButton("否", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        dialog.show();
                        break;
                    case R.id.nav_menu_myPlan:
                        Intent toMyPlan = new Intent(_1stActivity.this, MyplanActivity.class);
                        toMyPlan.putExtra("account", Account);
                        startActivityForResult(toMyPlan, 1);
                        break;
                    case R.id.nav_menu_myCollection:    // 我的收藏
//                        Toast.makeText(getApplicationContext(), "我的收藏", Toast.LENGTH_SHORT).show();
                        Intent toMyCollection = new Intent(_1stActivity.this, VocblistActivity.class);
                        toMyCollection.putExtra("account", Account);
                        startActivity(toMyCollection);
                        break;
                    case R.id.nav_menu_advice:  // 意见反馈
//                        Toast.makeText(getApplicationContext(), "意见反馈", Toast.LENGTH_SHORT).show();
                        Intent toFeedBack = new Intent(_1stActivity.this, FeedbackActivity.class);
                        startActivity(toFeedBack);
                        break;
                    case R.id.nav_menu_about_us:    // 关于我们
//                        Toast.makeText(getApplicationContext(), "关于我们", Toast.LENGTH_SHORT).show();
                        Intent toAboutUs = new Intent(_1stActivity.this, AboutourActivity.class);
                        startActivity(toAboutUs);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        // 添加
        search = (LinearLayout) findViewById(R.id._1st_search);
        search.setOnClickListener(this);
        tv_ChangePlan.setOnClickListener(this);
        tv_toVocbList.setOnClickListener(this);
        tv_toWordTv.setOnClickListener(this);
        btn_begin.setOnClickListener(this);
    }

    // 刷新数据, 从另一activity界面返回到该activity界面时, 此方法自动调用
    @Override
    protected void onResume() {
        super.onResume();
        showInsistDay();
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.tool_bar, menu);
//        return  true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id._1st_search:
                // 跳到搜索界面
                Intent toSearch = new Intent(_1stActivity.this, SearchActivity.class);
                startActivity(toSearch);
                break;
            case R.id._1st_info_changePlan:
                Intent toMyPlan = new Intent(_1stActivity.this, MyplanActivity.class);
                toMyPlan.putExtra("account", Account);
                startActivityForResult(toMyPlan, 1);
                break;
            case R.id._1st_info_vocabularyList:
                Intent toList = new Intent(_1stActivity.this, VocblistActivity.class);
                toList.putExtra("account", Account);
                startActivity(toList);
                break;
            case R.id._1st_info_toWordTv:
                Intent toWordTv = new Intent(_1stActivity.this, WordTvActivity.class);
                startActivity(toWordTv);
                break;
            case R.id._1st_info_begin:
                Intent toBeginback = new Intent(_1stActivity.this, BeginbackActivity.class);
                String PlanNum = tv_PlanNum.getText().toString().trim();
                toBeginback.putExtra("data", PlanNum+"#"+Account);
                startActivity(toBeginback);
                break;
            default:
                break;
        }
    }

    /**
     * MyplanAcivity返回数据，刷新planNum和SelectKind等信息
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    showPlanNum();
                    showSelectKind();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置Nav_header_username
     */
    private void SetNavHeaderUsername(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_NAV_USER, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            nav_header_username.setText(response.body().string().trim());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 显示坚持天数
     */
    private void showInsistDay(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_GET_INSISTDAY, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tv_InsistDay.setText(response.body().string().trim());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 显示今日要背的个数
     */
    public void showPlanNum(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_GET_PLANNUM, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tv_PlanNum.setText(response.body().string().trim());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 显示要背的单词类型
     */
    public void showSelectKind(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_GET_SELECTKIND, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseData = response.body().string().trim();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (responseData == null || responseData.equals("null")){
                                tv_SelectKind.setText("任意词汇");
                            }else {
                                tv_SelectKind.setText(responseData);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
}
