package com.example.client.utils;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import com.example.client.R;


public class MyViewPagerAdapter extends PagerAdapter {
    public Context context;

    public	ArrayList<View> views;


    public int curUpdatePager;


    public MyViewPagerAdapter(Context context){
        this.context=context;
        views=new ArrayList<View>();
        View view1 = View.inflate(context, R.layout.page1, null);
        View view2 = View.inflate(context, R.layout.page2, null);
        View view3 = View.inflate(context, R.layout.page3, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
    }

    public void destroyItem(View arg0, int arg1, Object arg2) {
        View view = (View)arg2;
        ((ViewPager) arg0).removeView((View)arg2);
    }


    public void finishUpdate(View arg0) {
    }


    public int getCount() {
        return views.size();
    }


    public Object instantiateItem(View arg0, int arg1) {
        views.get(arg1).setTag(arg1);
        ((ViewPager) arg0).addView(views.get(arg1));


        return views.get(arg1);
    }


    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }


    public Parcelable saveState() {
        return null;
    }


    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public int getItemPosition(Object object) {
        View view = (View) object;
        if (curUpdatePager == (Integer) view.getTag()) {
            return POSITION_NONE;
        } else {
            return POSITION_UNCHANGED;
        }
        // return super.getItemPosition(object);
    }

    /*public void updateViewPagerItem(View view,int index){
        curUpdatePager = index;
        views.remove(index);
        views.add(index, view);
        notifyDataSetChanged();
        // findViewById(getResources().getIdentifier("sysset_button"+(index+1), "id", "com.jzbyapp.suzhou")).requestFocus();
    }*/
}