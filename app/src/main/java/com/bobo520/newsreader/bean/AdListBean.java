package com.bobo520.newsreader.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leon on 2018/12/31. Copyright © Leon. All rights reserved.
 * Functions: splash 页面的广告对象解析
 *
 * implements Serializable  序列化 是为了 intent.putExtra(ADS_PIC_URL,adListBean); 的时候可以传值。
 * 这里面只要用到了类都需要将全部对象都序列化即：implements Serializable
 * 在这个类中 还像之前那样使用gson Formant 生成对象 出来bug 解决了很久最后手写搞定的。
 */
public class  AdListBean implements Serializable {

    private int result;
    private int rolls;
    private int next_req;
    private  String error;
    private List<AdsBean> ads;

    public int getRolls() {
        return rolls;
    }

    public void setRolls(int rolls) {
        this.rolls = rolls;
    }

    public int getNext_req() {
        return next_req;
    }

    public void setNext_req(int next_req) {
        this.next_req = next_req;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setAds(List<AdListBean.AdsBean> adsBean) {
        ads= adsBean;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<AdsBean> getAds() {
        return ads;
    }


    public static class AdsBean implements Serializable{

        /**splash页广告图片地址*/
        private List<String> res_url;
        private ActionParamsBean action_params;


        public List<String> getRes_url() {
            return res_url;
        }

        public void setRes_url(List<String> res_url) {
            this.res_url = res_url;
        }

        public ActionParamsBean getAction_params() {
            return action_params;
        }

        public void setAction_params(ActionParamsBean action_params) {
            this.action_params = action_params;
        }

        public static class  ActionParamsBean implements Serializable{

            /**点击广告跳转地址*/
            private String link_url;

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }
        }
    }

}
