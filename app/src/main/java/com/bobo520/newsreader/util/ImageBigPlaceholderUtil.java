package com.bobo520.newsreader.util;


import android.widget.ImageView;

import com.bobo520.newsreader.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Leon on 2019/2/5. Copyright © Leon. All rights reserved.
 * Functions: 图片加载工具类-懒汉式单例
 */
public class ImageBigPlaceholderUtil {

    private static volatile ImageBigPlaceholderUtil sInstance;

    private DisplayImageOptions mOptions;

    public static ImageBigPlaceholderUtil getSinstance(){

        if (sInstance == null){
            synchronized (ImageBigPlaceholderUtil.class){
                if (sInstance == null){
                    sInstance = new ImageBigPlaceholderUtil();
                }
            }
        }
        return sInstance;
    }

    //私有你构造方法单例
    private ImageBigPlaceholderUtil(){
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.splash) // resource or drawable
                .cacheInMemory(true) // default false
                .cacheOnDisk(true) // default false
                .build();
    }

    /**
     * 工具类中展示图片的方法
     * @param url 图片的地址
     * @param imageView 展示图片的控件
     */
    public void displayPic(String url, ImageView imageView){
        //设置图片
        ImageLoader.getInstance().displayImage(url,imageView,mOptions);
    }
}
