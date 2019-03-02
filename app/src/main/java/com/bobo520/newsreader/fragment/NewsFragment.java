package com.bobo520.newsreader.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_news, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mTablayout = (SlidingTabLayout) view.findViewById(R.id.tablayout);
        return view;
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
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());
        fragments.add(new EmptyFragment());

        //嵌套使用的时候要使用ChildFragmentManager
        NewsFragmentAdapter newsFragmentAdapter =  new NewsFragmentAdapter(getChildFragmentManager(),fragments);

        /**
         * 使用了viewpager和listview进行页面数据显示，在切换viewpager的时候会导致前面的fragment页面数据丢失，
         * 这是fragment重新加载而造成的问题，如果是固定数量viewpager，只需要指定页面数量，即可禁止重新加载：
         */
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(newsFragmentAdapter);

        //绑定标题控件FlycoLayout与view pager绑定
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
