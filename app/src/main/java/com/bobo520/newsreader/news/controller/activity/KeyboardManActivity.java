package com.bobo520.newsreader.news.controller.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bobo520.newsreader.util.Constant;
import com.bobo520.newsreader.R;
import com.bobo520.newsreader.news.controller.adapter.NewsCommectAdapter;
import com.bobo520.newsreader.news.model.NewsCommentBean;
import com.bobo520.newsreader.customDialog.LEloadingView;
import com.bobo520.newsreader.http.HttpHelper;
import com.bobo520.newsreader.http.OnResponseListener;
import com.bobo520.newsreader.util.JsonUtil;
import com.bobo520.newsreader.util.LETrtStBarUtil;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static com.bobo520.newsreader.news.controller.activity.NewsDetailActivity.NEWS_ID;

/**
 * Created by 求知自学网 on 2019/3/24 Copyright © Leon. All rights reserved.
 * Functions: 评论控制器
 */
public class KeyboardManActivity extends SwipeBackActivity {

    /**上一页面传递过来的新闻id*/
    private String mNewsId;

    /**左上角的返回按钮*/
    private ImageButton mBackImageButton;

    /**中间显示内容的list view*/
    private ListView mListViewReply;

    /**左下角的发送文本框 其实应该是按钮才对*/
    private TextView mTvSendReply;

    /**写评论内容的输入框*/
    private EditText mEtReply;

    /**网络加载时loding好看的动画*/
    private KProgressHUD mKProgressHUD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_man);

        //Leon设置了最近淘宝等各大app流行的沉浸式状态栏
        LETrtStBarUtil.setTransparentToolbar(this);

        Intent intent = getIntent();
        if (intent != null){
            mNewsId = intent.getStringExtra(NEWS_ID);
        }

        //实例化UI控件
        initView();

        //实例化数据
        initData();
    }

    //实例化数据-只实现热门跟帖
    private void initData(){

        //判断如果上一页面传递过来的新闻id为空就没有必要网络请求了
        if (TextUtils.isEmpty(mNewsId)){ return; }

        //开始网络请求-开始显示loading
        mKProgressHUD = KProgressHUD.create(KeyboardManActivity.this)
                .setCustomView(new LEloadingView(KeyboardManActivity.this))
                .setLabel("Please wait", Color.GRAY)
                .setBackgroundColor(Color.WHITE)
                .show();

        //根据每条新闻的id动态生成请求的url
        String replyUrl = Constant.getNewsReplyUrl(mNewsId);

        //开始发起get请求
        HttpHelper.getInstance().requestGET(replyUrl, new OnResponseListener() {
            @Override
            public void onFail(IOException e) {
                //loading结束（无论成功失败本次发起请求已经结束）
                mKProgressHUD.dismiss();
            }

            @Override
            public void onSuccess(String response) {
                //loading结束（无论成功失败本次发起请求已经结束）
                mKProgressHUD.dismiss();

                //准备一个集合出来
                ArrayList<NewsCommentBean> newsCommentList = new ArrayList<>();

                //解析起来首先需要手动解析id
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //1.分为两块，第一块先解析commentIds,评论的id数组，第二块comments这些评论jsonObject
                    JSONArray commentIds = jsonObject.getJSONArray("commentIds");
                    JSONObject comments = jsonObject.getJSONObject("comments");

                    //2解析commentIds,把这些id都读取出来
                    for (int i = 0;i < commentIds.length();i++){
                        //这里直接getString(i)就不需要再将对象转string了
                        String commentId = commentIds.getString(i);

                        //3.根据这些id来解析第二块comments中的各个评论 getJSONObject
                        //id可能两个并排在一起"56415914,57116175"，此时取出最后面的id进行使用
                        if (commentId.contains(",")){
                            //将56415914,57116175"从“，”剪切成N段
                            String[] split = commentId.split(",");
                            //只要最后一段
                            commentId = split[split.length - 1];
                        }
                        JSONObject comment = comments.optJSONObject(commentId);

                        //4.拿到各个匹配的string数据，通过三方库gson来解析成为JavaBean
                        String s = comment.toString();
                        NewsCommentBean newsCommentBean = JsonUtil.parseJson(s, NewsCommentBean.class);
                        newsCommentList.add(newsCommentBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //设置list view数据
                setListViewData(newsCommentList);
            }
        });


    }

    //设置list view数据（mListViewReply数据）
    private void setListViewData(ArrayList<NewsCommentBean> newsCommentList) {

        //将数据传递给adapter并将 adapter设置给list view
        NewsCommectAdapter newsCommectAdapter = new NewsCommectAdapter(newsCommentList);
        mListViewReply.setAdapter(newsCommectAdapter);
    }

    //实例化UI控件
    private void initView() {

        mBackImageButton = (ImageButton)findViewById( R.id.back_imageButton );
        mBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户点击了左上角返回按钮 返回上一页
                finish();
            }
        });

        mListViewReply = (ListView)findViewById( R.id.listView_reply );
        mTvSendReply = (TextView)findViewById( R.id.tv_send_reply );
        mEtReply = (EditText)findViewById( R.id.et_reply );
    }
}

