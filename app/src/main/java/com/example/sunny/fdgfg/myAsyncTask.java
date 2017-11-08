package com.example.sunny.fdgfg;

import android.os.AsyncTask;
import android.os.Process;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by sunny on 2017/10/29.
 */

public class myAsyncTask extends AsyncTask<Integer,Integer,String> {

    /*
    * class AsyncTask<Params, Progress, Result> 泛型参数
    * Params 在执行 AsyncTask 时需要传入的参数，可用于在后台任务中执行
    * Progress 用于显示执行进度
    * Result 返回结果 泛型
    *
    * */
    private TextView barTitle;
    private  ProgressBar bar;

    public myAsyncTask(TextView title, ProgressBar bar)
    {
        super();
        this.barTitle = title;
        this.bar = bar;
    }

    //该方法运行在UI线程中,在执行前调用，用于界面初始化
    @Override
    protected void onPreExecute() {
        this.barTitle.setText("开始执行异步线程~");
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

    /*更新UI
    * publishProgress()调用后，执行*/
    @Override
    protected void onProgressUpdate(Integer... values) {
        int value = values[0];
        this.bar.setProgress(value);
    }
    // doInBackground 执行完return 后调用
    @Override
    protected void onPostExecute(String s) {
        this.barTitle.setText("异步线程执行完毕~");
    }

}
