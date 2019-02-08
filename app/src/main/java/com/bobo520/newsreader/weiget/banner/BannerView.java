package com.bobo520.newsreader.weiget.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.util.ImageUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

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
    private LinearLayout mAdIndicator;

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
        mAdIndicator = (LinearLayout)view.findViewById( R.id.ad_indicator );
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
            //设置image view 拉伸 这个我后面在BannerAdapter中动态计算的没有使用
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageUtil.getSinstance().displayPic(url,imageView);
            imageViews.add(imageView);
        }
        //给view pager设置展示用的图片-这里使用了内部类
        BannerAdapter bannerAdapter = new BannerAdapter(imageViews);
        mViewpager.setAdapter(bannerAdapter);

        //标题
        mTvTitle.setText(mTitles.get(0));

        //设置页面的小点
        initDot();

        //选中小点
        selectDot(0);

        //设置翻页的监听
        MyPageChangeListener myPageChangeListener = new MyPageChangeListener();
        mViewpager.addOnPageChangeListener(myPageChangeListener);
    }

    /**设置页面的小点 pager count*/
    private void initDot(){
        //设置小点之间的间距 因为指示器的容器是线性布局-所以这里要用LinearLayout.LayoutParams
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mTitles.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.bg_dot);
            //设置右边距为10
            layoutParams.setMargins(0,0,10,0);
            mAdIndicator.addView(imageView,layoutParams);
        }
    }

    private void selectDot(int index){
        for (int i = 0;i < mAdIndicator.getChildCount();i++){
            ImageView imageView = (ImageView) mAdIndicator.getChildAt(i);
            if (i == index){//被选中的小点
                imageView.setImageResource(R.drawable.bg_dot_selector);
            }else{
                imageView.setImageResource(R.drawable.bg_dot);
            }
        }
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {

            //后面设置了BannerAdapter 的getcount 返回最大整型这里要避免数组越界
            position = position % mTitles.size();

            //页面发生变化标题跟随改变
            mTvTitle.setText(mTitles.get(position));
            //页面发生变化小点跟随改变
            selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    //自定义viewpager的适配器
    private class BannerAdapter extends PagerAdapter{

        private ArrayList<ImageView> mImageViews;

        public BannerAdapter(ArrayList<ImageView> imageViews){
            this.mImageViews = imageViews;
        }

        @Override
        public int getCount() {
            //return mImageViews == null ? 0 : mImageViews.size();
            //这样写是为了让banner图一直可以自动滚动-注意适配器其他方法中的position一定要取余避免数组越界
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container,int position) {
            //对Viewpager页号求模去除View列表中要显示的项
            position %=  mImageViews.size();
            if (position < 0) {
                position =  mImageViews.size() + position;
            }
            ImageView imageView = mImageViews.get(position);

            //使banner的宽度和屏幕宽度一样并且不拉伸变形↓
            Glide.with(getContext()).asBitmap().load(mImgPics.get(position % mImageViews.size())).into(new SimpleTarget<Bitmap>() {
            //获取图片真正的宽高
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    if (bitmap.getWidth() > 0 && bitmap.getHeight() > 0){
                            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.
                                    WINDOW_SERVICE);
                            double windowWidth = windowManager.getDefaultDisplay().getWidth();
                            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) dynamicHeight.getLayoutParams();
                            params.width = (int) windowWidth;
                            //高度计算公式 图片高度 / (图片宽度 / 屏幕宽度)
                            params.height = (int) ((double)bitmap.getHeight() / ((double) bitmap.getWidth() / windowWidth));
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
          //使banner的宽度和屏幕宽度一样并且不拉伸变形↑

          //原来是直接添加的
          //container.addView(imageView);

            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent viewParent = imageView.getParent();
            if (viewParent != null){
                ViewGroup parent = (ViewGroup)viewParent;
                parent.removeView(imageView);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);

            //警告:为了实现imageview 轮播图无限往一个方向滑动 不要在这里调用removeView
            //container.removeView((View) object);
        }
    }
}



