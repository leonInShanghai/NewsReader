package com.bobo520.newsreader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo520.newsreader.bean.NewsDetailBean;
import com.bobo520.newsreader.customDialog.CfLoadingView;
import com.bobo520.newsreader.http.HttpHelper;
import com.bobo520.newsreader.http.OnResponseListener;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

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

    /**网络加载时loding好看的动画*/
    private KProgressHUD mKProgressHUD;

    //webview
    private WebView mWebView;

    //文本输入框et_reply
    private EditText mEtReply;

    //多少人跟帖的textview右边是图tv_reply
    private TextView mTvReply;

    //分享的imageView iv_share
    private ImageView mIvShare;

    //发送的textView tv_send_reply
    private TextView mTvSendReply;

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

        initView();
        initData();
    }

    private void initView(){
        //webview
        mWebView = (WebView)findViewById(R.id.webView);

        //文本输入框et_reply
        mEtReply = (EditText)findViewById(R.id.et_reply);

        //多少人跟帖的textview右边是图tv_reply
        mTvReply = (TextView)findViewById(R.id.tv_reply);

        //分享的imageView iv_share
        mIvShare =  (ImageView)findViewById(R.id.iv_share);

        //发送的textView tv_send_reply
        mTvSendReply = (TextView)findViewById(R.id.tv_send_reply);

        //设置webview允许执行JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
    }

    /**
     * 请求新闻详情的数据
     */
    private void initData(){
        //避免空指针判断
        if (TextUtils.isEmpty(mNewsId)){ return; }

        //开始网络请求-开始显示loading
        mKProgressHUD = KProgressHUD.create(NewsDetailActivity.this)
                .setCustomView(new CfLoadingView(NewsDetailActivity.this))
                .setLabel("Please wait", Color.GRAY)
                .setBackgroundColor(Color.WHITE)
                .show();


        //动态生成当前新闻的详情url
        String newsDetailUrl = Constant.getNewsDetailUrl(mNewsId);

        HttpHelper.getInstance().requestGET(newsDetailUrl, new OnResponseListener() {
            @Override
            public void onFail(IOException e) {

                //loading结束（无论成功失败本次发起请求已经结束）
                mKProgressHUD.dismiss();

            }

            @Override
            public void onSuccess(String response) {

                //loading结束（无论成功失败本次发起请求已经结束）
                mKProgressHUD.dismiss();

                //解析json GosnFragment → JavaBean
                //解析json的时候因为键名是动态变化的，所有不能直接使用JavaBean解析
                //手动来解析org.json
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String realJson = jsonObject.getString(mNewsId);
                    //获得到标准格式可以转为JavaBean格式的json
                    Gson gson = new Gson();
                    NewsDetailBean newsDetailBean = gson.fromJson(realJson, NewsDetailBean.class);
                    setWebViewData(newsDetailBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //webview加载数据
    private void setWebViewData(NewsDetailBean newsDetailBean){
        mWebView.loadUrl(newsDetailBean.getShareLink());
    }
}
