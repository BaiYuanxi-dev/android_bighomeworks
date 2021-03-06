package com.example.client.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public abstract class BaseFragment extends Fragment {
    // 所在Activity
    protected Activity mActivity;
    // 传到所在Activity的Bundle数据
    protected Bundle mBundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mActivity = getActivity();
        mBundle = mActivity.getIntent().getExtras();
        View view = initBinding(inflater, container);
        init(savedInstanceState);
        return view;
    }

    // 初始化DataBinding，绑定View
    protected abstract View initBinding(LayoutInflater inflater, ViewGroup container);

    // 初始化操作
    protected abstract void init(Bundle savedInstanceState);

    // 获取上下文
    public Context getContext() {
        return mActivity;
    }

    // Toast出Msg
    protected void showToast(String msg) {
        ToastUtil.showShort(mActivity, msg);
    }

    // Toast出资源
    protected void showToast(int msg) {
        ToastUtil.showShort(mActivity, msg);
    }

    /**
     * 启动Activity
     *
     * @param clazz 启动的Activity的类
     */
    public void startActivity(Class clazz) {
        startActivity(clazz, null, null);
    }

    /**
     * 启动Activity
     *
     * @param clazz 启动的Activity的类
     * @param datas 传入的Bundle数据
     */
    public void startActivity(Class clazz, Bundle datas) {
        startActivity(clazz, datas, null);
    }

    /**
     * 启动Activity
     *
     * @param clazz   启动的Activity的类
     * @param datas   传入的Bundle数据
     * @param options Android过渡动画
     */
    public void startActivity(Class clazz, Bundle datas, Bundle options) {
        Intent intent = new Intent();
        intent.setClass(mActivity, clazz);
        if (datas != null) {
            intent.putExtras(datas);
        }
        startActivity(intent, options);
    }
}
