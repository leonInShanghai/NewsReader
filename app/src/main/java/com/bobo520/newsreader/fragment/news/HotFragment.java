package com.bobo520.newsreader.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.bobo520.newsreader.Constant;
import com.bobo520.newsreader.LELog;
import com.bobo520.newsreader.R;
import com.bobo520.newsreader.adapter.HotNewsAdater;
import com.bobo520.newsreader.bean.HotNewsBean;
import com.bobo520.newsreader.bean.HotNewsListBean;
import com.bobo520.newsreader.fragment.LogFragment;
import com.bobo520.newsreader.http.HttpHelper;
import com.bobo520.newsreader.http.OnResponseListener;
import com.bobo520.newsreader.weiget.banner.BannerView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 头条fragment
 */
public class HotFragment extends LogFragment {

    private ListView mLvHot;

    /**用来记录加载更多次数的变量*/
    private int loadMoreCount = 0;

    /**listview的adater*/
    private HotNewsAdater mHotNewsAdater;

    /**网络请求成功的变量-默认为true*/
    private boolean isSuccess = true;

    /**用来下拉刷新的GitHub上找的下拉刷新控件*/
    private PtrClassicFrameLayout mPtrFrame;

    /**自定义bannerview的变量*/
    private BannerView mBannerView;

    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_hot,container,false);
        mPtrFrame = (PtrClassicFrameLayout)inflate.findViewById(R.id.ptr_frame);
        mLvHot = (ListView)inflate.findViewById(R.id.lv_hot);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData(){

        //设置下拉刷新的配置
        setPullToRefres();

        //设置ListView的脚布局-上拉加载更多的加载框
        setFooter();

        requestData(false);
    }

    private void setPullToRefres(){
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                //下拉刷新-网络请求拿到最新的数据
                requestData(false);
            }

            /**
             * 当对listview列表进行下拉刷新，需要对该方法进行处理
             */
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // 默认实现，根据实际情况做改动
                //return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                //根据自身的情况修改后
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mLvHot, header);
            }
        });
    }

    private void setFooter(){
        //使用打气筒加载xml中的布局
        View footerView = View.inflate(getContext(), R.layout.view_foot, null);
        mLvHot.addFooterView(footerView);

        //监听滚动
        mLvHot.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //判断listview是否停止滚动
                if (scrollState == SCROLL_STATE_IDLE){//listview停止滚动-判断footerview是否显示了
                    //获取listview最后一个可见的控件的Position（位置）
                    int lastVisiblePosition = mLvHot.getLastVisiblePosition();
                    //获取listview的总item的数量
                    int count = mLvHot.getAdapter().getCount();

                    if (lastVisiblePosition == count - 1){//listview的footer你view显示了
                        //判断网络是否请求成功避免网络不好的情况下连续不断的请求
                        if (isSuccess){//如果上次请求成功
                            //当前正在请求变量置为false
                            isSuccess = false;
                            //开始加载更多
                            requestData(true);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    //请求数据
    private void requestData(final boolean isLoadMore){

        /**
         * 根据用户加载更多的次数，来不断的变化url
         * 第一次 ： 0-9
         * 第二次 ： 10-19
         * 第三次 ： ...     10 * 加载更多的次数 -  10 * 加载更多的次数 + 9
         */
        String url;
        if (!isLoadMore) {
            //下拉刷新-取出前面10条新闻的最新数据就行了
            url = Constant.getNewsUrl(0,9);
        } else {
            //上拉加载更多-注意loadMoreCount++;之前写前面写后面遇到了bug
            loadMoreCount++;
            url = Constant.getNewsUrl(loadMoreCount * 10, loadMoreCount * 10 + 9);
        }
        //HttpHelper已将子线程转换到UI线程请放心使用
        HttpHelper.getInstance().requestGET(url, new OnResponseListener() {
            @Override
            public void onFail(IOException e) {
                //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
                isSuccess = true;

                //结束下拉刷新（无论成功失败本次发起请求已经结束）
                mPtrFrame.refreshComplete();

                Toast.makeText(getActivity(), "請求失败請檢查網絡", Toast.LENGTH_SHORT).show();
                LELog.showLogWithLineNum(5,"onFail e："+e.getMessage());
            }

            @Override
            public void onSuccess(String response) {

                //结束下拉刷新（无论成功失败本次发起请求已经结束）
                mPtrFrame.refreshComplete();

                //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
                isSuccess = true;

                //解析 gson fastjson
                Gson gson = new Gson();
                HotNewsListBean hotNewsListBean = gson.fromJson(response, HotNewsListBean.class);
                setListViewData(hotNewsListBean,isLoadMore);
            }
        });
    }

    /**设置list view的数据*/
    private void setListViewData(HotNewsListBean hotNewsListBean,boolean isLoadMore){
        ArrayList<HotNewsBean> t1348647909107 = hotNewsListBean.getT1348647909107();
        //如果第一条是轮播图banner，那么就移除(2019年网易这边的风格是没有轮播图的)
        HotNewsBean firstBean = t1348647909107.get(0);
        //firstBean.getAbs()!=null && firstBean.getAbs().size()>0
        if (firstBean.getHasHead() == 1){//hashead如果为1就是轮播图不为1则不是轮播图
            //是轮播图就移除掉轮播图的数据
            t1348647909107.remove(0);
            Log.e("leon","有轮播图");
            //添加一个head给list view，制作轮播图
            setBanner(firstBean);
        }else {//第一次进来需要设置banner图if (mHotNewsAdater == null)
            //原来服务器是有返回轮播图的数据，按原来要写在if语句上半部分的
            setBanner(firstBean);
        }

        //第一次进来需要创建adater适配器
        if (mHotNewsAdater == null){
            mHotNewsAdater = new HotNewsAdater(t1348647909107);
            mLvHot.setAdapter(mHotNewsAdater);
        }else {
            //判断是否为加载更多
            if (isLoadMore){
                mHotNewsAdater.loadData(t1348647909107);
            }else {
                //下拉刷新-添加之前先清空数据再添加
                mHotNewsAdater.updateData(t1348647909107);
            }
        }
    }

    //设置轮播图
    private void setBanner(HotNewsBean firstBean) {
        //通过自定义控件来做一个封装，后面就方便复用了 getContext()空指针
        if (getContext() == null ) { return;}

        //ArrayList<BannerBean> abs = firstBean.getAbs();//2019年网易服务器没有返回这些
        //ArrayList<String> titles = new ArrayList<>();
        //ArrayList<String> imgUrls = new ArrayList<>();

        //由于2019年后网易没有返回abs（banner的数据）我先自己造了数据
        //for (int i = 0;i < ads.size();i++){
        //BannerBean bannerBean = ads.get(i);
        //titles.add(bannerBean.getTitle());
        //titles.add(bannerBean.getImgsrc());
        //}

        //----------上面是网络数据方法↑下面是造数据方法↓--------------

        ArrayList<String> imgUrls = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        imgUrls.add(Constant.BANNER1);
        imgUrls.add(Constant.BANNER2);
        titles.add("波波新闻有态度°");
        titles.add("波波 instant message");

        if (mBannerView == null){
            mBannerView = new BannerView(getContext());
            mBannerView.setBannerData(imgUrls, titles);
            mLvHot.addHeaderView(mBannerView);
        }else {
            mBannerView.updateData(imgUrls, titles);
        }
    }
}
