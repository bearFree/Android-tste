package com.example.sunny.fdgfg;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class FDPlayerActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "FDPlayerActivity";
    private static final String filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Video/dfsd.mp4";
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdplayer);

        configure(savedInstanceState);

        awakeFromXml();

    }


    private void awakeFromXml() {

        Button btn = (Button) findViewById(R.id.button_start);
        btn.setOnClickListener(this);

        Button btn1 = (Button) findViewById(R.id.button_pause);
        btn1.setOnClickListener(this);

        Button btn2 = (Button) findViewById(R.id.button_resume);
        btn2.setOnClickListener(this);

        Uri videoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dfsd);
        Log.e(TAG, filePath);
//        Uri videoPath = Uri.parse(filePath);
        //        videoView1.setVideoPath(Uri.parse("http://www.qeebu.com/newe/Public/Attachment/99/52958fdb45565.mp4").toString());

        VideoView videoView1 = (VideoView) findViewById(R.id.videoView);
        videoView1.setMediaController(new MediaController(this));
        videoView1.setVideoURI(videoPath);
        videoView1.start();
        videoView1.requestFocus();

        videoView = videoView1;
    }

    private void configure(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String camType = savedInstanceState.getString("modeType");
            Log.d(TAG, "onCreate: " + camType);
        }

        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        int id3 = intent.getIntExtra("id",0);

        Log.d(TAG, "onCreate: " + msg + id3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start:
//                ActivityCollector.finsinAPP();
                videoView.start();
                break;
            case R.id.button_pause:
                videoView.pause();
                break;
            case R.id.button_resume:
                videoView.resume();
                break;
            default:
                break;
        }

    }

    // 重写 onBackPressed（），监听Back键
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return", "Hello FirstActivity");
        setResult(RESULT_OK, intent);
        finish();
    }

    // 只有在系统即将要自动清理销毁Activity或Fragment前才会调用, 比如, 用于保存临时数据，恢复现场
    /*
    * 1, 由于重力感应 手机从竖屏变为横屏,
    * 2, 手机点击Home键和长按Home键
    * 3, 点击电源键锁屏时
    * 4, 从当前Activity跳到另一个Activity
    * 5, 应用内存不足即将自动销毁时等情况
    * */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        String tempData = "isCamModePreview";
        outState.putString("modeType", tempData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: dealloc");
    }

}
