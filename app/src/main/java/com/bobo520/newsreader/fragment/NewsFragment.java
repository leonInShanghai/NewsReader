package com.bobo520.newsreader.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bobo520.newsreader.R;

/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 新闻fragment
 */
public class NewsFragment extends LogFragment {

    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //返回的view咱们每个子类自己来确定,就不交给父类来确定了
        View view = inflater.inflate(R.layout.frag_news,container,false);
        return view ;
    }
}
