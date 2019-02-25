package android.classwork.com.android_lesson;

import android.classwork.com.android_lesson.util.vocb_list.OnItemClickListener;
import android.classwork.com.android_lesson.wordradio.adapter.WordTvAdapter;
import android.classwork.com.android_lesson.wordradio.entity.WordTv;
import android.classwork.com.android_lesson.wordradio.until.DoMediaPlayer;
import android.classwork.com.android_lesson.wordradio.until.HttpUtil;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 赵江江 on 2018/12/18.
 */

public class WordTvActivity extends AppCompatActivity{
    private static final String ADDRESS = android.classwork.com.android_lesson.util.HttpUtil.IP+":8080/android_lesson_work/";
    private DoMediaPlayer doMediaPlayer;
    private List<WordTv> wordTvList;
    private String jsonData;

    private TextView showTV;
    private RecyclerView wordTvRecyclerView;
    private WordTvAdapter adapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doMediaPlayer.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_tv);
        // 隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView wordLine = (TextView)findViewById(R.id.word_line);
        wordLine.setBackgroundColor(Color.parseColor("#C2B9A8"));
        //跳转到考试音频
        Button goExamTvBtn = (Button)findViewById(R.id.examTv_list);
        goExamTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordTvActivity.this, ExamTvActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);    //无转场动画
                finish();                           //关闭当前活动
            }
        });

        //RecyclerView初始化
        wordTvList = new ArrayList<>();
        initWordTvs();
        initRecyclerView();

        showTV = (TextView)findViewById(R.id.show_tv);
        doMediaPlayer = new DoMediaPlayer();
    }

    /**
     * WordTv数据初始化
     */
    private void initWordTvs(){
        HttpUtil.sendOkHttpGetRequest(ADDRESS + "getWordRadio.jsp", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                jsonData = response.body().string().trim();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseWordTvJson(jsonData);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /**
     * RecyclerView初始化
     */
    private void initRecyclerView(){
        wordTvRecyclerView = (RecyclerView)findViewById(R.id.word_tv_recyclerview);
        //网格布局
        wordTvRecyclerView.setLayoutManager(
                new GridLayoutManager(WordTvActivity.this, 3, GridLayoutManager.VERTICAL,false));
        adapter = new WordTvAdapter(wordTvList);
        //item点击事件
        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemTvClick(View view) {
                TextView tv = (TextView)view;
                //播放音频
                String fileName = tv.getText().toString();
                if(fileName != null && fileName.length() > 0){
                    String path = ADDRESS + "voice/word_radio/"
                            + fileName + ".mp3";
                    doMediaPlayer.startMediaPlayer(path);
                }
                //中间白色TextView显示信息
                showTV.setCompoundDrawables(null, null, null, null);    //去掉图片
                int position = (int)view.getTag();
                WordTv wordTv = wordTvList.get(position);
                showTV.setText(wordTv.getEnglish()+"\n\n"
                        +wordTv.getYinbiao()+"\n\n"
                        +wordTv.getChinese());
            }

            @Override
            public void onItemBtnClick(View view) {

            }
        });
        wordTvRecyclerView.setAdapter(adapter);
    }
    /**
     * 解析json
     * @param data
     */
    private void parseWordTvJson(String data){
        try {
            wordTvList.clear();
            //1.将Json数据传入Json数组中
            JSONArray jsonArray = new JSONArray(data);
            //2.遍历Json数组
            for(int i = 0; i < jsonArray.length(); i++){
                //取出每一个Json对象
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //获取相应数据
                String english = jsonObject.getString("english");
                String chinese = jsonObject.getString("chinese");
                String yinbiao = jsonObject.getString("yinbiao");
                //获取对象添加到集合中
                WordTv wordTv = new WordTv(english, chinese, yinbiao);
                wordTvList.add(wordTv);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
