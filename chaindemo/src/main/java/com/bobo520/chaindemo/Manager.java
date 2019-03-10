package com.bobo520.chaindemo;

/**
 * Created by 求知自学网 on 2019/3/10. Copyright © Leon. All rights reserved.
 * Functions: 请求链请假举例中的项目经理
 */
public class Manager extends Employee {
    Manager(String name) {
        super(name);
    }

    @Override
    public JiaTiao handleJiaTiao(JiaTiao jiaTiao) {

        //向上级传递值时，可以对假条进行批改
        jiaTiao.answer = jiaTiao.answer+" 同意,落款:"+name;

        if (jiaTiao.days > 1) {
            jiaTiao = this.leader.handleJiaTiao(jiaTiao);
        }

        return jiaTiao;
    }
}
