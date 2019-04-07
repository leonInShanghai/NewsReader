package com.bobo520.newsreader.Receiver;

import android.util.Log;

import cn.jpush.android.service.WakedResultReceiver;

/**
 * Created by 求知自学网 on 2019/4/7. Copyright © Leon. All rights reserved.
 * Functions:  复写onWake(int wakeType)或 onWake(Context context, int wakeType)方法以监听被拉起
 */
public class MyWakedResultReceiver extends WakedResultReceiver {

    @Override
    public void onWake(int i) {
        Log.e("TAG",String.valueOf(i));
        super.onWake(i);
    }
}
