package com.bobo520.newsreader.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Leon on 2019/4/16 Copyright © Leon. All rights reserved.
 * Functions: 时间戳与日期格式的互相转换 以及 自定义选择器
 */
public class LeTimeUtils {

    /**字符串转时间戳*/
    public static String getTime(String timeString){
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
        Date d;
        try{
            d = sdf.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**时间戳转字符串*/
    public static String getStrTime(String timeStamp){
        String timeString = null;

        //24小时计时法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

        //12小时计时法
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");

        long  l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

}
