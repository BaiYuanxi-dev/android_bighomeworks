package com.example.client.activity;

import android.os.Bundle;

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

public class UserShowOneActivity extends AppCompatActivity implements View.OnClickListener {

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

    //绑定
    private ActivityUserShowOneGameBinding binding;

    //fragment(积分、射手、红黄牌)
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    //fragment 适配器
    private FragmentPagerAdapter mAdapter;

    //获取TAG的activity名称
    protected final String TAG = this.getClass().getSimpleName();
    //是否显示标题栏
    private boolean isShowTitle = true;
    //是否显示状态栏
    private boolean isShowStatusBar = true;
    //是否允许旋转屏幕
    private boolean isAllowScreenRoate = true;

    // 上下文
    protected Context mContext;
    // 传过来的Bundle数据
    protected Bundle mBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isShowTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (!isShowStatusBar) {
            getWindow().setFlags(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }

        //初始化DataBinding，绑定View
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_show_one_game);


        matchid = getIntent().getExtras().getString("1");
        feedback = getIntent().getExtras().getString("2");
        jifenbang = getIntent().getExtras().getString("3");
        sheshoubang = getIntent().getExtras().getString("4");
        honghuangpaibang = getIntent().getExtras().getString("5");


        rvTest = (RecyclerView) findViewById(R.id.rv_test);
        rvTest.setLayoutManager(new LinearLayoutManager(this));

        appUtil = (ApplicationUtil) UserShowOneActivity.this.getApplication();

        adapter = new my1RVAdapterNew(this, feedback);
        rvTest.setAdapter(adapter);


        //初始化fragment布局
        mFragments.add(JiFenFragment.newInstance(jifenbang));
        mFragments.add(SheshouFragment.newInstance(sheshoubang));
        mFragments.add(HonghuangFragment.newInstance(honghuangpaibang));
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        binding.fragmentVp.setAdapter(mAdapter);
        // register listener
        binding.fragmentVp.setOffscreenPageLimit(3);
        binding.fragmentVp.addOnPageChangeListener(mPageChangeListener);
        //设置点击事件
        binding.llyJfb.setOnClickListener(this);
        binding.llySsb.setOnClickListener(this);
        binding.llyHhp.setOnClickListener(this);
    }



    /**
     * 设置是否显示标题栏
     *
     * @param showTitle true or false
     */
    public void setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
    }

    /**
     * 设置是否显示状态栏
     *
     * @param showStatusBar true or false
     */
    public void setShowStatusBar(boolean showStatusBar) {
        isShowStatusBar = showStatusBar;
    }

    /**
     * 是否允许屏幕旋转
     *
     * @param allowScreenRoate true or false
     */
    public void setAllowScreenRoate(boolean allowScreenRoate) {
        isAllowScreenRoate = allowScreenRoate;
    }

    /**
     * 保证同一按钮在1秒内只会响应一次点击事件
     */
    public abstract class OnSingleClickListener implements View.OnClickListener {
        //两次点击按钮之间的间隔，目前为1000ms
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

    // Toast出Msg
    protected void showToast(String msg) {
        ToastUtil.showShort(this, msg);
    }

    // Toast出资源
    protected void showToast(int msg) {
        ToastUtil.showShort(this, msg);
    }

    /**
     * 同一按钮在短时间内可重复响应点击事件
     */
    public abstract class OnMultiClickListener implements View.OnClickListener {
        public abstract void onMultiClick(View view);

        @Override
        public void onClick(View v) {
            onMultiClick(v);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (view != null && null != imm) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInput(view, 0);
        }
    }

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
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public void sendMyMessage(String message) throws IOException {
        dos = appUtil.getDos();
        dos.writeUTF(message);
        dos.flush();
    }

    /**
     * 点击事件
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
     * viewpager 适配器
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
     * viewpager 变化监听
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
}