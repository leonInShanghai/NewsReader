package com.bobo520.newsreader;

import static android.util.Patterns.GOOD_IRI_CHAR;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Leon on 2019/1/6. Copyright © Leon. All rights reserved.
 * Functions: 自定义 url 格式是否可达和 有没有网络
 */
public class CheckTheLinkNetwork {

    /**
     * 判断当前网络是否可用(通用方法)
     * 耗时12秒
     * @return 这个方法可以在主线程使用 目前这个方法有使用
     */
    public static boolean isNetPingUsable(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("ping -c 3 www.baidu.com");
            int ret = process.waitFor();
            if (ret == 0){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断url是否可达 - 涉及到网络请求在子线程调用
     * 这个方法有使用
     */
    public static Boolean checkUrl(final String address, final int waitMilliSecond) {

        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setConnectTimeout(waitMilliSecond);
            conn.setReadTimeout(waitMilliSecond);

            //HTTP connect
            try {
                conn.connect();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            int code = conn.getResponseCode();
            if ((code >= 100) && (code < 400)) {
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}


