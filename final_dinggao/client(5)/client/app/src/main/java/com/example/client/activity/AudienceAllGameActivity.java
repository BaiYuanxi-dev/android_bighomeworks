package com.example.client.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client.io.agora.openlive.activities.BroadcastActivity;
import com.example.client.user.SystemBarTintManager;
import com.example.client.utils.ApplicationUtil;
import com.example.client.utils.ExpandAnimationView;
import com.example.client.utils.MyRVAdapter;
import com.example.client.utils.Utils;
import com.example.client.utils.myRVAdapterNew;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youth.banner.Banner;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.example.client.R;

public class AudienceAllGameActivity extends AppCompatActivity {

    private String identity;

    private DataInputStream dis;
    private DataOutputStream dos;
    private ApplicationUtil appUtil;

    private String feedback;
    private RecyclerView rvTest;
    private MyRVAdapter adapter;

    //底栏
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_all_game);

        //隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        identity = getIntent().getExtras().getString("1");
        feedback = getIntent().getExtras().getString("2");

        mNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigationView.setSelectedItemId(R.id.navigation_3);

        rvTest = (RecyclerView) findViewById(R.id.rv_test);
        rvTest.setLayoutManager(new LinearLayoutManager(this));

        appUtil = (ApplicationUtil) AudienceAllGameActivity.this.getApplication();

        adapter = new MyRVAdapter(this, feedback);
        rvTest.setAdapter(adapter);

        //点击响应
        adapter.setOnItemClickListener(new MyRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(),
                        "click: " + position, Toast.LENGTH_SHORT).show();
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            int matchid = position + 1;
                            sendMyMessage("GETALLMATCHFORONEGAME#" + matchid);
                            dis = appUtil.getDis();
                            String feedback1 = dis.readUTF();
                            sendMyMessage("GETJIFENBANG#" + matchid);
                            dis = appUtil.getDis();
                            String jifenbang = dis.readUTF();
                            sendMyMessage("GETSHESHOUBANG#" + matchid);
                            dis = appUtil.getDis();
                            String sheshoubang = dis.readUTF();
                            sendMyMessage("GETHONGHUANGPAIBANG#" + matchid);
                            dis = appUtil.getDis();
                            String honghuangpaibang = dis.readUTF();

                            Intent i = new Intent(AudienceAllGameActivity.this, UserShowOneActivity.class);
                            Bundle b = new Bundle();
                            b.putString("1", Integer.toString(position+1));
                            b.putString("2", feedback1);
                            b.putString("3", jifenbang);
                            b.putString("4", sheshoubang);
                            b.putString("5", honghuangpaibang);
                            i.putExtras(b);
                            startActivity(i);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
//                if(identity.equals("audience")){
//                    new Thread(new Runnable(){
//                        @Override
//                        public void run() {
//                            try {
//                                int matchid = position + 1;
//                                sendMyMessage("GETALLMATCHFORONEGAME#" + matchid);
//                                dis = appUtil.getDis();
//                                String feedback1 = dis.readUTF();
//                                sendMyMessage("GETJIFENBANG#" + matchid);
//                                dis = appUtil.getDis();
//                                String jifenbang = dis.readUTF();
//                                sendMyMessage("GETSHESHOUBANG#" + matchid);
//                                dis = appUtil.getDis();
//                                String sheshoubang = dis.readUTF();
//                                sendMyMessage("GETHONGHUANGPAIBANG#" + matchid);
//                                dis = appUtil.getDis();
//                                String honghuangpaibang = dis.readUTF();
//
//                                Intent i = new Intent(AudienceAllGameActivity.this, UserShowOneActivity.class);
//                                Bundle b = new Bundle();
//                                b.putString("1", Integer.toString(position+1));
//                                b.putString("2", feedback1);
//                                b.putString("3", jifenbang);
//                                b.putString("4", sheshoubang);
//                                b.putString("5", honghuangpaibang);
//                                i.putExtras(b);
//                                startActivity(i);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
//                }else{
//                    new Thread(new Runnable(){
//                        @Override
//                        public void run() {
//                            try {
//                                int matchid = position + 1;
//                                sendMyMessage("GETALLMATCHFORONEGAME#" + matchid);
//                                dis = appUtil.getDis();
//                                String feedback1 = dis.readUTF();
//
//                                Intent i = new Intent(AudienceAllGameActivity.this, ManageGameActivity.class);
//                                Bundle b = new Bundle();
//                                b.putString("1", matchid + "");
//                                b.putString("2", feedback1);
//                                i.putExtras(b);
//                                startActivity(i);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
//                }
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

        //添加赛事按钮
        if(identity.equals("manager")){
            ExpandAnimationView expandAnimationView = findViewById(R.id.add);
        }
    }

    public void sendMyMessage(String message) throws IOException {
        dos = appUtil.getDos();
        dos.writeUTF(message);
        dos.flush();
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_1: {   //主页
                    Intent i = new Intent(AudienceAllGameActivity.this, AudienceMainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("key", identity);
                    i.putExtras(b);
                    startActivity(i);
                    break;
                }
                case R.id.navigation_2: {   //直播
                    Intent intent = new Intent(AudienceAllGameActivity.this, BroadcastActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.navigation_3: {   //赛事

                    break;
                }
                case R.id.navigation_4: {   //我的
                    Intent i = new Intent(AudienceAllGameActivity.this, MyActivity.class);
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

}
