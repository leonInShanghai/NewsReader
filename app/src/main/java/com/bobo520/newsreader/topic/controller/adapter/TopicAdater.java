package com.bobo520.newsreader.topic.controller.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.app.LeBaseAdapter;
import com.bobo520.newsreader.news.model.JokeBean;
import com.bobo520.newsreader.topic.model.TopicBean;
import com.bobo520.newsreader.util.ImageUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Leon on 2019/2/2. Copyright © Leon. All rights reserved.
 * Functions:  话题fragment中listview的适配器
 */
public class TopicAdater extends LeBaseAdapter<TopicBean.DataBean> {


    public TopicAdater(List<TopicBean.DataBean> list) {
        super(list);
    }

    /**添加(更新)数据的方法-下拉刷新上拉加载更多*/
    public void loadData(List<TopicBean.DataBean> list){
        mList.addAll(list);
        //刷新页面-通知已更改的数据集
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotNewsViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.item_topic_news,null);
            viewHolder = new HotNewsViewHolder();
            viewHolder.title = (TextView)convertView.findViewById( R.id.topic_title);
            viewHolder.desc = (TextView)convertView.findViewById( R.id.topic_desc);

            //将viewHolder设置给convertView,绑定起来
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (HotNewsViewHolder) convertView.getTag();
        }

        //给viewholder中的控件设置数据
        changeUI(viewHolder,mList.get(position));

        return convertView;
    }

    private void changeUI(HotNewsViewHolder viewHolder,TopicBean.DataBean listBean){

        //设置话题的标题
        viewHolder.title.setText(listBean.getTitle());

        //设置话题的描述
        viewHolder.desc.setText(listBean.getDesc());
    }

    /**用于下拉刷新时调用的方法*/
    public void updateData(List<TopicBean.DataBean> list){
        //先清空一下之前的数据
        mList.clear();

        //LELog.showLogWithLineNum(5,"先清空一下之前的数据");
        mList.addAll(list);
        //刷新页面-通知已更改的数据集
        notifyDataSetChanged();
    }

    private class HotNewsViewHolder{

        /**话题的标题*/
        private TextView title;

        /**话题的描述*/
        private TextView desc;
    }
}
