package com.bobo520.newsreader.me.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bobo520.newsreader.me.adapter.MineWorkAdapter;
import com.bobo520.newsreader.me.model.bean.MineWorkMap;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.customDialog.DensityUtil;
import com.bobo520.newsreader.app.LogFragment;
import com.bobo520.newsreader.me.decoration.GridWHSpaceDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;


/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 我的fragment
 */
public class MeFragment extends LogFragment implements View.OnClickListener, OnItemClickListener {


    /**最上面的那个GIF动画的view*/
    private GifImageView head;

    /**用户头像的圆角imageView*/
    private CircleImageView mIvUserPaint;

    /**用户昵称的TextView*/
    private TextView mTvUserName;

    /**用户头像下的RecyclerView*/
    private RecyclerView mWorkRecycler;

    /**右上角的消息图标*/
    private ImageView mIvMineMsg;

    //一款不错的上拉加载下拉刷新控件-SmartRefreshLayout
    private RefreshLayout mRefreshLayout;




    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(getClass().getSimpleName() + "xmg", "onCreateView: " + "");
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        head = (GifImageView)view.findViewById( R.id.head );
        mIvUserPaint = (CircleImageView)view.findViewById(R.id.iv_user_paint);
        mTvUserName = (TextView)view.findViewById(R.id.tv_user_name);
        mWorkRecycler = (RecyclerView)view.findViewById(R.id.work_recycler);
        mIvMineMsg = (ImageView)view.findViewById(R.id.iv_mine_msg);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnableRefresh(true);//启用刷新
        //设置 Header 为 贝塞尔雷达 样式 getResources().getColor(R.color.color_center_red)
        if (getContext() != null){
            mRefreshLayout.setRefreshHeader(new BezierRadarHeader(getContext())
                    .setEnableHorizontalDrag(true)
                    .setPrimaryColor(ContextCompat.getColor(getContext(),R.color.color_center_red)));
            mRefreshLayout.setOnRefreshListener(new MyOnRefreshListener());
        }

        //设置监听事件的代理为this
        mIvMineMsg.setOnClickListener(this);
        mIvUserPaint.setOnClickListener(this);


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
        adapter.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mIvMineMsg){
            Toast.makeText(getContext(),"1111111",Toast.LENGTH_SHORT).show();
        }else if (v == mIvUserPaint){
            Toast.makeText(getContext(),"22222",Toast.LENGTH_SHORT).show();
        }
    }

    //监听 mWorkRecycler item的点击事件
    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

            case 7:
                //我的会员

                break;
            case 8:

                break;
            case 9:

                break;
            case 10:

                break;
            case 11:

                break;
            case 12:

                break;
            case 13:

                break;
            case 14:

                break;
            case 15:
                //我的会员
//                if (loginIfNot()) {
//                    Bundle bundle = new Bundle();
//                    bundle.putLong("tag", 1);
//                    openActivityWithBundle(HomeAppliancesLeaseActivity.class, bundle);
//                }
                break;
            case 16:
                break;

        }
    }

    //mRefreshLayout下拉刷新
    class MyOnRefreshListener implements OnRefreshListener {

        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            //GifDrawable gifDrawable = (GifDrawable) head.getDrawable();
            //gifDrawable.start();//开始播放gif动画

            mRefreshLayout.finishRefresh();
            //gifDrawable.stop(); //停止播放gif动画
        }
    }
}
