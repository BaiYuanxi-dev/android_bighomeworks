package com.example.homework6;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import java.util.logging.Logger;

public class MyIntentService extends IntentService {
    private Logger logger;
    /**
     * @deprecated
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        logger = Logger.getLogger("MyIntentService");
        logger.info("Service Intent start");
        logger.info("Id:19301139\t" + "Name:白远希");
        logger.info("My Intent Service finish");

    }
}
