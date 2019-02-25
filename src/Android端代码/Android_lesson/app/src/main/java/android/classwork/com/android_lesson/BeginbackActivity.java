package android.classwork.com.android_lesson;

import android.classwork.com.android_lesson.back.Word;
import android.classwork.com.android_lesson.util.HttpUtil;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 赵江江 on 2018/12/19.
 */

public class BeginbackActivity extends AppCompatActivity {

    private static final String WEB_GET_DATA = HttpUtil.IP+":8080/android_lesson_work/bcz_user_get_unstudied.jsp";
    private final static String WEB_IMG_ADDRESS = HttpUtil.IP+":8080/android_lesson_work/Images/";
    private final static String WEB_SET_COLLECTION = HttpUtil.IP+":8080/android_lesson_work/bcz_user_set_collection.jsp";
    private final static String WEB_CANCEL_COLLECTION = HttpUtil.IP+":8080/android_lesson_work/bcz_user_cancel_collection.jsp";
    private final static String WEB_SET_STUDIED = HttpUtil.IP+":8080/android_lesson_work/bcz_user_set_studied.jsp";
    private final static String WEB_SET_CUT = HttpUtil.IP+":8080/android_lesson_work/bcz_user_set_cutted.jsp";
    private final static String WEB_ADD_INSISTDAY = HttpUtil.IP+":8080/android_lesson_work/bcz_user_add_insistday.jsp";


    TextView textView;
    TextView tv_remainderNum;
    ImageButton pic1;
    ImageButton pic2;
    ImageButton pic3;
    ImageButton pic4;
    ImageButton collect;
    Button remove;
    Button hint;

    Word word;
    ArrayList<Word> wordArrayList = new ArrayList<>();
    ArrayList<ImageButton> pic = new ArrayList<>();
    int cur = 0;        // 当前背到第几个单词
    int rightpc = 0;    // 当前单词正确的图片是哪张
    int planNum;        // 计划背单词数
    String Account;     // 当前用户
    StringBuilder EnglishData;
    String[] EnglishDatas;
    boolean[] isUseImg = new boolean[100]; //单词是否已经出现过
    int[] r = new int[3];

    RequestBody body;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if( resultCode == RESULT_OK){
                    if( data.getIntExtra("cur", -1) == 1 ){
                        if( setImage(++cur) == -1 ) {
                            finish();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginback);

        Intent intent = getIntent();
        String temp = intent.getStringExtra("data");
        String[] temps = temp.split("#");
        planNum = Integer.parseInt(temps[0]);
        Account = temps[1];
        body = new FormBody.Builder()
                .add("account", Account)
                .build();


        textView = (TextView) findViewById(R.id.word_id);
        tv_remainderNum = (TextView) findViewById(R.id.remainderNum);
        tv_remainderNum.setText("剩余单词数："+(planNum));
        pic1 = (ImageButton) findViewById(R.id.pic1);
        pic2 = (ImageButton) findViewById(R.id.pic2);
        pic3 = (ImageButton) findViewById(R.id.pic3);
        pic4 = (ImageButton) findViewById(R.id.pic4);
        collect = (ImageButton) findViewById(R.id.collect);
        remove = (Button) findViewById(R.id.remove);
        hint = (Button) findViewById(R.id.hint);

        pic.add(pic1);
        pic.add(pic2);
        pic.add(pic3);
        pic.add(pic4);

        getWordInfo();

        //预设图片
        if( setImage(cur) == -1 ) {
            finish();
        }

        // 提示
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHint = new Intent(BeginbackActivity.this, HintActivity.class);
                toHint.putExtra("English", wordArrayList.get(cur).wordtext);
                startActivity(toHint);
            }
        });

        // 斩 的点击事件
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( setImage(cur) != -1 ) {
                    body = new FormBody.Builder()
                            .add("account", Account)
                            .add("english", wordArrayList.get(cur).wordtext)
                            .build();
                    setCutToMySql();
                }
                setRandom();
                if (setImage(++cur) == -1){
                    finish();
                }
            }
        });

        // 收藏 的点击事件
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setcollect(cur);
            }
        });

        // 图片1 点击事件
        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightpc = wordArrayList.get(cur).rightpc;
                if( rightpc == 0 ){
                    pic1.setImageResource(R.drawable.right);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            body = new FormBody.Builder()
                                    .add("account", Account)
                                    .add("english", wordArrayList.get(cur).wordtext)
                                    .build();
                            setStudeidToMySql();
                            setRandom();
                            if( setImage(++cur) == -1 ) {
                                finish();
                            }
                        }
                    }, 300);//3秒后执行Runnable中的run方法
                    tv_remainderNum.setText("剩余单词数："+(planNum-cur-1));
                } else{
                    pic1.setImageResource(R.drawable.wrong);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImgAddressRandom(pic4);
//                            if( setImage(cur) == -1 ) {
//                                finish();
//                            }
                        }
                    }, 300);//3秒后执行Runnable中的run方法
                }
            }
        });
        // 图片2 点击事件
        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightpc = wordArrayList.get(cur).rightpc;
                if( rightpc == 1 ){
                    pic2.setImageResource(R.drawable.right);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            body = new FormBody.Builder()
                                    .add("account", Account)
                                    .add("english", wordArrayList.get(cur).wordtext)
                                    .build();
                            setStudeidToMySql();
                            setRandom();
                            if( setImage(++cur) == -1 ) {
                                finish();
                            }
                        }
                    }, 300);//3秒后执行Runnable中的run方法
                    tv_remainderNum.setText("剩余单词数："+(planNum-cur-1));
                } else{
                    pic2.setImageResource(R.drawable.wrong);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImgAddressRandom(pic2);
//                            if( setImage(cur) == -1 ) {
//                                finish();
//                            }finish
                        }
                    }, 300);//3秒后执行Runnable中的run方法
                }
            }
        });
        // 图片3点击事件
        pic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightpc = wordArrayList.get(cur).rightpc;
                if( rightpc == 2 ){
                    pic3.setImageResource(R.drawable.right);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            body = new FormBody.Builder()
                                    .add("account", Account)
                                    .add("english", wordArrayList.get(cur).wordtext)
                                    .build();
                            setStudeidToMySql();
                            setRandom();
                            if( setImage(++cur) == -1 ) {
                                finish();
                            }
                        }
                    }, 300);//1秒后执行Runnable中的run方法
                    tv_remainderNum.setText("剩余单词数："+(planNum-cur-1));
                } else{
                    pic3.setImageResource(R.drawable.wrong);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImgAddressRandom(pic3);
//                            if( setImage(cur) == -1 ) {
//                                finish();
//                            }
                        }
                    }, 300);//1秒后执行Runnable中的run方法
                }
            }
        });
        // 图片4 点击事件
        pic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightpc = wordArrayList.get(cur).rightpc;
                if( rightpc == 3 ){
                    pic4.setImageResource(R.drawable.right);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            body = new FormBody.Builder()
                                    .add("account", Account)
                                    .add("english", wordArrayList.get(cur).wordtext)
                                    .build();
                            setStudeidToMySql();
                            setRandom();
                            if( setImage(++cur) == -1 ) {
                                finish();
                            }
                        }
                    }, 300);//1秒后执行Runnable中的run方法
                    tv_remainderNum.setText("剩余单词数："+(planNum-cur-1));
                } else{
                    pic4.setImageResource(R.drawable.wrong);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImgAddressRandom(pic4);
//                            if( setImage(cur) == -1 ) {
//                                finish();
//                            }
                        }
                    }, 300);//1秒后执行Runnable中的run方法
                }
            }
        });
    }


    public void setcollect( int index ){
        Word wd = wordArrayList.get(index);
        body = new FormBody.Builder()
                .add("account", Account)
                .add("english", wd.wordtext)
                .build();
        if( wd.collect == false ){
            collect.setImageResource(R.drawable.collectok);
            wordArrayList.get(index).collect = true;
            setCollectToMySql();
        }
        else{
            collect.setImageResource(R.drawable.collect);
            wordArrayList.get(index).collect = false;
            cancelCollectToMySql();
        }
    }
    public int setImage( int index ){
        if( cur >= wordArrayList.size() ) {
            addInsistDayToMySql();
            Toast.makeText(getApplicationContext(), "任务完成", Toast.LENGTH_SHORT).show();
            return -1;
        }
        Word wd = wordArrayList.get(cur);
        textView.setText(wd.wordtext);

        if (cur == 0) {
            for (int i = 0; i < 4; i++) {
                pic.get(i).setImageResource(wd.pic[i]);
            }
        }else {
            getImgAddress(wd.wordtext);
            getImgAddressRandom(pic2);
            getImgAddressRandom(pic3);
            getImgAddressRandom(pic4);
        }

        if( wd.collect == false ) {
            collect.setImageResource(R.drawable.collect);
        }
        else {
            collect.setImageResource(R.drawable.collectok);
        }
        return 1;
    }

    public void getWordInfo(){
        word = new Word("lemon",
                R.drawable.p1, R.drawable.p4,
                R.drawable.p2, R.drawable.p3, 0);
        wordArrayList.add(word);

        EnglishData = new StringBuilder();
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_GET_DATA, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                parseXmlData(response.body().string());
                EnglishDatas = EnglishData.toString().split("#");
                Arrays.fill(isUseImg, false);
                for (int i = 0; i < planNum-1; i++){
                    word = new Word(EnglishDatas[i],
                            R.drawable.p1, R.drawable.p2,
                            R.drawable.p3, R.drawable.p4, 0);
                    isUseImg[i] = true;
                    wordArrayList.add(word);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 解析xml数据
     * @param XMLData
     */
    private void parseXmlData(String XMLData){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(XMLData));
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    // 开始解析某个节点
                    case XmlPullParser.START_TAG:{
                        if ("English".equals(nodeName)) {
                            EnglishData.append(xmlPullParser.nextText());
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if ("vocabulary".equals(nodeName)){
                            EnglishData.append("#");
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

    /**
     * 得到图片
     * @param English
     */
    private void getImgAddress(String English){
        HttpUtil.sendOkHttpRequestWithOutRequestBody(WEB_IMG_ADDRESS+English+".jpg", new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 通过message，拿到字节数组
                final byte[] Image = (byte[]) response.body().bytes();
                // 使用BitmaFactory工厂，把字节数组转换为bitmap
                final Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pic1.setVisibility(View.VISIBLE);
                        pic1.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    /**
     * 设置随机数图片
     */
    private void setRandom(){
        int total = EnglishDatas.length;
        for (int i = 0; i < 3; i++){
            r[i] = (int) (Math.random()*total);
            while (isUseImg[r[i]] == true){
                r[i] = (int) (Math.random()*total);
            }
            isUseImg[r[i]] = true;
        }
    }

    /**
     * 得到随机图片
     */
    private void getImgAddressRandom(final ImageButton pic){
        int i;

        if (pic == pic2){
            i = r[0];
        }else if(pic == pic3){
            i = r[1];
        }else {
            i = r[2];
        }
//        Toast.makeText(getApplicationContext(), "i: " + i, Toast.LENGTH_SHORT).show();
        HttpUtil.sendOkHttpRequestWithOutRequestBody(WEB_IMG_ADDRESS+EnglishDatas[i]+".jpg", new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 通过message，拿到字节数组
                final byte[] Image = (byte[]) response.body().bytes();
                // 使用BitmaFactory工厂，把字节数组转换为bitmap
                final Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pic.setVisibility(View.VISIBLE);
                        pic.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    private void setCollectToMySql(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_SET_COLLECTION, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void cancelCollectToMySql(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_CANCEL_COLLECTION, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void setStudeidToMySql(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_SET_STUDIED, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void setCutToMySql(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_SET_CUT, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void addInsistDayToMySql(){
        body = new FormBody.Builder()
                .add("account", Account)
                .build();
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_ADD_INSISTDAY, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
}
