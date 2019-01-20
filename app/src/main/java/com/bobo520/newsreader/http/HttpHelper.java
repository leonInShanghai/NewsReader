package com.bobo520.newsreader.http;

import android.util.Log;

import com.bobo520.newsreader.Constant;
import com.bobo520.newsreader.LELog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Leon on 2019/1/20. Copyright © Leon. All rights reserved.
 * Functions: 网络请求工具类 HTTP助理
 */
public class HttpHelper {

    /**
     * 将这个工具类做为一个单例
     * 单例：饿汉式 :HttpHelper httpHelper = new HttpHelper() 直接上来就创建
     *
     private static final HttpHelper sHttpHelper = new HttpHelper();

     private HttpHelper(){

     }

     public static HttpHelper getInstance(){
     return sHttpHelper;
     }
     *
     * 单例：懒汉式:线程安全风险（需要加同步锁）如下：
     */

    private static HttpHelper sHttpHelper;

    private HttpHelper(){

    }

    public static HttpHelper getInstance(){
        //懒汉式：在调用getInstance()方法后再去创建，先判断如果为空那就判断
        if (sHttpHelper == null){
            //懒汉式有线程安全风险
            synchronized (HttpHelper.class){
                if (sHttpHelper == null){
                    sHttpHelper = new HttpHelper();
                }
            }
        }
        return sHttpHelper;
    }

    /**
     * GET请求方法的封装
     * @param url 请求的url
     * @param listener 请求(成功 | 失败)回掉方法的实现（接口）
     */
    public void requestGET(String url, final OnResponseListener listener){
        //通过OK HTTP来请求网络
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        //这里用的异步加载
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求不成功的处理
                if (!response.isSuccessful()){
                    LELog.showLogWithLineNum(5,"响应未成功");
                    return;
                }

                //后续的业务需要交给不同的页面来实现-自定义接口来实现
                listener.onSuccess(response);
            }
        });
    }

}
