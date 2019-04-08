package com.bobo520.newsreader.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.bobo520.newsreader.app.HomeActivity;
import com.bobo520.newsreader.util.LELog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            LELog.showLogWithLineNum(5,"MyReceiver");
            Log.e("TAG", "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                LELog.showLogWithLineNum(5,"MyReceiver");
                Log.e("TAG", "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                LELog.showLogWithLineNum(5,"MyReceiver");
                Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                LELog.showLogWithLineNum(5,"MyReceiver");
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                LELog.showLogWithLineNum(5,"MyReceiver");
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户点击打开了通知");

                //打开自定义的Activity
                LELog.showLogWithLineNum(5,"MyReceiver");
                Intent i = new Intent(context, HomeActivity.class);
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                LELog.showLogWithLineNum(5,"MyReceiver");
                Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                LELog.showLogWithLineNum(5,"MyReceiver");
                Log.e(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
            } else {
                LELog.showLogWithLineNum(5,"MyReceiver");
                Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e){
            LELog.showLogWithLineNum(5,"MyReceiver");
            Log.e("#WARING:", "[MyReceiver] ERROR");
        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LELog.showLogWithLineNum(5,"MyReceiver");
                    Log.e(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LELog.showLogWithLineNum(5,"MyReceiver");
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        LELog.showLogWithLineNum(5,"processCustomMessage(Context context, Bundle bundle)");
        Log.e(TAG, "context"+context.toString()+"bundle"+ bundle.toString());
//        if (MainActivity.isForeground) {
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//        }
    }
}





//import android.content.BroadcastReceiver;
//        import android.content.Context;
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.support.constraint.solver.Cache;
//        import android.util.Log;
//        import android.widget.Toast;
//
//
//        import com.bobo520.newsreader.app.HomeActivity;
//        import com.bobo520.newsreader.app.MainActivity;
//        import com.bobo520.newsreader.util.LELog;
//
//
//        import cn.jpush.android.api.JPushInterface;
//
//
///**
// * Created by 求知自学网 on 2019/4/7. Copyright © Leon. All rights reserved.
// * Functions: * 自定义接收器 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
// */
//public class MyReceiver extends BroadcastReceiver {
//
//    private static final String TAG = "JIGUANG-Example";
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        try {
//
//            Bundle bundle = intent.getExtras();
//
//            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//                LELog.showLogWithLineNum(5,"MyReceiver");
//                Log.e("TAG", "[MyReceiver] 接收Registration Id : " + regId);
//                //send the Registration Id to your server...
//
//
//            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//                LELog.showLogWithLineNum(5,"MyReceiver");
//                Log.e("TAG", "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//                processCustomMessage(context, bundle);
//
//
//            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//                LELog.showLogWithLineNum(5,"MyReceiver");
//                Log.e("TAG", "[MyReceiver] 接收到推送下来的通知");
//                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//
//                Log.e("TAG", "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
//
//                //讯飞播报
//                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//                final String content = (String) bundle.get(JPushInterface.EXTRA_ALERT);
//                //Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
//                Log.e("TAG", "[MyReceiver] 接收到消息内容: " + message);
//                //AudioUtils.getInstance().init(context);
//                //AudioUtils.getInstance().speakText(content);
//            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//                LELog.showLogWithLineNum(5,"MyReceiver");
//                Log.e("TAG", "[MyReceiver] 用户点击打开了通知");
//
//                //讯飞播报
//                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//                final String content = (String) bundle.get(JPushInterface.EXTRA_ALERT);
//                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
//                Log.e("TAG", "[MyReceiver] 接收到消息内容2: " + message);
//                //打开自定义的Activity
//                Intent i = new Intent(context, HomeActivity.class);
//                i.putExtras(bundle);
//                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);
//
//
//            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//                LELog.showLogWithLineNum(5,"MyReceiver");
//                Log.e("TAG", "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
//                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
//
//            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
//                LELog.showLogWithLineNum(5,"MyReceiver");
//                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE,
//                        false);
//                Log.e("TAG", "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
//            } else {
//                Log.e("TAG", "[MyReceiver] Unhandled intent - " + intent.getAction());
//            }
//        }catch(Exception e){
//            LELog.showLogWithLineNum(5,"#WARING: MyReceiver Error"+e.toString());
//        }
//
//    }
//
//    //  打印所有的 intent extra 数据
//    private static String printBundle(Bundle bundle) {
//        return null;
//    }
//
//    //send msg to MainActivity
//    private void processCustomMessage(Context context, Bundle bundle) {
//
//    }
//
//    //JPush
//
//
//}
