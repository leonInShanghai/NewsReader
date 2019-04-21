package com.bobo520.newsreader.news.controller.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.app.LeBaseAdapter;
import com.bobo520.newsreader.news.model.JokeBean;
import com.bobo520.newsreader.news.model.PictureBean;
import com.bobo520.newsreader.util.ImageBigPlaceholderUtil;
import com.bobo520.newsreader.util.ImageUtil;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Leon on 2019/2/2. Copyright © Leon. All rights reserved.
 * Functions: 图片fragment中listview的适配器
 */
public class PictureAdater extends LeBaseAdapter<PictureBean.ListBean> {

    private Context mContext;

    public PictureAdater(List<PictureBean.ListBean> list,Context context) {
        super(list);
        this.mContext = context;
    }

    /**添加(更新)数据的方法-下拉刷新上拉加载更多*/
    public void loadData(List<PictureBean.ListBean> list){
        mList.addAll(list);
        //刷新页面-通知已更改的数据集
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotNewsViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.item_picture_news,null);
            viewHolder = new HotNewsViewHolder();
            viewHolder.ivJokeUser = (CircleImageView)convertView.findViewById( R.id.iv_picture_user );
            viewHolder.tvJokeUserName = (TextView)convertView.findViewById( R.id.tv_picture_user_name);
            viewHolder.ivPictureText = (ImageView)convertView.findViewById(R.id.iv_picture_text);
            viewHolder.isPictureGif = (TextView)convertView.findViewById( R.id.is_picture_gif );
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

    private void changeUI(HotNewsViewHolder viewHolder,PictureBean.ListBean listBean){

        //设置用户的头像
        ImageUtil.getSinstance().displayPic(listBean.getProfile_image(),viewHolder.ivJokeUser);

        //设置用户的名称
        viewHolder.tvJokeUserName.setText(listBean.getName());

        //设置图片的内容 上面一句代码不能加载GIF图  listBean.getIs_gif().equals("0")*/
        //ImageBigPlaceholderUtil.getSinstance().displayPic(listBean.getImage0(), viewHolder.ivPictureText);
        //Glide.with(mContext).load(listBean.getImage0()).into(viewHolder.ivPictureText);


        if (listBean.getIs_gif().equals("0")){
            viewHolder.isPictureGif.setText("长图");
        }else if (listBean.getIs_gif().equals("1")){
            viewHolder.isPictureGif.setText("GIF");
        }


        //设置图片到image控件 fit 它会自动测量我们的View的大小，然后内部调用reszie方法把图片裁剪到View的大小，
        // 这就帮我们做了计算size和调用resize fit会出现拉伸扭曲的情况，因此最好配合前面的centerCrop使用
        Picasso.get().load(listBean.getImage0()).centerCrop().placeholder(R.drawable.booth_map)
                .fit()
                .into(viewHolder.ivPictureText);

        //喜欢的（点赞）人数
        viewHolder.iconItemPostLikeCount.setText(listBean.getLove());

        viewHolder.username.setText(listBean.getTheme_name());

        viewHolder.content.setText(listBean.getText());
    }

    /**用于下拉刷新时调用的方法*/
    public void updateData(List<PictureBean.ListBean> list){
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

        /**图片的内容的文本*/
        private ImageView ivPictureText;

        /**评论下的用户头像*/
        private TextView isPictureGif;

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
