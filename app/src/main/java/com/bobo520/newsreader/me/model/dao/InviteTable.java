package com.bobo520.newsreader.me.model.dao;

/**
 * Created by Leon on 2019/4/16 Copyright © Leon. All rights reserved.
 * Functions: 接收极光推送信息表
 */
public class InviteTable {

    /**表名*/
    public static final String TABLE_NAME = "tab_invite";



    /**信息的内容 String*/
    public static final String COL_REASON = "message";


    /**邀请时间 long time=System.currentTimeMillis(); Leon新增加*/
    public static final String CURRENTTIME = "currentTime";


    /**建表的语句-"create table "后面少了空格产生bug*/
    public static final String CREATE_TAB = "create table "
            + TABLE_NAME +" ("
            + COL_REASON +" text,"
            + CURRENTTIME + " integer);";
}
