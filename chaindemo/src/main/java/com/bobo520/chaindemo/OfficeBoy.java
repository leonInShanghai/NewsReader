package com.bobo520.chaindemo;

/**
 * Created by 求知自学网 on 2019/3/10. Copyright © Leon. All rights reserved.
 * Functions: 请求链举例中的 码农小弟
 */
public class OfficeBoy extends Employee {

    OfficeBoy(String name) {
        super(name);
    }

    @Override
    public JiaTiao handleJiaTiao(JiaTiao jiaTiao) {

        //从人事部拿到打印出来的空白假条
        jiaTiao.days = 30;
        jiaTiao.reson = "有事回家";
        jiaTiao.requestName = name;

        //将假条向上级传递,请示上级领导
        jiaTiao = this.leader.handleJiaTiao(jiaTiao);

        return jiaTiao;
    }
}
