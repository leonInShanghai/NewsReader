package com.bobo520.newsreader;

/**
 * Created by 求知自学网 on 2019/4/4. Copyright © Leon. All rights reserved.
 * Functions: 解耦 fragment 和 HomeActivity 之間 顯示 / 隱藏 TabHost的接口
 */
public interface OnShowTabHostListener {
    void showTabHost(boolean needShow);
}
