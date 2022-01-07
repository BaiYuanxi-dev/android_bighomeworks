package com.example.client.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.client.io.agora.openlive.activities.BroadcastActivity;
import com.example.client.user.SystemBarTintManager;
import com.example.client.utils.ApplicationUtil;
import com.example.client.utils.MyViewPagerAdapter;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.example.client.R;
import com.example.client.utils.ProcessBar;
import com.example.client.utils.ScreenUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import org.angmarch.views.NiceSpinner;

import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;


public class NewGame1Activity extends AppCompatActivity{

    private DataInputStream dis;
    private DataOutputStream dos;
    private ApplicationUtil appUtil;

    ViewPager mViewPager;
    MyViewPagerAdapter mPagerAdapter;
    //日历
    //选择日期Dialog
    private DatePickerDialog datePickerDialog;
    //选择时间Dialog
    private TimePickerDialog timePickerDialog;

    private Calendar calendar;

    private String result;
    private String result1;

    private String memberInfo;

    private NiceSpinner niceSpinner;
    private NiceSpinner niceSpinner2;

    //底栏
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_1);

        //隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        mNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mNavigationView.setSelectedItemId(R.id.navigation_3);

        appUtil = (ApplicationUtil) NewGame1Activity.this.getApplication();

        this.setTitle("创建赛事");
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new MyViewPagerAdapter(this);

        mViewPager.setAdapter(mPagerAdapter);

        ProcessBar processBar = findViewById(R.id.processbar);
        processBar.setupWithViewPager(mViewPager);

        calendar = Calendar.getInstance();

        initPage3();
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

    public void mybuttonlistener(View v) throws IOException {
        switch (v.getId()) {
            case R.id.imageView5:
                //选择球队
                showTeamDialog();
                break;
            case R.id.imageView7:
                //选择起止时间
                showDialogTwo();
                break;
            case R.id.imageView3:
                //选择主办方
                showSponsorDialog();
                break;
            case R.id.imageViewP2_3:
                //添加球员
                showTeamMemberDialog();
                break;
            case R.id.confirm:{
                //生成赛程
                View view1 = mPagerAdapter.views.get(0);
                View view2 = mPagerAdapter.views.get(1);
                View view3 = mPagerAdapter.views.get(2);

                result = "CREATEGAME#";

                TextFieldBoxes match;
                match = view1.findViewById(R.id.text_field_boxes);
                String match1 = match.getText();
                if(match1.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "请填写赛事名称", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    result = result + match1 + "&";
                }


                TextFieldBoxes sponser;
                sponser = view1.findViewById(R.id.text_field_boxes1);
                String sponser1 = sponser.getText();
                if(sponser1.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "请填写主办方", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    String[] strs1 = sponser1.split("\n");
                    result = result + strs1[0] + "&";
                }


                int teamNum;
                TextFieldBoxes team;
                team = view1.findViewById(R.id.text_field_boxes2);
                String team1 = team.getText();
                if(team1.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "请填写参与球队", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    String[] strs1 = team1.split("\n");
                    teamNum = strs1.length;
                    String team2 = "";
                    for(int i = 0; i < strs1.length; i++){
                        team2 = team2 + strs1[i].trim() + ",";
                    }
                    result = result + team2 + "&";
                }


                TextFieldBoxes time;
                time = view1.findViewById(R.id.text_field_boxes3);
                String time1 = time.getText();
                if(time1.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "请填写赛事时间", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    String[] strs1 = time1.split("\n");
                    result = result + strs1[0] + "&";
                }

                int mode1 = niceSpinner.getSelectedIndex();
                boolean flag = true;
                switch (mode1){
                    case 0:{
                        if(teamNum != 8){
                            flag = false;
                            Toast.makeText(getApplicationContext(), "球队数目和比赛模式不匹配", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case 1:
                    case 2: {
                        if(teamNum != 12){
                            flag = false;
                            Toast.makeText(getApplicationContext(), "球队数目和比赛模式不匹配", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case 3:{
                        if(teamNum != 16){
                            flag = false;
                            Toast.makeText(getApplicationContext(), "球队数目和比赛模式不匹配", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
                if(flag){
                    result = result + mode1 + "&";
                }else{
                    break;
                }

                int mode2 = niceSpinner2.getSelectedIndex();
                result = result + mode2 + "&";


                new Thread() {
                    @Override
                    public void run() {
                        try {
                            System.out.println(result);
                            sendMyMessage(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                result1 = "ADDPLAYER#";

                int playerNum;
                int itemNum;
                TextFieldBoxes player;
                player = view2.findViewById(R.id.text_field_boxesP2);
                String player1 = player.getText();

                if(player1.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "请填写参与球员", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    String[] strs1 = player1.split("\n");
                    playerNum = strs1.length;
                    for(int i = 0; i < playerNum; i++){
                        String[] strs2 = strs1[i].split(" ");
                        itemNum = strs2.length;
                        for(int j = 0; j < itemNum; j++){
                            result1 = result1 + strs2[j].trim() + ",";
                        }
                        result1 = result1 + match1 + "#";
                    }
                    System.out.println(result1);
                }

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            System.out.println(result1);
                            sendMyMessage(result1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


                showFeedbackDialog("赛事生成成功");

                break;
            }
        }
    }

    // 简单消息提示框
    private void showFeedbackDialog(String feedback) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("TIPS")
                .setMessage(feedback)
                .setPositiveButton("确定", null)
                .show();
    }

    public void sendMyMessage(String message) throws IOException {
        dos = appUtil.getDos();
        dos.writeUTF(message);
        dos.flush();
    }

    //选择球队对话框
    private void showTeamDialog() {
        View view1 = mPagerAdapter.views.get(0);
        TextFieldBoxes teamText;

        teamText=view1.findViewById(R.id.text_field_boxes2);

        String[] team = new String[] { "软件学院", "运输学院", "计算机学院", "机电学院", "土建学院",
        "语传学院", "建艺学院", "电信学院", "詹天佑学院", "经法联盟", "经管学院", "法学院", "电气学院"};
        boolean[] isSelected=new boolean[] { false, false, false, false, false, false, false, false, false, false, false, false, false, false };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择参赛球队（可多选）").setMultiChoiceItems(team, isSelected,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {


                    }
                })
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s =new String();
                        // 扫描所有的列表项，如果当前列表项被选中，将列表项的文本追加到s变量中。
                        for (int i = 0; i < team.length; i++) {
                            if (isSelected[i]) {
                                s += team[i] + "\r\n";
                            }
                        }
                        teamText.setText(s);
                    }
                })
                .show();
    }



    //选择主办方对话框
    private void showSponsorDialog() {
        View view1 = mPagerAdapter.views.get(0);

        TextFieldBoxes sponsorText;

        sponsorText=view1.findViewById(R.id.text_field_boxes1);

        String[] team = new String[] { "北京交通大学", "北京大学", "北京邮电大学", "中国科学院大学", "北京航空航天大学" };
        boolean[] isSelected=new boolean[] { false, false, false, false, false };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择主办方").setMultiChoiceItems(team, isSelected,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                        int which, boolean isChecked) {

                        }
                })
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s =new String();
                        // 扫描所有的列表项，如果当前列表项被选中，将列表项的文本追加到s变量中。
                        for (int i = 0; i < team.length; i++) {
                            if (isSelected[i]) {
                                s += team[i] + "\r\n";
                            }
                        }
                        sponsorText.setText(s);
                    }
                })
                .show();
    }




    //选择起始时间和结束时间对话框
    private void showDialogTwo() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_date, null);

        View view1=mPagerAdapter.views.get(0);

        TextFieldBoxes timeText;

        timeText=view1.findViewById(R.id.text_field_boxes3);

        final DatePicker startTime = (DatePicker) view.findViewById(R.id.st);
        final DatePicker endTime = (DatePicker) view.findViewById(R.id.et);
        startTime.updateDate(startTime.getYear(), startTime.getMonth(), 01);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("选择时间");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int month = startTime.getMonth() + 1;
                String st = "" + startTime.getYear() + "/"+month + "/"+startTime.getDayOfMonth();
                int month1 = endTime.getMonth() + 1;
                String et = "" + endTime.getYear() + "/"+month1 + "/"+endTime.getDayOfMonth();
                String text;
                text=st+" - "+et;

                timeText.setText(text);
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        //自动弹出键盘问题解决
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }




    //添加球员：初始化并弹出对话框
    private void showTeamMemberDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.add_member_dialog_layout,null,false);
        View view1 = mPagerAdapter.views.get(1);
        TextFieldBoxes infoText;
        infoText=view1.findViewById(R.id.text_field_boxesP2);
        memberInfo=infoText.getText();
        TextFieldBoxes nameText;
        nameText=view.findViewById(R.id.text_field_boxes);
        NiceSpinner niceSpinner = (NiceSpinner) view.findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20",
                "21", "22", "23"));
        niceSpinner.attachDataSource(dataset);

        NiceSpinner niceSpinner1 = (NiceSpinner) view.findViewById(R.id.nice_spinner1);
        List<String> dataset1 = new LinkedList<>(Arrays.asList("中锋", "边锋", "前腰", "后腰", "左前卫","右前卫",
                "中前卫","左后卫","中后卫","右后卫","守门员"));
        niceSpinner1.attachDataSource(dataset1);

        View view0 = mPagerAdapter.views.get(0);
        TextFieldBoxes team=view0.findViewById(R.id.text_field_boxes2);
        NiceSpinner niceSpinner0 = (NiceSpinner) view.findViewById(R.id.nice_spinner0);
        List<String> dataset0 = new LinkedList();
        String[] s=team.getText().split("\\s+");
        for(int i=0;i<s.length;i++){
            dataset0.add(s[i]);
        }
        niceSpinner0.attachDataSource(dataset0);

        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

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
                //获取球员号码
                String num=(String)niceSpinner.getItemAtPosition(niceSpinner.getSelectedIndex());
                //获取所属球队
                String team=(String)niceSpinner0.getItemAtPosition(niceSpinner0.getSelectedIndex());
                //获取球员位置
                String duty=(String)niceSpinner1.getItemAtPosition(niceSpinner1.getSelectedIndex());
                //获取球员姓名
                memberInfo = memberInfo + num + " " + nameText.getText() + " " + duty + " " + team + "\r\n";
                infoText.setText(memberInfo);

//                String result = "ADDPLAYER#" + nameText.getText() + "," + num + "," + team + "," + duty;
//                new Thread() {
//                    @Override
//                    public void run() {
//                        try {
//                            System.out.println(result);
//                            sendMyMessage(result);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();

                dialog.dismiss();
            }
        });

        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4  注意一定要在show方法调用后再写设置窗口大小的代码，否则不起效果会
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)/10*9), LinearLayout.LayoutParams.WRAP_CONTENT);
    }



    //比赛模式和比赛时长
    private void initPage3(){

        View view1 = mPagerAdapter.views.get(2);
        niceSpinner = (NiceSpinner) view1.findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("8队2个小组，小组赛8进4+淘汰赛", "12队2小组，小组赛12进4+淘汰赛", "12队3小组，小组赛12进4+淘汰赛","16队4小组，小组赛16进8+淘汰赛"));
        niceSpinner.attachDataSource(dataset);

        niceSpinner2 = (NiceSpinner) view1.findViewById(R.id.nice_spinner1);
        List<String> dataset1 = new LinkedList<>(Arrays.asList("上下半场25分钟，中场休息10分钟", "上下半场45分钟，中场休息10分钟"));
        niceSpinner2.attachDataSource(dataset1);
    }



}
