package com.bobo520.newsreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        super.onDestroy();
    }
}





/* 设置沉浸式-狀態欄的背景跟隨app界面的背景-覺得這個功能好 這段代碼在這個項目沒有用到*/
//    private void setImmersive() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
//        //得到状态栏margin-top高度
//        statusBarHeightParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//        statusBarHeightParams.setMargins(0, UiUtils.getStatusBarHeight(), 0, 0);
//        llBody.setLayoutParams(statusBarHeightParams);
//    }

