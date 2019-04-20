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

    /**用户是否查看了信息详情默认为false  0*/
    private Boolean look_detais;

    public InvationInfo() {

    }

    public InvationInfo(String message,Long currentTime,Boolean look_detais) {
        this.message = message;
        this.currentTime = currentTime;
        this.look_detais = look_detais;
    }

    @Override
    public String toString() {
        return "InvationInfo{" +
                "message='" + message + '\'' +
                ", currentTime=" + currentTime +
                ", look_detais=" + look_detais +
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

    public Boolean getLook_detais() {
        return look_detais;
    }

    public void setLook_detais(Boolean look_detais) {
        this.look_detais = look_detais;
    }
}
