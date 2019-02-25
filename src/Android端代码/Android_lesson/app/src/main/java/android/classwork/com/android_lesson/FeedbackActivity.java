package android.classwork.com.android_lesson;

import android.classwork.com.android_lesson.util.HttpUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 赵江江 on 2018/12/1.
 * 意见反馈
 */

public class FeedbackActivity extends AppCompatActivity {

    private static final String WEB_SET_FEED_BACK = HttpUtil.IP+":8080/android_lesson_work/bcz_util_set_feed_back.jsp";

    private EditText info;
    private Button submit;

    private RequestBody body;
    String time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        info = (EditText) findViewById(R.id.feed_back_info);
        submit = (Button) findViewById(R.id.feed_back_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = info.getText().toString().trim();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                //获取当前时间
                Date date = new Date(System.currentTimeMillis());
                time = simpleDateFormat.format(date);
                body = new FormBody.Builder()
                        .add("feed_back_text", text)
                        .add("feed_back_time", time)
                        .build();
                setFeedBack();
                finish();
            }
        });
    }

    private void setFeedBack(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_SET_FEED_BACK, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "感谢您的宝贵意见", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
}
