package com.bobo520.webviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by 求知自学网 on 2019/3/17 Copyright © Leon. All rights reserved.
 * Functions:webview的基本使用
 */
public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView)findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.qq.com");
    }


}
