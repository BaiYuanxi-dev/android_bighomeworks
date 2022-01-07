package io.agora.openlive.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.agora.openlive.R;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.in_broadcast_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, BroadcastActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void onOpenBroadcastClick(View view) {
        Intent intent = new Intent(MainActivity.this, BroadcastActivity.class);
        startActivity(intent);
    }
//    android:onClick="onSettingClicked
}