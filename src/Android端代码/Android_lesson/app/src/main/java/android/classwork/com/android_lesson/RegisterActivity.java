package android.classwork.com.android_lesson;

import android.classwork.com.android_lesson.util.HttpUtil;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 赵江江 on 2018/11/11.
 */

public class RegisterActivity extends AppCompatActivity{

//    private final static String IP = "http://192.168.43.96";
    private final static String WEB_ADD_REG_USER = HttpUtil.IP+":8080/android_lesson_work/bcz_user_add_register_user.jsp";
    private final static String WEB_IS_EXIST_ACC = HttpUtil.IP+":8080/android_lesson_work/bcz_user_is_account_exist.jsp";



    private Button sendCodeBtn;
    private Button checkCodeBtn;
    private EditText writePhone;
    private EditText writeCode;
    private String phone;
    private String psw;
    private String rpt_psw;
    private String country = "86";

    private EditText password;
    private EditText repeat_password;
    private EditText username;
    private RequestBody body;
    private String isExist;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // 隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        password = (EditText) findViewById(R.id.Password);
        repeat_password = (EditText) findViewById(R.id.repeat_Password);
        username = (EditText) findViewById(R.id.username);

        //发送验证码
        writePhone = (EditText)findViewById(R.id.writePhone);
        MobSDK.init(this);
        sendCodeBtn = (Button) findViewById(R.id.sendCode);
        // 检测是否账号是否存在

        sendCodeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                phone = writePhone.getText().toString().trim();
                psw = password.getText().toString().trim();
                rpt_psw = repeat_password.getText().toString().trim();

                body = new FormBody.Builder()
                        .add("account", phone)
                        .build();
//                isExistAccount();
                // 手机号为空
                if (phone.equals("") || phone.length()==0){
                    Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                // 密码为空
                else if (psw.equals("") || psw.length() == 0){
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                }
                // 确认密码为空
                else if (rpt_psw.equals("") || rpt_psw.length() == 0){
                    Toast.makeText(getApplicationContext(), "请输入确认密码", Toast.LENGTH_SHORT).show();
                }
                // 密码长度小于6位
                else if(psw.length() < 6){
                    Toast.makeText(getApplicationContext(), "密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                }
                // 密码不一致
                else if (!psw.equals(rpt_psw)) {
                    Toast.makeText(getApplicationContext(), "两次密码不一致，请确认密码再重新输入", Toast.LENGTH_SHORT).show();
                }
                else {
                    isExistAccount();
                }
//                // 账号存在
//                else if ("1".equals(isExist)){
//                    Toast.makeText(getApplicationContext(), "该账号已注册", Toast.LENGTH_SHORT).show();
//                }
//                // 成功发送!
//                else {
////                    phone = writePhone.getText().toString().trim();
//                    sendCode(country, phone);
//                }
            }
        });
        //校验验证码
        writeCode = (EditText)findViewById(R.id.writeCode);
        checkCodeBtn = (Button)findViewById(R.id.checkCode);
        checkCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writePhone.setEnabled(false);   //设置编辑框不可编辑
                String myCode = writeCode.getText().toString();
                submitCode(country, phone, myCode);
                writePhone.setEnabled(true);
            }
        });
    }

    //发送验证码的函数实现
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果，此时接口请求为发送验证码
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"发送成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // TODO 处理错误的结果
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"发送失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码交予服务器验证，其中的code表示验证码，如“1357”
    public void submitCode(String country, final String phone, String code) {
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果，此时接口请求为提交验证码
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            body = new FormBody.Builder()
                                    .add("account", writePhone.getText().toString().trim())
                                    .add("password", password.getText().toString().trim())
                                    .add("username", username.getText().toString().trim())
                                    .build();
                            addRegisterUser();
                            Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_SHORT).show();
                            Intent RegToLog = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(RegToLog);
                        }
                    });
                } else {
                    // TODO 处理错误的结果
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        //提交验证码，其中的code表示验证码，如“1357”
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();

    }

    private void isExistAccount(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_IS_EXIST_ACC, body, new okhttp3.Callback(){

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                isExist = response.body().string().trim();
                if (!"1".equals(isExist)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            sendCode(country, phone);
                            Looper.loop();
                        }
                    }).start();
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "该账号已存在", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void addRegisterUser(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_ADD_REG_USER, body, new okhttp3.Callback(){

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
}
