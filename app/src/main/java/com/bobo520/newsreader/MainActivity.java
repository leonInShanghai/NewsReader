package com.bobo520.newsreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.bobo520.newsreader.bean.AdListBean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Leon on 2018/12/30. Copyright © Leon. All rights reserved.
 * Functions: 创建 project 之初的 第一个activity
 */
public class MainActivity extends Activity {

    /**传递 adListBean 给 DownloadIntentService 的常量*/
    public static final String ADS_PIC_URL = "ADS_PIC_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
    }

    private void initData(){
       // requestData();-网络请求 之前用的HttpUrlConnection 现在用的OK HTTP
       requestData();
    }

    private void requestData(){
        //1.使用okHttp进行网络请求
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象-建造者模式
        final Request request = new Request.Builder().url(Constant.ADS_URL).build();
        //3.调用new Call方法
        Call call = okHttpClient.newCall(request);
        //同步请求调用execute 异步调用enqueue方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功-response
                if (!response.isSuccessful()){
                    Log.e(getClass().getSimpleName(),"isSuccessful"+"响应未成功");
                    return;
                }

                ResponseBody body = response.body();
                String string = body.string();

                Gson gson = new Gson();

                //使用GSON，(数据对象)直接转成（java）Bean对象
                AdListBean adListBean = gson.fromJson(string,AdListBean.class);

                //在server中去执行下载的后台任务-（不能写在main activity中)下载完成后将后台的server组件释放
                downloadPic(adListBean);
            }
        });
    }

    /**开启服务下载广告图片的方法*/
    private void downloadPic(AdListBean adListBean){
        //在server中去执行下载的后台任务
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),DownloadIntentService.class);
        intent.putExtra(ADS_PIC_URL,adListBean);
        startService(intent);
    }
}




//使用HttpUrlConnection 的请求方法（可以用但是没有用 用的OK HTTP）
//    private void requestData() {
//        //请求网络HttpUrlConnection（子线程中操作）  Volley是 Google 2013 年 谷歌IO大会上推出的
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(Constant.ADS_URL);
//                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
//
//                    //设置请求方式 GET 或 POST
//                    urlConnection.setRequestMethod("GET");
//                    //设置请求超时时间单位是milliseconds 毫秒
//                    urlConnection.setConnectTimeout(10000);
//
//                    int responseCode = urlConnection.getResponseCode();
//
//                    if (responseCode == 200){
//                        //请求成功
//                        InputStream inputStream = urlConnection.getInputStream();
//
//                        //解析inputStream，将其解析为string
//                        String result = parseStreamToString(inputStream);
//
//                        Log.e("leon",result);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    //使用HttpUrlConnection 的请求方法（可以用但是没有用 用的OK HTTP）
//    private String parseStreamToString(InputStream inputStream) throws IOException {
//        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//        BufferedReader reader = new BufferedReader(inputStreamReader);
//        String line = "";
//        StringBuffer stringBuffer = new StringBuffer();
//
//        while (!TextUtils.isEmpty((line = reader.readLine()))){
//            stringBuffer.append(line);
//        }
//
//        return stringBuffer.toString();
//    }