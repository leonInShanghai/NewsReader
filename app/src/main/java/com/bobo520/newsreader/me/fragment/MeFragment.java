package com.bobo520.newsreader.me.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bobo520.newsreader.customDialog.LEloadingView;
import com.bobo520.newsreader.customDialog.SelectDialog;
import com.bobo520.newsreader.http.HttpHelper;
import com.bobo520.newsreader.http.OnResponseListener;
import com.bobo520.newsreader.me.activity.LoginActivity;
import com.bobo520.newsreader.me.activity.MessageActivity;
import com.bobo520.newsreader.me.adapter.MineWorkAdapter;
import com.bobo520.newsreader.me.model.bean.MineWorkMap;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.customDialog.DensityUtil;
import com.bobo520.newsreader.app.LogFragment;
import com.bobo520.newsreader.me.decoration.GridWHSpaceDecoration;
import com.bobo520.newsreader.me.share.ShareActivity;
import com.bobo520.newsreader.news.model.UserBean;
import com.bobo520.newsreader.util.Constant;
import com.bobo520.newsreader.util.ImageUtil;
import com.bobo520.newsreader.util.LELog;
import com.bobo520.newsreader.util.SpUtils;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

import org.byteam.superadapter.OnItemClickListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.droidsonroids.gif.GifImageView;

import static java.lang.String.*;


/**
 * Created by Leon on 2019/1/13. Copyright © Leon. All rights reserved.
 * Functions: 我的fragment
 */
public class MeFragment extends LogFragment implements View.OnClickListener, OnItemClickListener,
        TakePhoto.TakeResultListener, InvokeListener {

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

    //这里主要时为了接收广播-接收一定onDestroy() 关闭
    protected LocalBroadcastManager mLBM;

    /**网络请求成功的变量-默认为true*/
    private boolean isSuccess = true;

    /**加载时像X方那样的loding动画*/
    private KProgressHUD mKProgressHUD;

    /**用户选择 拍照 头像的 dialog*/
    private SelectDialog photoDialog;

    /** 支持通过相机拍照获取图片- 支持从相册选择图片- 支持从文件选择图片- 支持多图选择*/
    private TakePhoto takePhoto;

    private InvokeParam invokeParam;


    //接收到收到新极光推送广播的处理@drawable/mine_msg_ic_selector
    private BroadcastReceiver ReceivedANewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mIvMineMsg.setImageResource(R.drawable.right_new_message);
        }
    };

    //接收到用户通过安卓手机的通知中心点击直接进入消息详情的通知（即用户已经看过消息了）
    private BroadcastReceiver MessageDetailsPag = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mIvMineMsg.setImageResource(R.drawable.mine_msg_ic_selector);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        //在这里做一些类似刷新的操作 - 在这里刷新了 右上角的消息 和 用户的头像
        if (getContext() != null && SpUtils.getBoolean(getContext(),Constant.IS_NES_MESSAGE)){
            mIvMineMsg.setImageResource(R.drawable.right_new_message);
        }else {
            mIvMineMsg.setImageResource(R.drawable.mine_msg_ic_selector);
        }

        getMembers();
    }

    @Override
    public View getChildView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(getClass().getSimpleName() + "xmg", "onCreateView: " + "MeFragment");
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        head = (GifImageView)view.findViewById( R.id.head );
        mIvUserPaint = (CircleImageView)view.findViewById(R.id.iv_user_paint);
        mTvUserName = (TextView)view.findViewById(R.id.tv_user_name);
        mWorkRecycler = (RecyclerView)view.findViewById(R.id.work_recycler);
        mIvMineMsg = (ImageView)view.findViewById(R.id.iv_mine_msg);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnableRefresh(true);//启用刷新
        mRefreshLayout.setEnableLoadMore(false);//不启用上拉加载更多
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

        //初始化上传图片的dialog
        initTakePhotoDialog();


        //注册广播
        mLBM = LocalBroadcastManager.getInstance(getActivity());

        //定义接收广播的方法
        mLBM.registerReceiver(ReceivedANewMessage,new IntentFilter(Constant.RECEIVED_A_NEW_MESSAGE));
        mLBM.registerReceiver(MessageDetailsPag,new IntentFilter(Constant.USER_ENTERS_MESSAGE_DETAILS_PA));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //初始化takePhoto
        getTakePhoto().onCreate(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }



    private void initWorkPieces() {

        List<MineWorkMap> data = new ArrayList<>();
        //44*44  默认颜色 灰色：#888888   选择颜色 红色：#EA403C
        data.add(new MineWorkMap(R.drawable.wechat_btn_selector, "微信"));
        data.add(new MineWorkMap(R.drawable.circle_btn_selector, "朋友圈"));
        data.add(new MineWorkMap(R.drawable.qq_btn_selector, "QQ"));
        data.add(new MineWorkMap(R.drawable.qqzone_btn_selector, "QQ空间"));
        data.add(new MineWorkMap(R.drawable.head_btn_selector, "头像设置"));
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
            if (getContext() == null){return;}
            Intent intent = new Intent(getContext(), MessageActivity.class);
            startActivity(intent);
        }else if (v == mIvUserPaint){
            if (getContext() == null){return;}
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    //监听 mWorkRecycler item的点击事件
    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        switch (position) {
            case 0:
                    Intent intent = new Intent(getContext(), ShareActivity.class);
                    startActivity(intent);
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:
                //头像设置
                photoDialog.show(getActivity());
                break;
            case 5:

                break;
            case 6:

            case 7:


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

    /**获取网络请求 显示用户的头像图片*/
    private void getMembers(){

        final String url = "http://47.99.135.241:8086/chengfang-api/api/getMembers";

        if ((getContext() != null && mKProgressHUD == null)){
            //开始网络请求-开始显示loading
            mKProgressHUD = KProgressHUD.create(getContext())
                    .setCustomView(new LEloadingView(getContext()))
                    .setLabel("Please wait", Color.GRAY)
                    .setBackgroundColor(Color.WHITE)
                    .show();
        }else{
            mKProgressHUD.show();
        }

        //请求头
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                //获取用户设备 property（特性，属性）
                String property = System.getProperty("http.agent");
                request = request.newBuilder()
                        .header("access-token", "edb5b11733454d9cb64862502282382f")
                        .header("token",Constant.TOKEN)
                        .header("deviceId","d6fc895ea4d29c31d87b6daa7bc90b8d")
                        .build();
                return chain.proceed(request);
            }
        };

        //请求体
        RequestBody requestBody;

        requestBody =  new FormBody.Builder()
                .add("id", valueOf(193))
                .build();


        //开始进行网络请求
        HttpHelper.getInstance().requestPost(url,interceptor
                ,requestBody,new OnResponseListener() {
                    @Override
                    public void onFail(IOException e) {

                        //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
                        isSuccess = true;

                        //结束下拉刷新（无论成功失败本次发起请求已经结束）
                        mRefreshLayout.finishRefresh();

                        //loading结束（无论成功失败本次发起请求已经结束）
                        mKProgressHUD.dismiss();

                        //Toast.makeText(getActivity(), "請求失败請檢查網絡", Toast.LENGTH_SHORT).show();
                        LELog.showLogWithLineNum(5,e.toString());
                    }

                    @Override
                    public void onSuccess(String response) {

                        // LELog.showLogWithLineNum(5,response.toString());

                        //结束下拉刷新（无论成功失败本次发起请求已经结束）
                        mRefreshLayout.finishRefresh();

                        //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
                        isSuccess = true;

                        //loading结束（无论成功失败本次发起请求已经结束）
                        mKProgressHUD.dismiss();

                        //解析 gson fastjson
                        Gson gson = new Gson();
                        UserBean user = gson.fromJson(response, UserBean.class);

                        Picasso.get().load(user.getDataBean().getImg()).placeholder(R.drawable.logo)
                                .into(mIvUserPaint);
                    }
                });

    }

    //mRefreshLayout下拉刷新
    class MyOnRefreshListener implements OnRefreshListener {

        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
           //做一些刷新操作-这里每次下拉刷新了用户的头像
            if (isSuccess){
                getMembers();
            }
        }
    }


    /**解析上传图片后的内部类 JavaBean*/
    class DataBean{

        String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }


    //-----------------------------------选择 裁剪 上传头像---------------------------------------------

    @Override
    public void takeSuccess(final TResult result) {
        //选择照片成功

        final String url = "http://47.99.135.241:8086/chengfang-api/api/updateMemberImg";

        if (getContext() != null && mKProgressHUD == null){
            //开始网络请求-开始显示loading
            mKProgressHUD = KProgressHUD.create(getContext())
                    .setCustomView(new LEloadingView(getContext()))
                    .setLabel("Please wait", Color.GRAY)
                    .setBackgroundColor(Color.WHITE)
                    .show();
        }else{
            mKProgressHUD.show();
        }

        //请求头
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                //获取用户设备 property（特性，属性）
                String property = System.getProperty("http.agent");
                request = request.newBuilder()
                        .header("access-token", "edb5b11733454d9cb64862502282382f")
                        .header("token",Constant.TOKEN)
                        .header("deviceId","d6fc895ea4d29c31d87b6daa7bc90b8d")
                        .build();
                return chain.proceed(request);
            }
        };

        String filePath = result.getImage().getCompressPath();
        RequestBody img = RequestBody.create(MediaType.parse("image/*"), new File(filePath));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id",String.valueOf(193))
                .addFormDataPart("img", + System.currentTimeMillis() + ".jpg",
                       img)
                .build();


        //开始进行网络请求
        HttpHelper.getInstance().requestPost(url,interceptor
                ,requestBody,new OnResponseListener() {
                    @Override
                    public void onFail(IOException e) {

                        //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
                        isSuccess = true;

                        //结束下拉刷新（无论成功失败本次发起请求已经结束）
                        mRefreshLayout.finishRefresh();

                        //loading结束（无论成功失败本次发起请求已经结束）
                        mKProgressHUD.dismiss();

                        //Toast.makeText(getActivity(), "請求失败請檢查網絡", Toast.LENGTH_SHORT).show();
                        LELog.showLogWithLineNum(5,e.toString());
                    }

                    @Override
                    public void onSuccess(String response) {

                        // LELog.showLogWithLineNum(5,response.toString());

                        //结束下拉刷新（无论成功失败本次发起请求已经结束）
                        mRefreshLayout.finishRefresh();

                        //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
                        isSuccess = true;

                        //loading结束（无论成功失败本次发起请求已经结束）
                        mKProgressHUD.dismiss();

                        //解析 gson fastjson
                        Gson gson = new Gson();
                        DataBean dataBean = gson.fromJson(response, DataBean.class);

                        Picasso.get().load(dataBean.getData()).placeholder(R.drawable.logo)
                                .into(mIvUserPaint);
                    }
                });
    }

    @Override
    public void takeFail(TResult result, String msg) {
        //选择失败
        if (getContext() != null){
            Toast.makeText(getContext(),"失败"+msg,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void takeCancel() {
        //用户取消
        if (getContext() != null){
            Toast.makeText(getContext(),"您取消了操作",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (photoDialog != null) {
            photoDialog.dismiss();
        }
    }

    /**
     * 压缩设置
     */
    private void initCompressOptions() {
        //压缩是否显示进度条
        boolean showProgressBar = true;
        //照片拍照后是否保存原图
        boolean enableRawFile = true;
        //102400B
        int maxSize = 102400;
        int width = 800;
        int height = 800;
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(800).setAspectY(800);
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    private void initTakePhotoDialog() {
        ArrayList<String> selects = new ArrayList<>();
        selects.add("从相册中选择");
        selects.add("拍照");
        photoDialog = SelectDialog.newInstance(selects);
        photoDialog.setOnDialogItemClickListener(new SelectDialog.OnDialogItemClickListener() {
            @Override
            public void onItemClick(int position) {
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                Uri imageUri = Uri.fromFile(file);
                initCompressOptions();
                switch (position) {
                    case 0:
                        takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
                        break;
                    case 1:
                        takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                        break;
                    default:
                }
            }
        });
    }

    //---------------------------------------------选择 裁剪 上传头像-------------------------------------------------

    @Override
    public void onDestroy() {
        //注册的广播一定要关掉
        mLBM.unregisterReceiver(ReceivedANewMessage);
        mLBM.unregisterReceiver(MessageDetailsPag);
        if (photoDialog != null) {
            photoDialog.dismiss();
        }
        super.onDestroy();
    }
}
