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

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Leon on 2019/2/4. Copyright © Leon. All rights reserved.
 * Functions:
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
    }


    /**
     * 获取全局上下文的方法
     * @return mContext
     */
    public static Context getContext() {
        return mContext;
    }

}
