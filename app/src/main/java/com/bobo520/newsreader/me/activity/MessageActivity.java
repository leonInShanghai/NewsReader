package com.bobo520.newsreader.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.bobo520.newsreader.R;

import com.bobo520.newsreader.util.Constant;
import com.bobo520.newsreader.util.LETrtStBarUtil;

public class MessageActivity extends Activity {


    //广播收到后台推送的小消息后改变铃铛你的状态
    private LocalBroadcastManager mLBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        LETrtStBarUtil.setTransparentToolbar(this);
        //创建一个发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(this);

    }

    @Override
    public void onBackPressed() {
        //发送广播-用户直接进入了消息详情页
        mLBM.sendBroadcast(new Intent(Constant.USER_ENTERS_MESSAGE_DETAILS_PA));
        super.onBackPressed();
    }
}
