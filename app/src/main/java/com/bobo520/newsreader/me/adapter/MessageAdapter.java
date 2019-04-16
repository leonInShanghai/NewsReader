package com.bobo520.newsreader.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bobo520.newsreader.me.model.bean.InvationInfo;

import java.util.ArrayList;
import java.util.List;
import com.bobo520.newsreader.R;
import com.bobo520.newsreader.util.LeTimeUtils;

/**
 * Created by 求知自学网 on 2019/4/16. Copyright © Leon. All rights reserved.
 * Functions:
 */
public class MessageAdapter extends BaseAdapter {

    private Context mContext;

    private List<InvationInfo> mInvationInfos = new ArrayList<>();

    private OnInviteListener mOnInviteListener;

    private InvationInfo invationInfo;

    /**解決bug 把 position倒過來*/
    private InvationInfo lEinvationInfo;

    public MessageAdapter(Context context, OnInviteListener onInviteListener){
        this.mContext = context;
        this.mOnInviteListener = onInviteListener;
    }

    //刷新数据的方法
    public void refresh(List<InvationInfo> invationInfos){
        //注意这里要>=0
        if (invationInfos != null && invationInfos.size() >= 0){
            mInvationInfos.clear();//每次进来之前先清空一下

            mInvationInfos.addAll(invationInfos);

            //通知刷新页面 安卓系统方法
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mInvationInfos == null ? 0 : mInvationInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mInvationInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //1.获取创建一个ViewHolder
        ViewHodler hodler = null;
        if (convertView == null){
            hodler = new ViewHodler();
            convertView = View.inflate(mContext,R.layout.item_message,null);
            hodler.messageTime = (TextView)convertView.findViewById(R.id.tv_message_time);
            hodler.messageDetail = (TextView)convertView.findViewById(R.id.tv_message_details);
            hodler.delete = (Button)convertView.findViewById(R.id.bt_delete);
            convertView.setTag(hodler);
        }else {
            hodler = (ViewHodler)convertView.getTag();
        }

        int lePostion = 0;

        //----解決bug 因爲之前爲了用戶體驗把 InviteTable 表中數據倒序了 這裏把 position 也 倒過來-----
        if (mInvationInfos.size() - position >= 0){
            lePostion = mInvationInfos.size() - position -1;
        }
        lEinvationInfo = mInvationInfos.get(lePostion);
        //----解決bug 因爲之前爲了用戶體驗把 InviteTable 表中數據倒序了 這裏把 position 也 倒過來-----

        //2.获取当前item数据
        invationInfo = mInvationInfos.get(position);

        hodler.messageTime.setText(LeTimeUtils.getStrTime(String.valueOf(invationInfo.getCurrentTime())));
        hodler.messageDetail.setText(invationInfo.getMessage());

        //返回View
        return convertView;
    }

    /**内部类 ViewHodler*/
    private class ViewHodler{
        private TextView messageTime;
        private TextView messageDetail ;

        private Button delete;
    }

    public interface OnInviteListener{
//        //联系人接受按钮的点击事件
//        void onAccept(InvationInfo invationInfo);
//
//        //联系人拒绝按钮的点击事件
//        void onReject(InvationInfo invationInfo);
//
//        /**接受邀请按钮处理(群)*/
//        void onInviteAccept(InvationInfo invationInfo);
//
//        /**拒绝邀请按钮处理*/
//        void onInviteReject(InvationInfo invationInfo);
//
//        /**接受申请按钮处理*/
//        void  onApplicationAccept(InvationInfo invationInfo);
//
//        /**拒绝申请按钮处理*/
//        void onApplicationReject(InvationInfo invationInfo);
    }

}
