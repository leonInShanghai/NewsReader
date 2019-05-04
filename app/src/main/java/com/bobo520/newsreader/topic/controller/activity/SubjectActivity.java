package com.bobo520.newsreader.topic.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.topic.model.TopicBean;
import com.bobo520.newsreader.util.LETrtStBarUtil;


import me.imid.swipebacklayout.lib.app.SwipeBackActivity;



public class SubjectActivity extends SwipeBackActivity implements View.OnClickListener {

    /**用来传递 对象的 key*/
    public static final String SUBJECT_OBJECT = "SUBJECT_OBJECT";

    /**话题详情对象 html 中的body*/
    private String content;

    /**左上角的返回按钮*/
    private ImageButton backButton;

    /**用来展示内容的webView*/
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        //Leon设置了最近淘宝等各大app流行的沉浸式状态栏
        LETrtStBarUtil.setTransparentToolbar(this);

        backButton = (ImageButton)findViewById(R.id.back_button);
        mWebView = (WebView)findViewById(R.id.subject_webView);
        backButton.setOnClickListener(this);



        //获话题详情对象
        Intent intent = getIntent();

        if (intent != null){
            TopicBean.DataBean dataBean = (TopicBean.DataBean) intent.getSerializableExtra(SUBJECT_OBJECT);
            //加了img自适应宽高的代码
            content = "<html><head><meta charset='utf-8'/><title></title><style>img{ max-width: 100%; height: auto; width: auto; width: auto; }" +
                    "</style></head><body>"+ dataBean.getContent()+ "</body></html>";

            /* 测试代码写了 很多a标签测试过滤a标签的有效性
            content = "<html><head><meta charset='utf-8'/><title></title></head><body>"+
                    "<a href=\"http://blog.csdn.net/lmj623565791/\" rel=\"nofollow\" target" +
                    "=\"_blank\"><u><span style=\"color:#0066cc;\">张鸿洋的博客</span><br>" +
                    "</u></a><a href=\"http://blog.csdn.net/lmj623565791/\" rel=\"nofollow\"" +
                    " target=\"_blank\"><u><span style=\"color:#0066cc;\">张鸿洋的博客</span>" +
                    "<br></u></a><a href=\"http://blog.csdn.net/lmj623565791/\" rel=\"nofollow" +
                    "\" target=\"_blank\"><u><span style=\"color:#0066cc;\">张鸿洋的博客</span>" +
                    "<br></u></a><a href=\"http://blog.csdn.net/lmj623565791/\" rel=\"nofollow" +
                    "\" target=\"_blank\"><u><span style=\"color:#0066cc;\">张鸿洋的博客</span>" +
                    "<br></u></a>"+ "</body></html>";
                    */

            String body = "";

            //判断是否包含a标签  避免indexOf("</a>") 越界
            if (content.contains("</a>")) {
               // Toast.makeText(SubjectActivity.this,"包含a标签",Toast.LENGTH_SHORT).show();
                //----------------------------将被a标签包括的文字转换为普通文字-------------------------
                int startIndex = content.indexOf("<a");
                int endIndex = content.indexOf("</a>");
                String aStr = content.substring(startIndex, endIndex + "</a>".length());
                //<a class="relatedlink" href="http://www.xyjun.com/" target="_blank">襄阳</a>

                int startAIndex = aStr.indexOf(">");
                int endAIndex = aStr.indexOf("</");
                String characters = aStr.substring(startAIndex + 1, endAIndex);
                //襄阳

                //将被a标签包括的文字转换为普通文字
                body = content.replace(aStr, characters);
            //----------------------------将被a标签包括的文字转换为普通文字-------------------------
            }else{
               // Toast.makeText(SubjectActivity.this,"no 包含a标签",Toast.LENGTH_SHORT).show();
                body = content;
            }

            //设置webview允许执行JavaScript
            mWebView.getSettings().setJavaScriptEnabled(false);

            //加载字符串网页
            mWebView.loadDataWithBaseURL(null,body,"text/html",
                    "utf-8",null);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == backButton){
            finish();
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
