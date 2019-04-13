package com.bobo520.newsreader.me.model;

/**
 * Created by 求知自学网 on 2019/4/13 Copyright © Leon. All rights reserved.
 *Functions: MeFragment 中 mWorkRecycler 的数据模型
 */
public class MineWorkMap {
    private int imgRes;
    private String name;

    public MineWorkMap(int imgRes, String name) {
        this.imgRes = imgRes;
        this.name = name;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
