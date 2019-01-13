package com.bobo520.newsreader;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * Created by Leon on 2019/1/5. Copyright © Leon. All rights reserved.
 * Functions:设置状态栏透明为沉浸式导航栏打基础的类
 * 下面这个方法比较麻烦我没有用
 * 沉浸式状态栏： https://github.com/wuhenzhizao/android-titlebar
 */
public class LETrtStBarUtil {

    public final static void setTransparentToolbar(Activity activity){
        if(Build.VERSION.SDK_INT >= 21){

            //想要设置沉浸式状态栏的activity中都创建一个view 高度20dp 设置成自己想要的颜色
            View view = activity.findViewById(R.id.status_placeholder);
            if (view != null){//避免空指针异常
                view.setVisibility(View.VISIBLE);
            }
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            //低版本不适配沉浸式状态栏所以要隐藏
            View view = activity.findViewById(R.id.status_placeholder);
            if (view != null){
                view.setVisibility(View.GONE);
            }
        }
    }

}
