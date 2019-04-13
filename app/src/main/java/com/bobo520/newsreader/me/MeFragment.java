package com.bobo520.newsreader.me;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bobo520.newsreader.me.adapter.MineWorkAdapter;
import com.bobo520.newsreader.me.model.MineWorkMap;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.customDialog.DensityUtil;
import com.bobo520.newsreader.fragment.LogFragment;
import com.bobo520.newsreader.me.decoration.GridWHSpaceDecoration;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 我的fragment
 */
public class MeFragment extends LogFragment {


    /**最上面的那个GIF动画的view*/
    private ImageView head;

    /**用户头像的圆角imageView*/
    private CircleImageView mIvUserPaint;

    /**用户昵称的TextView*/
    private TextView mTvUserName;

    /**用户头像下的RecyclerView*/
    private RecyclerView mWorkRecycler;



    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(getClass().getSimpleName() + "xmg", "onCreateView: " + "");
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        head = (ImageView)view.findViewById( R.id.head );
        mIvUserPaint = (CircleImageView)view.findViewById(R.id.iv_user_paint);
        mTvUserName = (TextView)view.findViewById(R.id.tv_user_name);
        mWorkRecycler = (RecyclerView)view.findViewById(R.id.work_recycler);

        //初始化RecyclerView
        initWorkPieces();

        return view;
    }

    private void initWorkPieces() {

        List<MineWorkMap> data = new ArrayList<>();
        //44*44  默认颜色 灰色：#888888   选择颜色 红色：#EA403C
        data.add(new MineWorkMap(R.drawable.message_btn_selector, "我的消息"));
        data.add(new MineWorkMap(R.drawable.message_default, "我的合同"));
        data.add(new MineWorkMap(R.drawable.message_default, "我的室友"));
        data.add(new MineWorkMap(R.drawable.message_default, "我的预约"));
        data.add(new MineWorkMap(R.drawable.message_default, "交租记录"));
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        mWorkRecycler.addItemDecoration(new GridWHSpaceDecoration(4, 0,
                DensityUtil.dip2px(getActivity(), 19), false));
        mWorkRecycler.setLayoutManager(layoutManager);
        MineWorkAdapter adapter = new MineWorkAdapter(getActivity(), data, R.layout.mine_work_grid_item);
        mWorkRecycler.setAdapter(adapter);
        //adapter.setOnItemClickListener(this);

    }
}
