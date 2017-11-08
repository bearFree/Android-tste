package com.example.sunny.fdgfg;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.xml.sax.helpers.DefaultHandler;

public class MyService extends Service {

    class DownloadBinder extends Binder{

        public void startDownload(){
            Log.i(TAG, "startDownload: ");
        }

        public int getProgress(){
            Log.i(TAG, "getProgress: ");
            return 0;
        }

        public void endDownload(){
            Log.i(TAG, "endDownload: ");
        }
    }

    private DownloadBinder binder = new DownloadBinder();

    private static final String TAG = "MyService";
    /* 组件 Service
    * Service均需要在 AndroidManifest.xml 内注册才能生效
    * Service 启动和停止,借助 Intent 实现  bind start 两种方式
    *
    * */
    public MyService() {
    }

    // 创建service时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: 创建service时调用");

        // 创建前台服务
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Thsi is service")
                .setContentText("This is content")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);

    }

    // service 每次启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: 启动service时调用");
        return super.onStartCommand(intent, flags, startId);
    }

    // 销毁service时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy:  销毁service时调用");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return binder;
    }
}
