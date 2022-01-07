package com.example.homework6;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Random;
import java.util.logging.Logger;

public class MyBindService extends Service {
    private Logger logger = Logger.getLogger("MyBindService");
    private final IBinder myBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public void startDownload() {
            System.out.println("???");
            logger.info("MyBindService " + "startDownload executed");
            logger.info("Id:19301139\t" + "Name:白远希");
        }
        public int getProgress() {
            logger.info("MyBindService " + "getProgress executed");
            return 0;
        }
//        MyBindService getService(){
//            return MyBindService.this;
//        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }
}
