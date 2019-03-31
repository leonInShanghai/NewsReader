package com.bobo520.newsreader.fragment;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo520.newsreader.LELog;
import com.bobo520.newsreader.R;
import com.bobo520.newsreader.adapter.NewsFragmentAdapter;
import com.bobo520.newsreader.fragment.news.DisportFragment;
import com.bobo520.newsreader.fragment.news.EmptyFragment;
import com.bobo520.newsreader.fragment.news.HotFragment;
import com.flyco.tablayout.SlidingTabLayout;

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



    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_news, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mTablayout = (SlidingTabLayout) view.findViewById(R.id.tablayout);
        tvChangeTip = (TextView) view.findViewById(R.id.tv_change_tip);
        ibtnArrow = (ImageButton) view.findViewById(R.id.ibtn_arrow);
        tvChangeDone = (TextView) view.findViewById(R.id.tv_change_done);

        //ibtnArrow的动画效果
        initView();

        return view;
    }

    //ibtnArrow的点击事件监听
    private void initView(){
        ibtnArrow.setOnClickListener(new MyClickListener());
    }

    //用户点击了 ibtnArrow
    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //开始播放动画

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
