package com.bobo520.newsreader;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * Created by Leon on 2019/1/5. Copyright © Leon. All rights reserved.
 * Functions:
 */
public class LETrtStBarUtil {

    public final static void setTransparentToolbar(Activity activity){
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
