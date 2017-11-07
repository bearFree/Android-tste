package com.example.sunny.fdgfg;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by sunny on 2017/9/4.
 */

public class BaseActivity extends AppCompatActivity {

    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (networkChangeReceiver != null)
            unregisterReceiver(networkChangeReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(networkChangeReceiver.ACTION_FINISHAPP);  // 注册广播监听器
        registerReceiver(networkChangeReceiver,intentFilter);

//        intentFilter.setPriority(100);
//        localBroadcastManager.registerReceiver(networkChangeReceiver,intentFilter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCollector.addActivity(this);
        Log.d("BaseActivity", getClass().getSimpleName());
    }
}

