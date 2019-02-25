package android.classwork.com.android_lesson.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by 赵江江 on 2018/11/17.
 */

public class HttpUtil {

    public static String IP = "http://192.168.43.96";

    public static void sendOkHttpRequestWithOutRequestBody(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestWithRequestBody(String address, RequestBody body, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).post(body).build();
        client.newCall(request).enqueue(callback);
    }
}
