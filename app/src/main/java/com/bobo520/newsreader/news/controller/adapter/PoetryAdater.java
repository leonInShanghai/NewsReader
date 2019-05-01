package com.bobo520.newsreader.news.controller.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.app.LeBaseAdapter;
import com.bobo520.newsreader.news.model.PictureBean;
import com.bobo520.newsreader.news.model.PoetryBean;
import com.bobo520.newsreader.util.ImageUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.wuhenzhizao.titlebar.utils.ScreenUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Leon on 2019/2/2. Copyright © Leon. All rights reserved.
 * Functions: 诗词fragment中listview的适配器
 */
public class PoetryAdater extends LeBaseAdapter<PoetryBean.ListBean> {

    private Context mContext;

    public PoetryAdater(List<PoetryBean.ListBean> list, Context context) {
        super(list);
        this.mContext = context;
    }

    /**添加(更新)数据的方法-下拉刷新上拉加载更多*/
    public void loadData(List<PoetryBean.ListBean> list){
        mList.addAll(list);
        //刷新页面-通知已更改的数据集
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotNewsViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.item_poetry_news,null);
            viewHolder = new HotNewsViewHolder();
            viewHolder.ivJokeUser = (CircleImageView)convertView.findViewById( R.id.iv_picture_user );
            viewHolder.tvJokeUserName = (TextView)convertView.findViewById( R.id.tv_picture_user_name);
            viewHolder.tvInfo = (TextView)convertView.findViewById(R.id.tv_info);
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

    private void changeUI(HotNewsViewHolder viewHolder,PoetryBean.ListBean listBean) {

        //设置用户的头像
        ImageUtil.getSinstance().displayPic(listBean.getU().getHeader().get(0), viewHolder.ivJokeUser);

        //设置用户的名称
        viewHolder.tvJokeUserName.setText(listBean.getU().getName());

        //设置诗词neirong
        viewHolder.tvInfo.setText(listBean.getText());

        //发现百思的后台有时候不返回 image
        if (listBean.getImage() != null) {
            viewHolder.ivPictureText.setVisibility(View.VISIBLE);
            //动态计算最佳的图片宽高
            WindowManager wm = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
            int width = dm.widthPixels;// 屏幕宽度（像素）
            // 减去padding="8dp" 以后要是XML文件里面改了这里也要改
            int screenWidth = (int) (width - ScreenUtils.dp2Px(mContext, 16));
            int height = (int) (listBean.getImage().getHeight() / (float) listBean.getImage().getWidth() * screenWidth);

            if (height <= ScreenUtils.dp2Px(mContext, 360)) {
                viewHolder.isPictureGif.setVisibility(View.GONE);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewHolder.ivPictureText.getLayoutParams();
                lp.width = screenWidth;
                lp.height = height;
                viewHolder.ivPictureText.setLayoutParams(lp);
            } else {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewHolder.ivPictureText.getLayoutParams();
                lp.width = screenWidth;
                lp.height = (int) ScreenUtils.dp2Px(mContext, 360);
                viewHolder.ivPictureText.setLayoutParams(lp);
                viewHolder.isPictureGif.setVisibility(View.VISIBLE);
                viewHolder.isPictureGif.setText("长图");
            }

            Picasso.get().load(listBean.getImage().getBig().get(0)).centerCrop().placeholder(R.drawable.booth_map)
                    .fit()
                    .into(viewHolder.ivPictureText);
        }else{
            viewHolder.isPictureGif.setVisibility(View.GONE);
            viewHolder.ivPictureText.setVisibility(View.GONE);
        }

        //喜欢的（点赞）人数
        viewHolder.iconItemPostLikeCount.setText(listBean.getUp());

       //评论用户的用户名这里没有用
       //viewHolder.username.setText(listBean.getTags().get(0).getTail());

        viewHolder.content.setText(listBean.getPasstime());
    }

    /**用于下拉刷新时调用的方法*/
    public void updateData(List<PoetryBean.ListBean> list){
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

        /**诗词（info）内容的文本*/
        private TextView tvInfo;

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
