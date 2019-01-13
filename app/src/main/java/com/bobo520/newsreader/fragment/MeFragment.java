package com.bobo520.newsreader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 我的fragment
 */
public class MeFragment extends LogFragment {

    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(getClass().getSimpleName() + "xmg", "onCreateView: " + "");
        //返回的view咱们每个子类自己来确定,就不交给父类来确定了
        TextView textView = new TextView(getContext());
        textView.setText(getClass().getSimpleName());
        textView.setTextSize(35);
        return textView;
    }
}
