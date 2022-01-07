package com.example.homework11

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        dial.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel: 18800120157");
            startActivity(intent);
        }

        phone.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE), 1)
            } else {
                call();
            }

        }
        webBut.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW);
            intent.data = Uri.parse("https://www.baidu.com");
            startActivity(intent);
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1->{
                if(grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call();
                }
                else{
                    Toast.makeText(this,"You denied the permission",
                        Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private fun call(){
        try{
            val intent = Intent(Intent.ACTION_CALL);
            intent.data = Uri.parse("tel:13483322234");
            startActivity(intent);
        } catch(e:SecurityException){
            e.printStackTrace();
        }
    }
}