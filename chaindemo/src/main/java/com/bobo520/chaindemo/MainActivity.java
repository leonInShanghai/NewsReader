package com.bobo520.chaindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by 求知自学网 on 2019/3/10 Copyright © Leon. All rights reserved.
 * Functions: 责任链举例
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 责任链模式
     * 降低耦合度
     * 简化对象的相互连接
     * 增加新的请求处理类很方便
     *
     * 缺点：
     * 不能保证请求一定被接收
     * 系统性能受到一定影响，可能会造成循环调用
     */


    //例子：公司里员工请假

    //码农小弟请假
    //项目经理  只能在一天内可以批假，超过了就需要向上请示
    //技术总监CTO 3天内可以批假，超过了需要想你上请示
    //公司老板 随意想请多少天请多少天


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建 码农对象
        OfficeBoy officeBoy = new OfficeBoy("小弟");

        //创建项目经理对象
        Manager manager = new Manager("刘经理");

        //创建总监对象
        CTO cto = new CTO("刘总监");

        //创建老板对象
        BOSS boss = new BOSS("刘老板");

        //给码农设置领导
        officeBoy.setLeader(manager);
        //给项目经理设置领导
        manager.setLeader(cto);
        //给cto设置领导
        cto.setLeader(boss);
        
        //码农开始请假
        JiaTiao jiaTiao = officeBoy.handleJiaTiao(new JiaTiao());
        Log.e("假条", String.valueOf(jiaTiao));
    }
}
