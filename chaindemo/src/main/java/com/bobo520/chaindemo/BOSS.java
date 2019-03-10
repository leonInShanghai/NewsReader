package com.bobo520.chaindemo;

/**
 * Created by 求知自学网 on 2019/3/10. Copyright © Leon. All rights reserved.
 * Functions: 请求链举例中的老板
 */
public class BOSS extends Employee {

    BOSS(String name) {
        super(name);
    }

    @Override
    public JiaTiao handleJiaTiao(JiaTiao jiaTiao) {

        //只要到了老板这里老板都会批
        jiaTiao.answer = jiaTiao.answer+" 同意,落款"+name;

        return jiaTiao;
    }
}
