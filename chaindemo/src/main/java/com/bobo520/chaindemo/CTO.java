package com.bobo520.chaindemo;

/**
 * Created by 求知自学网 on 2019/3/10. Copyright © Leon. All rights reserved.
 * Functions:请求链举例中的CTO
 */
public class CTO extends Employee {

    CTO(String name) {
        super(name);
    }

    @Override
    public JiaTiao handleJiaTiao(JiaTiao jiaTiao) {

        if (jiaTiao.requestName.contains("王")){
            //小王的假条一概不能批
            jiaTiao.answer = jiaTiao.answer+" 不同意,小王的能力很突出，我们需要你留下来加班";
            return jiaTiao;
        }


        if (jiaTiao.days > 3){
            jiaTiao.answer = jiaTiao.answer+" 同意,落款:"+name;
            jiaTiao = this.leader.handleJiaTiao(jiaTiao);
        }else {
            jiaTiao.answer = jiaTiao.answer+" 同意,落款:"+name;
        }

        return jiaTiao;
    }
}
