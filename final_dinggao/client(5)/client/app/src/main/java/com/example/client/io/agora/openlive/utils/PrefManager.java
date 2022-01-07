package com.example.client.io.agora.openlive.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.client.io.agora.openlive.Constants;


public class PrefManager {
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }
}
