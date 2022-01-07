package com.example.homework6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MyBindService.LocalBinder myBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("connect");
            myBinder = (MyBindService.LocalBinder) service;
            myBinder.startDownload();
            myBinder.getProgress();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.start_service_but);
        Button button2 = (Button) findViewById(R.id.start_intent_service_but);
        Button button3 = (Button) findViewById(R.id.start_bind_service_but);

        button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);
//                stopService(intent);
            }

        });

        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                startService(intent);
//                stopService(intent);
            }

        });
        button3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyBindService.class);
                bindService(intent, connection,  BIND_AUTO_CREATE);
                //startService(intent);
//                stopService(intent);
            }

        });
    }
}