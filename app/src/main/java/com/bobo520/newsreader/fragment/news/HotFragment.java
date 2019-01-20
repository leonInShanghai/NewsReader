package com.bobo520.newsreader.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bobo520.newsreader.Constant;
import com.bobo520.newsreader.fragment.LogFragment;
import com.bobo520.newsreader.http.HttpHelper;
import com.bobo520.newsreader.http.OnResponseListener;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 头条fragment
 */
public class HotFragment extends LogFragment {

    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //返回的view咱们每个子类自己来确定,就不交给父类来确定了
        TextView textView = new TextView(getContext());
        textView.setText(getClass().getSimpleName());
        textView.setTextSize(35);
        return textView;
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

            }

            @Override
            public void onSuccess(Response response) {
                ResponseBody body = response.body();
                String string = null;
                try {
                    string = body.string();
                   Log.e("HotFragment网络请求数据:",string);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
