package com.example.sunny.Tool;

import android.app.Notification;
import android.os.Message;
import android.util.Log;

import com.example.sunny.Model.ProjectModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InterfaceAddress;
import java.net.URL;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sunny on 2017/11/8.
 */


public class HttpUtil {

    private static OkHttpClient client = null;

    private static final String TAG = "HttpUtil";


    /* OkHttp3 */
    /*
    * 1.创建 OkHttpClient client 实例
    * 2.创建 Request request 实例
    * 3.client.newcall(request) 的 execute() 方法 发送请求并获取 response（返回数据）
    *
    * POST 和 GET 的 区别
    * POST request请求需要添加 提交数据 RequestBody body
    *
    * enqueue() 异步发送  返回数据至回调函数
    * execute() 同步发送  返回响应数据 Response
    * */
    public static void sendHttpRequest(final String url, final okhttp3.Callback callback)
    {
        // 异步 enqueue(callback)
        if (client == null)
            client = new OkHttpClient();

        // post
//        RequestBody body = new FormBody.Builder()
//                .add("username","laowang")
//                .add("password","123456")
//                .build();

        Request request = new Request.Builder()
                .url(url)
//                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void syncSendHttpRequest(final String url, final okhttp3.Callback callback)
    {
        // 同步  execute() 需要自己创建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (client == null)
                    client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) throw new IOException("Unexpected code" + response);

                    // 打印 响应头
                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        Log.i(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

    }


    /* 原生 HttpURLConnection */
    public static void sendHttpURLConnection(final String url, final responseCallback callback)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    URL url1 = new URL(url);
                    connection = (HttpURLConnection)url1.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5*1000);       // l连接超时
                    connection.setReadTimeout(5*1000);        // 读取超时

                    if (connection.getResponseCode() == 200)
                    {
                        // 获得服务器返回的输入流
                        InputStream stream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null){
                            response.append(line);
                        }

                        callback.onFinish(response.toString());
                    }

                } catch (Exception e){callback.onError(e);}
                finally {
                    if (connection != null)
                        connection.disconnect(); // 关闭连接
                }

            }
        });
    }


    public interface responseCallback{

        public void onFinish(String response);

        public void onError(Exception e);

    }

}
