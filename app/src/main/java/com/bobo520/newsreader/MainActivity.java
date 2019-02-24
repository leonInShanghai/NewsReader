package com.bobo520.newsreader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bobo520.newsreader.bean.AdListBean;
import com.bobo520.newsreader.weiget.SkipView;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
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
 * okio mave仓库中选这个 ：g:com.squareup.okio 新版的会闪退 也可能是 OK HTTP jar 包太新。
 * 原来：增加了沉浸式导航栏滑动翻页 SwipeBack功能
 */
public class MainActivity extends Activity {

    /**传递 adListBean 给 DownloadIntentService 的常量*/
    public static final String ADS_PIC_URL = "ADS_PIC_URL";

    /**廣告内容本地持久化存儲的key*/
    private static final String CACHE_ADS_JSON = "CACHE_ADS_JSON";

    /**廣告内容本地持久化存儲有效時間的key*/
    private static final String CACHE_ADS_TIME = "CACHE_ADS_TIME";

    /**本地持久化存儲 廣告圖片位置的key*/
    private static final String CACHE_ADS_INDEX = "CACHE_ADS_INDEX";

    /**跳轉到廣告詳情頁面的傳遞的url*/
    public static final String AD_DETAIL_URL = "AD_DETAIL_URL";

    /**跳轉到廣告詳情頁面 廣告上名稱*/
    public  static final String AD_DETAIL_LTD = "AD_DETAIL_LTD";

    //顯示廣告圖片的imageview
    private ImageView mIv_ad;

    /**限制用户不能多次点击的变量*/
    private boolean once = true;
    private boolean onceSkip = true;

    /**解决用户在skip即将加载完成的时候点击广告开启2个homeactivity的bug*/
    private boolean noPlayAdvertising = true;

    /**自定义的skipview的按钮“倒计时跳过”*/
    private SkipView mSkipView;

    private final static int SPLASH = 2019208;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //如果当前的activity 已经退出那么不做任何的处理
            if (isFinishing()){
                return;
            }

            //进入主页。
            toHome();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化ui控件
        mIv_ad = (ImageView)findViewById(R.id.iv_ad);
        mSkipView = (SkipView)findViewById(R.id.skipView);

        //加載網絡數據
        initData();
    }

    private void initData(){
       //獲取本地持久化存儲的數據
       String json = SpUtils.getString(getApplicationContext(),CACHE_ADS_JSON);
       //獲取本地持久化存儲的過期時間數據
       final long nextReq = SpUtils.getLong(getApplicationContext(),CACHE_ADS_TIME);
       //獲取系統當前時間
       long currentTimeMillis = System.currentTimeMillis();
       //如果緩存存在并且沒有過期-**********************有可能播放广告的处理逻辑**********************
       if (!TextUtils.isEmpty(json) && currentTimeMillis < nextReq){
           //有緩存，顯示圖片
            Log.e(getClass().getSimpleName(),"有緩存，顯示圖片");
           //展示圖片 圖片文件→需要下載地址→JavaBean對象→json字符串
           Gson gson = new Gson();
           AdListBean adListBean = gson.fromJson(json,AdListBean.class);
           List<AdListBean.AdsBean> ads = adListBean.getAds();
           //index不能寫死每次進來就自加，并且緩存起來
           int index = SpUtils.getInt(getApplicationContext(),CACHE_ADS_INDEX);//廣告圖片的展示位置（順序）
           final AdListBean.AdsBean adsBean = ads.get(index);
           String picUrl = adsBean.getRes_url().get(0);
           File file = new File(getExternalCacheDir(),picUrl.hashCode()+".jpg");
           Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
           mIv_ad.setImageBitmap(bitmap);
           //展示一次廣告++ 避免内容重複
           index++;
           index = index % ads.size();//簡單巧妙地避免數組越界的問題
          // Log.e(getClass().getSimpleName(),"==="+String.valueOf(index)+"----"+ads.size());
          // if (index >= ads.size() - 1){
          //     index = 0;
          // }else {
          //     index++;
          // }
           SpUtils.setInt(getApplicationContext(),CACHE_ADS_INDEX,index);


           //设置SkipView的 点击跳转
           mSkipView.setVisibility(View.VISIBLE);//让视图显示
           mSkipView.setmOnSkipListener(new SkipView.OnSkipListener() {
               @Override
               public void onSkip() {
                   //跳转页面
                   if (onceSkip && noPlayAdvertising){//onceSkip限制用户多次点击&&没有点击广告才能条转到home
                       onceSkip= false;
                       Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                       startActivity(intent);
                       finish();//跳转完成后关闭splash页面
                   }
               }
           });
           mSkipView.start();

       }else {//不存在請求網絡下載圖片-**********************不播放广告的处理逻辑**********************

           //-----------------------------暂时将页面跳转代码加在这里------------------------------
           //handle发送一个1秒钟延时的消息
           handler.sendEmptyMessageDelayed(SPLASH, 1000);
           //-----------------------------暂时将页面跳转代码加在这里------------------------------

           //展示圖片 圖片文件→需要下載地址→JavaBean對象→json字符串
           Gson gson = new Gson();
           AdListBean adListBean = gson.fromJson(json,AdListBean.class);
           if (adListBean != null){//避免空指針異常
               List<AdListBean.AdsBean> ads = adListBean.getAds();
               for (int i = 0; i < ads.size(); i++) {//遍歷並刪除之前的所有數據
                   final AdListBean.AdsBean adsBean = ads.get(i);
                   String picUrl = adsBean.getRes_url().get(0);
                   File file = new File(getExternalCacheDir(),picUrl.hashCode()+".jpg");
                   deleteFile(file);
               }
           }

           //下載數據
           requestData();
           //重新將index設置為0 否則會數組越界
           SpUtils.setInt(getApplicationContext(),CACHE_ADS_INDEX,0) ;
       }
    }

    //跳转到主页面
    private void toHome(){
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();//跳转完成后关闭splash页面
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
               //当前是再OK HTTP创建的子线程中要做操作一定要在主线程中
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       //请求失败-先提示用户没有联网-再处理跳转逻辑
                       Toast.makeText(MainActivity.this,"請求失败請檢查網絡",Toast.LENGTH_SHORT).show();
                   }
               });
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

                //將 bean對象 轉換為 json對象 這裏轉換之後比上面的字符串少很多只有有用到的内容
                String json = gson.toJson(adListBean);

                /**
                 *  先緩存下載到的字符串
                 *  知識點：數據的持久化保存 SP  數據庫 文件 網絡 内容提供者
                 *  這裏用的是SP
                 */
                SpUtils.setString(getApplicationContext(),CACHE_ADS_JSON,json);

                //緩存過期上限時間
                long timeMillis = System.currentTimeMillis();
                long nextReq = adListBean.getNext_req() * 60 *1000;//有效期字段分鐘轉毫秒
                SpUtils.setLong(getApplicationContext(),CACHE_ADS_TIME,timeMillis + nextReq);

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

    //删除文件夹及文件夹下所有文件
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            file.delete();
        } else if (file.exists()) {
            file.delete();
        }

    }

    @Override
    protected void onDestroy() {

        //当activity销毁的时候移除掉handle发送的消息合理的管理内存
        handler.removeCallbacksAndMessages(null);

        super.onDestroy();
    }
}


// requestData();-网络请求 之前用的HttpUrlConnection 现在用的OK HTTP

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


//點擊圖片跳轉到對應的廣告詳情 - 由于网易后端返回的url格式太混乱后来没有用
//  mIv_ad.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        //结束绘制skipview
//        mSkipView.stop();
//        if (once) {//限制用户多次点击开启多个页面
//        once = false;
//        noPlayAdvertising = false;//用户已经点击了广告
//        //ANR_LOG: >>> msg's executing time is too long 注释了下面这些耗时操作
//        //if (!CheckTheLinkNetwork.isNetPingUsable()){//用户没有开启网络
//        //  Toast.makeText(MainActivity.this,"請求失败請檢查網絡",Toast.LENGTH_SHORT).show();
//        //once = true;//用户没有开启网络 等用用户开启网络后还可以再次点击
//        //noPlayAdvertising = true;//没有网络用户可以再次点击
//        //return;
//        //}
//        new Thread() {
//@Override
//public void run() {
//        //判断网易返回的url是否正确（这里判断的是是否可达）
//        if (CheckTheLinkNetwork.checkUrl(adsBean.getAction_params().getLink_url(),
//        3000)) {
//        //正确回到主线程展示广告
//        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//        Intent intent2 = new Intent(getApplicationContext(), AdDetailActivity.class);
//        //adsBean.getAction_params().getLink_url()
//        intent2.putExtra(AD_DETAIL_URL, "009900");
//        intent2.putExtra(AD_DETAIL_LTD, adsBean.getContent() == null ? "廣告" : adsBean.getContent());
//        Intent[] intents = new Intent[]{intent, intent2};
//        /**
//         *startActivities 注意後面帶S 這個方法一口氣打開2個頁面
//         * 很適合現在的場景（用戶關閉廣告詳情頁新聞頁面就顯示出來了）
//         */
//        startActivities(intents);
//        finish();
//        }
//        });
//        } else {
//        //网易返回的url不正确广告后还让用户进入app
//        noPlayAdvertising = true;//状态改为没有点击广告
//        }
//        }
//        }.start();
//        }
//        }
//        });