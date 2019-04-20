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
            hodler.messageDetails = (TextView)convertView.findViewById(R.id.tv_message_details);
            hodler.redDot = (View) convertView.findViewById(R.id.red_dot);
            hodler.delete = (TextView)convertView.findViewById(R.id.main_tv_delete);
            hodler.delete.setTag(position);

            convertView.setTag(hodler);
        }else {
            hodler = (ViewHodler)convertView.getTag();
        }

        //2.获取当前item数据
        invationInfo = mInvationInfos.get(position);

        hodler.messageTime.setText(LeTimeUtils.getStrTime(String.valueOf(invationInfo.getCurrentTime())));
        //hodler.messageTime.setText(String.valueOf(invationInfo.getCurrentTime()));
        hodler.messageDetails.setText(invationInfo.getMessage());

        //设置红点显示不显示
        if (invationInfo.getLook_detais()){//Look_detais 为true 则不显示
            hodler.redDot.setVisibility(View.GONE);
        }else{//Look_detais 为false 则显示
            hodler.redDot.setVisibility(View.VISIBLE);
        }


        //用户左划点击了删除
        hodler.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //自定义控件 SlideListView 有个小缺陷 会数组越界处理一下
                // indexOutBoundsException Invalid index 1,size is 1
                if ((int)v.getTag() > (mInvationInfos.size() -1)){return;}

                lEinvationInfo = mInvationInfos.get((int)v.getTag());

                //调用接口交给 MessageActivity 来实现
                mOnInviteListener.onDelete(lEinvationInfo.getCurrentTime());

            }
        });


        //返回View
        return convertView;
    }

    /**内部类 ViewHodler*/
    private class ViewHodler{
        private TextView messageTime;
        private TextView messageDetails;
        private View redDot;

        private TextView delete;
    }

    public interface OnInviteListener{
        void onDelete(long currentTime);
    }

}
