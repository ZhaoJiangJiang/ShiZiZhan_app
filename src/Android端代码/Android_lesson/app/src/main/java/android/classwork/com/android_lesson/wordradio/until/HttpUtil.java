package android.classwork.com.android_lesson.wordradio.until;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 赵江江 on 2018/12/18.
 */

public class HttpUtil {
    public static void sendOkHttpGetRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static void sendOkHttpPostRequest(String address, RequestBody requestBody, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
    }

    /**
     * 使用OkHttp传入参数获取相应数据
     * @param address
     * @param requestBody
     * @return 服务器返回的数据
     */
    public static String sendOkHttpPostRequest(String address, RequestBody requestBody){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(address)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String data = response.body().string().trim();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
