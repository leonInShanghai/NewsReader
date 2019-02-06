package com.bobo520.newsreader.app;

import android.app.Application;
import android.graphics.ImageDecoder;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

/**
 * Created by Leon on 2019/2/4. Copyright © Leon. All rights reserved.
 * Functions:
 */
public class NewsReaderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

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
    }
}
