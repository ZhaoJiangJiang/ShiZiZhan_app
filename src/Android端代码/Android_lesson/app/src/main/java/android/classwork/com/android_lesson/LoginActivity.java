package android.classwork.com.android_lesson;

import android.classwork.com.android_lesson.util.HttpUtil;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 赵江江 on 2018/11/11.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

//    private final static String IP = "http://192.168.43.96";
    private final static String WEB_LOGIN_CHECK = HttpUtil.IP+":8080/android_lesson_work/bcz_user_check_login_data.jsp";
    private final static String WEB_AUTO_LOGIN = HttpUtil.IP+":8080/android_lesson_work/bcz_user_auto_login.jsp";
    private final static String WEB_CANCLE_REMPSW = HttpUtil.IP+":8080/android_lesson_work/bcz_user_cancel_autologin.jsp";
    private final static String WEB_SET_RECENT_ACCOUNT = HttpUtil.IP+":8080/android_lesson_work/bcz_util_set_recent_account.jsp";

    private EditText Account_Edit, Password_Edit;
    private CheckBox RememberPass, AutoLogin;
    private Button ForgetPsw_Btn, Login_Btn, Reg_Btn;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Boolean isRememberPass;
    private Boolean isAutoLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        Account_Edit = (EditText) findViewById(R.id.edit_Account);
        Password_Edit = (EditText) findViewById(R.id.edit_Password);
        RememberPass = (CheckBox) findViewById(R.id.cb_RememberPass);
        AutoLogin = (CheckBox) findViewById(R.id.cb_AutoLogin);
        ForgetPsw_Btn = (Button) findViewById(R.id.btn_ForgetPass);
        Login_Btn = (Button) findViewById(R.id.btn_Login);
        Reg_Btn = (Button) findViewById(R.id.btn_Reg);

        // checkbox改变时的监听事件
        RememberPass.setOnCheckedChangeListener(this);
        AutoLogin.setOnCheckedChangeListener(this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        isRememberPass = pref.getBoolean("rememberPassword", false);
        isAutoLogin = pref.getBoolean("autoLogin", false);
        // 是否记住密码
        if (isRememberPass){
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            Account_Edit.setText(account);
            Password_Edit.setText(password);
            RememberPass.setChecked(true);
        }
//        // 是否自动登录
//        if (isAutoLogin){
//            Intent intent = new Intent(LoginActivity.this, _1stActivity.class);
//            startActivity(intent);
//            Toast.makeText(getApplicationContext(), "自动登录成功!", Toast.LENGTH_SHORT).show();
//            finish();
//        }

        // 按钮监听事件
        Login_Btn.setOnClickListener(this); // 登录按钮事件
        ForgetPsw_Btn.setOnClickListener(this);
        Reg_Btn.setOnClickListener(this);
    }

    /**
     * 记住密码或自动登录变化时
     * @param compoundButton
     * @param b
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.cb_RememberPass:
                if (!RememberPass.isChecked()){
                    AutoLogin.setChecked(false);
                }
                break;
            case R.id.cb_AutoLogin:
                if (AutoLogin.isChecked()){
                    RememberPass.setChecked(true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Login:
                checkAccountPassword(); // 检查账号密码是否正确
                break;
            case R.id.btn_ForgetPass:
                Intent intentForPass = new Intent(LoginActivity.this, ForgetpassActivity.class);
                startActivity(intentForPass);
                break;
            case R.id.btn_Reg:
                Intent intentReg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentReg);
//                finish();
            default:
                break;
        }
    }

    /**
     * 检验登录账户密码正确性
     */
    private void checkAccountPassword(){
        final String account = Account_Edit.getText().toString();
        final String password = Password_Edit.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account", account).add("password", password)
                            .build();
                    Request request = new Request.Builder()
                            .url(WEB_LOGIN_CHECK)
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    // 账号密码正确
                    if (responseData.trim().equals("true")){
                        editor = pref.edit();
                        // 记住密码
                        if (RememberPass.isChecked()){
                            editor.putBoolean("rememberPassword", true);
                            editor.putString("account", account);
                            editor.putString("password", password);
                            // 自动登录
                            if (AutoLogin.isChecked()){
                                editor.putBoolean("autoLogin", true);
                                setMySqlAutoLogin(account);
                            }else {
                                editor.putBoolean("autoLogin", false);
                                setMySqlCancelAutoLogin(account);
                            }
                        }else {
                            editor.clear();
                        }
                        editor.apply();
                        setRecentAccount(account);
                        Intent intent = new Intent(LoginActivity.this, _1stActivity.class);
                        intent.putExtra("account", account);
                        startActivity(intent);
                        finish();
                        MainActivity.instance.finish();
                    } else if(responseData.trim().equals("null")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (responseData.trim().equals("false")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 将数据库的自动登录设置为 1
     */
    private void setMySqlAutoLogin(final String account){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account", account)
                            .build();
                    Request request = new Request.Builder()
                            .url(WEB_AUTO_LOGIN)
                            .post(requestBody).build();
                    client.newCall(request).execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 将数据库的自动登录设置为0
     */
    private void setMySqlCancelAutoLogin(final String account){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account", account)
                            .build();
                    Request request = new Request.Builder()
                            .url(WEB_CANCLE_REMPSW)
                            .post(requestBody).build();
                    client.newCall(request).execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 设置最近登录账号
     */
    private void setRecentAccount(final String recentAccount){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), "132456", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("account", recentAccount)
                            .build();
                    Request request = new Request.Builder()
                            .url(WEB_SET_RECENT_ACCOUNT)
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}










