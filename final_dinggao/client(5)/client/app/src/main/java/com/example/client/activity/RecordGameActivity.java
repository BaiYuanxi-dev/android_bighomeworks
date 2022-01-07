package com.example.client.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.client.R;
import com.example.client.user.SystemBarTintManager;
import com.example.client.utils.ApplicationUtil;
import com.example.client.utils.ScreenUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.angmarch.views.NiceSpinner;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RecordGameActivity extends AppCompatActivity implements View.OnClickListener {
    boolean isRed = true;
    private String event;
    AlertDialog alertDialogGreen;

    private String matchid;
    private String gameNum;
    private String team1;
    private String team2;
    private String choose = "1";

    private boolean flag_continue = false;

    private String feedback;

    private String result;

    private Button suspend;
    private Button end;
    private Button begin;
    private Button check;

    private TextView teamBlue;
    private TextView teamRed;
    private TextView teamRedRed;
    private TextView teamRedGreen;
    private TextView teamRedYellow;
    private ImageView teamRedyellow;
    private ImageView teamRedred;
    private ImageView teamRedgreen;

    private TextView teamBlueRed;
    private TextView teamBlueGreen;
    private TextView teamBlueYellow;
    private ImageView teamBlueyellow;
    private ImageView teamBluered;
    private ImageView teamBluegreen;


    private DataInputStream dis;
    private DataOutputStream dos;
    private ApplicationUtil appUtil;

    private Chronometer timer;

    private long mRecordTime = 0;

    //底栏
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_game);

        //隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        //底栏
        mNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mNavigationView.setSelectedItemId(R.id.navigation_3);

        appUtil = (ApplicationUtil) RecordGameActivity.this.getApplication();

        feedback = getIntent().getExtras().getString("1");
        matchid = getIntent().getExtras().getString("2");
        gameNum = getIntent().getExtras().getString("3");

        String[] strs = feedback.split("#");
        team1 = strs[0];
        team2 = strs[1];


        suspend = findViewById(R.id.btn_break);
        end = findViewById(R.id.btn_end);
        begin = findViewById(R.id.btn_begin);
        check = findViewById(R.id.btn_check);
        suspend.setOnClickListener(this);
        end.setOnClickListener(this);
        begin.setOnClickListener(this);
        check.setOnClickListener(this);

        teamBlue=findViewById(R.id.textViewTeamBlue);
        teamRed=findViewById(R.id.textViewTeamRed);
        teamRedRed=findViewById(R.id.textViewRedRedNum);
        teamRedGreen=findViewById(R.id.textViewRedGreenNum);
        teamRedYellow=findViewById(R.id.textViewRedYellowNum);
        teamRedyellow=findViewById(R.id.ImageViewRedYellow);
        teamRedred=findViewById(R.id.ImageViewRedRed);
        teamRedgreen=findViewById(R.id.ImageViewRedGreen);

        teamBlueRed=findViewById(R.id.textViewBlueRedNum);
        teamBlueGreen=findViewById(R.id.textViewBlueGreenNum);
        teamBlueYellow=findViewById(R.id.textViewBlueYellowNum);
        teamBlueyellow=findViewById(R.id.ImageViewBlueYellow);
        teamBluered=findViewById(R.id.ImageViewBlueRed);
        teamBluegreen=findViewById(R.id.ImageViewBlueGreen);


        teamRed.setText(team1);
        teamBlue.setText(team2);
        teamRedRed.setText("0");
        teamRedGreen.setText("0");
        teamRedYellow.setText("0");
        teamBlueGreen.setText("0");
        teamBlueRed.setText("0");
        teamBlueYellow.setText("0");

        timer = (Chronometer) findViewById(R.id.timer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_break:{
                timer.stop();
                mRecordTime = SystemClock.elapsedRealtime();
                break;
            }
            case R.id.btn_end:{
                mRecordTime = 0;
                timer.stop();
                new Thread(() -> {
                    try {
                        String mes = "AFTERRECORD#";
                        int num1 = Integer.parseInt(String.valueOf(teamRedGreen.getText()));
                        int num2 = Integer.parseInt(String.valueOf(teamBlueGreen.getText()));
                        mes += matchid + "," + gameNum + "," + team1 + "," + num1 + "," + team2 + "," + num2;
                        sendMyMessage(mes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
            }
            case R.id.btn_begin:{
                if (mRecordTime != 0) {
                    timer.setBase(timer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                } else {
                    timer.setBase(SystemClock.elapsedRealtime());
                }
                timer.start();
                break;
            }
            case R.id.btn_check:{
                event = "check";
                View v = getWindow().getDecorView();
                String[] items = {"球员1", "球员2", "球员3", "球员4",
                        "球员5", "球员6", "球员7", "球员8",
                        "球员9", "球员10", "球员11", "球员12",
                        "球员13", "球员14", "球员15", "球员16",
                        "球员17", "球员18", "球员19", "球员20",
                        "球员21", "球员22", "球员23"};
                showSingleChoiceDialog(v, items,"请选择验证球员号码");
                break;
            }
            default:{
                break;
            }
        }
    }

    public void mybuttonlistener(View v) throws IOException {
        View p=v.getRootView();

        TextView text=p.findViewById(R.id.textView8);
        String[] items = {"球员1", "球员2", "球员3", "球员4",
                "球员5", "球员6", "球员7", "球员8",
                "球员9", "球员10", "球员11", "球员12",
                "球员13", "球员14", "球员15", "球员16",
                "球员17", "球员18", "球员19", "球员20",
                "球员21", "球员22", "球员23"};
        switch (v.getId()) {

            case R.id.imageViewBlue:
                ImageView red=p.findViewById(R.id.imageViewRed);
                //v.offsetTopAndBottom(20);
                //red.offsetTopAndBottom(-20);
                v.setTranslationY(-42);
                teamBlue.setTranslationY(-42);
                teamBlueRed.setTranslationY(-42);
                teamBlueGreen.setTranslationY(-42);
                teamBlueYellow.setTranslationY(-42);
                teamBlueyellow.setTranslationY(-42);
                teamBluered.setTranslationY(-42);
                teamBluegreen.setTranslationY(-42);
                red.setTranslationY(42);
                teamRed.setTranslationY(42);
                teamRedRed.setTranslationY(42);
                teamRedGreen.setTranslationY(42);
                teamRedYellow.setTranslationY(42);
                teamRedyellow.setTranslationY(42);
                teamRedred.setTranslationY(42);
                teamRedgreen.setTranslationY(42);
                v.setElevation(10);
                red.setElevation(0);

                isRed=false;

                text.setTextColor(Color.rgb(0,85,173));

                break;
            case R.id.imageViewRed:
                if(isRed){
                    break;
                }

                ImageView blue=p.findViewById(R.id.imageViewBlue);
                v.setTranslationY(0);
                teamRed.setTranslationY(0);
                teamRed.setTranslationY(0);
                teamRedRed.setTranslationY(0);
                teamRedGreen.setTranslationY(0);
                teamRedYellow.setTranslationY(0);
                teamRedyellow.setTranslationY(0);
                teamRedred.setTranslationY(0);
                teamRedgreen.setTranslationY(0);

                blue.setTranslationY(0);
                teamBlue.setTranslationY(0);
                teamBlueRed.setTranslationY(0);
                teamBlueGreen.setTranslationY(0);
                teamBlueYellow.setTranslationY(0);
                teamBlueyellow.setTranslationY(0);
                teamBluered.setTranslationY(0);
                teamBluegreen.setTranslationY(0);
                v.setElevation(10);
                blue.setElevation(0);
                text.setTextColor(Color.rgb(209,0,0));
                isRed=true;
                break;
            case R.id.green:
                event = "green";
                showSingleChoiceDialog(v,items,"请选择进球球员号码");
                break;
            case R.id.yellow:
                event = "yellow";
                showSingleChoiceDialog(v,items,"请选择被罚黄牌球员号码");
                break;
            case R.id.red:
                event = "red";
                showSingleChoiceDialog(v,items,"请选择被罚红牌球员号码");
                break;
            case R.id.black:
                event = "dian";
                showSingleChoiceDialog(v,items,"请选择点球球员号码");
                break;
            case R.id.purple:
                event = "change";
                showSwitchPlayerDialog(p);
                break;
            default:{
                break;
            }
        }
    }

    public void sendMyMessage(String message) throws IOException {
        dos = appUtil.getDos();
        dos.writeUTF(message);
        dos.flush();
    }

    public void showSingleChoiceDialog(View v,String[] items,String title){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(title);
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                choose = i + 1 + "";
                Toast.makeText(RecordGameActivity.this, items[i], Toast.LENGTH_SHORT).show();
            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                //更新数量展示
                switch (event){
                    case "red":{
                        if(isRed){
                            int num = Integer.parseInt(String.valueOf(teamRedRed.getText()));
                            teamRedRed.setText(String.valueOf(num + 1));
                        }else{
                            int num = Integer.parseInt(String.valueOf(teamBlueRed.getText()));
                            teamBlueRed.setText(String.valueOf(num + 1));
                        }
                        break;
                    }
                    case "yellow":{
                        if(isRed){
                            int num = Integer.parseInt(String.valueOf(teamRedYellow.getText()));
                            teamRedYellow.setText(String.valueOf(num + 1));
                        }else{
                            int num = Integer.parseInt(String.valueOf(teamBlueYellow.getText()));
                            teamBlueYellow.setText(String.valueOf(num + 1));
                        }
                        break;
                    }
                    case "green":{
                        if(isRed){
                            int num = Integer.parseInt(String.valueOf(teamRedGreen.getText()));
                            teamRedGreen.setText(String.valueOf(num + 1));
                        }else{
                            int num = Integer.parseInt(String.valueOf(teamBlueGreen.getText()));
                            teamBlueGreen.setText(String.valueOf(num + 1));
                        }
                        break;
                    }
                    case "check":{
                        if(isRed){
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        sendMyMessage("GETFACEINFO#" + matchid + "," + choose + "," + team1);
                                        dis = appUtil.getDis();
                                        String mes = dis.readUTF();
                                        String str[] = mes.split("#");
                                        String res = matchid + "_" + choose + "_" + str[1];
                                        if(str[0].equals("no")){
                                            res += "#none";
                                        }else{
                                            res += "#yes";
                                        }
                                        Intent i = new Intent(RecordGameActivity.this, Face1Activity.class);
                                        Bundle b = new Bundle();
                                        b.putString("1", res);
                                        i.putExtras(b);
                                        startActivity(i);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        }else{
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        sendMyMessage("GETFACEINFO#" + matchid + "," + choose + "," + team2);
                                        dis = appUtil.getDis();
                                        String mes = dis.readUTF();
                                        String str[] = mes.split("#");
                                        String res = matchid + "_" + choose + "_" + str[1];
                                        if(str[0].equals("no")){
                                            res += "#none";
                                        }else{
                                            res += "#yes";
                                        }
                                        Intent i = new Intent(RecordGameActivity.this, Face1Activity.class);
                                        Bundle b = new Bundle();
                                        b.putString("1", res);
                                        i.putExtras(b);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        }
                        break;
                    }
                    default:{
                        break;
                    }
                }

                if(!event.equals("check")){
                    int temp0 = Integer.parseInt(timer.getText().toString().split(":")[0]);
                    int temp1 =Integer.parseInt(timer.getText().toString().split(":")[1]);
                    int temp = temp0 * 60 + temp1;

                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                if(isRed){
                                    result = "NEWRECORD#" + matchid + "," + gameNum + "," + team1 + "," + choose + "," + event + "," + temp;
                                }else{
                                    result = "NEWRECORD#" + matchid + "," + gameNum + "," + team2 + "," + choose + "," + event + "," + temp;
                                }
                                sendMyMessage(result);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

                Toast toast=Toast.makeText(v.getContext(),"添加事件成功！",Toast.LENGTH_SHORT);
                toast.show();

                alertDialogGreen.dismiss();
            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialogGreen.dismiss();
            }
        });

        alertDialogGreen = alertBuilder.create();
        alertDialogGreen.show();
    }


    // 简单消息提示框
    private void showFeedbackDialog(String feedback) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("TIPS")
                .setMessage(feedback)
                .setPositiveButton("确定", null)
                .show();
    }

    private void showSwitchPlayerDialog(View v){
        View view = LayoutInflater.from(this).inflate(R.layout.switch_player_dialog_layout,null,false);
        TextView title=view.findViewById(R.id.textView);
        TextView redTeam=v.findViewById(R.id.textViewTeamRed);
        TextView blueTeam=v.findViewById(R.id.textViewTeamBlue);
        if(isRed){
            title.setText(redTeam.getText()+" 换人");
        }
        else{
            title.setText(blueTeam.getText()+" 换人");
        }
        NiceSpinner niceSpinner = (NiceSpinner) view.findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("球员1", "球员2", "球员3", "球员4",
                "球员5", "球员6", "球员7", "球员8",
                "球员9", "球员10", "球员11", "球员12",
                "球员13", "球员14", "球员15", "球员16",
                "球员17", "球员18", "球员19", "球员20",
                "球员21", "球员22", "球员23"));
        niceSpinner.attachDataSource(dataset);

        NiceSpinner niceSpinner1 = (NiceSpinner) view.findViewById(R.id.nice_spinner1);
        List<String> dataset1 = new LinkedList<>(Arrays.asList("球员1", "球员2", "球员3", "球员4",
                "球员5", "球员6", "球员7", "球员8",
                "球员9", "球员10", "球员11", "球员12",
                "球员13", "球员14", "球员15", "球员16",
                "球员17", "球员18", "球员19", "球员20",
                "球员21", "球员22", "球员23"));
        niceSpinner1.attachDataSource(dataset1);

        final android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this).setView(view).create();

        ImageView btn_close = view.findViewById(R.id.close);
        Button btn_save = view.findViewById(R.id.btn_save);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferencesUnitls.setParam(getApplicationContext(),"HighOpinion","false");
                //... To-do
                dialog.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(v.getContext(),"添加事件成功！",Toast.LENGTH_SHORT);
                toast.show();

                int shang = niceSpinner.getSelectedIndex();
                int xia = niceSpinner1.getSelectedIndex();

                int temp0 = Integer.parseInt(timer.getText().toString().split(":")[0]);
                int temp1 =Integer.parseInt(timer.getText().toString().split(":")[1]);
                int temp = temp0 * 60 + temp1;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            if(isRed){
                                result = "NEWRECORD#" + matchid + "," + gameNum + "," + team1 + "," + (xia+1) + "," + "xia" + "," + temp;
                                sendMyMessage(result);
                                result = "NEWRECORD#" + matchid + "," + gameNum + "," + team1 + "," + (shang+1) + "," + "shang" + "," + temp;
                                sendMyMessage(result);
                            }else{
                                result = "NEWRECORD#" + matchid + "," + gameNum + "," + team2 + "," + (xia+1) + "," + "xia" + "," + temp;
                                sendMyMessage(result);
                                result = "NEWRECORD#" + matchid + "," + gameNum + "," + team2 + "," + (shang+1) + "," + "shang" + "," + temp;
                                sendMyMessage(result);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                dialog.dismiss();
            }
        });

        dialog.show();
        //此处设置位置窗体大小
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)/10*9), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
