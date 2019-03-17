package com.bobo520.webviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by 求知自学网 on 2019/3/17 Copyright © Leon. All rights reserved.
 * Functions:webview的基本使用
 */
public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    //是否要显示加载错的页面的变量
    private boolean loadError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView)findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);

        //腾讯网站做了手脚设置重定向后加载an unexpected error has occurred
        //mWebView.loadUrl("https://www.qq.com");

        //加载百度
        //mWebView.loadUrl("https://www.baidu.com");

        //加载本地HTML 固定写法：mWebView.loadUrl("file///android_asset/xxx.html");
        mWebView.loadUrl("file:///android_asset/my.html");

        //加载数据
        //mWebView.loadDataWithBaseURL(null, "<head><body>aaa</body></head>",
                //"text/html", "utf-8",null);

        //重定向的话，自动跳转其他页面时，会默认跳转到其他的应用去，比如：系统浏览器（能够打开和处理网业的应用）
        //解决方案
        mWebView.setWebViewClient(new WebViewClient());

        //Java和JavaScript交互的第一种方案
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.e("弹出了弹框","onJsAlert:"+message);

                //点击js弹框上的确定
                result.confirm();
                //return true;就拦截掉弹框了
                return true;
                //return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                //相比onJsAlert而言，有一个Boolean类型返回值可以直接用Java代码来指定，不需要手动点击

                //如果不希望弹框弹出 return true
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

               //相对于onJsConfirm来说，它也有返回值，并且不局限于Boolean类型，可以时string


                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });

        //java和JavaScript交互的第二种方案
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

                //加载失败loadError = true
                super.onReceivedError(view, errorCode, description, failingUrl);

                loadError = true;
            }

            //低版本手机会走这个（过时的）方法
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                loadError = true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {//加载成功
                super.onPageFinished(view, url);

                //严谨起见还是在做判断
                if (loadError != true) {
                    //显示错误页面

                    view.setVisibility(View.VISIBLE);
                }

            }
        });


        //Java和JavaScript交互的第三种方案
        //准备一个交互的桥接类addJavascriptInterface(要调用的Java方法的实例,该类的别名)
        mWebView.addJavascriptInterface(MainActivity.this,"alias");
    }

    //凡是提供给js去调用的方法，该方法前面必须要有这个注解
    @JavascriptInterface
    public void show(String text){
        Toast.makeText(MainActivity.this,"在Java代码中弹出吐司"+text,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        //点击后退后
        if (mWebView.canGoBack()){
            mWebView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
