package com.example.homework6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    /**
     * NotificationManager 服务，用于管理通知
     */
    private NotificationManager nm;
    /**
     * 通知对象
     */
    private NotificationCompat.Builder mBuilder;
    /**
     * 为响应创建通知的按钮
     */
    private Button btnSend;
    private static final String CHANNEL_ID = "123";
    private static final String CHANNEL_NAME = "MyChannel";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    /**
     * 设置通知
     */
    private void setupViews() {
        btnSend = (Button) findViewById(R.id.button1);
        //获取系统服务
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //构建一个通知渠道
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }
        //创建一个通知对象并设置其属性
        mBuilder =new NotificationCompat.Builder(
                this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img1)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.img1))
                .setContentTitle("My notification")
                .setContentText("Hello World!");
        //创建通知响应（点击通知）后的操作
        Intent resultIntent = new Intent(this, MyTestActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        /**
         * 按钮响应
         */
        btnSend.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //创建通知
                nm.notify(123, mBuilder.build());
            }

        });
    }
}

