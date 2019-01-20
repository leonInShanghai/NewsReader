package com.bobo520.viewpagerdemo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Leon on 2019/1/19 Copyright © Leon. All rights reserved.
 * Functions: viewpager使用的简单回顾
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private ArrayList<TextView> mTextViews;
    //private ArrayList<WebView> mTextViews;

    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager)findViewById(R.id.viewPager);

        mTextViews = new ArrayList<>();
        for (int i =0; i < 8;i++){
            TextView textView = new TextView(getApplicationContext());
            //WebView textView = new WebView(getApplicationContext());
            //textView.setWebViewClient(new WebViewClient());
            //textView.loadUrl("https://www.baidu.com");
            //textView.loadUrl("http://clickc.admaster.com.cn/c/a121927,b3163300,c3078,i0,m101,8a2,8b1,0a__OS__,z__IDFA__,0c__IMEI__,h");
            //设置自适应任意大小的pc网页-解决了轩尼诗广告不能加载的问题
            //textView.getSettings().setUseWideViewPort(true);
            //允许js运行-webView默認是不會加載js的
            //textView.getSettings().setJavaScriptEnabled(true);
            //开启本地DOM存储-解决网易严选不能加载的问题
            //textView.getSettings().setDomStorageEnabled(true);
            //textView.getSettings().setBlockNetworkImage(false);
            //textView.getSettings().setLoadWithOverviewMode(true);
            textView.setText(""+i);
            textView.setTextSize(40);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(i % 2 == 0 ? Color.RED : Color.GREEN);
            mTextViews.add(textView);
        }


        //准备Fragment集合出来
        mFragments = new ArrayList<Fragment>();

        for (int i = 0;i < 6;i++){
            Fragment fragment = i % 2 == 0 ? new TopicFragment() : new MeFragment();
            mFragments.add(fragment);
        }

        //让每个item都时fragment  FragmentPagerAdapter和FragmentStatePagerAdapter基本没有区别
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });

    }
}

/**  Adapter的一种
 *   mViewPager.setAdapter(new PagerAdapter() {
@Override
public int getCount() {
return mTextViews.size();
}

@Override
public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//这里是固定的写法-这样写就可以了
return view == object;
}

//创建每一页的布局view的方法
@NonNull
@Override
public Object instantiateItem(@NonNull ViewGroup container, int position) {
TextView textView = mTextViews.get(position);
//container（容器）就是view pager
container.addView(textView);
return textView;
}

//销毁每一页的布局view的方法
@Override
public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//super.destroyItem(container, position, object);
//container（容器）就是view pager
//TextView textView = mTextViews.get(position);
//container.removeView(textView);
container.removeView((View) object);
}
});
 */
