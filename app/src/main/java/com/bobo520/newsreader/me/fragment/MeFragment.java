package com.bobo520.newsreader.me.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bobo520.newsreader.customDialog.LEloadingView;
import com.bobo520.newsreader.customDialog.SelectDialog;
import com.bobo520.newsreader.http.HttpHelper;
import com.bobo520.newsreader.http.OnResponseListener;
import com.bobo520.newsreader.me.activity.HelpCenterActivity;
import com.bobo520.newsreader.me.activity.JianshuActivity;
import com.bobo520.newsreader.me.activity.LoginActivity;
import com.bobo520.newsreader.me.activity.MessageActivity;
import com.bobo520.newsreader.me.activity.OpenSourceActivity;
import com.bobo520.newsreader.me.activity.SettingActivity;
import com.bobo520.newsreader.me.adapter.MineWorkAdapter;
import com.bobo520.newsreader.me.model.bean.MineWorkMap;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.customDialog.DensityUtil;
import com.bobo520.newsreader.app.LogFragment;
import com.bobo520.newsreader.me.decoration.GridWHSpaceDecoration;
import com.bobo520.newsreader.me.share.ShareActivity;
import com.bobo520.newsreader.me.share.ShareUtil;
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
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

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

    //帮助中心 后来放的关于我们的内容
    private FrameLayout helpCenter;

    //开源社区
    private FrameLayout openSource;

    //简书
    private FrameLayout jianshu;

    //系统设置
    private FrameLayout setting;

    /*获取权限的标识 目前就QQ需要获取*/
    private final int PERMISSION_REQUEST_STOREAGE = 201955;

    //用户分享的内容 权限申请前赋值 权限回调后使用
    private String mPlatform = "";


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
        helpCenter = (FrameLayout)view.findViewById(R.id.mine_help_center);
        openSource = (FrameLayout)view.findViewById(R.id.open_source_community);
        jianshu = (FrameLayout)view.findViewById(R.id.jianshu);
        setting = (FrameLayout)view.findViewById(R.id.mine_system_setting);
        helpCenter.setOnClickListener(this);
        mRefreshLayout.setEnableRefresh(true);//启用刷新
        mRefreshLayout.setEnableLoadMore(false);//不启用上拉加载更多
        openSource.setOnClickListener(this);
        jianshu.setOnClickListener(this);
        setting.setOnClickListener(this);

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

        //加上这句比较严谨避免空指针
        if (getContext() == null){return;}

        if (v == mIvMineMsg){
            Intent intent = new Intent(getContext(), MessageActivity.class);
            startActivity(intent);
        }else if (v == mIvUserPaint){
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }else if (v == helpCenter){
            Intent intent = new Intent(getContext(), HelpCenterActivity.class);
            startActivity(intent);
        }else if (v == openSource){
            Intent intent = new Intent(getContext(), OpenSourceActivity.class);
            startActivity(intent);
        }else if (v == jianshu){
            Intent intent = new Intent(getContext(), JianshuActivity.class);
            startActivity(intent);
        }else if (v == setting){
            Intent intent = new Intent(getContext(), SettingActivity.class);
            startActivity(intent);
        }
    }

    //监听 mWorkRecycler item的点击事件
    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        switch (position) {
            case 0:
                /**
                 * DESCRIBE  ShareActivity.class只是一个试验类
                 * Intent intent = new Intent(getContext(), ShareActivity.class);
                 * startActivity(intent); 传如String平台：1 qq 2 qzone 3 wechat 4 wechatCircle
                 */
                shareMessage("3");
                break;
            case 1:
                //传如String平台：1 qq 2 qzone 3 wechat 4 wechatCircle
                shareMessage("4");
                break;
            case 2:
                //传如String平台：1 qq 2 qzone 3 wechat 4 wechatCircle
                requestPermissions("1");
                break;
            case 3:
                //传如String平台：1 qq 2 qzone 3 wechat 4 wechatCircle
                requestPermissions("2");
                break;
            case 4:
                //头像设置
                photoDialog.show(getActivity());
                break;
            case 5:
                //RecyclerView 只有5个item 往后的用不到 以后添加再扩展
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


    //-----------------------------------选择 裁剪 上传头像---↓------------------------------------------

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

    //获取权限的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_STOREAGE) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission Granted
            if (!mPlatform.equals("")){
                shareMessage(mPlatform);
            }
            } else {
                // Permission Denied
                Toast.makeText(getContext(),"您没有允许相机权限",Toast.LENGTH_SHORT).show();
            }
        }
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
        builder.setAspectX(1200).setAspectY(1200);
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

    //---------------------------------------------选择 裁剪 上传头像-----↑--------------------------------------------



    //申请存储空间权限-注意这里是申请 有申请还有回调
    private void requestPermissions(String platform) {

        mPlatform = platform;

        //避免空指针
        if (getContext() == null || getActivity() == null){ return;}

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,},
                            PERMISSION_REQUEST_STOREAGE);
                }else {
                    shareMessage(platform);
                }
            } else {
                shareMessage(platform);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    /**
     * 分享工具类的二次封装
     * @param platform   平台：1 qq 2 qzone 3 wechat 4 wechatCircle
     */
    private void shareMessage(String platform){
        ShareUtil.ShareWeb(getActivity(), platform, "NewsReader",
          "https://github.com/leonInShanghai/NewsReader/blob/master/README.md",Constant.DESCRIBE,
                R.drawable.umeng_socialize_menu_default, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Toast.makeText(getContext(),"onStart"+share_media.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Toast.makeText(getContext(),"onResult"+share_media.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Toast.makeText(getContext(),"onError"+throwable.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Toast.makeText(getContext(),"onCancel"+share_media.toString(),Toast.LENGTH_LONG).show();
                    }
                });
    }

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
