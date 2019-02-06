package com.bobo520.newsreader.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.flyco.tablayout.widget.MsgView;

import java.util.ArrayList;

/**
 * Created by Leon on 2019/2/2. Copyright © Leon. All rights reserved.
 * Functions: 自定义的父类adapter 将前三个可以抽取的方法抽取到本类中
 * 抽象类有些（BaseAdapter的第四个方法）不实现也可以
 * 不确定的泛型声明的方法 ： class MyBaseAdapter<T>
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    protected ArrayList<T> mList;

    MyBaseAdapter(ArrayList<T> list){
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
