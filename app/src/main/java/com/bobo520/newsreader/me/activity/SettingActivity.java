package com.bobo520.newsreader.me.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.util.Constant;
import com.bobo520.newsreader.util.LETrtStBarUtil;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class SettingActivity extends SwipeBackActivity implements View.OnClickListener {

    private ImageButton helpBack;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //设置沉浸式导航栏
        LETrtStBarUtil.setTransparentToolbar(this);

        helpBack = (ImageButton)findViewById(R.id.help_back_button);
        helpBack.setOnClickListener(this);
        content = (TextView)findViewById(R.id.tv_content);

        content.setText("当前版本："+getVersionName(SettingActivity.this)+"    "+
                "这是一个开源项目，有兴趣的伙伴可以二次开发，加入一些新的元素。");
    }

    @Override
    public void onClick(View v) {
        if (v == helpBack){
            finish();
        }
    }



    /**
     * get App versionCode
     * @param context
     * @return
     */
    public String getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     * @param context
     * @return
     */
    public String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;

    }

}
