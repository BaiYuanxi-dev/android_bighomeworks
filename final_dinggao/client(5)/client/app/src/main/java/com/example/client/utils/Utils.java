package com.example.client.utils;


import android.content.Context;
import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.youth.banner.Banner;

import java.util.List;

public class Utils {

    /**
     * 加载图片轮播图公共方法
     * @param context 上下文
     * @param imagesList 图片资源list
     * @param radius 圆角值
     */
    /**
     * 加载图片轮播图公共方法
     * @param context 上下文
     * @param imagesList 图片资源list
     * @param radius 圆角值
     */
    public static Banner initBanner(Context context, Banner banner, List<Integer> imagesList, final float radius){

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader(radius));
        //防止图片切换时的直角
        /*if (Build.VERSION.SDK_INT>21){
            banner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
                }
            });
            banner.setClipToOutline(true);
        }*/
        banner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
            }
        });
        banner.setClipToOutline(true);
        //设置图片集合
        banner.setImages(imagesList);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        return banner;
    }

}

