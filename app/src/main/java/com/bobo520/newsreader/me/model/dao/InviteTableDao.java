package com.bobo520.newsreader.me.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bobo520.newsreader.me.model.bean.InvationInfo;
import com.bobo520.newsreader.me.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 2019/4/16 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class InviteTableDao {

    private DBHelper mHelper;

    public InviteTableDao(DBHelper helper) {
        this.mHelper = helper;
    }

    /**添加信息*/
    public void addInvitation(InvationInfo invationInfo){

        //1.获取数据库连接
        SQLiteDatabase db = mHelper.getReadableDatabase();

        //2.执行添加语句
        ContentValues values = new ContentValues();

        //信息的内容
        values.put(InviteTable.COL_REASON,invationInfo.getMessage());

        //为了排序倒顺序----------Leon新增加---------------------------------------
        values.put(InviteTable.CURRENTTIME,invationInfo.getCurrentTime());//邀请的时间

        //增加 Boolean 类型的用户是否查看详情
        values.put(InviteTable.LOOK_DETAIS,String.valueOf(invationInfo.getLook_detais()));

        //Leon修改bug
        //db.insert(InviteTable.TABLE_NAME,null,values);.//這個方法插入有問題 primary key 不可重複
        db.replace(InviteTable.TABLE_NAME,null,values);//这是原来的方法
    }

    /**获取邀请信息 返回集合类型*/
    public List<InvationInfo> getInvittations(){
        //1.获取数据库连接
        SQLiteDatabase db = mHelper.getReadableDatabase();

        //2.执行查询语句 ↓可以正常使用
        //String sql = "select * from "+InviteTable.TABLE_NAME;
        //2.执行查询语句 ↓倒序 根据后来添加的自增长id字段
        String sql = "select * from "+InviteTable.TABLE_NAME + " order by currentTime desc";

        Cursor cursor = db.rawQuery(sql,null);

        List<InvationInfo> invationInfos = new ArrayList<>();
        while (cursor.moveToNext()){
            InvationInfo invationInfo = new InvationInfo();
            invationInfo.setMessage(cursor.getString(cursor.getColumnIndex(InviteTable.COL_REASON)));
            invationInfo.setCurrentTime(cursor.getLong(cursor.getColumnIndex(InviteTable.CURRENTTIME)));

            //String转Boolean
            boolean isLookDetals = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(InviteTable.LOOK_DETAIS)));
            invationInfo.setLook_detais(isLookDetals);

            //添加遍历的邀请信息
            invationInfos.add(invationInfo);
        }

        //3.关闭资源
        cursor.close();

        //4.返回数据
        return invationInfos;
    }

    /**
     * 根据传递的时间删除对应的数据
     * @param currentTime
     */
    public void removeInvitation(Long currentTime){
        //避免空指针
        //if (currentTime == 0){ return; }

        //获取数据库连接
        SQLiteDatabase db = mHelper.getReadableDatabase();

        //执行删除语句
        db.delete(InviteTable.TABLE_NAME, InviteTable.CURRENTTIME + "=?;",new String[]{String.valueOf(currentTime)});
    }

    /**
     * 根据时间修改用户是否查看过消息详情
     * @param currentTime
     */
    public void userToSeeDetails(Long currentTime){
        //避免空指针
        //if (currentTime == 0){ return; }

        //获取数据库连接
        SQLiteDatabase db = mHelper.getReadableDatabase();

        //将 InviteTable.LOOK_DETAIS 字段更新为true
        ContentValues values = new ContentValues();
        values.put(InviteTable.LOOK_DETAIS,String.valueOf(true));
        //db.update(InviteTable.TABLE_NAME,values,strFilter,null);
        db.update(InviteTable.TABLE_NAME,values,InviteTable.CURRENTTIME + "=?",new String[]{String.
                valueOf(currentTime)});
    }



}
