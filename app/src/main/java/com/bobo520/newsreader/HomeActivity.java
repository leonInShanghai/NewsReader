package com.bobo520.newsreader;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bobo520.newsreader.fragment.MeFragment;
import com.bobo520.newsreader.fragment.NewsFragment;
import com.bobo520.newsreader.fragment.TopicFragment;
import com.bobo520.newsreader.fragment.VaFragment;
import com.bobo520.newsreader.weiget.MyFragmentTabHost;

/**
 * Created by Leon on 2019/1/1 Copyright © Leon. All rights reserved.
 * Functions: app的 主頁面 各個fragment 都在這裏
 */
public class HomeActivity extends AppCompatActivity {

    //private FragmentTabHost mTabHost;
    private MyFragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Leon设置了最近淘宝等各大app流行的沉浸式状态栏
         LETrtStBarUtil.setTransparentToolbar(this);

        //mTabHost = (FragmentTabHost)findViewById(R.id.tabHost);
        mTabHost = (MyFragmentTabHost)findViewById(R.id.tabHost);
        initData();
    }

    private void initData(){
        //给FragmentTabHost控件设置数据 Fragment数组
        Class[] fragments = new Class[]{NewsFragment.class,VaFragment.class,
                TopicFragment.class,MeFragment.class};

        //图片选择器数组
        int[] resIds = new int[]{R.drawable.tab_news_selecter,R.drawable.tab_va_selecter,
                R.drawable.tab_topic_selecter,R.drawable.tab_my_selecter};

        //按钮下文字数组 注意不是R.string.tab_titles
        String[] tabTitles = getResources().getStringArray(R.array.tab_titles);

        /**
         * 1.能够进行fragment的切换，必须通过事务来进行
         * activity要继承自FragmentActivity或者AppCompatActivity 才可以:getSupportFragmentManager();
         */
        mTabHost.setup(getApplicationContext(),getSupportFragmentManager(),R.id.fl_frag);

        //2.添加一些fragment和tab给 mTabHost 控件 - 代码优化后
        for (int i = 0;i < fragments.length;i++){
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(String.valueOf(i));
            View view = View.inflate(getApplicationContext(),R.layout.view_tab_item,null);
            ImageView ivTab = (ImageView)view.findViewById(R.id.iv_tab);
            TextView tvTab = (TextView)view.findViewById(R.id.tv_tab);
            ivTab.setBackgroundResource(resIds[i]);
            tvTab.setText(tabTitles[i]);
            tabSpec.setIndicator(view);
            mTabHost.addTab(tabSpec, fragments[i],null);
        }

        /**
         * mTabHost常用方法-点击了第几个item
         */
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //当tabhost发生切换变化就会调用这个方法
                Log.e("0000","OnTabChangedListener"+"切换到了："+tabId);
            }
        });

        /**设置默认选中的tab item*/
        mTabHost.setCurrentTab(0);

    }
}
