package com.example.homework3

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter

class SimpleAdapter_act : AppCompatActivity() {
    private val imgIds = intArrayOf(R.drawable.img1, R.drawable.img2, R.drawable.img3)
    private val names = arrayOf("白远希", "wnibs", "teacher")
    private val ids = arrayOf("19301139", "19301001", "18002245")
    private val itemlists = ArrayList<HashMap<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_adapter_layer)
        initList()
        val simpleAdapter = SimpleAdapter(this, itemlists, R.layout.item_layer,
                arrayOf("imgId", "name", "id"),
        intArrayOf(R.id.imageview1, R.id.textview1, R.id.textview2))
        val list = findViewById<ListView>(R.id.listView3)
        // 为ListView设置Adapter
        list.adapter = simpleAdapter

    }

    private fun initList(){

        for (i in names.indices){
            var hashmap:HashMap<String,Any> = HashMap<String, Any>()
            hashmap.put("imgId", imgIds[i]);
            hashmap.put("name", names[i]);
            hashmap.put("id", ids[i]);
            itemlists.add(hashmap);
        }



    }
}
