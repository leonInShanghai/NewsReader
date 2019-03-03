package com.bobo520.newsreader.bean;

import java.util.ArrayList;

/**
 * Created by Leon on 2019/2/2. Copyright © Leon. All rights reserved.
 * Functions: 新闻fragment  bean 中的对象
 */
public class HotNewsBean {


    //banenr轮播图的数据最新返回的没有这些
    // ArrayList<BannerBean> abs;

    /**
     * 新闻的标题
     * 例如：华夏银行“内鬼”在总行服务器植入病毒，作案一年多，银行账户余额取之不尽"
     */
    String title;

    /**
     * 新闻的来源
     * 例如："和讯网"
     */
    String source;

    /**
     * 回复的数量
     * 例如：16886
     */
    int replyCount;

    /**
     * 图片的url路径
     * 例如:"http://cms-bucket.ws.126.net/2019/02/02/bef041719bde456e9c30db0a30d1e301.jpeg"
     */
    String img;

    /**
     * 为 S ，代表是要置顶
     * 例如："财经"
     */
    String interest;

    /**
     * 新闻的标志id

     */
    String id;

    /**
     * 代表该新闻是否为轮播图
     * 例如：0
     */
    int hasHead;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public int getHasHead() {
        return hasHead;
    }

    public void setHasHead(int hasHead) {
        this.hasHead = hasHead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
