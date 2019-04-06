package com.bobo520.newsreader.news.tool;

/**
 * Created by 求知自学网 on 2019/4/4. Copyright © Leon. All rights reserved.
 * Functions: 解耦 fragment 和 HomeActivity 之間 顯示 / 隱藏 TabHost的接口 没有用到后面用的EventBus
 */
public interface OnShowTabHostListener {
    void showTabHost(boolean needShow);
}
