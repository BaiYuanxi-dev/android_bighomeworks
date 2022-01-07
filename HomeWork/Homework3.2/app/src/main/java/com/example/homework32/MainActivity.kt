package com.example.homework32

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button1:Button = findViewById(R.id.button1)
        val button2:Button = findViewById(R.id.button2)
//        button1.setOnClickListener {
//            val intent = Intent(this, ArrayAdapter_act::class.java)
//            startActivity(intent)
//        }
//
//        button2.setOnClickListener {
//            val intent = Intent(this, SimpleAdapter_act::class.java)
//            startActivity(intent)
//        }
    }
}