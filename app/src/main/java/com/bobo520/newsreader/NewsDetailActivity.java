package com.bobo520.newsreader;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bobo520.newsreader.http.HttpHelper;
import com.bobo520.newsreader.http.OnResponseListener;

import java.io.IOException;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by 求知自学网 on 2019/3/3 Copyright © Leon. All rights reserved.
 * Functions: 新闻详情的activity 主要展示内容的控件是webview
 */
public class NewsDetailActivity extends SwipeBackActivity {

    /**activity之间传值的key*/
    public static final String NEWS_ID = "NEWS_ID";

    /**新闻详情中的id*/
    private String mNewsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);

        //Leon设置了最近淘宝等各大app流行的沉浸式状态栏
        LETrtStBarUtil.setTransparentToolbar(this);

        //为了能够展示对应的点击新闻，需要传递新闻对应的id给我
        Intent intent = getIntent();
        if (intent != null){
           mNewsId = intent.getStringExtra(NEWS_ID);
        }

        initData();
    }

    /**
     * 请求新闻详情的数据
     */
    private void initData(){
        //避免空指针判断
        if (TextUtils.isEmpty(mNewsId)){ return; }

        //动态生成当前新闻的详情url
        String newsDetailUrl = Constant.getNewsDetailUrl(mNewsId);

        HttpHelper.getInstance().requestGET(newsDetailUrl, new OnResponseListener() {
            @Override
            public void onFail(IOException e) {

            }

            @Override
            public void onSuccess(String response) {
                LELog.showLogWithLineNum(5,response);
            }
        });

    }
}
