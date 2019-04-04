package com.bobo520.newsreader.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo520.newsreader.HomeActivity;
import com.bobo520.newsreader.LELog;
import com.bobo520.newsreader.OnShowTabHostListener;
import com.bobo520.newsreader.R;
import com.bobo520.newsreader.adapter.NewsFragmentAdapter;
import com.bobo520.newsreader.event.ShowTabEvent;
import com.bobo520.newsreader.fragment.news.DisportFragment;
import com.bobo520.newsreader.fragment.news.EmptyFragment;
import com.bobo520.newsreader.fragment.news.HotFragment;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 新闻fragment
 */
public class NewsFragment extends LogFragment {

    private ViewPager mViewPager;
    private SlidingTabLayout mTablayout;

    private TextView tvChangeTip;
    private ImageButton ibtnArrow;
    private TextView tvChangeDone;
    //白色的顯示所有標題的區域
    private FrameLayout mFlChangeTitle;


    /**mTablayout中箭头的属性动画 方法一*/
    private ObjectAnimator mAnimaUp;

    /**mTablayout中箭头的属性动画 方法二*/
    private ValueAnimator mAnimUp;
    private ValueAnimator mAnimDown;

    /**mTablayout中 箭頭旋轉（朝上or朝下）的變量 默認為朝下*/
    private boolean isDown = true;

    /**mTablayout中 箭頭旋轉動畫是否處於動畫中 默認為否*/
    private boolean isAnimStart = false;

    /**準備補間動畫來給白屏區域設置位移-顯示*/
    private TranslateAnimation mTranslateAnimShow;

    /**準備補間動畫來給白屏區域設置位移-隱藏*/
    private TranslateAnimation mTranslateAnimHide;



    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_news, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mTablayout = (SlidingTabLayout) view.findViewById(R.id.tablayout);
        tvChangeTip = (TextView) view.findViewById(R.id.tv_change_tip);
        ibtnArrow = (ImageButton) view.findViewById(R.id.ibtn_arrow);
        tvChangeDone = (TextView) view.findViewById(R.id.tv_change_done);
        mFlChangeTitle = (FrameLayout)view.findViewById(R.id.fl_change_title);

        //ibtnArrow的动画效果
        initView();

        return view;
    }

    //ibtnArrow的点击事件监听
    private void initView(){
        //方法一：有一定的局限性 String propertyName必须能提供get 和 set 方法
        //回顾属性动画ObjectAnimator. Object target, String propertyName, float... values 方法一
        //mAnimaUp = ObjectAnimator.ofFloat(ibtnArrow,"rotation",0,180);
        //mAnimaUp.setDuration(500);//动画的持续事件500毫秒

        //属性动画方法二: ValueAnimator
        MyAnimatorUpdateListener myAnimatorUpdateListener = new MyAnimatorUpdateListener();
        //設置動畫完成用戶才能點擊
        MyAnimatorListenerAdapter myAnimatorListenerAdapter = new MyAnimatorListenerAdapter();
        mAnimUp = ValueAnimator.ofFloat(0,180);
        mAnimUp.setDuration(500);//动画的持续事件500毫秒
        mAnimUp.addUpdateListener(myAnimatorUpdateListener);

        //倒轉動畫
        mAnimDown = ValueAnimator.ofFloat(180,0);
        mAnimDown.setDuration(500);//动画的持续事件500毫秒
        mAnimDown.addUpdateListener(myAnimatorUpdateListener);

        //設置動畫完成用戶才能點擊
        mAnimDown.addListener(myAnimatorListenerAdapter);

        //準備補間動畫來給白屏區域設置位移-顯示
        mTranslateAnimShow = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, -1,
                TranslateAnimation.RELATIVE_TO_SELF, 0);
        mTranslateAnimShow.setDuration(500);//設置動畫持續時間500毫秒

        //準備補間動畫來給白屏區域設置位移-隱藏
        mTranslateAnimHide = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, -1);
        mTranslateAnimHide.setDuration(500);//設置動畫持續時間500毫秒

        //设置ibtnArrow点击事件
        ibtnArrow.setOnClickListener(new MyClickListener());
    }

    //代碼抽取 new AnimatorListenerAdapter()
    private class MyAnimatorListenerAdapter extends AnimatorListenerAdapter{

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            //設置動畫已經開始了
            isAnimStart = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //設置動畫已經結束了
            isAnimStart = false;
        }
    }

    //代碼抽取 ValueAnimator.AnimatorUpdateListener()
    private class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener{

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float animatedValue = (float) animation.getAnimatedValue();
            ibtnArrow.setRotation(animatedValue);
        }
    }

    //用户点击了 ibtnArrow
    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            //开始播放动画 方法一：
            //mAnimaUp.start();

            //如果動畫中點擊無效 達到運行完一個動畫才開始另一個動畫 避免用戶快速點擊二次 動畫剛正轉立刻又反轉
            if (isAnimStart){
                //如果動畫正在執行中就不再執行後續的邏輯
                return;
            }

            //开始播放动画 方法二：
            if (isDown) {
                //動畫正轉
                mAnimUp.start();
                //isDown = false; 後面代碼精簡成一句

                //讓白色的顯示所有標題的區域展示
                mFlChangeTitle.setVisibility(View.VISIBLE);

                //開始show動畫
                mFlChangeTitle.startAnimation(mTranslateAnimShow);

                //处理 Android 点击穿透
                mFlChangeTitle.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //TODO: 用户点击的时候想做些什么在这里添加

                        return true;
                    }
                });

                //調用HomeActivity 公有方法去隱藏下面的TabHost控件-第一種寫法
                //HomeActivity activity = (HomeActivity)getActivity();
                //activity.showTabHost(false);

                //調用HomeActivity 公有方法去隱藏下面的TabHost控件-第二種寫法
                //OnShowTabHostListener tabHostListener = (OnShowTabHostListener)getActivity();
                //tabHostListener.showTabHost(false);

                //HomeActivity 隱藏下面的TabHost控件第三種寫法 EventBus
                EventBus.getDefault().post(new ShowTabEvent(false));

            }else{
                //動畫倒轉
                mAnimDown.start();
                //isDown = true; 後面代碼精簡成一句

                //讓白色的顯示所有標題的區域隱藏
                mFlChangeTitle.setVisibility(View.GONE);

                //開始hide動畫
                mFlChangeTitle.startAnimation(mTranslateAnimHide);

                //調用HomeActivity 公有方法去顯示下面的TabHost控件 第一種寫法
                //HomeActivity activity = (HomeActivity)getActivity();
                //activity.showTabHost(true);

                //調用HomeActivity 公有方法去隱藏下面的TabHost控件-第二種寫法
                //OnShowTabHostListener tabHostListener = (OnShowTabHostListener)getActivity();
                //tabHostListener.showTabHost(true);

                //HomeActivity 隱藏下面的TabHost控件第三種寫法  EventBus
                EventBus.getDefault().post(new ShowTabEvent(true));
            }
            //用戶點擊后 箭頭朝向取反 原來朝上 變成朝下 反之 反之
            isDown = !isDown;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //在onActivityCreated中来设置数据
        initData();
    }

    private void initData(){

         ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        //标题数据
        String[] titles = getResources().getStringArray(R.array.news_titles);

        fragments.add(new HotFragment());
        fragments.add(new DisportFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());
//        fragments.add(new EmptyFragment());

        //嵌套使用的时候要使用ChildFragmentManager
        NewsFragmentAdapter newsFragmentAdapter =  new NewsFragmentAdapter(getChildFragmentManager(),fragments);

        /**
         * 使用了viewpager和listview进行页面数据显示，在切换viewpager的时候会导致前面的fragment页面数据丢失，
         * 这是fragment重新加载而造成的问题，如果是固定数量viewpager，只需要指定页面数量，即可禁止重新加载：
         */
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(newsFragmentAdapter);

        //解决4个tab的时候不好看 方法一：
        //设置mTablayout（SlidingTabLayout）标题宽度为 屏幕宽度 / titles.length
        if (titles.length < 5 && getActivity() != null && getContext() != null){
            WindowManager wm = (WindowManager) getActivity().getSystemService(getContext().WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;// 屏幕宽度（像素）
            int height= dm.heightPixels; // 屏幕高度（像素）
            float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
            int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）

            //计算ibtnArrow的宽度
            int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            ibtnArrow.measure(w, h);
            int ibtnArrowWidth = (int) (ibtnArrow.getMeasuredWidth() / density);

            //屏幕宽度：屏幕宽度（像素）/ 屏幕密度
            //int screenWidth = (int) (width / density);

            //宽度算法:屏幕宽度（像素）/ 屏幕密度  需要 （- 24 （是mTablayout 左右边距） - ibtn_arrow 宽度）
            int screenWidth = (int) (width / density - 24 - ibtnArrowWidth);
            mTablayout.setTabWidth(screenWidth / titles.length);
        }


        //绑定标题控件FlycoLayout与view pager绑定\
        mTablayout.setViewPager(mViewPager,titles);
    }

}


//        for (int i = 0;i < titles.length;i++){
//            if (i == 0){
//                fragments.add(new HotFragment());
//            }else {
//                //这里均展示占位fragment 有空了自己慢慢实现
//                fragments.add(new EmptyFragment());
//            }
//        }
