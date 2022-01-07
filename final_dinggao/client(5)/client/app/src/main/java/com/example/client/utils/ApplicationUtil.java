package com.example.client.utils;

import android.app.Activity;
import android.app.Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.client.R;
import com.example.client.io.agora.openlive.Constants;
import com.example.client.io.agora.openlive.rtc.EngineConfig;
import com.example.client.io.agora.openlive.rtc.AgoraEventHandler;
import com.example.client.io.agora.openlive.rtc.EventHandler;
import com.example.client.io.agora.openlive.stats.StatsManager;
import com.example.client.io.agora.openlive.utils.FileUtil;
import com.example.client.io.agora.openlive.utils.PrefManager;
import io.agora.rtc.RtcEngine;

public class ApplicationUtil extends Application {

    public static final String ADDRESS = "81.70.242.194";
    public static final int PORT = 2013;

    private Socket socket;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;

//    private List<Activity> mList = new LinkedList<Activity>();
//    private static ApplicationUtil instance;
//
//    public synchronized static ApplicationUtil getInstance() {
//        if (instance == null) {
//            instance = new ApplicationUtil();
//        }
//        return instance;
//    }
//
//    // 添加Activity到列表中维持
//    public void addActivity(Activity activity) {
//        mList.add(activity);
//    }
//
//    public void exit() {
//        try {
//            for (Activity activity : mList) {
//                if (activity != null) {
//                    activity.finish();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            System.exit(0);
//        }
//    }

    public void init() throws IOException, Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //与服务器建立连接
                try {
                    socket = new Socket(ADDRESS, PORT);
                    dos = new DataOutputStream(socket.getOutputStream());
                    dis = new DataInputStream(socket.getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }


    //下面是后加的  for  ------ 直播



    /**实时通信对象*/
    private RtcEngine mRtcEngine;
    /**配置*/
    private EngineConfig mGlobalConfig = new EngineConfig();
    private AgoraEventHandler mHandler = new AgoraEventHandler();
    private StatsManager mStatsManager = new StatsManager();

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mRtcEngine = RtcEngine.create(getApplicationContext(), getString(R.string.private_app_id), mHandler);
            mRtcEngine.setLogFile(FileUtil.initializeLogFile(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

        initConfig();
    }

    private void initConfig() {
        SharedPreferences pref = PrefManager.getPreferences(getApplicationContext());
        mGlobalConfig.setVideoDimenIndex(pref.getInt(
                Constants.PREF_RESOLUTION_IDX, Constants.DEFAULT_PROFILE_IDX));

        boolean showStats = pref.getBoolean(Constants.PREF_ENABLE_STATS, false);
        mGlobalConfig.setIfShowVideoStats(showStats);
        mStatsManager.enableStats(showStats);

        mGlobalConfig.setMirrorLocalIndex(pref.getInt(Constants.PREF_MIRROR_LOCAL, 0));
        mGlobalConfig.setMirrorRemoteIndex(pref.getInt(Constants.PREF_MIRROR_REMOTE, 0));
        mGlobalConfig.setMirrorEncodeIndex(pref.getInt(Constants.PREF_MIRROR_ENCODE, 0));
    }

    public EngineConfig engineConfig() {
        return mGlobalConfig;
    }

    public RtcEngine rtcEngine() {
        return mRtcEngine;
    }

    public StatsManager statsManager() {
        return mStatsManager;
    }

    public void registerEventHandler(EventHandler handler) {
        mHandler.addHandler(handler);
    }

    public void removeEventHandler(EventHandler handler) {
        mHandler.removeHandler(handler);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        RtcEngine.destroy();
    }
}
