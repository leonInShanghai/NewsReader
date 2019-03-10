package com.bobo520.chaindemo;

/**
 * Created by 求知自学网 on 2019/3/10. Copyright © Leon. All rights reserved.
 * Functions: 请求链 请假举例 中公司的人（员工包括老板）
 */
public abstract class Employee {

    /**职工的姓名*/
    String name;

    /**职工的领导*/
    Employee leader;

    Employee(String name) {
        this.name = name;
    }

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }

    /**处理假条的抽象方法*/
    public abstract JiaTiao handleJiaTiao(JiaTiao jiaTiao);
}
