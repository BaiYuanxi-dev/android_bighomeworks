package com.example.client.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
//import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client.R;
import com.example.client.utils.ApplicationUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChangeNameActivity extends AppCompatActivity implements View.OnClickListener {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ApplicationUtil appUtil;

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private String content;

    private TextView change_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        appUtil = (ApplicationUtil) ChangeNameActivity.this.getApplication();
        socket = appUtil.getSocket();

        change_name = findViewById(R.id.change_name);

        Button subBut = findViewById(R.id.change_name_button);
        subBut.setOnClickListener(this);

        mHandlerThread = new HandlerThread("CHANGENAME", 5);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());

        mHandler.post(Receiver);
    }

    @Override
    public void onClick(View view) {
        content = change_name.getText().toString().trim();
        if(content.equals("")){
            Toast.makeText(ChangeNameActivity.this,"====请输入新昵称===", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.change_name_button: {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            content = change_name.getText().toString().trim();
                            String processedData = "CHANGENAME_";
                            processedData += content;
                            sendMyMessage(processedData);
                            System.out.println("发送新昵称");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            break;
            default:
                break;
        }
    }

    private Runnable Receiver = new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    dis = appUtil.getDis();
                    String feedback = dis.readUTF();
                    System.out.println(feedback);
                    if (feedback.equals("CHANGENAME_YES")) {
                        System.out.println("yes");
                        Message msg = new Message();
                        msg.what = 0;
                        showTips.sendMessage(msg);
                    } else if (feedback.equals("CHANGENAME_NO")) {
                        System.out.println("no");
                        Message msg = new Message();
                        msg.what = 1;
                        showTips.sendMessage(msg);
                    } else if (feedback.equals("ENDACTIVITY")) {
                        System.out.println("break");
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private final Handler showTips = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:{
                    showFeedbackDialog("修改成功");
                    break;
                }
                case 1:{
                    showFeedbackDialog("修改失败");
                    break;
                }
                default:{
                    break;
                }
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        sendMyMessage("ENDACTIVITY");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            this.finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 简单消息提示框
    private void showFeedbackDialog(String feedback) {
        new AlertDialog.Builder(this)
                .setTitle("TIPS")
                .setMessage(feedback)
                .setPositiveButton("确定", null)
                .show();
    }

    public void sendMyMessage(String message) throws IOException {
        dos = appUtil.getDos();
        dos.writeUTF(message);
        dos.flush();
    }
}