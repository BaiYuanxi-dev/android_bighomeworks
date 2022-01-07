package com.example.client.activity;


import android.content.Intent;
import android.os.Looper;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client.R;
import com.example.client.utils.ApplicationUtil;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Socket socket;

    private DataInputStream dis;
    private DataOutputStream dos;
    private ApplicationUtil appUtil;

    private EditText userID;
    private EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button butLoginSend = findViewById(R.id.butLoginSend);
        TextView loginHintView = (TextView) findViewById(R.id.LoginTipsView);

        butLoginSend.setOnClickListener(this);
        loginHintView.setOnClickListener(this);

        userID = findViewById(R.id.LoginID);
        userPassword = findViewById(R.id.LoginPassword);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        appUtil = (ApplicationUtil) LoginActivity.this.getApplication();
        try {
            appUtil.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        socket = appUtil.getSocket();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.butLoginSend:
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String temp = dataProcess();
                            sendMyMessage(temp);
                            dis = appUtil.getDis();
                            String feedback = dis.readUTF();
                            receievFeedback(feedback);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.LoginTipsView: {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
            break;
            default:
                break;
        }
    }

    public void sendMyMessage(String message) throws IOException {
        dos = appUtil.getDos();
        dos.writeUTF(message);
        dos.flush();
    }

    //接受服务器消息
    public void receievFeedback(String feedback) throws IOException {
        Looper.prepare();
        if (feedback.indexOf("YES") != -1) {
            Intent i = new Intent(LoginActivity.this, AudienceMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle b = new Bundle();
            if (feedback.indexOf("manager") != -1) {
                b.putString("key", "manager");
                i.putExtras(b);
            } else if (feedback.indexOf("audience") != -1) {
                b.putString("key", "audience");
                i.putExtras(b);
            }
            startActivity(i);
            finish();
        } else if (feedback.indexOf("REPEAT") != -1) {
            showFeedbackDialog("账号已登陆");
        } else {
            showFeedbackDialog("登录失败");
        }
        Looper.loop();
    }

    //处理登录数据
    public String dataProcess() {
        String processedData = "LOGIN_";
        processedData += userID.getText().toString();
        processedData += "#";
        processedData += userPassword.getText().toString();
        System.out.println();
        return processedData;
    }

    // 简单消息提示框
    private void showFeedbackDialog(String feedback) {
        new AlertDialog.Builder(this)
                .setTitle("TIPS")
                .setMessage(feedback)
                .setPositiveButton("确定", null)
                .show();
    }
}