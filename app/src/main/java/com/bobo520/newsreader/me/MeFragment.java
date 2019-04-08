package com.bobo520.newsreader.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.fragment.LogFragment;

/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 我的fragment
 */
public class MeFragment extends LogFragment {

    //Error inflating class android.support.v7.widget.RecyclerView
    private RecyclerView recyclerView;


    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(getClass().getSimpleName() + "xmg", "onCreateView: " + "");
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        return view;
    }
}
