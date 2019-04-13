package com.bobo520.newsreader.me.adapter;

import android.content.Context;

import com.bobo520.newsreader.me.model.MineWorkMap;
import com.bobo520.newsreader.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;



/**
 * Created by 求知自学网 on 2019/4/13 Copyright © Leon. All rights reserved.
 * Functions:  我的界面的列表
 */
public class MineWorkAdapter extends SuperAdapter<MineWorkMap> {

    public MineWorkAdapter(Context context, List<MineWorkMap> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, MineWorkMap item) {
        holder.setText(R.id.tv, item.getName());
        holder.setImageResource(R.id.iv, item.getImgRes());
    }
}
