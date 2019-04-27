package com.bobo520.newsreader.util;

/**
 * Created by Leon on 2018/12/30.
 * Functions: 存储全局常量
 */
public class Constant {

    /**从最新版百思不得姐获取的诗词 url*/
    public static final String POETRY = "http://d.api.budejie.com/topic/forum/60660/1/new/" +
            "budejie-android-8.0.5/ID-20.json";

    /**
     * 上次获取的的时间戳用作下次下拉刷新
     * @param id 上次获取的的时间戳
     * @return 诗词的url
     */
    public static String getPoetry(String id){
        return POETRY.replace("ID",id);
    }

    /**百思不得姐的接口*/
    public static final String BAISHI = "http://api.budejie.com/api/api_open.php";

    /**发送用户收到消息的 持久化Boolean变量*/
    public static final String IS_NES_MESSAGE = "IS_NES_MESSAGE";

    /**发送用户进入消息详情页的广播KEY（此时铃铛不需要再为绿色）*/
    public static final String USER_ENTERS_MESSAGE_DETAILS_PA = "USER_ENTERS_MESSAGE_DETAILS_PA";

    /**发送用户收到新的极光消息的广播KEY*/
    public static final String RECEIVED_A_NEW_MESSAGE = "RECEIVED_A_NEW_MESSAGE";

    /**广告图片的json请求url  2019-4-6 这个接口不提供数据了自己造数据*/
    public static final String ADS_URL = "http://g1.163.com/madr?app=7A16FBB" +
            "6&platform=android&category=STARTUP&location=1";

    /**新闻页面数据请求url*/
    private static final String NEWS_URL = "http://c.m.163.com/nc/article/hea" +
            "dline/T1348647909107/START-END.html?from=toutiao&size=10&passport=&de" +
            "vId=bMo6EQztO2ZGFBurrbgcMQ%3D%3D&net=wifi";

    /**新闻详情页面的url*/
    private static final String NEWS_DETAIL_URL = "http://c.m.163.com/nc/article/ID/full.html";

    /**新闻评论页面的url*/
    private static final String NEWS_REPLY_URL = "http://comment.api.163.com/api/" +
            "v1/products/a2869674571f77b5a0867c3d71db5856/threads/ID/app/comments" +
            "/hotList?offset=0&limit=10&showLevelThreshold=10&headLimit=2&tailLimit=2";

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

    /**
     * 确定详情请求接口的地址
     * @param id  例如：C85DDFSQ00964J4O
     * @return 根据每条新闻详情请求接口的地址
     */
    public static String getNewsReplyUrl(String id){
        return NEWS_REPLY_URL.replace("ID",id);
    }


    /**由于2019年网易没有返回banner的数据自己创造的banner1图片地址*/
    public static final String BANNER1 = "https://raw.githubusercontent.com/leonInShanghai/" +
            "NewsReader/master/otherPic/banner1.jpg";

    /**由于2019年网易没有返回banner的数据自己创造的banner2图片地址*/
    public static final String BANNER2 = "https://raw.githubusercontent.com/leonInShanghai/" +
            "NewsReader/master/otherPic/banner2.jpg";

    /**2019-4-6网易启动图广告页面接口停止服务自己造数据*/
    public static final String[] LE_ADS_ARR = {"https://github.com/leonInShanghai/NewsReader/" +
            "blob/master/otherPic/launch1.jpg?raw=true","https://raw.githubusercontent.com/" +
            "leonInShanghai/NewsReader/master/otherPic/launch2.jpg","https://raw.githubuser" +
            "content.com/leonInShanghai/NewsReader/master/otherPic/launch3.jpg","https://raw." +
            "githubusercontent.com/leonInShanghai/NewsReader/master/otherPic/launch4.jpg",
            "https://raw.githubusercontent.com/leonInShanghai/NewsReader/master/otherPic/launch5.jpg"
            ,"https://raw.githubusercontent.com/leonInShanghai/NewsReader/master/otherPic/launch6.gif"};
}


/**
 *（热门回帖URL）
 http://comment.api.163.com/api/v1/products/a2869674571f77b5a0867c3d71db5856/threads/C85DDFSQ00964J4O/app/comm
 ents/hotList?offset=0&limit=10&showLevelThreshold=10&headLimit=2&tailLimit=2

 （最新回帖URL）
 http://comment.api.163.com/api/v1/products/a2869674571f77b5a0867c3d71db5856/threads/C85DDFSQ00964J4O/app/comme
 nts/newList?offset=0&limit=20&showLevelThreshold=10&headLimit=2&tailLimit=2
 （一直往下拉，加载更多评论时的请求URL）
 http://comment.api.163.com/api/v1/products/a2869674571f77b5a0867c3d71db5856/threads/C85DDFSQ00964J4O/app/comme
 nts/newList?offset=20&limit=20&showLevelThreshold=10&headLimit=2&tailLimit=2


 PS：
 URL中的C85Q4KIF000187VE为对应的新闻id，需要被替换
 URL中的"newList"代表是最新回帖类型，"hotList"代表是热门回帖类型
 URL中的offset=0，代表从第一条记录开始查询
 URL中的limit=10，代表需要查出来多少条数据
 */