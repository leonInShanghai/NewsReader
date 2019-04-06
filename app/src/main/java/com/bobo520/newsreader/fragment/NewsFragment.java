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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo520.newsreader.HomeActivity;
import com.bobo520.newsreader.LELog;
import com.bobo520.newsreader.OnShowTabHostListener;
import com.bobo520.newsreader.R;
import com.bobo520.newsreader.adapter.NewsFragmentAdapter;
import com.bobo520.newsreader.adapter.ShowTitleAdapter;
import com.bobo520.newsreader.adapter.ToAddTitleAdapter;
import com.bobo520.newsreader.event.ShowTabEvent;
import com.bobo520.newsreader.fragment.news.DisportFragment;
import com.bobo520.newsreader.fragment.news.EmptyFragment;
import com.bobo520.newsreader.fragment.news.HotFragment;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;

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

    /**白屏上半（长按排序或删除以上） 的GridView*/
    private GridView mGvShowTitle;

    /**白屏下半（长按排序或删除以下） 的GridView*/
    private GridView mGvAddTitle;

    /**白屏上半（长按排序或删除以上） 的适配器*/
    private ShowTitleAdapter mShowTitleAdapter;

    /**白屏下半（长按排序或删除以下） 的适配器*/
    private ToAddTitleAdapter mToAddAdapter;

    /**白屏上半（长按排序或删除以上） 的gridview 有没有被用户长按过*/
    private boolean isLongClick = false;

    /**mTablayout 数据源数组*/
    private String[] mTitles;

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

        /**初始化白屏區域的GridView textView等控件*/
        initChangeTitleLayout();

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

    //初始化白屏展示所有标题数据的Layout布局
    private void initChangeTitleLayout(){

        //通过打气筒的形式添加
        if (getContext() == null){ return; }//避免快速切换空指针异常
        View inflate = View.inflate(getContext(),R.layout.view_change_title,null);
        mGvShowTitle = (GridView)inflate.findViewById( R.id.gv_show_title );
        mGvAddTitle = (GridView)inflate.findViewById( R.id.gv_add_title );

        //添加到FrameLayout中 这里不是在XML中添加 而是用Java添加到FrameLayout
        mFlChangeTitle.addView(inflate);

        //设置GridView的长按事件
        mGvShowTitle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //用户长按了
                isLongClick = true;

                //如果用户长按就显示item左上角删除图片 参数二：当前所处的tab position
                mShowTitleAdapter.setShowDelete(true,mShowTitleAdapter.getLisStr(mTablayout.
                                getCurrentTab()));

                //显示完成文本
                tvChangeDone.setVisibility(View.VISIBLE);

                //return false; 这里要改为return true 否者触摸事件没有被消费掉 点击事件会响到
                return true;
            }
        });

        //GridView的点击事件，进行增删
        mGvShowTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //如果左上角显示了删除图片
                if (mShowTitleAdapter.isShowDelete(mShowTitleAdapter.getLisStr(position),isLongClick)){
                    //删除该item，将其添加到下面的grid view中
                    String deleteItem = mShowTitleAdapter.deleteItem(position);
                    mToAddAdapter.addItem(deleteItem);
                }else{//如果左上角没有显示删除图片 用户点击跳转到 对应的tab 并且收起所有的标题
                    if (position < mTablayout.getTabCount()) {

                        //方法一：注意不要直接设置索引 索引当 用户删除 后位置会改变照成不正确
                        //String lisStr = mShowTitleAdapter.getLisStr(position);
                        //mViewPager.setCurrentItem(position);

                        //方法二：注意不要直接设置索引 索引当 用户删除 后位置会改变照成不正确
                        String lisStr = mShowTitleAdapter.getLisStr(position);//获取用户点击索引的标题
                        for (int i = 0;i < mTitles.length;i++){
                            if (mTitles[i].equals(lisStr)){
                                mTablayout.setCurrentTab(i);
                            }
                        }

                        //用代码来点击箭头按钮收起所有的标题  perform:执行的意识
                        ibtnArrow.performClick();
                        tvChangeDone.performClick();
                    }
                }

                //点击后还原不可还原会有bug
                //isLongClick = false;
            }
        });


        //添加titleGridView 的点击事件处理
        mGvAddTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击删除自己的item title
                String deleteItem = mToAddAdapter.deleteItem(position);

                //给mGvShowTitle添加item title
                mShowTitleAdapter.addItem(deleteItem);
            }
        });

        //选择删除tab类型后 完成按钮的点击事件
        tvChangeDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击就不再显示左上角的删除图片
                mShowTitleAdapter.setShowDelete(false,mShowTitleAdapter.getLisStr(
                        mTablayout.getCurrentTab()));

                //并且将自己隐藏
                tvChangeDone.setVisibility(View.GONE);

                //点击后还原用户长按的状态
                isLongClick = false;
            }
        });
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

                //让切换栏目显示遮挡tabHost
                tvChangeTip.setVisibility(View.VISIBLE);
                tvChangeTip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //这里并不是为了处理点击事件而是为了避免点击事件穿透用户点击到 下层的TabHost
                        LELog.showLogWithLineNum(5,"避免了点击事件穿透😂");
                    }
                });

                //isDown = false; 後面代碼精簡成一句

                //讓白色的顯示所有標題的區域展示
                mFlChangeTitle.setVisibility(View.VISIBLE);

                //開始show動畫
                mFlChangeTitle.startAnimation(mTranslateAnimShow);


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

                //让切换栏目隐藏
                tvChangeTip.setVisibility(View.INVISIBLE);

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

        //标题数据TabHost数据源
        mTitles = getResources().getStringArray(R.array.news_titles);

        //设置展示标题的网络控件GridView的数据(适配器)
        String allTitles[] = getResources().getStringArray(R.array.news_titles_all);

        //将全部的标题数组转集合 String[] →  ArrayList<String>
        ArrayList<String> showTitleList = new ArrayList<>();
        showTitleList.addAll(Arrays.asList(allTitles));

        //注意 直接通过Arrays.asList(allTitles) 生成的list是无法进行直接增删的 如下↓
        //ShowTitleAdapter showTitleAdapter = new ShowTitleAdapter(new ArrayList<String>(Arrays.asList(allTitles)));

        //将数组转换好的集合放入adapter的构造方法中
        mShowTitleAdapter = new ShowTitleAdapter(showTitleList,mTitles);
        mGvShowTitle.setAdapter(mShowTitleAdapter);

        String[] toAddTitles = getResources().getStringArray(R.array.to_add_news_titles);

        //将全部的标题数组转集合 String[] →  ArrayList<String>
        ArrayList<String> toAddList = new ArrayList<>();
        toAddList.addAll(Arrays.asList(toAddTitles));

        //将数组转换好的集合放入adapter的构造方法中
        mToAddAdapter = new ToAddTitleAdapter(toAddList);
        mGvAddTitle.setAdapter(mToAddAdapter);


        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

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
        if (mTitles.length < 5 && mTitles.length > 2 &&getActivity() != null && getContext() != null){
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
            mTablayout.setTabWidth(screenWidth / mTitles.length);
        }


        //绑定标题控件FlycoLayout与view pager绑定\
        mTablayout.setViewPager(mViewPager,mTitles);
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
