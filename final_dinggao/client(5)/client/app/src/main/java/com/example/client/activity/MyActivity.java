package com.example.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client.R;
import java.io.DataOutputStream;
import java.io.IOException;

import com.example.client.utils.ApplicationUtil;
import com.example.client.utils.SelectPicPopupWindow;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private SelectPicPopupWindow menuWindow;
    private ApplicationUtil appUtil;

    //底栏
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        appUtil = (ApplicationUtil) MyActivity.this.getApplication();

        TextView changeHead = (TextView) findViewById(R.id.changeHead);
        TextView changeName = (TextView) findViewById(R.id.changeName);
        TextView changePassword = (TextView) findViewById(R.id.changePassword);
        TextView feedback = (TextView) findViewById(R.id.feedback);
        TextView aboutUs = (TextView) findViewById(R.id.aboutUs);
        TextView exit = (TextView) findViewById(R.id.exit);

        changeHead.setOnClickListener(this);
        changeName.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        feedback.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        exit.setOnClickListener(this);

        mNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mNavigationView.setSelectedItemId(R.id.navigation_4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeHead: {
                selectImgs();
            }
            break;
            case R.id.changeName: {
                Intent i = new Intent(MyActivity.this, ChangeNameActivity.class);
                startActivity(i);
            }
            break;
            case R.id.changePassword: {
                Intent i = new Intent(MyActivity.this, ChangePasswordActivity.class);
                startActivity(i);
            }
            break;
            case R.id.feedback: {
                Intent i = new Intent(MyActivity.this, FeedBackActivity.class);
                startActivity(i);
            }
            break;
            case R.id.aboutUs: {
                Intent i = new Intent(MyActivity.this, AboutUsActivity.class);
                startActivity(i);
            }
            break;
            case R.id.exit: {
                //需要改一下
//                sendMyMessage("RESET");
//                Intent i = new Intent(MyActivity.this, LoginActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
            }
            break;
            default:
                break;
        }
    }

    private void selectImgs() {
        menuWindow = new SelectPicPopupWindow(MyActivity.this, itemsOnClick);
        //设置弹窗位置
        menuWindow.showAtLocation(MyActivity.this.findViewById(R.id.my), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void sendMyMessage(String message) {
        new Thread() {
            @Override
            public void run() {
                try {
                    DataOutputStream dos = appUtil.getDos();
                    dos.writeUTF(message);
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.item_popupwindows_camera: //点击拍照按钮
                    break;
                case R.id.item_popupwindows_Photo: //点击从相册中选择按钮
                    break;
                default:
                    break;
            }
        }
    };
}