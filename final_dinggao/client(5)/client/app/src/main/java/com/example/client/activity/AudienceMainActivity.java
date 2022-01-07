package com.example.client.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.client.R;
import com.example.client.io.agora.openlive.activities.BroadcastActivity;
import com.example.client.news.MainFragment;
import com.example.client.user.SystemBarTintManager;
import com.example.client.utils.ApplicationUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AudienceMainActivity extends AppCompatActivity{

    private DataInputStream dis;
    private DataOutputStream dos;
    private ApplicationUtil appUtil;

    private String identity;

    private MainFragment mainFragment;

    private List<Fragment> fragmentList = new ArrayList<>();

    //设置图片轮播器
    private RollPagerView mRollViewPager;

    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_main);


        //标题栏去除
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        identity = getIntent().getExtras().getString("key");

        appUtil = (ApplicationUtil) AudienceMainActivity.this.getApplication();

        //底栏
        mNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigationView.setSelectedItemId(R.id.navigation_1);

        initroll();
        initFragment();
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

                    break;
                }
                case R.id.navigation_2: {   //直播
                    Intent intent = new Intent(AudienceMainActivity.this, BroadcastActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.navigation_3: {   //赛事
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                sendMyMessage("GETALLGAME");
                                dis = appUtil.getDis();
                                String allgame = dis.readUTF();
                                if(identity.equals("audience")){
                                    Intent i = new Intent(AudienceMainActivity.this, AudienceAllGameActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("1", "audience");
                                    b.putString("2", allgame);
                                    i.putExtras(b);
                                    startActivity(i);
                                }else{
                                    Intent i = new Intent(AudienceMainActivity.this, ManagerAllGameActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("1", "manager");
                                    b.putString("2", allgame);
                                    i.putExtras(b);
                                    startActivity(i);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                }
                case R.id.navigation_4: {   //我的
                    Intent i = new Intent(AudienceMainActivity.this, MyActivity.class);
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

    public void sendMyMessage(String message) throws IOException {
        dos = appUtil.getDos();
        dos.writeUTF(message);
        dos.flush();
    }

    //初始化图片滚播
    private void initroll() {

        mRollViewPager = (RollPagerView) findViewById(R.id.roll_view_pager);

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(2000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestNormalAdapter());

        mRollViewPager.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
    }

    //初始化新闻
    private void initFragment() {
        mainFragment = new MainFragment();
        addFragment(mainFragment);
        showFragment(mainFragment);
    }

    /*添加fragment*/
    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(R.id.main_content, fragment).commit();
            fragmentList.add(fragment);
        }
    }

    /*显示fragment*/
    private void showFragment(Fragment fragment) {
        for (Fragment frag : fragmentList) {
            if (frag != fragment) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(frag).commit();
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.show(fragment).commit();
    }


    //图片滚播配适器
    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
//                R.drawable.campus1,
//                R.drawable.campus2,
//                R.drawable.campus3,
//                R.drawable.campus4,
//                R.drawable.campus6,
                R.drawable.gun1,
                R.drawable.gun2,
                R.drawable.gun3,
                R.drawable.gun4,
                R.drawable.gun5,
        };


        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }


    }
}
