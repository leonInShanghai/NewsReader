package com.bobo520.newsreader.util;

import android.text.TextUtils;

import com.bobo520.newsreader.bean.NewsCommentBean;
import com.google.gson.Gson;

/**
 * Created by 求知自学网 on 2019/3/30. Copyright © Leon. All rights reserved.
 * Functions: json解析工具类
 */
public class JsonUtil {

    private static Gson mGson;

    public static <T> T parseJson(String json,Class<T> clazz){
        if (!TextUtils.isEmpty(json)){
            if (mGson == null){
                mGson = new Gson();
            }
            T bean = mGson.fromJson(json,clazz);
            return bean;
        }else{
            return null;
        }
    }

}
