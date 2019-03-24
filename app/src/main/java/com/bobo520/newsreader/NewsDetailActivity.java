package com.bobo520.newsreader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo520.newsreader.bean.NewsDetailBean;
import com.bobo520.newsreader.customDialog.CfLoadingView;
import com.bobo520.newsreader.http.HttpHelper;
import com.bobo520.newsreader.http.OnResponseListener;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static com.bobo520.newsreader.ShowPicActivity.IMG_LIST;
import static com.bobo520.newsreader.ShowPicActivity.IMG_INDEX;

/**
 * Created by 求知自学网 on 2019/3/3 Copyright © Leon. All rights reserved.
 * Functions: 新闻详情的activity 主要展示内容的控件是webview
 */
public class NewsDetailActivity extends SwipeBackActivity implements View.OnClickListener {

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

    /**解析json数据用的JavaBean*/
    private NewsDetailBean mNewsDetailBean;

    /**左上角的返回按钮*/
    private ImageButton mBackButton;

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

        //add JavaScriptInterfacre 来进行交互 第二个参数 name 是别名可以随便写但是JavaScript用的时候要一致
        mWebView.addJavascriptInterface(this,"demo");

        //实例化左上角返回按钮并设置点击事件的监听对象
        mBackButton = (ImageButton)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(this);

    }


    /**跳转到展示图片的activity  Javascript中调用 window.demo.showPic(index)
     * @param index 用户点击图片的索引
     */
    @JavascriptInterface
    public void showPic(int index){

        //传递数据
        if (mNewsDetailBean != null){
            Intent intent = new Intent(getApplicationContext(),ShowPicActivity.class);
            List<NewsDetailBean.ImgBean> imgList = mNewsDetailBean.getImg();

            //传递图片数据集合需要序列化才能传递
            intent.putExtra(IMG_LIST, (Serializable) imgList);

            //传递图片的索引
            intent.putExtra(IMG_INDEX,index);

            //开启图片详情页
            startActivity(intent);
        }
    }

    /**
     * 请求新闻详情的数据
     */
    private void initData(){

        Log.e("mNewsId==",mNewsId+",,,,,,,,,,,,,,,,,");

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
                Toast.makeText(NewsDetailActivity.this,"网易加密新闻解析异常"
                        ,Toast.LENGTH_SHORT).show();
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
                    mNewsDetailBean = gson.fromJson(realJson, NewsDetailBean.class);
                    setWebViewData(mNewsDetailBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //webview加载数据
    private void setWebViewData(NewsDetailBean newsDetailBean){
        
        //加载服务器端返回的数据拼接成为string来进行展示
        if (newsDetailBean != null){
            String body = newsDetailBean.getBody();
            mWebView.loadDataWithBaseURL(null,body,"text/html",
                    "utf-8",null);


            mTvReply.setText(newsDetailBean.getReplyCount()+"");


            //加载标题
            String titleHTML = "<p style = \"margin:25px 0px 0px 0px\"><span style='font-size:22px;'>" +
                    "<strong>" + newsDetailBean.getTitle() + "</strong></span></p>";// 标题
            titleHTML = titleHTML+ "<p><span style='color:#999999;font-size:14px;'>"+
                    newsDetailBean.getSource()+"&nbsp&nbsp"+newsDetailBean.getPtime()+"</span></p>";//来源与时间
            titleHTML = titleHTML+"<div style=\"border-top:1px dotted #999999;margin:20px 0px\">" +
                    "</div>";//加条虚线
            //设置正文的字体
            body = "<div style='line-height:180%;'><span style='font-size:15px;'>"+body+"</span></div>";

            body = titleHTML+body;

            //替换显示图片
            List<NewsDetailBean.ImgBean> imgList = newsDetailBean.getImg();
            for (int i = 0; i < imgList.size(); i++) {
                NewsDetailBean.ImgBean imgBean = imgList.get(i);
                String ref = imgBean.getRef();
                String src = imgBean.getSrc();
                //在这里把索引i传递送给JavaScript
                src = "<img src = '"+src+"' onclick = 'show("+i+")'></img>";
                body = body.replace(ref,src);
            }

            //修改图片的宽度-使得富文本中图片的宽度不会超过屏幕的宽度
            body = "<html><head><style>img{width:100%}</style>" +
                    "<script>function show(index){window.demo.showPic(index);}</script>"
                    +"</head>"+body+"</html>";

            mWebView.loadDataWithBaseURL(null,body,"text/html",
                    "utf-8",null);
        }

        //mWebView.loadUrl(newsDetailBean.getShareLink());
    }


    @Override
    public void onClick(View v) {
        //用户点击了左上角的返回按钮
        if (v == mBackButton){
            finish();
        }
    }
}

/**
 *  //替换显示图片
 ArrayList<ImgBean> imgList = newsDetailBean.getImg();
 for (int i = 0; i < imgList.size(); i++) {
 ImgBean imgBean = imgList.get(i);
 String ref = imgBean.getRef();
 String src = imgBean.getSrc();
 src = "<img src='"+src+"' onclick='show("+i+")'></img>";
 body = body.replace(ref,src);
 }
 */
