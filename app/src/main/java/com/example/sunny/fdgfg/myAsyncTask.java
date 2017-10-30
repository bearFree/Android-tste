package com.example.sunny.fdgfg;

import android.os.AsyncTask;
import android.os.Process;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by sunny on 2017/10/29.
 */

public class myAsyncTask extends AsyncTask<Integer,Integer,String> {

    private TextView barTitle;
    private  ProgressBar bar;

    public myAsyncTask(TextView title, ProgressBar bar)
    {
        super();
        this.barTitle = title;
        this.bar = bar;
    }

    //该方法不运行在UI线程中,主要用于异步操作,通过调用publishProgress()方法
    //触发onProgressUpdate对UI进行操作
    @Override
    protected String doInBackground(Integer... params) {

        int i = 0;
        for (i = 10;i <= 100;i+=10)
        {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return  i + params[0].intValue() + "";

    }

    //该方法运行在UI线程中,可对UI控件进行设置
    @Override
    protected void onPreExecute() {
        this.barTitle.setText("开始执行异步线程~");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int value = values[0];
        this.bar.setProgress(value);
    }

}
