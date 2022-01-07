package com.example.homework5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //进度条对话框
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            Handler handler;
            @Override
            /*
                点击按钮后加载对话框并执行后台程序
             */
            public void onClick(View view) {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("任务执行...");
                dialog.setMax(100);//设置任务条最大值为100
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.show();
                handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg){
                        int progress = Integer.parseInt(msg.obj.toString());
                        dialog.setProgress(progress);
                        if(progress == 100){
                            dialog.dismiss();
                        }
                    }
                };

                /*
                    执行后台线程
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            final int progress = i;
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //把当前程序执行进度传到Handler对象中
                            Message msg = handler.obtainMessage(1, progress);
                            handler.sendMessage(msg);
                        }
                    }
                }).start();
            }
        });
    }
}