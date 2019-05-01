package com.bobo520.newsreader.http;



import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebSettings;

import com.bobo520.newsreader.app.NewsReaderApplication;
import com.bobo520.newsreader.util.LELog;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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

    private final OkHttpClient mOkHttpClient;

    private  Handler mHandler;

    private HttpHelper(){
        //原来时这样写的
        //mOkHttpClient = new OkHttpClient();

        //我改成这样了
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                .build();

        //确保mHandler运行在主线程中：Looper.getMainLooper()
        //如果handler的构造方法中传入了某个线程对应的looper，那么handler就会跟这个线程绑定起来
        mHandler = new Handler(Looper.getMainLooper());
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
        Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        //这里用的异步加载
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call,final IOException e) {

                //让onFailure在主线程中执行避免异常
                mHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onFail(e);
                }
            });
        }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求不成功的处理
                if (!response.isSuccessful()){
                    LELog.showLogWithLineNum(5,"HttpHelper 响应未成功");
                    mHandler.post(new Runnable() {//运行在创建handler时对应的线程中
                        @Override
                        public void run() {
                            listener.onFail(new IOException());
                        }
                    });
                    return;
                }

                //后续的业务需要交给不同的页面来实现-自定义接口来实现
                final String string = response.body().string();
                //让onSuccess方法直接运行在主线程
                mHandler.post(new Runnable() {//运行在创建handler时对应的线程中
                    @Override
                    public void run() {
                        listener.onSuccess(string);
                    }
                });
            }
        });
    }



    /**
     * GET请求方法的封装 针对百思不得姐又做了封装
     * @param url 请求的url
     * @param listener 请求(成功 | 失败)回掉方法的实现（接口）
     */
    public void requestHeaderGET(String url, final OnResponseListener listener){
        //通过OK HTTP来请求网络
        OkHttpClient httpClient;

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                //获取用户设备 property（特性，属性）
                String property = System.getProperty("http.agent");
                String t = System.currentTimeMillis()+"";

                //解决 okhttp git请求 403 Forbidden https://blog.csdn.net/qq_38228254/article/details/79506149
                request = request.newBuilder()
                        .removeHeader("User-Agent")
                        .addHeader("User-Agent","Mozilla/5.0 " +
                                "(Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                        .build();
                return chain.proceed(request);

            }
        };

        //.addInterceptor(interceptor)
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Request request = new Request.Builder().url(url).build();

        Call call = httpClient.newCall(request);
        //这里用的异步加载
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call,final IOException e) {

                //让onFailure在主线程中执行避免异常
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求不成功的处理
                if (!response.isSuccessful()){
                    LELog.showLogWithLineNum(5,"HttpHelper 响应未成功");
                    mHandler.post(new Runnable() {//运行在创建handler时对应的线程中
                        @Override
                        public void run() {
                            listener.onFail(new IOException());
                        }
                    });
                    return;
                }

                //后续的业务需要交给不同的页面来实现-自定义接口来实现
                final String string = response.body().string();
                //让onSuccess方法直接运行在主线程
                mHandler.post(new Runnable() {//运行在创建handler时对应的线程中
                    @Override
                    public void run() {
                        listener.onSuccess(string);
                    }
                });
            }
        });
    }


    /**
     * POST请求方法
     * @param url 请求的url
     * @param listener 请求(成功 | 失败)回掉方法的实现（接口）
     * @param tokenInterceptor 请求头 可以传null
     * @param requestBody 请求体
     */
    public void requestPost(String url,Interceptor tokenInterceptor, RequestBody requestBody
            , final OnResponseListener listener){

        OkHttpClient httpClient;

        if (tokenInterceptor != null) {
            httpClient = new OkHttpClient.Builder()
                    .addInterceptor(tokenInterceptor)
                    .build();
        }else{
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();

                    //获取用户设备 property（特性，属性）
                    String property = System.getProperty("http.agent");
                    request = request.newBuilder()
                            .header("Charset", "UTF-8")
                            .header("User-Agent", property)
                            .build();
                    return chain.proceed(request);

                }
            };

            httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        }


        // 构造Request->call->执行
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)//参数放在body体里
                .build();
        Call call = httpClient.newCall(request);

        //这里用的异步加载
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call,final IOException e) {

                //让onFailure在主线程中执行避免异常
                LELog.showLogWithLineNum(5,"HttpHelperP 响应未成功");

                //让onFailure在主线程中执行避免异常
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求不成功的处理
                if (!response.isSuccessful()){
                    LELog.showLogWithLineNum(5,"响应未成功P"+response.toString());
                    mHandler.post(new Runnable() {//运行在创建handler时对应的线程中
                        @Override
                        public void run() {
                            listener.onFail(new IOException());
                        }
                    });
                    return;
                }
                //后续的业务需要交给不同的页面来实现-自定义接口来实现
                final String string = response.body().string();
                //让onSuccess方法直接运行在主线程
                LELog.showLogWithLineNum(5,"HttpHelperP 请求成功");
                //让onSuccess方法直接运行在主线程
                mHandler.post(new Runnable() {//运行在创建handler时对应的线程中
                    @Override
                    public void run() {
                        listener.onSuccess(string);
                    }
                });
            }
        });

    }


    /**获取用户设备 property（特性，属性） 注意：获取的太详细了这里没有用到*/
    private static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(NewsReaderApplication.getContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
