package com.bobo520.newsreader.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.util.LETrtStBarUtil;

import static com.bobo520.newsreader.app.MainActivity.AD_DETAIL_LTD;
import static com.bobo520.newsreader.app.MainActivity.AD_DETAIL_URL;

/**
 * Created by Leon on 2019/1/1 Copyright © Leon. All rights reserved.
 * Functions: 开源社区的展示页面（GitHub的展示页面）
 */
public class OpenSourceActivity extends Activity {

    /**webview上面的進度條 可以用但是没有用 为了适配轩尼诗的广告*/
    private ProgressBar pbProgress;

    /**加載廣告詳情的webview*/
    private WebView mWebView;

    //加载失败时提示的image view
    private ImageView mErrorView;

    //是否要显示加载错的页面的变量
    private boolean loadError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opensource);

        //Leon设置了最近淘宝等各大app流行的沉浸式状态栏
        LETrtStBarUtil.setTransparentToolbar(this);

        mWebView = (WebView) findViewById(R.id.webView);
        pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
        mErrorView = (ImageView)findViewById(R.id.mErrorView);


        //webview顯示網頁的url
        String url = "https://github.com/leonInShanghai";

        /**
         * 發現網易的url會重定向-即你輸入一個鏈接他會自動跳轉到另外一個鏈接
         * 重定向解決方法: mWebView.setWebViewClient(new WebViewClient());
         */
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

                //加载失败loadError = true
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.setVisibility(View.GONE);
                mErrorView.setVisibility(View.VISIBLE);
                loadError = true;
            }

                //低版本手机会走这个（过时的）方法
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    view.setVisibility(View.GONE);
                    mErrorView.setVisibility(View.VISIBLE);
                    loadError = true;
                }


                @Override
                public void onPageFinished(WebView view, String url) {//加载成功
                    super.onPageFinished(view, url);

                    //严谨起见还是在做判断
                    if (loadError != true) {
                        //显示错误页面
                        mErrorView.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);
                    }

                }
            });

            mWebView.loadUrl(url);
            //设置自适应任意大小的
            // pc网页-解决了轩尼诗广告不能加载的问题 还要让进度条默认不显示才解决了 ↓
            //解决步骤二  -----→    --------------------------------- ProgressBar android:visibility="gone"
            mWebView.getSettings().setUseWideViewPort(true);
            // mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            //允许js运行-webView默認是不會加載js的
            mWebView.getSettings().setJavaScriptEnabled(true);
            //开启本地DOM存储-解决网易严选不能加载的问题
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setBlockNetworkImage(false);
            mWebView.getSettings().setLoadWithOverviewMode(true);


            //监听网页加载-讓進度條發揮作用（这里的进度条其实不会发挥作用View.GONE了）
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress >= 100) {
                        // 网页加载完成
                        pbProgress.setVisibility(View.GONE);
                    } else {
                        // 加载中 轩尼诗广告不能加载 改成加载错误显示错误不用进度条以后其他地方可以用
                        //pbProgress.setVisibility(View.VISIBLE);
                        pbProgress.setProgress(newProgress);
                    }
                    super.onProgressChanged(view, newProgress);
                }

            });

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
        //mWebView.destroy();

        ///销毁webview比较正规的写法
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }

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


/** 尝试了很多方法都加载不了下面这个网页 加载图片-解决轩尼诗广告不能加载的问题 有空接着搞
 *mWebView.loadUrl("http://clickc.admaster.com.cn/c/a121927,b3163300,c3078,i0,m101,8a2,8b1,0a__OS__,z__IDFA__,0c__IMEI__,h");
 *  // 支持获取手势焦点
 mWebView.requestFocusFromTouch();
 mWebView.setHorizontalFadingEdgeEnabled(true);
 mWebView.setVerticalFadingEdgeEnabled(false);
 mWebView.setVerticalScrollBarEnabled(false);
 // 支持JS
 mWebView.getSettings().setJavaScriptEnabled(true);
 mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
 mWebView.getSettings().setBuiltInZoomControls(true);
 mWebView.getSettings().setDisplayZoomControls(true);
 mWebView.getSettings().setLoadWithOverviewMode(true);
 // 支持插件
 mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
 mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
 // 自适应屏幕
 mWebView.getSettings().setUseWideViewPort(true);
 mWebView.getSettings().setLoadWithOverviewMode(true);
 // 支持缩放
 mWebView.getSettings().setSupportZoom(false);//就是这个属性把我搞惨了，
 // 隐藏原声缩放控件
 mWebView.getSettings().setDisplayZoomControls(false);
 // 支持内容重新布局
 mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
 mWebView.getSettings().supportMultipleWindows();
 mWebView.getSettings().setSupportMultipleWindows(true);
 // 设置缓存模式
 mWebView.getSettings().setDomStorageEnabled(true);
 mWebView.getSettings().setDatabaseEnabled(true);
 mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
 mWebView.getSettings().setAppCacheEnabled(true);
 mWebView.getSettings().setAppCachePath(mWebView.getContext().getCacheDir().getAbsolutePath());
 // 设置可访问文件
 mWebView.getSettings().setAllowFileAccess(true);
 mWebView.getSettings().setNeedInitialFocus(true);
 // 支持自定加载图片
 if (Build.VERSION.SDK_INT >= 19) {
 mWebView.getSettings().setLoadsImagesAutomatically(true);
 } else {
 mWebView.getSettings().setLoadsImagesAutomatically(false);
 }
 mWebView.getSettings().setNeedInitialFocus(true);
 // 设定编码格式
 mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
 */



/* 设置沉浸式-狀態欄的背景跟隨app界面的背景-覺得這個功能好 這段代碼在這個項目沒有用到*/
//    private void setImmersive() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
//        //得到状态栏margin-top高度
//        statusBarHeightParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//        statusBarHeightParams.setMargins(0, UiUtils.getStatusBarHeight(), 0, 0);
//        llBody.setLayoutParams(statusBarHeightParams);
//    }

