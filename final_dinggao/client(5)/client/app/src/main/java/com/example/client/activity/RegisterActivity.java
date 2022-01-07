package com.example.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client.utils.ApplicationUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import com.example.client.R;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private RadioButton radioButton;
    private RadioGroup radioGroup;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ApplicationUtil appUtil;

    private EditText userID;
    private EditText userPassword;

    private Button register;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        appUtil = (ApplicationUtil) RegisterActivity.this.getApplication();
        socket = appUtil.getSocket();

        userID = findViewById(R.id.PersonName);
        userPassword = findViewById(R.id.Password);

        register = findViewById(R.id.register_btn);
        register.setOnClickListener(this);

        TextView hintView = (TextView) findViewById(R.id.textView3);

        hintView.setOnClickListener(this);

        radioGroup = findViewById(R.id.identityGroup);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView3: {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                break;
            }
            case R.id.register_btn:{
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            dos = appUtil.getDos();
                            dis = appUtil.getDis();
                            String temp = dataProcess();
                            dos.writeUTF(temp);
                            dos.flush();
                            String feedback = dis.readUTF();
                            receievFeedback(feedback);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            default:
                break;
        }
    }

    private String selectRadioBtn(){
        radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        if(radioButton != null) return radioButton.getText().toString();
        else return "";
    }

    //接受服务器消息
    public void receievFeedback(String feedback) throws IOException {
        Looper.prepare();
        if (feedback.indexOf("YES") != -1) {
            showFeedbackDialog("注册成功");
            Intent i = new Intent(RegisterActivity.this, AudienceMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle b = new Bundle();
            if(radioButton.getText().toString().equals("观众")){
                b.putString("key", "audience");
                i.putExtras(b);
            }else if(radioButton.getText().toString().equals("管理员")){
                b.putString("key", "manager");
                i.putExtras(b);
            }
            startActivity(i);
            finish();
        } else if(feedback.indexOf("REPEAT") != -1){
            showFeedbackDialog("该账号已存在");
        } else {
            showFeedbackDialog("注册失败");
        }
        Looper.loop();
    }

    //处理注册数据
    public String dataProcess() {
        String processedData = "REGISTER_";
        processedData += userID.getText().toString();
        processedData += "#";
        processedData += userPassword.getText().toString();
        processedData += "#";
        if (selectRadioBtn().equals("观众")){
            processedData += "audience";
        }else if(selectRadioBtn().equals("管理员")){
            processedData += "manager";
        }
        return processedData;
    }

    // 简单消息提示框
    private void showFeedbackDialog(String feedback){
        new AlertDialog.Builder(this)
                .setTitle("TIPS")
                .setMessage(feedback)
                .setPositiveButton("确定", null)
                .show();
    }

}
