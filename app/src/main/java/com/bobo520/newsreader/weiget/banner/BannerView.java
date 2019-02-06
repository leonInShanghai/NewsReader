package com.bobo520.newsreader.weiget.banner;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.util.ImageUtil;
import com.bobo520.newsreader.util.ReturnImgWH;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Leon on 2019/2/6. Copyright © Leon. All rights reserved.
 * Functions: 自定义bannerView
 * 自定义控件的方式一：①继承自 view 或 viewgroup  ②view 复写 onMeasure onDraw 方法，
 * ③view group 复写 onLayout
 * 自定义控件方法二：将一些系统控件组合在一块，然后添加一些自己的业务逻辑在其中：组合型自定义控件
 */
public class BannerView extends RelativeLayout{

    //xml中的控件
    private ViewPager mViewpager;
    private TextView mTvTitle;
    //private LinearLayout mAdIndicator;

    //这个是最大的那个RelativeLayout 布局控件
    private RelativeLayout dynamicHeight;

    //banner轮播图上需要的图片和标题
    private ArrayList<String> mImgPics;
    private ArrayList<String> mTitles;

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        //将xml展示在当前的自定义控件中-等价于打气筒生成的view会被addview到当前this中
        View view = View.inflate(getContext(),R.layout.view_banner,this);
        mViewpager = (ViewPager)view.findViewById( R.id.viewpager );
        mTvTitle = (TextView)view.findViewById( R.id.tv_title );
        //mAdIndicator = (LinearLayout)view.findViewById( R.id.ad_indicator );
        dynamicHeight = (RelativeLayout)findViewById(R.id.dynamicHeight);
    }

    /**
     * 设置轮播图的地址和标题
     * @param imgPics ：图片地址集合
     * @param titles ：标题集合
     */
    public void setBannerData(ArrayList<String> imgPics,ArrayList<String> titles){
        this.mImgPics = imgPics;
        this.mTitles = titles;

        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i = 0;i < imgPics.size();i++){
            String url = imgPics.get(i);
            ImageView imageView = new ImageView(getContext());
            ImageUtil.getSinstance().displayPic(url,imageView);
            imageViews.add(imageView);
        }
        //给view pager设置展示用的图片-这里使用了内部类
        BannerAdapter bannerAdapter = new BannerAdapter(imageViews);
        mViewpager.setAdapter(bannerAdapter);
    }

    //自定义viewpager的适配器
    private class BannerAdapter extends PagerAdapter{

        private ArrayList<ImageView> mImageViews;

        public BannerAdapter(ArrayList<ImageView> imageViews){
            this.mImageViews = imageViews;
        }

        @Override
        public int getCount() {
            return mImageViews == null ? 0 : mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container,final int position) {
            ImageView imageView = mImageViews.get(position);

            //使banner的宽度和屏幕宽度一样并且不拉伸变形①获取用户手机屏幕宽度
            //获取图片真正的宽高
            Glide.with(getContext()).asBitmap().load(mImgPics.get(position)).into(new SimpleTarget<Bitmap>() {

                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    if (bitmap.getWidth() > 0 && bitmap.getHeight() > 0){
                            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                            double windowWidth = windowManager.getDefaultDisplay().getWidth();
                            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) dynamicHeight.getLayoutParams();
                            params.width = (int) windowWidth;
                            //高度计算公式 图片高度 / (图片宽度 / 屏幕宽度)
                            params.height = (int) ((double)bitmap.getHeight() / ((double) bitmap.getWidth() / windowWidth));
                            Toast.makeText(getContext(), params.height + "1", Toast.LENGTH_SHORT).show();
                            dynamicHeight.setLayoutParams(params);
                        }else {
                            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE); ;
                            double windowWidth = windowManager.getDefaultDisplay().getWidth();
                            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) dynamicHeight.getLayoutParams();
                            params.width = (int) windowWidth;
                            params.height = 608;;
                            dynamicHeight.setLayoutParams(params);
                    }
                }
            });
        //使banner的宽度和屏幕宽度一样并且不拉伸变形①获取用户手机屏幕宽度

            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
