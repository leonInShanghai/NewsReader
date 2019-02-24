package com.bobo520.newsreader;

/**
 * Created by Leon on 2018/12/30.
 * Functions: 存储全局常量
 */
public class Constant {

    /**广告图片的json请求url*/
    public static final String ADS_URL = "http://g1.163.com/madr?app=7A16FBB" +
            "6&platform=android&category=STARTUP&location=1";

    /**新闻页面数据请求url*/
    private static final String NEWS_URL = "http://c.m.163.com/nc/article/hea" +
            "dline/T1348647909107/START-END.html?from=toutiao&size=10&passport=&de" +
            "vId=bMo6EQztO2ZGFBurrbgcMQ%3D%3D&net=wifi";

    /**确定新闻请求接口的地址*/
    public static String getNewsUrl(int start,int end){
        return NEWS_URL.replace("START",start+"").replace("END",
                end+"");
    }


    /**由于2019年网易没有返回banner的数据自己创造的banner1图片地址*/
    public static final String BANNER1 = "https://raw.githubusercontent.com/leonInShanghai/" +
            "NewsReader/master/otherPic/banner1.jpg";

    /**由于2019年网易没有返回banner的数据自己创造的banner2图片地址*/
    public static final String BANNER2 = "https://raw.githubusercontent.com/leonInShanghai/" +
            "NewsReader/master/otherPic/banner2.jpg";
}
