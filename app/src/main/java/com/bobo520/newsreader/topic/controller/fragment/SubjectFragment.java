package com.bobo520.newsreader.topic.controller.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.app.LogFragment;
import com.bobo520.newsreader.customDialog.LEloadingView;
import com.bobo520.newsreader.http.HttpHelper;
import com.bobo520.newsreader.http.OnResponseListener;
import com.bobo520.newsreader.news.controller.adapter.JokeAdater;
import com.bobo520.newsreader.news.model.JokeBean;
import com.bobo520.newsreader.news.view.BannerView;
import com.bobo520.newsreader.topic.controller.activity.SubjectActivity;
import com.bobo520.newsreader.topic.controller.adapter.TopicAdater;
import com.bobo520.newsreader.topic.model.TopicBean;
import com.bobo520.newsreader.util.Constant;
import com.bobo520.newsreader.util.IsNotFastClickUtils;
import com.bobo520.newsreader.util.LELog;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.bobo520.newsreader.topic.controller.activity.SubjectActivity.SUBJECT_OBJECT;



/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 话题 fragment
 */
public class SubjectFragment extends LogFragment {

        private ListView mLvHot;

        /**用来记录加载更多分页的变量*/
        private int page = 1;

        /**下拉刷新需要的参数*/
        private String maxtime = null;

        /**listview的adater*/
        private TopicAdater mTopicAdapter;

        /**网络请求成功的变量-默认为true*/
        private boolean isSuccess = true;

        /**用来下拉刷新的GitHub上找的下拉刷新控件*/
        private PtrClassicFrameLayout mPtrFrame;

        /**自定义bannerview的变量*/
        private BannerView mBannerView;

        /**加载时像X方那样的loding动画*/
        private KProgressHUD mKProgressHUD;

        /**数据源 集合*/
        List<TopicBean.DataBean> mListBeans;

    @Override
        public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View inflate = inflater.inflate(R.layout.frag_subject,container,false);
            mPtrFrame = (PtrClassicFrameLayout)inflate.findViewById(R.id.subject_ptr_frame);
            //关闭下拉刷新
            mPtrFrame.setEnabled(false);
            mLvHot = (ListView)inflate.findViewById(R.id.subject_lv_hot);
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

            /**
             * listView Item点击事件的处理
             */
            mLvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    LELog.showLogWithLineNum(5,"SubjectFragment点击了Item"+position);
                    //加上避免用户重复点击开启两个activity的方法
                    if (IsNotFastClickUtils.isNotFastClick()){
                        Intent intent = new Intent(getContext(), SubjectActivity.class);
                        //因为listView header 的原因这里的position要 - 1

                        //传递图片数据对象需要序列化才能传递
                        TopicBean.DataBean dataBean = mListBeans.get(position);
                        intent.putExtra(SUBJECT_OBJECT, (Serializable) dataBean);

                        startActivity(intent);
                    }
                }
            });

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


            if (getContext() != null){
                //开始网络请求-开始显示loading
                mKProgressHUD = KProgressHUD.create(getContext())
                        .setCustomView(new LEloadingView(getContext()))
                        .setLabel("Please wait",Color.GRAY)
                        .setBackgroundColor(Color.WHITE)
                        .show();
            }

            //请求体
           String url = "";

            // 当需要加载下一页时：需要传入加载上一页时返回值字段“maxtime”中的内容。
            if (!isLoadMore){
                page = 1;
                url = Constant.TOPIC_URL+page;
            }else {
                //上拉加载更多
                url = Constant.TOPIC_URL+page;
            }

            //开始进行网络请求
//            HttpHelper.getInstance().requestPost(Constant.BAISHI,null
//                    ,requestBody,new OnResponseListener() {

            HttpHelper.getInstance().requestHeaderGET(url, new OnResponseListener() {

                @Override
                public void onFail(IOException e) {

                    //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
                    isSuccess = true;

                    //结束下拉刷新（无论成功失败本次发起请求已经结束）
                    mPtrFrame.refreshComplete();
                    //loading结束（无论成功失败本次发起请求已经结束）
                    mKProgressHUD.dismiss();

                    Toast.makeText(getActivity(), "請求失败請檢查網絡", Toast.LENGTH_SHORT).show();
                    LELog.showLogWithLineNum(5,e.toString());
                }

                @Override
                public void onSuccess(String response) {

                   LELog.showLogWithLineNum(5,response.toString());

                    //结束下拉刷新（无论成功失败本次发起请求已经结束）
                    mPtrFrame.refreshComplete();

                    //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
                    isSuccess = true;

                    //loading结束（无论成功失败本次发起请求已经结束）
                    mKProgressHUD.dismiss();

                    //解析 gson fastjson
                    Gson gson = new Gson();
                    TopicBean topicBean = gson.fromJson(response, TopicBean.class);

                    page++;

                    //用户下拉刷新时的变量
                    setListViewData(topicBean,isLoadMore);
                }
            });

 }

        /**设置list view的数据*/
        private void setListViewData(TopicBean topicBean,boolean isLoadMore){

            //获取到 数据数组
            List<TopicBean.DataBean> listBeans = topicBean.getData();


            //第一次进来需要创建adater适配器
            if (mTopicAdapter == null){
                mTopicAdapter = new TopicAdater(listBeans);
                mListBeans = listBeans;
                mLvHot.setAdapter(mTopicAdapter);
            }else {
                //判断是否为加载更多
                if (isLoadMore){
                    mTopicAdapter.loadData(listBeans);
                    mListBeans.addAll(listBeans);
                }else {
                    //下拉刷新-添加之前先清空数据再添加
                    mTopicAdapter.updateData(listBeans);

                    //先清空一下之前的数据
                    mListBeans.clear();

                    //LELog.showLogWithLineNum(5,"先清空一下之前的数据");
                    mListBeans.addAll(listBeans);
                }
            }
        }



}


