package android.classwork.com.android_lesson.util.vocb_dialog;

import android.app.Dialog;
import android.classwork.com.android_lesson.R;
import android.classwork.com.android_lesson.util.HttpUtil;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 赵江江 on 2018/12/15.
 */

public class WordDialog extends AppCompatActivity{

    private static final String WEB_GET_WORD_INFO = HttpUtil.IP+":8080/android_lesson_work/bcz_vocb_get_word_by_English.jsp";

    private String word;
    private Dialog dialog;

    private TextView info;
    private TextView root;
    private TextView Chinese;

    private String rootInfo;
    private String ChineseInfo;
    private String[] ChineseInfos;

    private RequestBody body;

    public WordDialog(@NonNull Context context, String word) {
        dialog = new Dialog(context);
        this.word = word;
        dialog.setContentView(R.layout.click_word);
        info = (TextView)dialog.findViewById(R.id.click_word_info);
        root = (TextView)dialog.findViewById(R.id.click_word_root);
        Chinese = (TextView)dialog.findViewById(R.id.click_word_chinese);
    }

    public void show() {
        body = new FormBody.Builder()
                .add("English", word)
                .build();
        getWordInfo();
//        info.setText(word);
//        root.setText(rootInfo);
//        Chinese.setText(ChineseInfo);
        dialog.show();
    }

    private void getWordInfo(){
        HttpUtil.sendOkHttpRequestWithRequestBody(WEB_GET_WORD_INFO, body, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                parseJsonData(response.body().string());
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 解析json数据
     * @param jsonData
     */
    private void parseJsonData(String jsonData){
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            if (jsonArray.length() == 0){
                word = "暂未录入该单词";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        info.setText("");
                        root.setText("暂未录入该单词");
                        Chinese.setText("");
                    }
                });
                return;
            }
            for (int i = 0, len = jsonArray.length(); i < len; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChineseInfos = jsonObject.getString("Chinese").split("；");
                ChineseInfo = "";
                for (int index=0, _len=ChineseInfos.length; index<_len; index++){
                    ChineseInfo = ChineseInfo+ChineseInfos[index]+"\n";
                }
                rootInfo = jsonObject.getString("vocbroot");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    info.setText(word);
                    root.setText(rootInfo);
                    Chinese.setText(ChineseInfo);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
