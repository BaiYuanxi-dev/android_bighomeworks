package com.example.homework2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linear_but.setOnClickListener {
            var intent = Intent(this, LinearLayoutTest::class.java);
            startActivity(intent);
        }
        
        relative_but.setOnClickListener {
            var intent = Intent(this, RelativeLayoutTest::class.java);
            startActivity(intent);
        }
        table_but.setOnClickListener {
            var intent = Intent(this, TableLayoutTest::class.java);
            startActivity(intent);
        }
        constraint_but.setOnClickListener {
            var intent = Intent(this, ConstraintLayoutTest::class.java);
            startActivity(intent);
        }
    }
}