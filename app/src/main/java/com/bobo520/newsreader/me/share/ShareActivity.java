package com.bobo520.newsreader.me.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.util.LELog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        findViewById(R.id.share_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

    String describe = "内存小，省流量，使用流畅，为你提供有温度有价值的新闻资讯。\n" +
            "\n" +
            "　　高品质内容传递有温度有价值的新闻资讯。\n" +
            "\n" +
            "　　热点及时达热点新闻，及时聚焦，一键刷新即可触达。\n" +
            "\n" +
            "　　海量内容源时事，热点，视频，体育，财经，时尚，美女，汽车等海量内容等你来看。";


    ShareUtil.ShareWeb(ShareActivity.this, "1", "NewsReader",
            "https://github.com/leonInShanghai/NewsReader", "这是描述",
            R.drawable.login_icon, new UMShareListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {
                    LELog.showLogWithLineNum(5,share_media.toString());
                }

                @Override
                public void onResult(SHARE_MEDIA share_media) {
                    LELog.showLogWithLineNum(5,share_media.toString());
                }

                @Override
                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                    LELog.showLogWithLineNum(5,share_media.toString());
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media) {
                    LELog.showLogWithLineNum(5,share_media.toString());
                }
            });






                findViewById(R.id.share_auth).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UMShareAPI.get(ShareActivity.this).getPlatformInfo(ShareActivity.this,
                                SHARE_MEDIA.QZONE, new UMAuthListener() {
                                    @Override
                                    public void onStart(SHARE_MEDIA share_media) {
                                        LELog.showLogWithLineNum(5,share_media.toString());
                                    }

                                    @Override
                                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                                        LELog.showLogWithLineNum(5,share_media.toString());
                                    }

                                    @Override
                                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                                        LELog.showLogWithLineNum(5,"ERROR-SHARE");
                                    }

                                    @Override
                                    public void onCancel(SHARE_MEDIA share_media, int i) {
                                        LELog.showLogWithLineNum(5,share_media.toString());
                                    }
                                });
                    }
                });
            }
        });


        /**分享连接的方法
         * UMWeb  web = new UMWeb(Defaultcontent.url);
            web.setTitle("This is music title");//标题
            web.setThumb(thumb);  //缩略图
            web.setDescription("my description");//描述

         new ShareAction(ShareActivity.this)
            .withMedia(web)
             .share();
         */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UMShareAPI.get(ShareActivity.this).onActivityResult(requestCode,resultCode,data);
    }


}


//      友盟官方的老写法
//                //这里的代码是使用的方法：SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN_CIRCLE
//                new ShareAction(ShareActivity.this).setDisplayList(SHARE_MEDIA.QQ)
//                        .withText("hello").setCallback(new UMShareListener() {
//                    @Override
//                    public void onStart(SHARE_MEDIA share_media) {
//                        LELog.showLogWithLineNum(5,share_media.toString());
//                    }
//
//                    @Override
//                    public void onResult(SHARE_MEDIA share_media) {
//                        LELog.showLogWithLineNum(5,share_media.toString());
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                        LELog.showLogWithLineNum(5,share_media.toString());
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media) {
//                        LELog.showLogWithLineNum(5,share_media.toString());
//                    }
//                }).open();