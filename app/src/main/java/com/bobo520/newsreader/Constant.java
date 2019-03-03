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

    /**新闻详情页面的url*/
    private static final String NEWS_DETAIL_URL = "http://c.m.163.com/nc/article/ID/full.html";

    /**
     * 根据id返回新闻详情url
     * @param id 每条新闻的id
     * @return 动态的新闻详情页面的url
     */
    public static String getNewsDetailUrl(String id){
        return NEWS_DETAIL_URL.replace("ID",id);
    }

    /**
     * 确定新闻请求接口的地址
     * @param start：分页用开始数据
     * @param end：分页用结束数据
     * @return 动态的新闻请求接口的地址
     */
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
