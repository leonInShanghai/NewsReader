package com.bobo520.newsreader.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bobo520.newsreader.R;

/**
 * Created by Leon on 2019/1/5. Copyright © Leon. All rights reserved.
 * Functions:设置状态栏透明为沉浸式导航栏打基础的类
 * 下面这个方法比较麻烦我没有用
 * 沉浸式状态栏： https://github.com/wuhenzhizao/android-titlebar  99
 */
public class LETrtStBarUtil {

    public final static void setTransparentToolbar(Activity activity){

        if(Build.VERSION.SDK_INT >= 21){

            //想要设置沉浸式状态栏的activity中都创建一个view 高度20dp 设置成自己想要的颜色
            View view = activity.findViewById(R.id.status_placeholder);
            if (view != null){//避免空指针异常
                //动态的设置view的高度==状态栏的高度
                view.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view.getLayoutParams();
                params.height = getStatusBarHeight(activity);
                view.setLayoutParams(params);
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


    /**
     * 获得状态栏的高度
     */
    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
