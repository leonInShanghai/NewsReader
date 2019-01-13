package com.bobo520.newsreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import static com.bobo520.newsreader.MainActivity.AD_DETAIL_URL;
import static com.bobo520.newsreader.MainActivity.AD_DETAIL_LTD;

/**
 * Created by Leon on 2019/1/1 Copyright © Leon. All rights reserved.
 * Functions: 點擊splash頁面跳轉的 廣告詳情頁面
 */
public class AdDetailActivity extends Activity {

    /**webview上面的進度條*/
    private ProgressBar pbProgress;

    /**加載廣告詳情的webview*/
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail);
        mWebView = (WebView)findViewById(R.id.webView);
        pbProgress = (ProgressBar) findViewById(R.id.pb_progress);

        //取出上一頁面傳遞的數據
        Intent intent = getIntent();
        if (intent != null){
          //webview顯示網頁的url
          String url =  intent.getStringExtra(AD_DETAIL_URL);
          //廣告商名稱-用在titlebar中間的title
          String titleStr = intent.getStringExtra(AD_DETAIL_LTD);

          /**
          * 發現網易的url會重定向-即你輸入一個鏈接他會自動跳轉到另外一個鏈接
           * 重定向解決方法: mWebView.setWebViewClient(new WebViewClient());
          */
          mWebView.setWebViewClient(new WebViewClient());
          mWebView.loadUrl(url);


          //允许js运行-webView默認是不會加載js的
          mWebView.getSettings().setJavaScriptEnabled(true);
          //开启本地DOM存储-解决网易严选不能加载的问题
          mWebView.getSettings().setDomStorageEnabled(true);
          //监听网页加载-讓進度條發揮作用
          mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        // 网页加载完成
                        pbProgress.setVisibility(View.GONE);
                    } else {
                        // 加载中
                        pbProgress.setProgress(newProgress);
                    }
                    super.onProgressChanged(view, newProgress);
                }
           });

        }
    }

    @Override
    public void onBackPressed() {
        //判斷webview能夠返回上一個網頁
        if (mWebView.canGoBack()){
            //返回上一個網頁
            mWebView.goBack();
        }else {
            super.onBackPressed();//返回上一頁finish();
        }
    }

    @Override
    protected void onDestroy() {
        //這裏解決本頁面finish后音樂還在播放的問題
        mWebView.destroy();

        ///销毁webview比较正规的写法
//        if (mWebView != null) {
//            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//            mWebView.clearHistory();
//
//            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
//            mWebView.destroy();
//            mWebView = null;
//        }

        super.onDestroy();
    }
}


/**
 * webView不能加载url问题的解决方法
 mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
 mWebSettings.setJavaScriptEnabled(true);//是否允许JavaScript脚本运行，默认为false。设置true时，会提醒可能造成XSS漏洞
 mWebSettings.setSupportZoom(true);//是否可以缩放，默认true
 mWebSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
 mWebSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
 mWebSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
 mWebSettings.setAppCacheEnabled(true);//是否使用缓存
 mWebSettings.setDomStorageEnabled(true);//开启本地DOM存储
 mWebSettings.setLoadsImagesAutomatically(true); // 加载图片
 mWebSettings.setMediaPlaybackRequiresUserGesture(false);//播放音频，多媒体需要用户手动？设置为false为可自动播放
 ---------------------
 作者：Esmussssein
 来源：CSDN
 原文：https://blog.csdn.net/qq_39071530/article/details/82586847
 版权声明：本文为博主原创文章，转载请附上博文链接！
 *
 */




/* 设置沉浸式-狀態欄的背景跟隨app界面的背景-覺得這個功能好 這段代碼在這個項目沒有用到*/
//    private void setImmersive() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
//        //得到状态栏margin-top高度
//        statusBarHeightParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//        statusBarHeightParams.setMargins(0, UiUtils.getStatusBarHeight(), 0, 0);
//        llBody.setLayoutParams(statusBarHeightParams);
//    }

