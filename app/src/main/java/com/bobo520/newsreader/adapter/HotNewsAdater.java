package com.bobo520.newsreader.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo520.newsreader.LELog;
import com.bobo520.newsreader.R;
import com.bobo520.newsreader.bean.HotNewsBean;
import com.bobo520.newsreader.bean.HotNewsListBean;
import com.bobo520.newsreader.util.ImageUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Leon on 2019/2/2. Copyright © Leon. All rights reserved.
 * Functions: 新闻fragment中listview的适配器
 */
public class HotNewsAdater extends MyBaseAdapter<HotNewsBean>{


    public HotNewsAdater(ArrayList<HotNewsBean> list) {
        super(list);
    }

    /**添加(更新)数据的方法-下拉刷新上拉加载更多*/
    public void loadData(ArrayList<HotNewsBean> list){
        mList.addAll(list);
        //刷新页面-通知已更改的数据集
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotNewsViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.item_hot_news,null);
            viewHolder = new HotNewsViewHolder();
            viewHolder.ivHot = (ImageView) convertView.findViewById(R.id.iv_hot);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvSource = (TextView) convertView.findViewById(R.id.tv_source);
            viewHolder.tvReply = (TextView) convertView.findViewById(R.id.tv_reply);
            viewHolder.tvTop = (TextView) convertView.findViewById(R.id.tv_top);
            //将viewHolder设置给convertView,绑定起来
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (HotNewsViewHolder) convertView.getTag();
        }

        //给viewholder中的控件设置数据
        changeUI(viewHolder,mList.get(position));

        return convertView;
    }

    private void changeUI(HotNewsViewHolder viewHolder,HotNewsBean hotNewsBean){
        viewHolder.tvTitle.setText(hotNewsBean.getTitle());
        viewHolder.tvSource.setText(hotNewsBean.getSource());
        //LELog.showLogWithLineNum(5,hotNewsBean.getInterest()+"<<<<<<<<<<<<<<<<<<<<<<<<<");

        if (hotNewsBean.getInterest().equals("S")) {
            //要显示置顶
            viewHolder.tvTop.setVisibility(View.VISIBLE);
            viewHolder.tvReply.setVisibility(View.GONE);
        }else {
            //不置顶
            viewHolder.tvTop.setVisibility(View.GONE);
            if (hotNewsBean.getReplyCount() == 0){//如果回复数量为0回复的text view隐藏
                viewHolder.tvReply.setVisibility(View.GONE);
            }else{
                viewHolder.tvReply.setVisibility(View.VISIBLE);
                viewHolder.tvReply.setText("跟贴数："+hotNewsBean.getReplyCount());
            }
        }
        //用封装好的单例工具类设置图片
        if (hotNewsBean.getImg() == null){//2019-3-2发现网易返回的数据有时候没有图片
            viewHolder.ivHot.setVisibility(View.GONE);
        }else {
            viewHolder.ivHot.setVisibility(View.VISIBLE);
            ImageUtil.getSinstance().displayPic(hotNewsBean.getImg(),viewHolder.ivHot);
        }

        //2019-3-2之前
        //ImageUtil.getSinstance().displayPic(hotNewsBean.getImg(),viewHolder.ivHot);
    }

    /**用于下拉刷新时调用的方法*/
    public void updateData(ArrayList<HotNewsBean> list){
        //先清空一下之前的数据
        mList.clear();

        //LELog.showLogWithLineNum(5,"先清空一下之前的数据");
        mList.addAll(list);
        //刷新页面-通知已更改的数据集
        notifyDataSetChanged();
    }

    private class HotNewsViewHolder{
        ImageView ivHot;
        TextView tvTitle;
        TextView tvSource;
        TextView tvReply;
        TextView tvTop;
    }
}
