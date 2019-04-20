package com.bobo520.newsreader.news.controller.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bobo520.newsreader.app.LogFragment;
import com.bobo520.newsreader.util.LELog;
import com.bobo520.newsreader.R;
import com.bobo520.newsreader.util.SpUtils;
import com.bobo520.newsreader.news.controller.adapter.NewsFragmentAdapter;
import com.bobo520.newsreader.news.controller.adapter.ShowTitleAdapter;
import com.bobo520.newsreader.news.controller.adapter.ToAddTitleAdapter;
import com.bobo520.newsreader.event.ShowTabEvent;
import com.bobo520.newsreader.util.JsonUtil;
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

    /**白屏上半（长按排序或删除以上） 数据持久化保存的键*/
    public static final String CACHE_SHOW_TITLE = "CACHE_SHOW_TITLE";

    /**白屏下半（长按排序或删除以下） 数据持久化保存的键*/
    public static final String CACHE_TOADD_TITLE = "CACHE_TOADD_TITLE";

    private NewsFragmentAdapter mNewsFragmentAdapter;

    /**用户上一次选择的 ShowTitle */
    private String lastUpdateTitleData = "";

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
                    //记录一下标题数据的更改
                    //lastUpdateTitleData = JsonUtil.listToString(mShowTitleAdapter.getList());

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

                //记录一下标题数据的更改
                //lastUpdateTitleData = JsonUtil.listToString(mShowTitleAdapter.getList());
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

                //保存标题数据到SP里面
                //优化项：判断一下，如果标题数据没有发生变化，就不去更新和保存标题数据了
                String nowString = JsonUtil.listToString(mShowTitleAdapter.getList());
                if (lastUpdateTitleData.equals(nowString)){
                    //Toast.makeText(getContext(),"没有做任何修改",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getContext(),"已将您的修改保存",Toast.LENGTH_SHORT).show();
                    saveCache();
                    lastUpdateTitleData = nowString;
                }

                //更新view page中的数据，以及标题控件 tablayout 中的数据
                //updateViewPager();
            }
            //用戶點擊后 箭頭朝向取反 原來朝上 變成朝下 反之 反之
            isDown = !isDown;
        }
    }

    //更新view page中的数据，以及标题控件 tablayout 中的数据
    private void updateViewPager() {

       ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        ArrayList<String> showTitleList = mShowTitleAdapter.getList();
        for (int i = 0;i < showTitleList.size();i++){
            if (i == 0){
                fragments.add(new ImportantNewsFragment());
            }else {
                //这里均展示占位fragment 有空了自己慢慢实现
                fragments.add(new EmptyFragment());
            }
        }
        //刷新界面AdapterUI
        if (mNewsFragmentAdapter != null){
            mNewsFragmentAdapter.update(fragments,showTitleList);
        }

        //还需要更新TabLayout标题
        mTablayout.setViewPager(mViewPager);
    }

    //保存标题数据到SP里面
    private void saveCache() {
        //避免用户快速切换造成空指针异常
        if (getContext() == null){return;}

        //得到mShowTitleAdapter 的数据源数组
        ArrayList<String> showTitleList = mShowTitleAdapter.getList();

        //将一个集合和json字符串互相转换的方法
        String string = JsonUtil.listToString(showTitleList);

        //将转换好的string 做持久化保存
        SpUtils.setString(getContext(),CACHE_SHOW_TITLE,string);

        //得到mShowTitleAdapter 的数据源数组
        ArrayList<String> toaddTitleList = mToAddAdapter.getList();

        //将一个集合和json字符串互相转换的方法
        String toaddString = JsonUtil.listToString(toaddTitleList);

        //将转换好的string 做持久化保存
        SpUtils.setString(getContext(),CACHE_TOADD_TITLE,toaddString);
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

        //注意 直接通过Arrays.asList(allTitles) 生成的list是无法进行直接增删的 如下↓
        //ShowTitleAdapter showTitleAdapter = new ShowTitleAdapter(new ArrayList<String>(Arrays.asList(allTitles)));

        String[] toAddTitles = getResources().getStringArray(R.array.to_add_news_titles);

        //将全部的标题数组转集合 String[] →  ArrayList<String>
        ArrayList<String> toAddList = new ArrayList<>();


        //读取缓存的标题数据，有缓存就使用缓存没有就使用XML中的数据
        if (getContext() != null){
            String cacheShowTitle = SpUtils.getString(getContext(),CACHE_SHOW_TITLE);
            String cacheToAddTitle = SpUtils.getString(getContext(),CACHE_TOADD_TITLE);

            if (TextUtils.isEmpty(cacheShowTitle)){
                showTitleList.addAll(Arrays.asList(allTitles));
            }else{
                //如果有缓存就使用缓存的
                showTitleList.addAll(JsonUtil.stringToList(cacheShowTitle));
            }

            if (TextUtils.isEmpty(cacheToAddTitle)){
                toAddList.addAll(Arrays.asList(toAddTitles));
            }else{
                //如果有缓存就使用缓存的
                toAddList.addAll(JsonUtil.stringToList(cacheToAddTitle));
            }
        }

        //将数组转换好的集合放入adapter的构造方法中
        mShowTitleAdapter = new ShowTitleAdapter(showTitleList,mTitles);
        mGvShowTitle.setAdapter(mShowTitleAdapter);

        //记录一下标题数据的更改-第一次初始化也要记录 位置放的不要再往上了 会空指针
        lastUpdateTitleData = JsonUtil.listToString(mShowTitleAdapter.getList());

        //将数组转换好的集合放入adapter的构造方法中
        mToAddAdapter = new ToAddTitleAdapter(toAddList);
        mGvAddTitle.setAdapter(mToAddAdapter);

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        fragments.add(new JokeFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new ImportantNewsFragment());
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
        ArrayList<String> list = mShowTitleAdapter.getList();
        //mNewsFragmentAdapter =  new NewsFragmentAdapter(getChildFragmentManager(),fragments,list);
        mNewsFragmentAdapter =  new NewsFragmentAdapter(getChildFragmentManager(),fragments);

        /**
         * 使用了viewpager和listview进行页面数据显示，在切换viewpager的时候会导致前面的fragment页面数据丢失，
         * 这是fragment重新加载而造成的问题，如果是固定数量viewpager，只需要指定页面数量，即可禁止重新加载：
         */
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(mNewsFragmentAdapter);

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
//                fragments.add(new ImportantNewsFragment());
//            }else {
//                //这里均展示占位fragment 有空了自己慢慢实现
//                fragments.add(new EmptyFragment());
//            }
//        }
