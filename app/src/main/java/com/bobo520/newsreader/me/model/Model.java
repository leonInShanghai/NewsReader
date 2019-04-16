package com.bobo520.newsreader.me.model;

import android.content.Context;

import com.bobo520.newsreader.me.model.db.DBManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Leon on 2019/4/16 Copyright © Leon. All rights reserved.
 * Functions: 数据模型层全局类 - 单例模式
 */
public class Model {

    //上下文
    private Context mContext;

    // 联系人和邀请信息表的操作类的管理类
    private DBManager dbManager;

    //线程池
    private ExecutorService executors = Executors.newCachedThreadPool();

    //创建一个静态的对象
    private static Model model = new Model();

    //创建一个私有的构造函数-单例
    private Model(){

    }

    //获取单例对象
    public static Model getInstance(){
        return model;
    }

    //初始化的方法
    public void init(Context context){
        this.mContext = context;
        //第二个参数是数据库的名称 例如：account.getName()
        dbManager = new DBManager(mContext,"leon");
    }

    /**获取全局的线程池*/
    public ExecutorService getGloabalThreadPool() {
        return executors;
    }


    /**获取数据库的操作类对象*/
    public DBManager getDbManager(){
        return dbManager;
    }

}
