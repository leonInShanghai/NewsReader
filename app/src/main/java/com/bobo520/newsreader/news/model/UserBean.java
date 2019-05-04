package com.bobo520.newsreader.news.model;

import android.media.Image;

;

/**
 * Created by 求知自学网 on 2019/5/3. Copyright © Leon. All rights reserved.
 * Functions:
 */
public class UserBean {

    public DataBean getDataBean() {
        return data;
    }

    public void setDataBean(DataBean dataBean) {
        this.data = dataBean;
    }

    private DataBean data;

    public static class DataBean{

        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

}
