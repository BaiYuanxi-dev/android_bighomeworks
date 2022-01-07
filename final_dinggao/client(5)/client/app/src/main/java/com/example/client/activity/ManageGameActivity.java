package com.example.client.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client.R;
import com.example.client.io.agora.openlive.activities.BroadcastActivity;
import com.example.client.user.SystemBarTintManager;
import com.example.client.utils.ApplicationUtil;
import com.example.client.utils.ExpandAnimationView;
import com.example.client.utils.MyRVAdapter;
import com.example.client.utils.Utils;
import com.example.client.utils.my1RVAdapterNew;
import com.example.client.utils.myRVAdapterNew;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youth.banner.Banner;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageGameActivity extends AppCompatActivity {
    private DataInputStream dis;
    private DataOutputStream dos;
    private ApplicationUtil appUtil;

    private String matchid;
    private String feedback;
    private RecyclerView rvTest;
    private myRVAdapterNew adapter;


    //底栏
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_game);
        setContentView(R.layout.activity_manage_game);


        //隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        matchid = getIntent().getExtras().getString("1");
        feedback = getIntent().getExtras().getString("2");


        //加上就会崩，实际不能点，就摆个样子得了
        //底栏
        mNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigationView.setSelectedItemId(R.id.navigation_3);

        rvTest = (RecyclerView) findViewById(R.id.rv_test);
        rvTest.setLayoutManager(new LinearLayoutManager(this));

        appUtil = (ApplicationUtil) ManageGameActivity.this.getApplication();


        adapter = new myRVAdapterNew(this, feedback);
        rvTest.setAdapter(adapter);


        //点击响应
        adapter.setOnItemClickListener(new myRVAdapterNew.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(),
                        "click: " + position, Toast.LENGTH_SHORT).show();

                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            int gameNum = position + 1;
                            sendMyMessage("GETTEAM#" + matchid + "," + gameNum);
                            dis = appUtil.getDis();
                            String gameinfo = dis.readUTF();
                            Intent i = new Intent(ManageGameActivity.this, RecordGameActivity.class);
                            Bundle b = new Bundle();
                            b.putString("1", gameinfo);
                            b.putString("2", matchid);
                            b.putString("3", gameNum + "");
                            i.putExtras(b);
                            startActivity(i);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(),
                        "long click: " + position, Toast.LENGTH_SHORT).show();
            }
        });


        /**
         * 加载轮播图
         *
         * @param mainLayout
         */
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.pic1);
        images.add(R.drawable.pic2);
        images.add(R.drawable.pic3);
        Banner banner = (Banner) findViewById(R.id.banner);
        Utils.initBanner(this, banner, images, 15);

//        ExpandAnimationView expandAnimationView = findViewById(R.id.add);
    }

    public void sendMyMessage(String message) throws IOException {
        dos = appUtil.getDos();
        dos.writeUTF(message);
        dos.flush();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_1: {   //主页
                    Intent i = new Intent(ManageGameActivity.this, AudienceMainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("key", "manager");
                    i.putExtras(b);
                    startActivity(i);
                    break;
                }
                case R.id.navigation_2: {   //直播
                    Intent intent = new Intent(ManageGameActivity.this, BroadcastActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.navigation_3: {   //赛事
                    Intent i = new Intent(ManageGameActivity.this, ManagerAllGameActivity.class);
                    Bundle b = new Bundle();
                    b.putString("key", "manager");
                    i.putExtras(b);
                    startActivity(i);
                    break;
                }
                case R.id.navigation_4: {   //我的
                    Intent i = new Intent(ManageGameActivity.this, MyActivity.class);
                    startActivity(i);
                    break;
                }
                default:{
                    break;
                }
            }

            // 默认 false
            // false 的话 下面颜色不会变化
            return false;
        }
    };


//    private void setTranslucentStatus(boolean on) {
//        Window win = getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }
}
