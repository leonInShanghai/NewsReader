package com.bobo520.newsreader;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.bobo520.newsreader.bean.AdListBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.bobo520.newsreader.MainActivity.ADS_PIC_URL;

/**
 *  Created by Leon on 2018/12/30. Copyright © Leon. All rights reserved.
 *  一个服务的子类，用于处理单独处理程序线程上的服务中的异步任务请求。
 * 辅助方法 - IntentService Service 的一个子类  会自动开启异步任务 任务完成后会自己释放
 */
public class DownloadIntentService extends IntentService {

    //构造方法
    public DownloadIntentService() {
        super("DownloadIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        //在这个方法就可以去做一些异步的任务-直接进行（耗时任务）网络请求 不用开子线程
        if (intent != null) {
           // Log.e("leon",Thread.currentThread().getName());-打印到的：IntentService[DownloadIntentService]
           AdListBean adListBean = (AdListBean)intent.getSerializableExtra(ADS_PIC_URL);

           //开始下载 12
           List<AdListBean.AdsBean> abs = adListBean.getAds();


            for (AdListBean.AdsBean ab : abs) {
                String picUrl = ab.getRes_url().get(0);
                //截取.jpg之前的字符串  https://yt-adp.ws.126.net/channel6/10802045_axwn_20181212.jpg?dpi=6401136
                if (picUrl.contains(".jpg")){
                    picUrl.substring(0, picUrl.indexOf(".jpg"));
                    //在下载之前先判断是否已存在该图片，已存在的图片不要下载
                    File file = new File(getExternalCacheDir(),picUrl.hashCode()+".jpg");
                    if (!file.exists()){//如果文件不存在 下载图片。
                        downloadPic(picUrl);
                    }else {//图片存在不做处理跳过本次循环
                        Log.e(getClass().getSimpleName(),"图片存在,不做下载跳过本次循环");
                        continue;
                    }
                }
            }
        }
    }

    /**下载图片的方法-下载到本地磁盘*/
    private void downloadPic(String picUrl) {

        /**
         * 准备图片文件输出流getExternalCacheDir() == sd卡中的对应当前应用的缓存目录
         *  getExternalCacheDir()优点：只要应用被删除目录，对应的目录也会自动删除
         *  getExternalCacheDir()不仅可以存储在sd卡 如果sd卡不存在 等同getCachDir() 存储在手机
         *  第二个参数是图片的名字:按照图片地址取名 picUrl.hashCode() 这样命名方便以后找出来用
         *  hashCode() 的特征当两个字符串的内容不同 那么它们的 hash值也不同
         */
        File file = new File(getExternalCacheDir(),picUrl.hashCode()+".jpg");
        //1创建okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2创建request对象
        Request request = new Request.Builder().url(picUrl).build();
        //3生成call对象
        Call call = okHttpClient.newCall(request);
        //4通过call对象来进行网络请求-这里用的同步，反正是后台任务，慢慢下不必要异步方法来同时进行下载
        try {
          Response response = call.execute();
          InputStream inputStream = response.body().byteStream();
          //保存图片文件
          Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
          //输出流
          FileOutputStream fileOutputStream = new FileOutputStream(file);
          bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
          Log.e(getClass().getSimpleName(),"downloadPic"+"下载广告图片完毕:"+file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
