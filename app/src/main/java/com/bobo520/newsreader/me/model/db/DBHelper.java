package com.bobo520.newsreader.me.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bobo520.newsreader.me.model.dao.InviteTable;

/**
 * Created by Leon on 2019/4/16 Copyright © Leon. All rights reserved.
 * Functions: 数据库助手
 */
public class DBHelper  extends SQLiteOpenHelper {

    public DBHelper(Context context,String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建信息表
        db.execSQL(InviteTable.CREATE_TAB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
