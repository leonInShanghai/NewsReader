package com.bobo520.newsreader.event;

/**
 * Created by 求知自学网 on 2019/4/4. Copyright © Leon. All rights reserved.
 * Functions: event 事件的 对象
 */
public class ShowTabEvent {

    public boolean needShow;

    public String show;

    public ShowTabEvent(boolean needShow) {
        this.needShow = needShow;
    }
}
