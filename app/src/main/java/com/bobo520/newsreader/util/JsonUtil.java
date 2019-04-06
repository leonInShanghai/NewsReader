package com.bobo520.newsreader.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

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

    //准备一个集合和json字符串互相转换的方法
    public static String listToString(ArrayList arrayList){

        if (mGson == null){
            mGson = new Gson();
        }

        String json = mGson.toJson(arrayList);

        return json;
    }

    /**json转为arraylist集合*/
    public static ArrayList<String> stringToList(String json){

        if (mGson == null){
            mGson = new Gson();
        }

        ArrayList<String> list = mGson.fromJson(json,new TypeToken<ArrayList<String>>(){}.getType());

        return list;
    }

}
