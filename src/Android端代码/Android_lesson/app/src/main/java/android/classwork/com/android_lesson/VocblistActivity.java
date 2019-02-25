package android.classwork.com.android_lesson;

import android.classwork.com.android_lesson.util.HttpUtil;
import android.classwork.com.android_lesson.util.vocb_list.OnItemClickListener;
import android.classwork.com.android_lesson.util.vocb_list.vocbAdapter;
import android.classwork.com.android_lesson.util.vocb_list.vocbInfo;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 赵江江 on 2018/12/2.
 */

public class VocblistActivity extends AppCompatActivity implements View.OnClickListener{

//    private static final String WEB_GET_ENLISH_LIST = HttpUtil.IP+":8080/android_lesson_work/bcz_vocb_get_all.jsp";
    private static final String WEB_GET_COLLECTION = HttpUtil.IP+":8080/android_lesson_work/bcz_user_get_collection.jsp";
    private static final String WEB_GET_STUDIED = HttpUtil.IP+":8080/android_lesson_work/bcz_user_get_studied.jsp";
    private static final String WEB_GET_CUTTED = HttpUtil.IP+":8080/android_lesson_work/bcz_user_get_cutted.jsp";
    private static final String WEB_GET_UNSTUDIED = HttpUtil.IP+":8080/android_lesson_work/bcz_user_get_unstudied.jsp";

    private RecyclerView recyclerView;
    private vocbAdapter adapter;
    private List<vocbInfo> vocbList = new ArrayList<>();

    private Button orderBtn;
    private Button studiedBtn;
    private Button unstudyBtn;
    private Button getYetBtn;
    private Button collectBtn;
    private Button back;

    private TextView studiedLine;
    private TextView unstudyLine;
    private TextView getYetLine;
    private TextView collectLine;
    private TextView vocbNum;

    private RequestBody body;

    private String Account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocblist);
        // 隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        Account = intent.getStringExtra("account");
        body = new FormBody.Builder()
                .add("account", Account)
                .build();

        //单词列表
        initVocbs(WEB_GET_COLLECTION);    // 初始化消息数据
        recyclerView = (RecyclerView) findViewById(R.id.vocb_list_word_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new vocbAdapter(vocbList);
        // 列表item的点击事件
        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemTvClick(View view) {
                // 中文意思的隐藏显示
                TextView tv_Chinese = (TextView) view.findViewById(R.id.vocb_item_chinese);
                Drawable drawable = tv_Chinese.getBackground();
                ColorDrawable colorDrawable = (ColorDrawable)drawable;
                int i = colorDrawable.getColor();
                if (i == -8027006){
                    tv_Chinese.setBackgroundColor(Color.parseColor("#F8F6F1")); // 显示中文意思
                }else {
                    tv_Chinese.setBackgroundColor(Color.parseColor("#858482")); // 隐藏中文意思
                }
            }

            // 跳到 单词详情
            @Override
            public void onItemBtnClick(View view) {
                String EnglishInfo = (String) view.getTag();
                Intent toDetail = new Intent(VocblistActivity.this, VocbdetailActivity.class);
                toDetail.putExtra("English", EnglishInfo);
                startActivity(toDetail);
            }
        });

        recyclerView.setAdapter(adapter);
        // 单词总数的显示
        vocbNum = (TextView) findViewById(R.id.vocb_list_count);

        // 返回
        back = (Button) findViewById(R.id.vocb_list_go_back);
        back.setOnClickListener(this);

        // 排序
        orderBtn = (Button) findViewById(R.id.vocb_list_order);
        orderBtn.setOnClickListener(this);

        // 已学、未学、已斩、收藏
        studiedLine = (TextView) findViewById(R.id.vocb_list_studied_line);
        unstudyLine = (TextView) findViewById(R.id.vocb_list_unstudied_line);
        getYetLine = (TextView) findViewById(R.id.vocb_list_got_line);
        collectLine = (TextView) findViewById(R.id.vocb_list_collect_line);

        // 默认选中收藏单词
        studiedLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
        unstudyLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
        getYetLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
        collectLine.setBackgroundColor(Color.parseColor("#858482"));

        studiedBtn = (Button) findViewById(R.id.vocb_list_studied_list);
        studiedBtn.setOnClickListener(this);

        unstudyBtn = (Button) findViewById(R.id.vocb_list_unstudied_list);
        unstudyBtn.setOnClickListener(this);

        getYetBtn = (Button) findViewById(R.id.vocb_list_got_list);
        getYetBtn.setOnClickListener(this);

        collectBtn = (Button) findViewById(R.id.vocb_list_collect_list);
        collectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.vocb_list_order:
                initMenu();
                break;
            case R.id.vocb_list_go_back:
                finish();
                break;
            case R.id.vocb_list_studied_list:
                studiedLine.setBackgroundColor(Color.parseColor("#858482"));
                unstudyLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                getYetLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                collectLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                initVocbs(WEB_GET_STUDIED);
                break;
            case R.id.vocb_list_unstudied_list:
                studiedLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                unstudyLine.setBackgroundColor(Color.parseColor("#858482"));
                getYetLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                collectLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                initVocbs(WEB_GET_UNSTUDIED);
                break;
            case R.id.vocb_list_got_list:
                studiedLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                unstudyLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                getYetLine.setBackgroundColor(Color.parseColor("#858482"));
                collectLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                initVocbs(WEB_GET_CUTTED);
                break;
            case R.id.vocb_list_collect_list:
                studiedLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                unstudyLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                getYetLine.setBackgroundColor(Color.parseColor("#DBD4CC"));
                collectLine.setBackgroundColor(Color.parseColor("#858482"));
                initVocbs(WEB_GET_COLLECTION);
                break;
            default:
                break;
        }
    }

    private void initVocbs(String address){
        // 从数据库导入单词信息
        HttpUtil.sendOkHttpRequestWithRequestBody(address, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resposneData = response.body().string();
                insertToList(resposneData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 刷新
                        adapter.notifyDataSetChanged();
                        // 设置单词总数
                        vocbNum.setText("单词总数："+vocbList.size()+"");
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void insertToList(String XMLData){
        vocbList.clear();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(XMLData));
            int eventType = xmlPullParser.getEventType();
            String English = "";
            String Chinese = "";
            vocbInfo info;
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    // 开始解析某个节点
                    case XmlPullParser.START_TAG:{
                        if ("English".equals(nodeName)) {
                            English = xmlPullParser.nextText();
                        }else if ("Chinese".equals(nodeName)){
                            Chinese = xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if ("vocabulary".equals(nodeName)){
                            info = new vocbInfo(Chinese, English);
                            vocbList.add(info);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initMenu(){
        // 创建弹出式菜单对象
        PopupMenu popupMenu = new PopupMenu(VocblistActivity.this, orderBtn);
        // 获取菜单填充器
        MenuInflater inflater = popupMenu.getMenuInflater();
        // 填充菜单
        inflater.inflate(R.menu.vocb_list_order, popupMenu.getMenu());
        // 绑定菜单项的点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    // 字母顺序
                    case R.id.vocb_list_order_up_order:
                        Collections.sort(vocbList, new Comparator<vocbInfo>() {
                            @Override
                            public int compare(vocbInfo vocbInfo, vocbInfo t1) {
                                return vocbInfo.getEnglish().compareTo(t1.getEnglish());
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                    // 字母倒序
                    case R.id.vocb_list_order_down_order:
                        Collections.sort(vocbList, new Comparator<vocbInfo>() {
                            @Override
                            public int compare(vocbInfo vocbInfo, vocbInfo t1) {
                                return t1.getEnglish().compareTo(vocbInfo.getEnglish());
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}




























