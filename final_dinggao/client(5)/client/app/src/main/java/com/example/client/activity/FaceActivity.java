package com.example.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.*;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.client.R;


public class FaceActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnOpenCamera, btnSavePhoto;
    private ImageView ivShowPicture;
    private static int REQUEST_CAMERA_1 = 1;
    private static int REQUEST_CAMERA_2 = 2;
    private String mFilePath;
    private EditText nameText,idText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);

        // 初始化控件
        init();

        // 控件绑定点击事件
        bindClick();
    }

    // 初始化控件和变量
    private void init() {
        btnOpenCamera = (Button) findViewById(R.id.btnOpenCamera);
        btnSavePhoto = (Button) findViewById(R.id.btnSavePhoto);
        ivShowPicture = (ImageView) findViewById(R.id.ivShowPicture);
        nameText = (EditText) findViewById(R.id.nameText);
        idText = (EditText) findViewById(R.id.idText);
        mFilePath = Environment.getExternalStorageDirectory().getPath();// 获取SD卡路径
        mFilePath = mFilePath + "/" + "temp.png";// 指定路径
    }

    // 控件绑定点击事件
    private void bindClick() {
        btnOpenCamera.setOnClickListener(this);
        btnSavePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOpenCamera:
                // 拍照并显示图片
                openCamera_1();
                break;
            case R.id.btnSavePhoto:
                // 拍照后存储并显示图片
                openCamera_2();
                break;
            default:
                break;
        }
    }

    // 拍照并显示图片
    private void openCamera_1() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        startActivityForResult(intent, REQUEST_CAMERA_1);
    }

    // 拍照后存储并显示图片
    private void openCamera_2() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        startActivityForResult(intent, REQUEST_CAMERA_2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA_1) { // 判断请求码是否为REQUEST_CAMERA,如果是代表是这个页面传过去的，需要进行获取
                Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        verified(bitmapToBase64(bitmap));
                        //你要执行的连接
                    }
                }).start();
                ivShowPicture.setImageBitmap(bitmap);// 显示图片
            } else if (requestCode == REQUEST_CAMERA_2) {
                String name = nameText.getText().toString();
                String id = idText.getText().toString();
                Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        addPerson(id, name, bitmapToBase64(bitmap));
                        //你要执行的连接
                    }
                }).start();
                ivShowPicture.setImageBitmap(bitmap);// 显示图片
            }
        }
    }

    public static String bitmapToBase64(Bitmap bitmap){
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
                result = result.replaceAll("[\\s\\t\\n\\r]", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("result:"+result);
        return result;

    }

    private void verified(String photo){
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("AKIDoSccMp1mIYWEgrkNjoc9RSCfsnRoujHY", "2nHTO3SZQ1LN8droTryoNfJkvpSothEU");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            IaiClient client = new IaiClient(cred, "ap-beijing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SearchPersonsRequest req = new SearchPersonsRequest();
            String[] groupIds1 = {"test1"};
            req.setGroupIds(groupIds1);
            req.setImage(photo);
            req.setNeedPersonInfo(1L);

//            req.setUrl("https://s2.loli.net/2021/12/18/EB87QwmnKGjqULO.jpg");
            // 返回的resp是一个SearchPersonsResponse的实例，与请求对象对应
            SearchPersonsResponse resp = client.SearchPersons(req);
            // 输出json格式的字符串回包
            System.out.println(SearchPersonsResponse.toJsonString(resp));
//            Map map = JSON.parseObject(SearchPersonsResponse.toJsonString(resp), Map.class);
//            Map Results = JSON.parseObject(String.valueOf(map.get("Results")), Map.class);
            JSONObject object = (JSONObject) JSONObject.parse(SearchPersonsResponse.toJsonString(resp));
            JSONArray array = (JSONArray)object.get("Results");
            JSONObject array0 = (JSONObject) array.get(0);
            JSONArray candidates = (JSONArray)array0.get("Candidates");
            JSONObject candidates0 = (JSONObject) candidates.get(0);

//            System.out.println("candidates" + candidates0.toString());
            //JSONObject group = (JSONObject)candidates0.get(0);

            System.out.println("Results: " + candidates0.get("PersonId")+ " " + candidates0.get("PersonName") + " " + candidates0.get("Score"));

            //System.out.println("result: "+ map.get("Results"));

        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }

    private void addPerson(String id, String name, String photo){
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("AKIDoSccMp1mIYWEgrkNjoc9RSCfsnRoujHY", "2nHTO3SZQ1LN8droTryoNfJkvpSothEU");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            IaiClient client = new IaiClient(cred, "ap-beijing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreatePersonRequest req = new CreatePersonRequest();
            req.setGroupId("test1");
            req.setPersonId(id);
            req.setPersonName(name);
            req.setImage(photo);
//            req.setPersonName("zc");
//            req.setPersonId("111");
//            req.setUrl("https://s2.loli.net/2021/12/20/jLXg9Fht4IYs6a5.jpg");
            // 返回的resp是一个CreatePersonResponse的实例，与请求对象对应
            CreatePersonResponse resp = client.CreatePerson(req);
            // 输出json格式的字符串回包
            System.out.println(CreatePersonResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
}
