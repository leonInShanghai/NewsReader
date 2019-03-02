package com.bobo520.newsreader.weiget.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bobo520.newsreader.LELog;
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

    //负责让轮播图自动滚动的handler
    private Handler mHandler;

    //当只有一张banner图的时候
    private boolean noOnly = true;

    /**发送自动轮播消息的唯一标识 刚好也是延时的时长2000毫秒*/
    private static final int SHOW_TIME = 2000;

    private BannerAdapter mBannerAdapter;

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

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case SHOW_TIME :
                        //接收到消息自动翻页-先获取当前页
                        int currentItem = mViewpager.getCurrentItem();
                        currentItem++;
                        mViewpager.setCurrentItem(currentItem);
                        //再次发送这个消息使得轮播图可以无限自动轮播
                        mHandler.sendEmptyMessageDelayed(SHOW_TIME,SHOW_TIME);
                        break;
                }
            }
        };
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
        mBannerAdapter = new BannerAdapter(imageViews);
        mViewpager.setAdapter(mBannerAdapter);

        //标题
        mTvTitle.setText(mTitles.get(0));

        //设置页面的小点
        initDot();

        //选中小点
        selectDot(0);

        //设置翻页的监听
        MyPageChangeListener myPageChangeListener = new MyPageChangeListener();
        mViewpager.addOnPageChangeListener(myPageChangeListener);

        /**
         * 设置view pager的初始位置从max value的中间开始 实现用户可以左划banner
         * 细节并不是从第一张图片开始展示
         */
        if (mImgPics.size() > 1){
            mViewpager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mTitles.size());
            //调用自动无限轮播的方法
            start();
            noOnly = true;
        }else {
            //只有一张图就不要滚动了
            mViewpager.setCurrentItem(0);
            noOnly = false;
        }
    }


    /**
     * 分发
     * 备注：如果希望在原先的触摸逻辑的基础上面，添加一些额外的业务逻辑，并且该业务逻辑不需要影响到先前
     * 的触摸逻辑，可以考虑将业务逻辑代码写在分发方法中
     * ACTION_CANCEL:事件类型：当控件在触摸的一系列过程中，突然中断了。
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        switch (eventAction){
            case MotionEvent.ACTION_DOWN:
                //用户按下了banner图 停止发送让banner图无限滚动的消息
                stop();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //用户抬起了手指开始发送让banner图无限滚动的消息
                if (noOnly){
                    start();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                //用户取消了（当控件在触摸的一系列过程中，突然中断了）（手指移动到别的控件上了）
                if (noOnly) {
                    start();
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    public void start(){
        //开始自动轮播
        mHandler.sendEmptyMessageDelayed(SHOW_TIME,SHOW_TIME);
    }

    public void stop(){
        //停止自动轮播-销毁handler
        mHandler.removeCallbacksAndMessages(null);
    }

    /**设置页面的小点 pager count*/
    private void initDot(){

        //在添加之前先清空以前的小点view-主要是为了配合下拉刷新
        mAdIndicator.removeAllViews();

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

    /**
     * 配合listView下拉刷新时的更新方法
     * @param imgUrls；轮播图图片url地址
     * @param titles：轮播图标题
     */
    public void updateData(ArrayList<String> imgUrls, ArrayList<String> titles) {

        this.mImgPics = imgUrls;
        this.mTitles = titles;

        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i = 0;i < imgUrls.size();i++){
            String url = imgUrls.get(i);
            ImageView imageView = new ImageView(getContext());
            //设置image view 拉伸 这个我后面在BannerAdapter中动态计算的没有使用
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageUtil.getSinstance().displayPic(url,imageView);
            imageViews.add(imageView);
        }
        //推荐的刷新数据的写法：第一次才去new Adapter 以后再数据变化时建议不要再new Adapter，最好使用notify
        if (mBannerAdapter == null){
            mBannerAdapter = new BannerAdapter(imageViews);
            mViewpager.setAdapter(mBannerAdapter);
        }else {
            //更新
            mBannerAdapter.updateData(imageViews);
        }

        //updateData主要是配合list view 下拉刷新的 所以每刷新一次都要判断
        if (mImgPics.size() > 1){
            //不只有一张轮播图自动滚动
            noOnly = true;
        }else {
            //要是只有一张轮播图就别自动滚动了
            noOnly = false;
        }

        //设置页面的小点
        initDot();
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {

            //后面设置了BannerAdapter 的getcount 返回最大整型这里要避免数组越界
            position = position % mTitles.size();
            mTvTitle.setText(mTitles.get(position));
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

            //正常不做轮播图的写法
            //return mImageViews == null ? 0 : mImageViews.size();

            if (mImageViews.size() == 1) {// 一张图片时不用流动
                return mImageViews.size();
            }

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
            //position %=  mImageViews.size();
            //if (position < 0) {
                //position =  mImageViews.size() + position;
            //}
            //ImageView imageView = mImageViews.get(position);

            //2019-3-2 解决左划白屏的bug ↓
            position %=  mImageViews.size();
            if (position < 0) {
               position =  mImgPics.size() + position;
            }
            ImageView imageView = new ImageView(getContext());
            ImageUtil.getSinstance().displayPic(mImgPics.get(position % mImageViews.size()),imageView);
            //2019-3-2 解决左划白屏的bug ↑

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
                LELog.showLogWithLineNum(5,"003");
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);

            //警告:为了实现imageview 轮播图无限往一个方向滑动 不要在这里调用removeView
            //container.removeView((View) object);

            //2019-3-2 解决左划白屏的问题↓
            container.removeView((View)object);
            if (object != null){
                object = null;
            }

        }

        /**
         * 配合listview下拉刷新的方法
         * @param imageViews bannerview 图片url集合
         */
        public void updateData(ArrayList<ImageView> imageViews) {
            //先清除以前的数据
            mImageViews.clear();
            //再添加新的数据
            mImageViews.addAll(imageViews);
            //调用系统PagerAdapter的方法更新
            notifyDataSetChanged();
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        /**
         * 和window对象断开关系
         * 视图死亡（视图被移除，或者activity死亡之前）时调用
         * stop();方法中会移除handler合理的管理内存
         */
        stop();
        super.onDetachedFromWindow();
    }
}



