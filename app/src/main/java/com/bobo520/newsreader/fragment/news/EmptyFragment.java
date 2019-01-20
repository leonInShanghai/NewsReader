package com.bobo520.newsreader.fragment.news;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bobo520.newsreader.fragment.LogFragment;

/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 空的目前就写一个fragment
 */
public class EmptyFragment extends LogFragment {

    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //返回的view咱们每个子类自己来确定,就不交给父类来确定了
        TextView textView = new TextView(getContext());
        textView.setTextColor(Color.RED);
        textView.setText("空空如也的示例");
        textView.setTextSize(35);
        return textView;
    }
}
