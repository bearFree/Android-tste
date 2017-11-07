package com.example.sunny.fdgfg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by sunny on 2017/10/30.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    public final String ACTION_BOOT = "com.example.sunny.fdgfg.MY_BROADCAST";
    // 强制下线
    public final String ACTION_FINISHAPP = "com.example.sunny.fdgfg.MY_BROADCAST_FINISH";

    @Override
    public void onReceive(final Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null && info.isAvailable()) {
            Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "network is unAvailable", Toast.LENGTH_SHORT).show();
        }

        if (ACTION_BOOT.equals(intent.getAction())) {
            Toast.makeText(context, "收到自定义广播", Toast.LENGTH_SHORT).show();
            abortBroadcast(); // （有序广播）截断广播，低优先级的接收者不再受到广播
        }

        if (ACTION_FINISHAPP.equals(intent.getAction())) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("警告：");
            builder.setMessage("您的账号在别处登录，请重新登录！");
            builder.setCancelable(false);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.finishAPP();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            builder.show();

        }
    }
}
