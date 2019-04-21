package com.bobo520.newsreader.me.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;


import com.bobo520.newsreader.me.adapter.MessageAdapter;
import com.bobo520.newsreader.me.model.Model;
import com.bobo520.newsreader.me.model.bean.InvationInfo;
import com.bobo520.newsreader.util.Constant;
import com.bobo520.newsreader.util.LETrtStBarUtil;
import com.bobo520.newsreader.util.SpUtils;
import com.bobo520.newsreader.R;
import com.bobo520.newsreader.weiget.SlideListView;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class MessageActivity extends SwipeBackActivity {


    //广播收到后台推送的小消息后改变铃铛你的状态
    private LocalBroadcastManager mLBM;

    //展示信息列表的listview
    private SlideListView iv_invite;

    //信息列表的listView的适配器
    private MessageAdapter inviteAdapter;

    //左上角返回的按钮
    private ImageButton back_button;

    private List<InvationInfo> detailsInfos = new ArrayList<>();

    /**两个activity传递消息的key*/
    public static final String JPUSH_MESSGAE = "JPUSH_MESSGAE";


    //接收到收到新极光推送广播的处理@drawable/mine_msg_ic_selector
    private BroadcastReceiver ReceivedANewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新数据
            refresh();
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        //刷新数据
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        LETrtStBarUtil.setTransparentToolbar(this);
        //创建一个发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(this);

        //定义接收广播的方法-接收到新消息
        mLBM.registerReceiver(ReceivedANewMessage,new IntentFilter(Constant.RECEIVED_A_NEW_MESSAGE));

        initView();

        initListener();

        initData();
    }



    //实现Adapter接口 用户左划并点击了删除按钮
    private MessageAdapter.OnInviteListener mOnInviteListener = new MessageAdapter.OnInviteListener() {

        @Override
        public void onDelete(long currentTime) {

            //Toast.makeText(MessageActivity.this,""+currentTime,Toast.LENGTH_SHORT).show();
            //从数据库中删除数据
            Model.getInstance().getDbManager().getInviteTableDao().removeInvitation(currentTime);

            //删除按钮返回到
            iv_invite.turnToNormal();

            //刷新数据
            refresh();
        }
    };


    private void initData(){
        //初始化ListView
        inviteAdapter = new MessageAdapter(MessageActivity.this,mOnInviteListener);
        iv_invite.setAdapter(inviteAdapter);

        //刷新方法
        refresh();

    }

    private void refresh(){
        //获取数据库中的所有邀请信息
        detailsInfos.clear();
        List<InvationInfo> invationInfos = Model.getInstance().getDbManager().getInviteTableDao().getInvittations();
        detailsInfos.addAll(invationInfos);

        //刷新适配器
        inviteAdapter.refresh(detailsInfos);
    }

    private void initView(){
        iv_invite = (SlideListView)findViewById(R.id.iv_invite);
        back_button = (ImageButton) findViewById(R.id.back_button);
        //titlebar_invite.setLeftImageResource(R.drawable.back_button_selecter);
    }

    //titleBar左上角点击返回键的处理
    private void initListener(){

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_invite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InvationInfo invationInfo = detailsInfos.get(position);
                Intent intent = new Intent(MessageActivity.this,MesDetailedActivity.class);
                intent.putExtra(JPUSH_MESSGAE,invationInfo.getMessage());

                //修改数据库红点状态
                Model.getInstance().getDbManager().getInviteTableDao().userToSeeDetails(invationInfo.getCurrentTime());

                //跳转到详情activity
                startActivity(intent);
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
