package com.bobo520.viewpagerdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 话题fragment
 */
public class TopicFragment extends LogFragment {

    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //返回的view咱们每个子类自己来确定,就不交给父类来确定了
        TextView textView = new TextView(getContext());
        textView.setText(getClass().getSimpleName());
        textView.setTextSize(35);
        return textView;
    }
}
