package com.bobo520.newsreader.news.controller.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.customDialog.LEloadingView;
import com.bobo520.newsreader.util.LETrtStBarUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.liji.imagezoom.activity.ImageDetailFragment;
import com.liji.imagezoom.util.BottomMenuDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.bobo520.newsreader.app.NewsReaderApplication.getContext;
import static com.bobo520.newsreader.news.controller.fragment.PictureFragment.PICTURE_URL;

/**
 * Created by Leon on 2018/10/21.
 * Functions: 点击查看大图activity 不支持GIF图
 */

public class PictureActivity extends AppCompatActivity implements View.OnLongClickListener {


    final int REQUEST_WRITE = 1001;//申请权限的请求码
    private ScrollView scrollView_le;
    private String uri;
    private ProgressBar picture_pro;
    private boolean pointOut = true;
    private WebView web_view;
    private List<String> list;
    private Bitmap bitmap;
    private KProgressHUD mKProgressHUD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_picture);
        uri = getIntent().getStringExtra(PICTURE_URL);

        //Leon设置了最近淘宝等各大app流行的沉浸式状态栏
        LETrtStBarUtil.setTransparentToolbar(this);

        mKProgressHUD = KProgressHUD.create(PictureActivity.this)
                .setCustomView(new LEloadingView(PictureActivity.this))
                .setLabel("Please wait", Color.GRAY)
                .setBackgroundColor(Color.WHITE)
                .show();


        /**
         * 这段时间一直用手机连接WiFi测试APP，但是一直打不开webview的网页内容。一直显示net::ERR_PROXY_CO
         * NNECTION_FAILED
         * 如果变成了手机4G流量就可以打开。一直以为是网络问题，结果发现是我把WiFi设置成代理了。
         * 解决方法：取消WiFi的代理。commet to github
         */

        web_view = (WebView) findViewById(R.id.web_view);
        web_view.loadUrl(uri);
        web_view.setOnLongClickListener(this);
        WebSettings webSettings = web_view.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js

        //设置载入页面自适应手机屏幕，居中显示
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        bitmap = returnBitMap(uri);

        //设置可任意缩放
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //设置WebViewClient类
        web_view.setWebViewClient(new WebViewClient() {

            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //picture_pro.setVisibility(View.VISIBLE);
                //开始网络请求-开始显示loading
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                //bitmap = returnBitMap(url);
                //picture_pro.setVisibility(View.GONE);
                //loading结束（无论成功失败本次发起请求已经结束）
                mKProgressHUD.dismiss();
            }
        });
    }


    //url转bitmap 的方法
    public Bitmap returnBitMap(final String url){
        if (TextUtils.isEmpty(url)){
            Toast.makeText(PictureActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
            return null;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    //java.lang.OutOfMemoryError: Failed to allocate a 8696772 byte
                    // allocation with 1801216 free bytes and 1759KB until OOM
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }

    //activity销毁的时候移除webview 合理的管理内存
    @Override
    protected void onDestroy() {
        if (web_view != null) {
            web_view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            web_view.clearHistory();

            ((ViewGroup) web_view.getParent()).removeView(web_view);
            web_view.destroy();
            web_view = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onLongClick(View view) {

        if (view == web_view){

            //导入了一个框架一句代码保存到相册
            ImageDetailFragment.newInstance(uri);

            final BottomMenuDialog dialog = new BottomMenuDialog.Builder()

                    .addItem("保存到本地相册", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkPermission();

                        }
                    })
                    .addItem("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(PictureActivity.this, "取消", Toast.LENGTH_LONG).show();
                        }
                    })

                    .build();
            dialog.show(getSupportFragmentManager());

            return true;
        }

        return false;
    }

    private void checkPermission() {

        if (bitmap == null){
            return;
        }

        //判断是否6.0以上的手机   不是就不用
        if (Build.VERSION.SDK_INT >= 23) {
            //判断是否有这个权限
            if (ContextCompat.checkSelfPermission(PictureActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //2、申请权限: 参数二：权限的数组；参数三：请求码
                requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_WRITE);
            }
            else {
                saveImageToGallery(PictureActivity.this,bitmap);
            }
        }
        else {
            saveImageToGallery(PictureActivity.this,bitmap);
        }
    }

    /**
     * 保存到本地相册
     * @param context
     * @param bmp
     */
    public void saveImageToGallery(Context context, Bitmap bmp) {
        Log.d("ZoomImage", "saveImageToGallery:" + bmp);
        final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)
                ? Environment.getExternalStorageDirectory().getAbsolutePath()
                : "/mnt/sdcard";//保存到SD卡

        // 首先保存图片
        File appDir = new File(SAVE_PIC_PATH + "/波波新闻/");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        long nowSystemTime = System.currentTimeMillis();
        //String fileName = nowSystemTime + ".png";
        String fileName = nowSystemTime + ".jpg";
        File file = new File(appDir, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //保存图片后发送广播通知更新数据库
        Uri uri = Uri.fromFile(file);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // 最后通知图库更新
//        context.sendBroadcast(
//                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        Toast.makeText(PictureActivity.this, "已保存到本地相册", Toast.LENGTH_LONG).show();
    }

}
