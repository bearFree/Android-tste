package com.example.sunny.fdgfg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherDetailActivity extends BaseActivity implements View.OnClickListener {




    private TextView isTextView;
    private ProgressBar progressBar;

    private static final String TAG = "TeacherDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("UIWidget");

        // 从Intent获取数据
        int imageId = getIntent().getIntExtra("teacher_image", 0);
        String desc = getIntent().getStringExtra("teacher_desc");

        Log.e("pushInfo",(String) String.valueOf(imageId));
        // 获取特定的视图
        ImageView imageView = (ImageView) findViewById(R.id.teacher_large_imageView);
        final TextView textView = (TextView) findViewById(R.id.teacher_desc_textView);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar_1);

        // 根据数据设置视图展现
        imageView.setImageResource(imageId);
        textView.setText(desc);
        isTextView = textView;

        /* button 点击事件
        * 1.匿名类注册监听器 （类似iOS block回调）
        * 2.实现接口的方式   （类似iOS 遵守协议，实现接口）
        * */
        //
        Button button = (Button) findViewById(R.id.button_4);
        button.setOnClickListener(this);
//        button.setVisibility(View.GONE);

        // 1.匿名类注册监听器 （类似iOS block回调）
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView.setText("Tish is TextView");
//            }
//        });




        // 多线程
        // AsyncTask --- tets
        TextView title = (TextView) findViewById(R.id.barTitle);
        myAsyncTask asyncTask = new myAsyncTask(title,progressBar);
        asyncTask.execute(1000);


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: dealloc");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String str = data.getStringExtra("data_return");
                    Log.d(TAG, "startActivityForResult: " + str);

                }
        }
    }

    // 2.实现接口的方式   （类似iOS 遵守协议，实现接口）
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_4:
                isTextView.setText("button delegate change textView");
                if (progressBar.getVisibility() == View.VISIBLE)
                    progressBar.setVisibility(View.INVISIBLE);
                else
                    progressBar.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }

    /*
    * 复用的逻辑进行封装*/
    private static void pushActivity(Context context,String msg,int id) {
        Intent intent = new Intent(context, FDPlayerActivity.class);
        intent.putExtra("msg",msg);
        intent.putExtra("id",id);

        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.add_item:
                pushActivity(this,"is secondActivity push", 2);
                break;
            case R.id.remove_item:

                // 调起系统游览器
//                String abaidu = "http://www.baidu.com";
//                Uri uri = Uri.parse(abaidu);
//                Intent intent = new Intent (Intent.ACTION_VIEW,uri);
//                startActivity(intent);

                // 调起系统拨号
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
                break;
            case R.id.test_item:

                /*
                * Intent 是Android组件之间交互的一种重要方式
                * activity 之间跳转；启动服务；发送广播等场景
                * Intent 分为两种，显式和隐式
                * Activity 中statrActivity() 启动活动，传入一个构建好的Intent
                * */

                // 显式调用 及 通过Intent传递数据
//                String str = "Hello SecondActivity";
                Intent intent1 = new Intent(TeacherDetailActivity.this, FDPlayerActivity.class);
//                intent1.putExtra("extra_str", str);
//                this.startActivity(intent1);

                // 显式调用 及 期望得到上个活动的数据回调
                startActivityForResult(intent1, 1);

                // 隐式调用。 Intent并不明确指出目标活动，交由系统通过条件分析目的活动。
                // 筛选目的活动的条件是： 一系列的action和category。满足Intent的activiry即为目的活动
//                Intent ntent = new Intent("com.example.activitytest.ACTION_START");
//                ntent.addCategory("com.example.activitytest.MY_CATEGORY");
//                this.startActivity(ntent);

                break;
        }

        return true;
    }

}
