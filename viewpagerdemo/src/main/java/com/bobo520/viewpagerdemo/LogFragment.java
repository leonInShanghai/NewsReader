package com.bobo520.viewpagerdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 打印fragment生命周期的fragment
 */
public abstract class LogFragment extends Fragment {

    private String mString;


    public static final String FRAG_KEY = "STR";

    //创建Fragment的视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(getClass().getSimpleName() + "xmg", "onCreateView: " + "");
        /*mString = getClass().getSimpleName();

        Bundle bundle = getArguments();
        if(bundle!=null){
            mString = bundle.getString(FRAG_KEY);
        }
        TextView textView = new TextView(getActivity());
        textView.setText(mString);
        textView.setTextSize(30);*/
        return getChildView(inflater,container,savedInstanceState);
    }

    public abstract View getChildView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);

    //第一个被调起的生命周期  关联宿主Activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(getClass().getSimpleName() + "xmg", "onAttach: " + "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getSimpleName() + "xmg", "onCreate: " + "");
    }

    //当宿主Activity创建完毕,就会被调用,一般在这个方法做数据的请求和初始化
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(getClass().getSimpleName() + "xmg", "onActivityCreated: " + "");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(getClass().getSimpleName() + "xmg", "onStart: " + "");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(getClass().getSimpleName() + "xmg", "onResume: " + "");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(getClass().getSimpleName() + "xmg", "onPause: " + "");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(getClass().getSimpleName() + "xmg", "onStop: " + "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(getClass().getSimpleName() + "xmg", "onDestroyView: " + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(getClass().getSimpleName() + "xmg", "onDestroy: " + "");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(getClass().getSimpleName() + "xmg", "onDetach: " + "");
    }
}