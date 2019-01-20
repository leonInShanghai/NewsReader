package com.bobo520.newsreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Leon on 2019/1/20. Copyright © Leon. All rights reserved.
 * Functions: 新闻fragment 的适配器
 * FragmentStatePagerAdapter 和 FragmentPagerAdapter 的不同之处：
 * FragmentStatePagerAdapter：只保存fragment的状态 Adapter中fragment实例可以被销毁，建议在fragment页数较多时使用
 * FragmentPagerAdapter 保存整个fragment，建议在fragment页数较少的时候使用
 */
public class NewsFragmentAdapter extends FragmentStatePagerAdapter{

    private ArrayList<Fragment> mFragments;

    public NewsFragmentAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }
}
