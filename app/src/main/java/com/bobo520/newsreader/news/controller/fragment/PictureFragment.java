package com.bobo520.newsreader.news.controller.fragment;

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
import com.bobo520.newsreader.news.controller.activity.GifPictureActivity;
import com.bobo520.newsreader.news.controller.activity.PictureActivity;
import com.bobo520.newsreader.news.controller.adapter.JokeAdater;
import com.bobo520.newsreader.news.controller.adapter.PictureAdater;
import com.bobo520.newsreader.news.model.JokeBean;
import com.bobo520.newsreader.news.model.PictureBean;
import com.bobo520.newsreader.news.view.BannerView;
import com.bobo520.newsreader.util.Constant;
import com.bobo520.newsreader.util.IsNotFastClickUtils;
import com.bobo520.newsreader.util.LELog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 图片 fragment
 */
public class PictureFragment extends LogFragment {

        private ListView mLvHot;

        /**用来记录加载更多分页的变量*/
        private int page = 0;

        /**下拉刷新需要的参数*/
        private String maxtime = null;

        /**listview的adater*/
        private PictureAdater mPictureAdater;

        /**网络请求成功的变量-默认为true*/
        private boolean isSuccess = true;

        /**用来下拉刷新的GitHub上找的下拉刷新控件*/
        private PtrClassicFrameLayout mPtrFrame;

        /**自定义bannerview的变量*/
        private BannerView mBannerView;

        /**加载时像X方那样的loding动画*/
        private KProgressHUD mKProgressHUD;

        /**数据源数组集合*/
        private List<PictureBean.ListBean> mList;


    @Override
        public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View inflate = inflater.inflate(R.layout.frag_joke,container,false);
            mPtrFrame = (PtrClassicFrameLayout)inflate.findViewById(R.id.joke_ptr_frame);
            mLvHot = (ListView)inflate.findViewById(R.id.joke_lv_hot);
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

                    LELog.showLogWithLineNum(5,"JokeFragment点击了Item"+position);
                    //加上避免用户重复点击开启两个activity的方法
                    if (IsNotFastClickUtils.isNotFastClick()){

                        //普通图片跳转到 PictureActivity
                        if (mList.get(position).getIs_gif().equals("0")){
                            Intent intent = new Intent(getContext(), PictureActivity.class);
                            intent.putExtra(Constant.PICTURE_URL,mList.get(position).getImage0());
                            startActivity(intent);

                        //gif图片跳转到
                        }else if (mList.get(position).getIs_gif().equals("1")){
                            Intent intent = new Intent(getContext(), GifPictureActivity.class);
                            intent.putExtra(Constant.PICTURE_URL,mList.get(position).getImage0());
                            startActivity(intent);
                        }

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
            RequestBody requestBody;

            // 当需要加载下一页时：需要传入加载上一页时返回值字段“maxtime”中的内容。
            if (!isLoadMore){
                if (TextUtils.isEmpty(maxtime)) {
                    //正常加载  .add("page", String.valueOf(page))
                    requestBody =  new FormBody.Builder()
                            .add("a", "list")
                            .add("c", "data")
                            //type  false  int  1为全部，10为图片，29为段子，31为音频，41为视频，默认为1
                            .add("type", "10")
                            .build();
                }else{
                    page = 0;
                    //下拉刷新.add("page", String.valueOf(page))
                    requestBody =  new FormBody.Builder()
                            .add("a", "list")
                            .add("c", "data")
                            .add("type", "10")
                            .add("maxtime",maxtime)
                            .build();
                }
            }else {
                page++;
                Toast.makeText(getContext(),"上拉加载更多"+page+"--"+maxtime,Toast.LENGTH_SHORT).show();
                //上拉加载更多
                requestBody = new FormBody.Builder()
                    .add("a", "list")
                    .add("c", "data")
                    .add("page", String.valueOf(page))
                    .add("type", "10")
                    .add("maxtime",maxtime)
                    .build();

            }

            //开始进行网络请求
            HttpHelper.getInstance().requestPost(Constant.BAISHI,null
                    ,requestBody,new OnResponseListener() {
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

                  // LELog.showLogWithLineNum(5,response.toString());

                    //结束下拉刷新（无论成功失败本次发起请求已经结束）
                    mPtrFrame.refreshComplete();

                    //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
                    isSuccess = true;

                    //loading结束（无论成功失败本次发起请求已经结束）
                    mKProgressHUD.dismiss();

                    //解析 gson fastjson
                    Gson gson = new Gson();
                    PictureBean pictureBean = gson.fromJson(response, PictureBean.class);

                    //用户下拉刷新时的变量
                    if (pictureBean != null && pictureBean.getInfo() != null){
                        maxtime = pictureBean.getInfo().getMaxtime();
                    }
                    setListViewData(pictureBean,isLoadMore);
                }
            });

 }

        /**设置list view的数据  mPictureAdater*/
        private void setListViewData(PictureBean pictureBean,boolean isLoadMore){

            //获取到 数据数组
            List<PictureBean.ListBean> listBeans = pictureBean.getList();


            //第一次进来需要创建adater适配器
            if (mPictureAdater == null && getContext() != null){
                mPictureAdater = new PictureAdater(listBeans,getContext());
                mList = listBeans;
                mLvHot.setAdapter(mPictureAdater);
            }else {
                //判断是否为加载更多
                if (isLoadMore){
                    mPictureAdater.loadData(listBeans);
                    mList.addAll(listBeans);
                }else {
                    //下拉刷新-添加之前先清空数据再添加
                    mPictureAdater.updateData(listBeans);
                    //先清空一下之前的数据
                    mList.clear();

                    //LELog.showLogWithLineNum(5,"先清空一下之前的数据");
                    mList.addAll(listBeans);
                }
            }
        }



}


