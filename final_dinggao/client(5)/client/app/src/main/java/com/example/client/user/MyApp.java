package com.example.client.user;

import android.content.Context;

import androidx.multidex.MultiDexApplication;


public class MyApp extends MultiDexApplication {

    private static Context context;
    private volatile static MyApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MyApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApp.context;
    }


    public static MyApp getInstance() {
        if (instance == null) {
            synchronized (MyApp.class) {
                if (instance == null) {
                    instance = new MyApp();
                }
            }
        }
        return instance;
    }
}
