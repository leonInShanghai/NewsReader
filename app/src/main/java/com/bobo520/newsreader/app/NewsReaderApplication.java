package com.bobo520.newsreader.app;

import android.app.Application;
import android.content.Context;
import android.graphics.ImageDecoder;

import com.bobo520.newsreader.me.model.Model;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Leon on 2019/2/4. Copyright © Leon. All rights reserved.
 * Functions:  友盟的key 5ccac6b83fc195d00f0012e5
 */
public class NewsReaderApplication extends Application {


    //全局上下文
    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        //缓存目录 File.separator ：文件的分割符相当于 /
        File cacheDir = new File(getExternalCacheDir().getAbsolutePath()+File.separator+"pic");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480,800)
                .diskCacheExtraOptions(480,800,null)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                .diskCache(new UnlimitedDiskCache(cacheDir))//default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())//default
               // .imageDownloader(new BaseImageDownloader(this))//default
               // .imageDecoder(new BaseImageDecoder())//default
               // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())//default
               // .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);

        //初始化数据模型层类 极光推送消息本地创建数据库用到
        Model.getInstance().init(this);

        //初始化 JPush
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);


        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);

        //初始化 友盟 Ushare
       // UMConfigure.init(this,"5ccac6b83fc195d00f0012e5","umeng",
              //  UMConfigure.DEVICE_TYPE_PHONE,"");
        UMConfigure.init(this, "5ccac6b83fc195d00f0012e5", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
               "");
        UMShareAPI.get(this);
        //Config.DEBUG = true;
    }

    //各平台的配置建议放在全局 application 或程序入口 友盟官方就是这样写的
    {
        //AppID  AppSecret
       // PlatformConfig.setWeixin("111111","");

        //APP ID：101572053 APP Key：a57dfb9a54c2af792120514a64cb9733
        PlatformConfig.setQQZone("101572053 ","a57dfb9a54c2af792120514a64cb9733");
    }




        /**
         * 获取全局上下文的方法
         * @return mContext
         */
    public static Context getContext() {
        return mContext;
    }

}
