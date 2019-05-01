package com.bobo520.newsreader.video.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.app.LogFragment;
import com.bobo520.newsreader.news.controller.adapter.NewsFragmentAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * Created by 求知自学网 on 2019/5/1. Copyright © Leon. All rights reserved.
 * Functions: 为了 给 VaFragment 添加 导航栏  PtrClassicFrameLayout上面不能添加控件 出此下策
 */
public class VideoFragment extends LogFragment {


    private ViewPager mViewPager;
   // private SlidingTabLayout mTablayout;

    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_video, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        //mTablayout = (SlidingTabLayout) view.findViewById(R.id.tablayout);

        return view;
    }

    /**
     * 这里 大部分控件 都没有发挥自己的作用就为了在 VideoFragment 有导航栏 添加子类 VaFragment
     * 为了让导航栏显示 因为 PtrClassicFrameLayout（下拉刷新控件）不能 上面不能添加东西  这里的
     * ViewPager 只是 为了让VideoFragment添加 导航栏 再 子类 VaFragment 做的过渡
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //String[] mTitles = {""};
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        fragments.add(new VaFragment());

        //嵌套使用的时候要使用ChildFragmentManager
        //mNewsFragmentAdapter =  new NewsFragmentAdapter(getChildFragmentManager(),fragments,list);
        NewsFragmentAdapter mNewsFragmentAdapter =  new NewsFragmentAdapter(getChildFragmentManager(),fragments);

        /**
         * 使用了viewpager和listview进行页面数据显示，在切换viewpager的时候会导致前面的fragment页面数据丢失，
         * 这是fragment重新加载而造成的问题，如果是固定数量viewpager，只需要指定页面数量，即可禁止重新加载：
         */
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(mNewsFragmentAdapter);


        //绑定标题控件FlycoLayout与view pager绑定
       // mTablayout.setViewPager(mViewPager,mTitles);

    }
}
