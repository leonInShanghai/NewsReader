package com.bobo520.newsreader.video.controller.adapter;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.app.LeBaseAdapter;
import com.bobo520.newsreader.news.model.PoetryBean;
import com.bobo520.newsreader.util.ImageUtil;
import com.bobo520.newsreader.video.model.VaBean;
import com.squareup.picasso.Picasso;
import com.wuhenzhizao.titlebar.utils.ScreenUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Leon on 2019/2/2. Copyright © Leon. All rights reserved.
 * Functions: 视频fragment中listview的适配器
 */
public class VaAdater extends LeBaseAdapter<VaBean.ListBean> {

    private Context mContext;

    public VaAdater(List<VaBean.ListBean> list, Context context) {
        super(list);
        this.mContext = context;
    }

    /**添加(更新)数据的方法-下拉刷新上拉加载更多*/
    public void loadData(List<VaBean.ListBean> list){
        mList.addAll(list);
        //刷新页面-通知已更改的数据集
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VaViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.item_va,null);
            viewHolder = new VaViewHolder();
            viewHolder.userPicture = (CircleImageView)convertView.findViewById( R.id.iv_picture_user );
            viewHolder.tvUserName = (TextView)convertView.findViewById( R.id.tv_user_name);
            viewHolder.text = (TextView)convertView.findViewById(R.id.tv_text);
            viewHolder.jzvdStd = (JCVideoPlayerStandard)convertView.findViewById(R.id.videoplayer);

            //将viewHolder设置给convertView,绑定起来
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (VaViewHolder) convertView.getTag();
        }

        //给viewholder中的控件设置数据
        changeUI(viewHolder,mList.get(position));

        return convertView;
    }

    private void changeUI(VaViewHolder viewHolder,VaBean.ListBean listBean) {

        //设置饺子播放器7.0 的宽度 == 屏幕的宽度 高度 == 200 解决全屏返回后 高度改变的bug
//        if (viewHolder.jzvdStd.getLayoutParams() instanceof LinearLayout.LayoutParams) {
//            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewHolder.jzvdStd.getLayoutParams();
//            lp.height = (int) ScreenUtils.dp2Px(mContext, 200);
//            viewHolder.jzvdStd.setLayoutParams(lp);
//        }

        //jiecaovideoplayer:5.5 全屏时横向 打开这两句代码
        //viewHolder.jzvdStd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        //viewHolder.jzvdStd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向

        //设置用户的头像
        ImageUtil.getSinstance().displayPic(listBean.getProfile_image(), viewHolder.userPicture);

        //设置用户的名称
        viewHolder.tvUserName.setText(listBean.getName());

        //设置视频上面的文字内容
        viewHolder.text.setText(listBean.getText());

        //设置饺子播放器  SCREEN_STATE_OFF  SCREEN_NORMAL
        viewHolder.jzvdStd.setUp(listBean.getVideouri(),JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL);
        //设置播放器封面 饺子播放器中原装的方法 好像不起作用
        //viewHolder.jzvdStd.thumbImageView.setImageURI(Uri.parse(listBean.getImage0()));
        //设置播放器封面 方法二
        Picasso.get().load(listBean.getImage0()).into(viewHolder.jzvdStd.thumbImageView);
    }

    /**用于下拉刷新时调用的方法*/
    public void updateData(List<VaBean.ListBean> list){
        //先清空一下之前的数据
        mList.clear();

        //LELog.showLogWithLineNum(5,"先清空一下之前的数据");
        mList.addAll(list);
        //刷新页面-通知已更改的数据集
        notifyDataSetChanged();
    }

    private class VaViewHolder{

        /**用户的头像*/
        private CircleImageView userPicture;

        /**用户的名称*/
        private TextView tvUserName;

        /**视频内容的文字文本*/
        private TextView text;

        /**评论下的用户头像*/
        private JCVideoPlayerStandard jzvdStd;
    }
}
