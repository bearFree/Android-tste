package com.example.sunny.fdgfg;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceActivity;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


import javax.xml.transform.sax.TemplatesHandler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Callback;
import okhttp3.ResponseBody;
import okio.BufferedSink;

// 主入口函数
public class MainActivity extends BaseActivity {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private LocalBroadcastManager localBroadcastManager;

    private static final String TAG = "MainActivity";
    /* Activity 生命周期
    * 1.onCreate === viewDidLoad
    * 2.onStart  === viewWillAppear
    * 3.onResume === viewDidAppear
    * 4.onPause  === viewWillDisAppear
    * 5.onStop   === viewDidDisAppear
    * 6.onDestroy === dealloc
    * 7.onRestart
    * */

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(networkChangeReceiver);
        Log.d(TAG, "onDestroy: dealloc");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop: viewDidDisAppear");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: viewWillDisAppear");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: viewDidAppear");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: viewWillAppear");
    }

    private String[] data = {"www", "eee","qqq", "bbb","gfff", "sdss","ghh", "ikiu","yhn", "mju"};

    /**
     * ListView === UITableView
     * ListView dataSource需要通过适配器来构建数据 （ArrayAdapter）
     *
     * */

    // 伪代码，表示重写：编译器可以验证关键字下面的函数是否是父类中声明的
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        setTitle("Android-Test");

        Log.d(TAG, "onCreate: viewDidLoad");

        final TeacherAdapter teachersAdapter = new TeacherAdapter(this, R.layout.teacher_list_item, Teacher.getAllTeachers());
        final ListView listview = (ListView) findViewById(R.id.main_listview);
        listview.setAdapter(teachersAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0){
                    Teacher teacher = teachersAdapter.getItem(i);
                    // 这里进行跳转
                    //  初始化一个准备跳转到TeacherDetailActivity的Intent
                    Intent intent = new Intent(MainActivity.this, TeacherDetailActivity.class);

                    // 1.往Intent中传入Teacher相关的数据，供TeacherDetailActivity使用
                    intent.putExtra("teacher_image", teacher.getImageId());
                    intent.putExtra("teacher_desc", teacher.getDesc());

                    // 2.传递数据为对象时，需要序列化。
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", 1);
                    bundle.putString("name", "laowang");
                    intent.putExtra("data", bundle);

                    //  初始化一个准备跳转到TeacherDetailActivity的Intent
                    startActivity(intent);
                }else if (i == 1) {
                    /*运行时权限  任何权限都需要在AndroidManifest.xml中静态声明
                    * 1.检查权限 ContextCompat.checkSelfPermission 返回值等于PERMISSION_GRANTED则，权限许可
                    * 2.申请权限 ActivityCompat.requestPermissions
                    * 3.结果在回调 onRequestPermissionsResult中，数据封装在grantResults
                    * */
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) !=
                            PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                    } else {
                        call();
                    }

                } else if (i == 2) {
                    /*内容提供器 ContentResolver
                    * Context getContentResolver() 获取实例，
                    * 实例方法提供 查询，删除，插入，更新 query() delete() insert() update()
                    * 提供一块匿名的数据共享空间，底层通过进程间通信提供数据访问
                    * 访问现有的内容 -- 通讯录， 或创建自己程序的内容提供器
                    * */
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) !=
                            PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},2);
                    } else {
                        queryContentResolver();
                    }

                    /*创建内容提供器
                    * 不常用，不看了
                    * */
                    MyContentProvider contentProvider = new MyContentProvider();

                }else if (i==3){
                    /*通知 Notification
                    * NotificationManager Context getSystemService() 获取 NotificationManager 实例
                    * NotificationCompat.Builder 构建器  .build()生成Notification 实例
                    * PendingIntent 构建跳转意图
                    * anager.notify(1,notification);         NotificationManager发送通知 1位通知id
                    * */

                    Intent intent = new Intent(MainActivity.this, TeacherDetailActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);


                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!")
                            .setWhen(System.currentTimeMillis());
//                            .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg"))); // 添加声音
//                            .setAutoCancel(true);            // 设置点击后对通知取消

                    mBuilder.setContentIntent(pendingIntent);
                    Notification notification = mBuilder.build();
                    manager.notify(1,notification);

                } else if (i==4){  // 网络

                    /* 原生 HttpURLConnection */
                    /*
                    new  Thread() {
                        @Override
                        public void run() {

                            String path = "http://121.40.84.157/download_Plist.php";
                            HttpURLConnection connection = null;

                            try {
                                URL url = new URL(path);
                                connection = (HttpURLConnection) url.openConnection();
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
                                    showResponse(response.toString());
                                }

                            } catch (Exception e){e.printStackTrace();}
                            finally {
                                if (connection != null)
                                    connection.disconnect(); // 关闭连接
                            }
                 )};
                        */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                         /* OkHttp3 */
                         /*
                         * 1.创建 OkHttpClient client 实例
                         * 2.创建 Request request 实例
                         * 3.client.newcall(request) 的 execute() 方法 发送请求并获取 response（返回数据）
                         *
                         * POST 和 GET 的 区别
                         * POST request请求需要添加 提交数据 RequestBody body
                         * */
                        OkHttpClient client = new OkHttpClient();
                        String url = "http://121.40.84.157/download_Plist.php";

//                        try {

                            // post
//                            RequestBody body = new FormBody.Builder()
//                                    .add("username", "admin")
//                                    .add("password", "1234567")
//                                    .build();
//
//                            Request request1 = new Request.Builder()
//                                    .url(url)
//                                    .post(body)
//                                    .build();

//                            Response response1 = client.newCall(request1).execute();
//                            if (response1.isSuccessful()) {
//                                String string = response1.body().string();
//                            }

                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();

                            // 同步
//                            Response response = client.newCall(request).execute();
//                            if (!response.isSuccessful()) throw new IOException("Unexpected code" + response);
//
//                            Headers responseHeaders = response.headers();
//                            for (int i = 0; i < responseHeaders.size(); i++) {
//                                Log.i(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                            }
//                        } catch (Exception e) {e.printStackTrace();}
//                        finally {
//
//                        }

                        // 异步
                        client.newCall(request).enqueue(new Callback(){

                            // 响应回调
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                Gson gson = new Gson();
                                List<ProjectModel> projects = gson.fromJson(response.body().string(), new TypeToken<List<ProjectModel>>(){}.getType());

                                for (ProjectModel model : projects)
                                {
                                    Log.i(TAG, "onItemClick: " + model.getName() + "project-id" + model.getProject_id());     // 返回值
                                }

                            }

                            // 错误处理
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                        });


                    }
                }).start();


                }else if (i==5){  // 视频播放 音频 VideoView MediaPlayer
                    Uri videoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dfsd);

                }else if (i==6){  // http




                }



            }
        });


        //        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,data);
//        ListView listView = (ListView) findViewById(R.id.main_listview);
//        listView.setAdapter(adapter);

        // 敏感操作，需要声明权限
        // 广播 BroadcastReceiver
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");  // 需要监听的广播action
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver, intentFilter);            // 动态注册广播：registerReceiver，和销毁广播成对出现：unregisterReceiver



        /*本地广播
        * 使用LocalBroadcastManager 来管理广播 .getInstance()获取实例
        *
        *
        * */




        /*自定义广播
        * 定义接收者，即继承 BroadcastReceiver抽象类，实现方法onReceive（）
        * 定义广播action名  private final String ACTION_BOOT = "com.example.sunny.fdgfg.MY_BROADCAST";
        * 广播action判断，事件处理。
        * 注册广播
        * 发送广播 标准广播：sendBroadcast 有序广播： sendOrderedBroadcast
        */

        /*
        networkChangeReceiver = new NetworkChangeReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(networkChangeReceiver.ACTION_BOOT);  // 需要监听的广播action
        intentFilter.setPriority(100);                                   // 设置优先级  -1000 ~ 1000
        registerReceiver(networkChangeReceiver, intentFilter);
        */

        // 有序广播
//        sendOrderedBroadcast(new Intent(),null);

        // 标准广播
//        String cationName = networkChangeReceiver.ACTION_BOOT;
//        sendBroadcast(new Intent(cationName));

    }

    /*在UI线程 操作请求返回值*/
    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // UI操作 TextView.setTitle = response;
                Log.i(TAG, "showResponse: " + response);
            }
        });
    }

    private void call() {
        try {
            Intent intent1 = new Intent(Intent.ACTION_CALL);
            intent1.setData(Uri.parse("tel:10086"));
            startActivity(intent1);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void deleteContentResolver() {
        Uri uri = Uri.parse("conent://com.example.app.provider/table1");
        ContentValues values = new ContentValues();
        values.put("column1", "text");
        values.put("column2", 56);
//        getContentResolver().delete(uri,values);
    }

    private void updateContentResolver() {
        Uri uri = Uri.parse("conent://com.example.app.provider/table1");
        ContentValues values = new ContentValues();
        values.put("column1", "text");
        values.put("column2", 56);
//        getContentResolver().update(uri,values);
    }

    private void insertContentResolver() {
        Uri uri = Uri.parse("conent://com.example.app.provider/table1");
        ContentValues values = new ContentValues();
        values.put("column1", "text");
        values.put("column2", 56);
//        getContentResolver().insert(uri,values);
    }

    private void queryContentResolver() {

        Cursor cursor = null;
        try {
            ContentResolver contentResolver = getContentResolver(); // 实例

            Uri uri = Uri.parse("conent://com.example.app.provider/table1");
            uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            cursor = contentResolver.query(uri,null,null,null,null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String column1 = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    int column2 = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    Toast.makeText(this, "query number: " + column2 + " displayName: " + column1, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            switch (requestCode) {
                case 1:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        call();
                    }else {
                        Toast.makeText(this, "1 You denied the permission", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        queryContentResolver();
                    }else {
                        Toast.makeText(this, "2 You denied the permission", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
            }

    }
}

