package com.bobo520.newsreader.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo520.newsreader.Constant;
import com.bobo520.newsreader.R;
import com.bobo520.newsreader.adapter.HotNewsAdater;
import com.bobo520.newsreader.bean.BannerBean;
import com.bobo520.newsreader.bean.HotNewsBean;
import com.bobo520.newsreader.bean.HotNewsListBean;
import com.bobo520.newsreader.fragment.LogFragment;
import com.bobo520.newsreader.http.HttpHelper;
import com.bobo520.newsreader.http.OnResponseListener;
import com.bobo520.newsreader.weiget.banner.BannerView;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.ResponseBody;

/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 头条fragment
 */
public class HotFragment extends LogFragment {

    private ListView mLvHot;


    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_hot,container,false);
        mLvHot = (ListView)inflate.findViewById(R.id.lv_hot);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData(){
        requestData();
    }

    //请求数据
    private void requestData(){

        HttpHelper.getInstance().requestGET(Constant.NEWS_URL, new OnResponseListener() {
            @Override
            public void onFail() {
                //当前是再OK HTTP创建的子线程中要做操作一定要在主线程中
                if (getActivity() != null) {//避免fragment切换快的时候空指针异常
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //请求失败-先提示用户没有联网-再处理跳转逻辑
                            Toast.makeText(getActivity(), "請求失败請檢查網絡", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onSuccess(String response) {
                //解析 gson fastjson
                Gson gson = new Gson();
                HotNewsListBean hotNewsListBean = gson.fromJson(response, HotNewsListBean.class);
                setListViewData(hotNewsListBean);
            }
        });
    }

    /**设置list view的数据*/
    private void setListViewData(HotNewsListBean hotNewsListBean){
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
        }else {
            //原来服务器是有返回轮播图的数据，按原来要写在if语句上半部分的
            setBanner(firstBean);
        }
        HotNewsAdater hotNewsAdater = new HotNewsAdater(t1348647909107);
        mLvHot.setAdapter(hotNewsAdater);
    }

    //设置轮播图
    private void setBanner(HotNewsBean firstBean){
        //通过自定义控件来做一个封装，后面就方便复用了
        BannerView bannerView = new BannerView(getContext());
        //ArrayList<BannerBean> abs = firstBean.getAbs();//2019年网易服务器没有返回这些

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> imgUrls = new ArrayList<>();

        //由于2019年后网易没有返回abs（banner的数据）我先自己造了数据
//        for (int i = 0;i < ads.size();i++){
//            BannerBean bannerBean = ads.get(i);
//            titles.add(bannerBean.getTitle());
//            titles.add(bannerBean.getImgsrc());
//        }
        bannerView.setBannerData(imgUrls,titles);
        mLvHot.addHeaderView(bannerView);
    }
}
