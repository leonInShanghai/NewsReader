package com.bobo520.newsreader.me.model.bean;

/**
 * Created by Leon on 2019/4/16 Copyright © Leon. All rights reserved.
 * Functions: 极光推送信息的模型
 */
public class InvationInfo {


    /**信息内容*/
    private String message;

    /**用户接收到信息的时间*/
    private Long currentTime;

    public InvationInfo() {

    }

    public InvationInfo(String message,Long currentTime) {
        this.message = message;
        this.currentTime = currentTime;
    }


    @Override
    public String toString() {
        return "InvationInfo{" +
                "message='" + message + '\'' +
                ", currentTime=" + currentTime +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }
}
