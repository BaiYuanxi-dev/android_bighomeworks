package com.example.client.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.client.R;
import com.example.client.io.agora.openlive.activities.BroadcastActivity;

public class WelcomeActivity extends AppCompatActivity {

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//              Intent intent = new Intent(WelcomeActivity.this, BroadcastActivity.class);
//              startActivity(intent);


//            Intent intent = new Intent(WelcomeActivity.this, NewGame1Activity.class);
//            startActivity(intent);

            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);

//            Intent intent = new Intent(WelcomeActivity.this, AudienceMainActivity.class);
//            startActivity(intent);

            finish();
        }

    };

    final Message message = new Message();
    final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                message.what = 1;
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        thread.start();
    }

}
