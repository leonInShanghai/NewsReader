package com.bobo520.chaindemo;

/**
 * Created by 求知自学网 on 2019/3/10. Copyright © Leon. All rights reserved.
 * Functions: 责任链举例中的请假条
 */
public class JiaTiao {

    /**请假原因*/
    String reson;

    /**请假人姓名*/
    String requestName;

    /**回复*/
    String answer;

    /**请假天数*/
    float days;

    @Override
    public String toString() {
        return "JiaTiao{" +
                "reson='" + reson + '\'' +
                ", requestName='" + requestName + '\'' +
                ", answer='" + answer + '\'' +
                ", days=" + days +
                '}';
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public float getDays() {
        return days;
    }

    public void setDays(float days) {
        this.days = days;
    }
}
