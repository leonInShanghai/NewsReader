package com.bobo520.newsreader.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.bean.NewsCommentBean;
import com.bobo520.newsreader.util.ImageUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 求知自学网 on 2019/3/30. Copyright © Leon. All rights reserved.
 * Functions: 新闻详情二级页面 新闻评论页list view的适配器
 */
public class NewsCommectAdapter extends MyBaseAdapter<NewsCommentBean>{


    /**热门跟帖item的标识*/
    private static final int TYPE_HOT_TITLE = 0;

    /**评论类型item的标识*/
    private static final int TYPE_COMMENT = 1;

    public NewsCommectAdapter(ArrayList<NewsCommentBean> list) {
        super(list);
        //在集合中插入一条空数据放在第一条 补上被 热门item占据的位置
        list.add(0,new NewsCommentBean());
    }

    //让adapter展示多种item类型
    //确定下来的这个position位置上的item是属于哪一种类型
    @Override
    public int getItemViewType(int position) {

        //真实项目当中判断的时候，一般是根据JavaBean当中的字段来进行判断的

        if (position == 0){
            //热门跟帖类型 0代表是热门跟帖类型
            return TYPE_HOT_TITLE;
        }else{
            //其他类型-评论类型
            return TYPE_COMMENT;
        }

        //return super.getItemViewType(position);
    }

//    @Override
//    public int getCount() {
//        //这里+1是因为第一条被热门item占用了 - 方案一
//        return super.getCount() + 1;
//    }

    //有多少种类型在列表中展示
    @Override
    public int getViewTypeCount() {
        //item view的类型有多少种 如果希望展示2种那么就返回2
       return 2;
        // return super.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //根据对应position上的item类型，来展示对应的view的效果
        if (getItemViewType(position) == TYPE_HOT_TITLE){
            //热门跟帖类型-展示热门跟帖
            convertView = View.inflate(parent.getContext(), R.layout.item_hot_title,null);
        }else{
            //展示评论内容
            MyCommentViewHolder viewHolder;

            //convertView绑定tag
            if (convertView == null){
                convertView = View.inflate(parent.getContext(), R.layout.item,null);
                viewHolder = new MyCommentViewHolder();
                viewHolder.ivUserIcon = (CircleImageView)convertView.findViewById( R.id.iv_user_icon );
                viewHolder.tvUserName = (TextView)convertView.findViewById( R.id.tv_user_name );
                viewHolder.tvUserInfo = (TextView)convertView.findViewById( R.id.tv_user_info );
                //点赞这里只放置了一张图
                viewHolder.ivSupport = (ImageView)convertView.findViewById( R.id.iv_support );
                viewHolder.tvVoteCount = (TextView)convertView.findViewById( R.id.tv_vote_count );
                //楼中楼，自定义View，现在这里占个坑
                viewHolder.flSubFloor = (FrameLayout)convertView.findViewById( R.id.fl_sub_floor );
                viewHolder.tvComment = (TextView)convertView.findViewById( R.id.tv_comment );
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (MyCommentViewHolder)convertView.getTag();
            }

            //这里-1是因为第一条被热门item占用了 - 方案一
            // 原来+1是为了补热门跟帖的位置其实JavaBean里并没有那么多数据要-1
            //changUI(viewHolder,mList.get(position - 1));

            changUI(viewHolder,mList.get(position));
        }

        //返回convertView
        return convertView;
    }

    private void changUI(MyCommentViewHolder viewHolder, NewsCommentBean newsCommentBean) {
        //评论的内容
        viewHolder.tvComment.setText(newsCommentBean.getContent());

        //获取用户的JavaBean对象
        NewsCommentBean.UserBean userBean = newsCommentBean.getUser();

        //评论的用户名昵称
        viewHolder.tvUserName.setText(newsCommentBean.getUser().getNickname());

        //用户的信息 例如：北京市 iPhone 6  2小时前(这里显示的是创建时间)
        viewHolder.tvUserInfo.setText(userBean.getLocation()+" "+newsCommentBean.getDeviceInfo().
                getDeviceName()+" "+newsCommentBean.getCreateTime());

        //点赞数
        viewHolder.tvVoteCount.setText(String.valueOf(newsCommentBean.getVote()));

        //设置用户头像
        String leonAvatar = "https://github.com/leonInShanghai/NewsReader/blob/master/app/" +
                "src/main/res/drawable-xhdpi/icon_user_default.png?raw=true";
        //如果用户的头像为空就显示网易的默认头像
        String url = TextUtils.isEmpty(userBean.getAvatar()) ? leonAvatar : userBean.getAvatar();
        ImageUtil.getSinstance().displayPic(url,viewHolder.ivUserIcon);
    }

    //内部静态类ViewHolder
    private static class MyCommentViewHolder{
        private CircleImageView ivUserIcon;
        private TextView tvUserName;
        private TextView tvUserInfo;
        //点赞这里只放置了一张图
        private ImageView ivSupport;
        private TextView tvVoteCount;
        //楼中楼，自定义View，现在这里占个坑
        private FrameLayout flSubFloor;
        private TextView tvComment;
    }
}
