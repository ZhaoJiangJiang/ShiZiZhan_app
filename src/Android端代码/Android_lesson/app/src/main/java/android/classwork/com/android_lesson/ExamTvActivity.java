package android.classwork.com.android_lesson;

import android.classwork.com.android_lesson.util.vocb_list.OnItemClickListener;
import android.classwork.com.android_lesson.wordradio.adapter.ExamTvAdapter;
import android.classwork.com.android_lesson.wordradio.entity.ExamTv;
import android.classwork.com.android_lesson.wordradio.until.DoMediaPlayer;
import android.classwork.com.android_lesson.wordradio.until.HttpUtil;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by 赵江江 on 2018/12/18.
 * 考试音频Activity
 */

public class ExamTvActivity extends AppCompatActivity{
    /** eclipse项目地址 */
    private static final String ADDRESS = android.classwork.com.android_lesson.util.HttpUtil.IP+
            ":8080/android_lesson_work/";
    /** 服务器上获取的json字符串 */
    private String jsonData;

    private RecyclerView examTvRecyclerView;
    private ExamTvAdapter adapter;
    /** 考试音频列表 */
    private List<ExamTv> examTvList;
    /** MediaPlay操作类 */
    private DoMediaPlayer doMediaPlayer;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doMediaPlayer.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_tv);
        // 隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView examLine = (TextView)findViewById(R.id.exam_line);
        examLine.setBackgroundColor(Color.parseColor("#C2B9A8"));
        //回到单词电台
        Button goWordTvBtn = (Button)findViewById(R.id.wordTv_list);
        goWordTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExamTvActivity.this, WordTvActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        //RecyclerView初始化
        examTvList = new ArrayList<>();
        initExams("CET4真题");
        initRecyclerView();

        //点击播放试题类型
        clickExamTitle();
    }
    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        examTvRecyclerView = (RecyclerView)findViewById(R.id.exam_tv_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ExamTvActivity.this);
        examTvRecyclerView.setLayoutManager(layoutManager);
        adapter = new ExamTvAdapter(examTvList);
        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemTvClick(View view) {
                String fileName = (String)view.getTag();
                if(fileName != null && fileName.length() > 0 && !("null".equals(fileName))){
                    String path = ADDRESS + "voice/"
                            + fileName + ".mp3";
                    doMediaPlayer.startMediaPlayer(path);
                }
            }

            @Override
            public void onItemBtnClick(View view) {

            }
        });
        examTvRecyclerView.setAdapter(adapter);
    }

    /**
     * 点击考试类型获取相应列表
     */
    private void clickExamTitle(){
        examTvList.clear();
        doMediaPlayer = new DoMediaPlayer();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String examType = tab.getText().toString();
                initExams(examType);     //初始化数据
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    /**
     * 根据传入的种类初始化List
     * @param examType
     */
    private void initExams(final String examType){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody requestBody = new FormBody.Builder()
                        .add("exam_type", examType).build();
                jsonData = HttpUtil.sendOkHttpPostRequest(ADDRESS + "getExamRadio.jsp", requestBody);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseExamJson(jsonData);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    /**
     * 解析json
     * @param data
     */
    private void parseExamJson(String data){
        try {
            examTvList.clear();
            //1.将Json数据传入Json数组中
            JSONArray jsonArray = new JSONArray(data);
            //2.遍历Json数组
            for(int i = 0; i < jsonArray.length(); i++){
                //取出每一个Json对象
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //获取相应数据
                String title = jsonObject.getString("title");
                String fileName = jsonObject.getString("fileName");
                //获取对象添加到集合中
                ExamTv examTv = new ExamTv(title, fileName);
                examTvList.add(examTv);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
