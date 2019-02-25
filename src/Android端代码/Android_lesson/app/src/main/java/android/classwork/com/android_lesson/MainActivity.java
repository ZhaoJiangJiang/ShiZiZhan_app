package android.classwork.com.android_lesson;

/**
 * 功能上：核心功能必须完整、每个人都要有单独的功能模块
 * 数据完整：1）真实的数据 2）数量
 * 1)代码：a）必须有注释 b）使用Doxygen生成文档(html格式)
 * 2)文档
 *
 * 文档：
 * 1)需求：功能清单
 * 2)完成情况小结
 * 3)主体框架
 *  a)大的框架
 *  b)数据刘翔
 *  c)文件清单
 *
 *  4)使用说明
 *
 *  5)小组的工作情况
 *   a)每个人的工作
 *   b)评估每个贡献百分比
 *  6)使用技术(有一定的特色、有难度，可以写)
 *  7)小结
 *
 *  时间：13周、15周、16周(检查)   上午8：00-11：00  下午14：00-16：00
 */

import android.classwork.com.android_lesson.util.HttpUtil;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String WEB_REC_ACCOUNT = HttpUtil.IP+":8080/android_lesson_work/bcz_util_get_recent_account.jsp";
    private final static String WEB_IS_AUTO = HttpUtil.IP+":8080/android_lesson_work/bcz_user_get_isautologin.jsp";


    public static MainActivity instance = null; // finish()用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        // 隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getRecentAccount();

        Button login = (Button) findViewById(R.id.Login);
        Button register = (Button) findViewById(R.id.Register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Login){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
//            finish();
        } else if (view.getId() == R.id.Register){
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
//            finish();
        }
    }

    /**
     * 得到最近登录的账号
     */
    private void getRecentAccount(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(WEB_REC_ACCOUNT)
                            .build();
                    Response response = client.newCall(request).execute();
                    String ResponseData = response.body().string().trim();
                    if (ResponseData!=null || ResponseData.length()!=0){
                        // 查看是否记住密码
                        isAutoLogin(ResponseData);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 查看是否自动登录
     */
    private void isAutoLogin(final String queryAccount){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account", queryAccount)
                            .build();
                    Request request = new Request.Builder()
                            .url(WEB_IS_AUTO)
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String isAuto = response.body().string().trim();
                    if (isAuto.equals("1")){
                        Intent intent = new Intent(MainActivity.this, _1stActivity.class);
                        intent.putExtra("account", queryAccount);
                        startActivity(intent);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "自动登录成功!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
