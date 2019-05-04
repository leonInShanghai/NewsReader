package com.bobo520.newsreader.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.event.ShowTabEvent;
import com.bobo520.newsreader.me.fragment.MeFragment;
import com.bobo520.newsreader.me.share.ShareActivity;
import com.bobo520.newsreader.news.controller.fragment.NewsFragment;
import com.bobo520.newsreader.topic.controller.fragment.TopicFragment;
import com.bobo520.newsreader.util.LETrtStBarUtil;
import com.bobo520.newsreader.video.controller.fragment.VideoFragment;
import com.bobo520.newsreader.weiget.MyFragmentTabHost;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Leon on 2019/1/1 Copyright © Leon. All rights reserved.
 * Functions: app的 主頁面 各個fragment 都在這裏
 * 用第二种隐藏tabhost 的方法时需要 implements OnShowTabHostListener 当前使用的是第三种EventBus
 */
public class HomeActivity extends AppCompatActivity  {

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

        //为了能够监听 NewsFragment 发出的隐藏tabHost 的事件 必须先注册eventbus
        EventBus.getDefault().register(this);

        initData();
    }

    /**前2种方法直接调用 公開的- 顯示/隱藏 底部 showTabHost的方法
     * 后面用了eventbus 3.0 必须加注解 @Subscribe
     */
    @Subscribe
    public void showTabHost(ShowTabEvent showTabEvent){
        if (showTabEvent.needShow){
            mTabHost.setVisibility(View.VISIBLE);
        }else{
            mTabHost.setVisibility(View.GONE);
        }
    }

    private void initData(){
        //给FragmentTabHost控件设置数据 Fragment数组
        Class[] fragments = new Class[]{NewsFragment.class,VideoFragment.class,
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

    //友盟分享的回调 调用的起点是在 MeFragment里面  回调 友盟官方说必须在Activity中
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UMShareAPI.get(HomeActivity.this).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        //销毁 EventBus 合理的管理内存   unregister：未挂号（释放）的意识
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

   public void onBackPressed() {
        if (JCVideoPlayerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }

}
