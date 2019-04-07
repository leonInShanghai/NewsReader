package com.bobo520.newsreader.Receiver;

import android.content.Context;
import android.util.Log;

import com.bobo520.newsreader.util.LELog;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * Created by 求知自学网 on 2019/4/7. Copyright © Leon. All rights reserved.
 * Functions: JPush 自定义项
 */
public class MyJPushMessageReceiver extends JPushMessageReceiver {

  @Override
  public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
      LELog.showLogWithLineNum(5,jPushMessage.toString());
      Log.e("TAG",jPushMessage.toString());
    //TagAliasOperatorHelper.getInstance().onTagOperatorResult(context,jPushMessage);
   super.onTagOperatorResult(context, jPushMessage);
  }
  @Override
  public void onCheckTagOperatorResult(Context context,JPushMessage jPushMessage){
    //TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context,jPushMessage);
      LELog.showLogWithLineNum(5,jPushMessage.toString());
      Log.e("TAG",jPushMessage.toString());
   super.onCheckTagOperatorResult(context, jPushMessage);
  }
  @Override
  public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
      LELog.showLogWithLineNum(5,jPushMessage.toString());
      Log.e("TAG",jPushMessage.toString());
       // TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context,jPushMessage);
       super.onAliasOperatorResult(context, jPushMessage);
   }


}


/**
 在需要的地方启动
    if (!APPUtil.isServiceWork(this, "com.example.administrator.ydxcfwpt.Service.MyService")) {
                 Intent intent2 = new Intent(this, MyService.class);
                 startService(intent2);
      }

 */

