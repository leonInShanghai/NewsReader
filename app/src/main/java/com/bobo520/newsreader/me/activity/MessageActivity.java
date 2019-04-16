package com.bobo520.newsreader.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bobo520.newsreader.R;

import com.bobo520.newsreader.me.adapter.InviteAdapter;
import com.bobo520.newsreader.me.model.Model;
import com.bobo520.newsreader.me.model.bean.InvationInfo;
import com.bobo520.newsreader.util.Constant;
import com.bobo520.newsreader.util.LETrtStBarUtil;
import com.bobo520.newsreader.util.SpUtils;

import java.util.List;


public class MessageActivity extends Activity {


    //广播收到后台推送的小消息后改变铃铛你的状态
    private LocalBroadcastManager mLBM;

    //显示内容的ListView
    private ListView iv_invite;

    //显示内容ListView的适配器
    private InviteAdapter inviteAdapter;

    private ImageButton backImageBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        LETrtStBarUtil.setTransparentToolbar(this);
        //创建一个发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(this);

        initView();

        initListener();

        initData();
    }

    //Adapter中按钮点击事件的处理
    private InviteAdapter.OnInviteListener mOnInviteListener = new InviteAdapter.OnInviteListener() {
        //refresh();
    };


    private void initData(){
        //初始化ListView
        inviteAdapter = new InviteAdapter(MessageActivity.this,mOnInviteListener);
        iv_invite.setAdapter(inviteAdapter);

        //刷新方法
        refresh();

    }

    private void refresh(){
        //获取数据库中的所有邀请信息
        List<InvationInfo> invationInfos = Model.getInstance().getDbManager().getInviteTableDao().getInvittations();

        //刷新适配器
        inviteAdapter.refresh(invationInfos);
    }

    private void initView(){
        iv_invite = (ListView)findViewById(R.id.iv_invite);
        backImageBtn = (ImageButton) findViewById(R.id.back_button);
    }

    //titleBar左上角点击返回键的处理
    private void initListener(){
        backImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        //发送广播-用户直接进入了消息详情页
        mLBM.sendBroadcast(new Intent(Constant.USER_ENTERS_MESSAGE_DETAILS_PA));
        SpUtils.setBoolean(MessageActivity.this,Constant.IS_NES_MESSAGE,false);
        super.onBackPressed();
    }
}
