package com.bobo520.newsreader.news.controller.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bobo520.newsreader.R;

import com.bobo520.newsreader.app.LeBaseAdapter;
import com.bobo520.newsreader.app.MyBaseAdapter;
import com.bobo520.newsreader.news.model.JokeBean;
import com.bobo520.newsreader.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Leon on 2019/2/2. Copyright © Leon. All rights reserved.
 * Functions: 笑话fragment中listview的适配器
 */
public class JokeAdater extends LeBaseAdapter<JokeBean.ListBean> {


    public JokeAdater(List<JokeBean.ListBean> list) {
        super(list);
    }

    /**添加(更新)数据的方法-下拉刷新上拉加载更多*/
    public void loadData(List<JokeBean.ListBean> list){
        mList.addAll(list);
        //刷新页面-通知已更改的数据集
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotNewsViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.item_joke_news,null);
            viewHolder = new HotNewsViewHolder();
            viewHolder.ivJokeUser = (CircleImageView)convertView.findViewById( R.id.iv_joke_user );
            viewHolder.tvJokeUserName = (TextView)convertView.findViewById( R.id.tv_joke_user_name );
            viewHolder.tvJokeText = (TextView)convertView.findViewById( R.id.tv_joke_text );
            viewHolder.ivJokeComment = (CircleImageView)convertView.findViewById( R.id.iv_joke_comment );
            viewHolder.iconItemPostLikeCount = (TextView)convertView.findViewById( R.id.icon_item_post_like_count );
            viewHolder.username = (TextView)convertView.findViewById(R.id.username);
            viewHolder.content = (TextView)convertView.findViewById( R.id.content );
            viewHolder.comment_user_area = (RelativeLayout)convertView.findViewById(R.id.comment_user_area);

            //将viewHolder设置给convertView,绑定起来
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (HotNewsViewHolder) convertView.getTag();
        }

        //给viewholder中的控件设置数据
        changeUI(viewHolder,mList.get(position));

        return convertView;
    }

    private void changeUI(HotNewsViewHolder viewHolder,JokeBean.ListBean listBean){

        //设置用户的头像
        ImageUtil.getSinstance().displayPic(listBean.getProfile_image(),viewHolder.ivJokeUser);

        //设置用户的名称
        viewHolder.tvJokeUserName.setText(listBean.getName());

        //设置笑话的文本*/
        viewHolder.tvJokeText.setText(listBean.getText());


        //设置评论下的用户头像
        if (listBean.getTop_cmt() != null && listBean.getTop_cmt().size() > 0){
            //设置显示评论区域
            viewHolder.comment_user_area.setVisibility(View.VISIBLE);

            //设置评论区域的数据
            JokeBean.ListBean.TopCmtBean topCmtBean = listBean.getTop_cmt().get(0);
            ImageUtil.getSinstance().displayPic(topCmtBean.getUser().getProfile_image(),viewHolder.ivJokeComment);

            //设置点赞个数
            viewHolder.iconItemPostLikeCount.setText(topCmtBean.getLike_count()+"");

            //设置评论用户名
            viewHolder.username.setText(topCmtBean.getUser().getUsername());

            //设置评论内容
            viewHolder.content.setText(topCmtBean.getContent());

        }else{
            //设置不现实评论区域
            viewHolder.comment_user_area.setVisibility(View.GONE);
        }
    }

    /**用于下拉刷新时调用的方法*/
    public void updateData(List<JokeBean.ListBean> list){
        //先清空一下之前的数据
        mList.clear();

        //LELog.showLogWithLineNum(5,"先清空一下之前的数据");
        mList.addAll(list);
        //刷新页面-通知已更改的数据集
        notifyDataSetChanged();
    }

    private class HotNewsViewHolder{

        /**用户的头像*/
        private CircleImageView ivJokeUser;

        /**用户的名称*/
        private TextView tvJokeUserName;

        /**笑话的文本*/
        private TextView tvJokeText;

        /**评论下的用户头像*/
        private CircleImageView ivJokeComment;

        /**点赞个数*/
        private TextView iconItemPostLikeCount;

        /** 评论用的username*/
        private TextView username;

        /**评论内容*/
        private TextView content;

        /**用户发布内容通过审核的时间  未处理*/
        private TextView passtime;

        /**评论区域*/
        private RelativeLayout comment_user_area;

    }
}
