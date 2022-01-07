package com.example.client.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client.R;
import com.example.client.utils.ApplicationUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ApplicationUtil appUtil;

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private TextView lastPassword;
    private TextView newPassword;
    private TextView newnewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        appUtil = (ApplicationUtil) ChangePasswordActivity.this.getApplication();
        socket = appUtil.getSocket();

        mHandlerThread = new HandlerThread("CHANGEPASSWORD", 5);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());

        lastPassword = findViewById(R.id.lastPassword);
        newPassword = findViewById(R.id.newPassword);
        newnewPassword = findViewById(R.id.newnewPassword);

        Button subBut = findViewById(R.id.change_password_button);
        subBut.setOnClickListener(this);

        mHandler.post(Receiver);
    }

    @Override
    public void onClick(View view) {
        String lastPassword1 = lastPassword.getText().toString().trim();
        String newPassword1 = newPassword.getText().toString().trim();
        String newnewPassword1 = newnewPassword.getText().toString().trim();
        if(lastPassword1.equals("") || newPassword1.equals("") || newnewPassword1.equals("")){
            Toast.makeText(ChangePasswordActivity.this,"====请输入完整信息===", Toast.LENGTH_SHORT).show();
            return;
        }
        if(lastPassword1.equals(newPassword1)){
            Toast.makeText(ChangePasswordActivity.this,"====新旧密码不能相同===", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!newPassword1.equals(newnewPassword1)){
            Toast.makeText(ChangePasswordActivity.this,"====新密码两次输入不一致===", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (view.getId()) {
            case R.id.change_password_button: {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String processedData = "CHANGEPASSWORD_";
                            processedData += newPassword1;
                            processedData += "#";
                            processedData += lastPassword1;
                            sendMyMessage(processedData);
                            System.out.println("发送修改密码");
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
                    if (feedback.equals("CHANGEPASSWORD_YES")) {
                        System.out.println("yes");
                        Message msg = new Message();
                        msg.what = 0;
                        showTips.sendMessage(msg);
                    } else if (feedback.equals("CHANGEPASSWORD_NO")) {
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