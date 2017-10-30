package com.example.sunny.fdgfg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

// 主入口函数
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

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
        unregisterReceiver(networkChangeReceiver);
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
            }
        });
        //        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,data);
//        ListView listView = (ListView) findViewById(R.id.main_listview);
//        listView.setAdapter(adapter);

        // 敏感操作，需要声明权限
        // 广播 BroadcastReceiver
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");  // 需要监听的广播action
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);            // 动态注册广播：registerReceiver，和销毁广播成对出现：unregisterReceiver


    }

    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();

            if (info != null && info.isAvailable()) {
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "network is unAvailable", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
