package com.example.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.client.R;


import com.example.client.databinding.ActivityUserShowOneGameBinding;
import com.example.client.io.agora.openlive.activities.BroadcastActivity;
import com.example.client.user.AppManager;
import com.example.client.user.BaseActivity;
import com.example.client.user.HonghuangFragment;
import com.example.client.user.JiFenFragment;
import com.example.client.user.SheshouFragment;
import com.example.client.user.StatusBarUtil;
import com.example.client.user.ToastUtil;
import com.example.client.utils.ApplicationUtil;
import com.example.client.utils.ExpandAnimationView;
import com.example.client.utils.my1RVAdapterNew;
import com.example.client.utils.myRVAdapterNew;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youth.banner.listener.OnBannerListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;





import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserShowOneGameActivity extends AppCompatActivity implements View.OnClickListener {

//public class UserShowOneGameActivity extends BaseActivity implements View.OnClickListener {

    private DataInputStream dis;
    private DataOutputStream dos;
    private ApplicationUtil appUtil;

    private String matchid;
    private String feedback;
    private String jifenbang;
    private String sheshoubang;
    private String honghuangpaibang;

    private RecyclerView rvTest;
    private RecyclerView.Adapter adapter;

    private boolean flag_continue_1 = false;
    private boolean flag_continue_2 = false;
    private boolean flag_continue_3 = false;
    private boolean flag_continue_4 = false;

    //??????
    private ActivityUserShowOneGameBinding binding;

    //fragment(???????????????????????????)
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    //fragment ?????????
    private FragmentPagerAdapter mAdapter;


//    /** ?????????????????????????????? */
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d("aa", "The onStop() event");
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Log.d("aa", "The onkeydown1() event");
//            this.finish();
//            return true;
//        }
//        Log.d("aa", "The onkeydown2() event");
//        return super.onKeyDown(keyCode, event);
//    }



    /**
     * ???????????????
     *
     * @param savedInstanceState
     */
//    @Override
    protected void initBinding(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_show_one_game);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        this.finish();
//        return super.onKeyDown(keyCode, event);
//    }

    /**
     * ???????????????
     */
//    @Override
    protected void initView() {
        //setStatusBarDarkTheme(this, true);
        matchid = getIntent().getExtras().getString("1");
        feedback = getIntent().getExtras().getString("2");
        jifenbang = getIntent().getExtras().getString("3");
        sheshoubang = getIntent().getExtras().getString("4");
        honghuangpaibang = getIntent().getExtras().getString("5");

        Log.i("aa", "tag6");


        rvTest = (RecyclerView) findViewById(R.id.rv_test);
        rvTest.setLayoutManager(new LinearLayoutManager(this));

        appUtil = (ApplicationUtil) UserShowOneGameActivity.this.getApplication();

        flag_continue_1 = false;
        flag_continue_2 = false;
        flag_continue_3 = false;
        flag_continue_4 = false;

        adapter = new my1RVAdapterNew(this, feedback);
        rvTest.setAdapter(adapter);


        Log.i("aa", "tag7");


//        adapter = new my1RVAdapterNew(this, feedback);
//        rvTest.setAdapter(adapter);

        Log.i("aa", "tag8");


//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    sendMyMessage("GETALLMATCHFORONEGAME#" + matchid);
//                    dis = appUtil.getDis();
//                    feedback = dis.readUTF();
//
//                    Log.i("aa", feedback);
//                    Log.i("aa", "000" + String.valueOf(flag_continue_1));
//
//                    flag_continue_1 = true;
//                    Log.i("aa", "111" + String.valueOf(flag_continue_1));
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        Log.i("aa", "hear1");
//
//        Log.i("aa", "222" + String.valueOf(flag_continue_1));
//
//        while (flag_continue_1 != true){}
//        //flag_continue = false;
//
//        Log.i("aa", "hear2");
//        adapter = new my1RVAdapterNew(this, feedback);
//        rvTest.setAdapter(adapter);
//
//        Log.i("aa", "hear3");
//
//
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    sendMyMessage("GETJIFENBANG#" + matchid);
//                    dis = appUtil.getDis();
//                    jifenbang = dis.readUTF();
//                    Log.i("aa", jifenbang);
//
//                    flag_continue_2 = true;
//                    Log.i("aa", "333" + String.valueOf(flag_continue_2));
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        Log.i("aa", "hear4");
//
//        Log.i("aa", "444" + String.valueOf(flag_continue_2));
//
//        while (flag_continue_2 != true){}
////        flag_continue = false;
//
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    sendMyMessage("GETSHESHOUBANG#" + matchid);
//                    dis = appUtil.getDis();
//                    sheshoubang = dis.readUTF();
//                    flag_continue_3 = true;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        while (flag_continue_3 != true){}
////        flag_continue = false;
//
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    sendMyMessage("GETHONGHUANGPAIBANG#" + matchid);
//                    dis = appUtil.getDis();
//                    honghuangpaibang = dis.readUTF();
//                    flag_continue_4 = true;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        while (flag_continue_4 != true){}
////        flag_continue = false;

//        jifenbang = "A,????????????,3,3,0,0,14:0,9#A,?????????,3,2,0,1,13:9,6#A,????????????,3,1,0,2,11:13,3#A,????????????,3,0,0,3,1:17,0#&B,????????????,3,3,0,0,13:7,9#B,????????????,3,2,0,1,15:8,6#B,????????????,3,1,0,2,1:4,3#B,???????????????,3,1,0,2,1:4,3#&C,???????????????,3,2,0,1,17:13,6#C,????????????,3,2,0,1,8:8,6#C,????????????,3,2,0,1,8:6,6#C,????????????,3,0,0,3,7:13,0#";
//        sheshoubang = "????????????,????????????,6,0#?????????,????????????,6,0#?????????,????????????,4,0#????????????,????????????,4,1#??????,????????????,3,0#??????,?????????,2,0#?????????,????????????,2,0#";
//        honghuangpaibang = "?????????,????????????,1#??????,????????????,3#????????????,????????????,1#&??????,????????????,1#mouss,???????????????,2#";

        //?????????fragment??????
        mFragments.add(JiFenFragment.newInstance(jifenbang));
        mFragments.add(SheshouFragment.newInstance(sheshoubang));
        mFragments.add(HonghuangFragment.newInstance(honghuangpaibang));
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        binding.fragmentVp.setAdapter(mAdapter);
        // register listener
        binding.fragmentVp.setOffscreenPageLimit(3);
        binding.fragmentVp.addOnPageChangeListener(mPageChangeListener);
        //??????????????????
        binding.llyJfb.setOnClickListener(this);
        binding.llySsb.setOnClickListener(this);
        binding.llyHhp.setOnClickListener(this);
    }



    public void sendMyMessage(String message) throws IOException {
        dos = appUtil.getDos();
        dos.writeUTF(message);
        dos.flush();
    }


//    /**
//     * ???????????????
//     */
//    @Override
//    protected void initData() {
//
//    }

    /**
     * ????????????
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lly_jfb:
                tabChange(0);
                break;
            case R.id.lly_ssb:
                tabChange(1);
                break;
            case R.id.lly_hhp:
                tabChange(2);
                break;
            default:
                break;
        }
    }

    private void tabChange(int position){
        binding.fragmentVp.setCurrentItem(position);
        binding.tvJfb.setTextColor(getResources().getColor(R.color.black));
        binding.tvSsb.setTextColor(getResources().getColor(R.color.black));
        binding.tvHhp.setTextColor(getResources().getColor(R.color.black));
        binding.vJfb.setVisibility(View.INVISIBLE);
        binding.vSsb.setVisibility(View.INVISIBLE);
        binding.vHhp.setVisibility(View.INVISIBLE);
        switch (position){
            case 0:
                binding.tvJfb.setTextColor(getResources().getColor(R.color.theme));
                binding.vJfb.setVisibility(View.VISIBLE);
                break;
            case 1:
                binding.tvSsb.setTextColor(getResources().getColor(R.color.theme));
                binding.vSsb.setVisibility(View.VISIBLE);
                break;
            case 2:
                binding.tvHhp.setTextColor(getResources().getColor(R.color.theme));
                binding.vHhp.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * viewpager ?????????
     */
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }

    /**
     * viewpager ????????????
     */
    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            tabChange(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };






    //??????TAG???activity??????
    protected final String TAG = this.getClass().getSimpleName();
    //?????????????????????
    private boolean isShowTitle = true;
    //?????????????????????
    private boolean isShowStatusBar = true;
    //????????????????????????
    private boolean isAllowScreenRoate = true;

    // ?????????
    protected Context mContext;
    // ????????????Bundle??????
    protected Bundle mBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_show_one_game);

        Log.i("aa", "tag0");


//        super.onCreate(savedInstanceState);
//        initSys();
//        mContext = UserShowOneGameActivity.this;
//        mBundle = getIntent().getExtras();
//        setCustomDensity(this, this.getApplication());
//        //??????Activity?????????
//        AppManager.getAppManager().addActivity(this);
        if (!isShowTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        Log.i("aa", "tag2");

        if (!isShowStatusBar) {
            getWindow().setFlags(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }
        Log.i("aa", "tag3");

        //?????????DataBinding?????????View
        initBinding(savedInstanceState);
        Log.i("aa", "tag4");

//        //???FitsSystemWindows?????? true ?????????????????????????????????????????????????????? padding
//        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
//        //?????????????????????
//        StatusBarUtil.setTranslucentStatus(this);
//        //?????????????????????????????????????????????????????????, ???????????????????????????????????????, ?????????????????????????????????
//        //??????????????????????????????,?????????????????????, ??????????????????????????????????????????, ???????????????????????????????????????if??????
//        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
//            //????????????????????????????????? ???????????????????????????????????????????????????, ?????????????????????????????????????????????,
//            //???????????????+???=???, ??????????????????????????????
//            StatusBarUtil.setStatusBarColor(this, 0x55000000);
//        }
//
//        //???????????????????????????
//        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
//            if (!isAllowScreenRoate) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            } else {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//        }

        Log.i("aa", "tag5");
        //?????????
        initView();
        //????????????
//        initData();
    }


    // ???setContentView???????????????
    protected void initSys() {

    }


    /**
     * ???????????????????????????
     *
     * @param showTitle true or false
     */
    public void setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
    }

    /**
     * ???????????????????????????
     *
     * @param showStatusBar true or false
     */
    public void setShowStatusBar(boolean showStatusBar) {
        isShowStatusBar = showStatusBar;
    }

    /**
     * ????????????????????????
     *
     * @param allowScreenRoate true or false
     */
    public void setAllowScreenRoate(boolean allowScreenRoate) {
        isAllowScreenRoate = allowScreenRoate;
    }

    /**
     * ?????????????????????1????????????????????????????????????
     */
    public abstract class OnSingleClickListener implements View.OnClickListener {
        //?????????????????????????????????????????????1000ms
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View view) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = curClickTime;
                onSingleClick(view);
            }
        }
    }

    // Toast???Msg
    protected void showToast(String msg) {
        ToastUtil.showShort(this, msg);
    }

    // Toast?????????
    protected void showToast(int msg) {
        ToastUtil.showShort(this, msg);
    }

    /**
     * ??????????????????????????????????????????????????????
     */
    public abstract class OnMultiClickListener implements View.OnClickListener {
        public abstract void onMultiClick(View view);

        @Override
        public void onClick(View v) {
            onMultiClick(v);
        }
    }

    /**
     * ???????????????
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * ???????????????
     */
    public void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (view != null && null != imm) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * ???????????????
     */
    public void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInput(view, 0);
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d("aa", "The onDestroy() event");
//        //??????Activity&??????????????????
//        AppManager.getAppManager().finishActivity(this);
//    }

    private static float sNoncompatDensity;
    private static float sNoncompatScaleDensity;

    public static void setCustomDensity(@Nullable Activity activity, @Nullable Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final float targetScaledDensity = targetDensity * (sNoncompatScaleDensity / sNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * ????????????????????????????????????
     *
     * @param bgAlpha ???????????????0.0-1.0 1?????????????????????
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }


}