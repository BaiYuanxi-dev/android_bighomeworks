package com.example.homework8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private Button saveData;
    private Button showData;
    private TextView edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveData = (Button) findViewById(R.id.saveBut);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("data",
                        MODE_PRIVATE).edit();
                editor.putString("name", "白远希");
                editor.putString("id", "19301139");
                editor.commit();
                Toast.makeText(MainActivity.this, "Save succeeded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        edit = (TextView) findViewById(R.id.textView);
//
//        if (!TextUtils.isEmpty(inputText)) {
////            edit.setText(inputText);
////            edit.setSelection(inputText.length());
//            Toast.makeText(this, "Restoring succeeded",
//                    Toast.LENGTH_SHORT).show();
//        }

        showData = (Button) findViewById(R.id.showBut);
        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("data",
                        MODE_PRIVATE);
                String name = pref.getString("name", "");
                String id = pref.getString("id", "");
                edit.setText("name: " + name + "\n id: " + id);
                Toast.makeText(MainActivity.this, "Show succeeded",
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
//    public String load() {
//        FileInputStream in = null;
//        BufferedReader reader = null;
//        StringBuilder content = new StringBuilder();
//        try {
//            in = openFileInput("data");
//
//            reader = new BufferedReader(new InputStreamReader(in));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                content.append(line);
//            }
//            System.out.println(line);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return content.toString();
//    }


}