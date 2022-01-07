package com.example.homework6;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.logging.Logger;

public class MyService extends Service {
    private Logger logger;
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger = Logger.getLogger("MyService");
        logger.info("Service start");
        logger.info("Id:19301139\t" + "Name:白远希");

        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.info("MyService finish");
    }

}
