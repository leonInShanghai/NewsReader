package com.bobo520.newsreader.me.model.db;


import android.content.Context;
import com.bobo520.newsreader.me.model.dao.InviteTableDao;

/**
 * Created by Leon on 2019/4/16 Copyright © Leon. All rights reserved.
 * Functions: 联系人和邀请信息表的操作类的管理类
 */
public class DBManager {

    private final DBHelper dbHelper;
    private final InviteTableDao inviteTableDao;

    public DBManager(Context context,String name) {
        //创建数据库
        dbHelper = new DBHelper(context,name);
        inviteTableDao = new InviteTableDao(dbHelper);
    }


    /**获取信息表的操作类对象*/
    public InviteTableDao getInviteTableDao() {
        return inviteTableDao;
    }

    /**关闭数据库的方法*/
    public void close(){
        dbHelper.close();
    }

}
