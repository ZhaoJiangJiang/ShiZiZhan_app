package android.classwork.com.android_lesson;

import android.classwork.com.android_lesson.util.HttpUtil;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 赵江江 on 2018/12/1.
 */

public class MyplanActivity extends AppCompatActivity implements View.OnClickListener{

    private String Account;

    private static final String WEB_SET_SELECT_KIND = HttpUtil.IP+":8080/android_lesson_work/bcz_user_set_selectkind.jsp";
    private static final String WEB_SET_PLAN_NUM = HttpUtil.IP+":8080/android_lesson_work/bcz_user_set_plannum.jsp";

    private TextView tv_Forth;  // 四级
    private TextView tv_Sixth;  // 六级
    private TextView tv_Posth;  // 考研
    private String selectKind = "四级词汇";
    private int vocbNum = 3486; // 四级单词书
    private EditText edit_PlanNum;
    private TextView tv_PlanDay;
    private String str_planNum;
    private int planNum;        // 计划背多少
    private int planDay;        // 按计划需要多少天
    private Button startPlan;   // 开始计划按钮
    private ImageView back;     // 返回按钮

    private RequestBody body_SetNum;
    private RequestBody body_SetKind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplan);

        Intent getAccount = getIntent();
        Account = getAccount.getStringExtra("account");
//        body = new FormBody.Builder().add("account", Account).build();

        tv_Forth = (TextView) findViewById(R.id.my_plan_forth);
        tv_Sixth = (TextView) findViewById(R.id.my_plan_sixth);
        tv_Posth = (TextView) findViewById(R.id.my_plan_posth);
        back = (ImageView) findViewById(R.id.my_plan_back);

        tv_Forth.setOnClickListener(this);
        tv_Sixth.setOnClickListener(this);
        tv_Posth.setOnClickListener(this);
        back.setOnClickListener(this);

        edit_PlanNum = (EditText) findViewById(R.id.my_plan_num);
        tv_PlanDay = (TextView) findViewById(R.id.my_plan_day);
        edit_PlanNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setPlanDayByPlanNum();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        startPlan = (Button)findViewById(R.id.my_plan_start);
        startPlan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_plan_back:
                finish();
                break;
            case R.id.my_plan_forth:
                tv_Forth.setTextColor(Color.parseColor("#1C86EE"));
                tv_Sixth.setTextColor(Color.parseColor("#7A8B8B"));
                tv_Posth.setTextColor(Color.parseColor("#7A8B8B"));
                selectKind = "四级词汇";
                vocbNum = 3486;
                setPlanDayByPlanNum();
                break;
            case R.id.my_plan_sixth:
                tv_Forth.setTextColor(Color.parseColor("#7A8B8B"));
                tv_Sixth.setTextColor(Color.parseColor("#1C86EE"));
                tv_Posth.setTextColor(Color.parseColor("#7A8B8B"));
                selectKind = "六级词汇";
                vocbNum = 3071;
                setPlanDayByPlanNum();
                break;
            case R.id.my_plan_posth:
                tv_Forth.setTextColor(Color.parseColor("#7A8B8B"));
                tv_Sixth.setTextColor(Color.parseColor("#7A8B8B"));
                tv_Posth.setTextColor(Color.parseColor("#1C86EE"));
                selectKind = "考研词汇";
                vocbNum = 6279;
                setPlanDayByPlanNum();
                break;
            case R.id.my_plan_start:
                // 设置planNum和selectKind到数据库
                str_planNum = edit_PlanNum.getText().toString();
                body_SetNum = new FormBody.Builder()
                        .add("account", Account)
                        .add("plannum", str_planNum)
                        .build();
                body_SetKind = new FormBody.Builder()
                        .add("account", Account)
                        .add("selectkind", selectKind)
                        .build();
                setMySQLPlanNum();
                setMySQLSelectKind();
                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                Intent back = new Intent();
                setResult(RESULT_OK, back);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 输入planNum设置planDay
     */
    private void setPlanDayByPlanNum(){
        str_planNum = edit_PlanNum.getText().toString();
        if ("".equals(str_planNum)){
            planNum = 0;
            tv_PlanDay.setText("");
        } else {
            planNum = Integer.parseInt(edit_PlanNum.getText().toString());
            planDay = (int)(vocbNum/planNum);
            if (vocbNum%planNum > 0){
                planDay ++;
            }
            tv_PlanDay.setText(planDay+"");
        }
    }

    /**
     * 设置MySQL->plan_Num
     */
    private void setMySQLPlanNum(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_SET_PLAN_NUM, body_SetNum, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 设置MySQL->select_kind
     */
    private void setMySQLSelectKind(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_SET_SELECT_KIND, body_SetKind, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

}
