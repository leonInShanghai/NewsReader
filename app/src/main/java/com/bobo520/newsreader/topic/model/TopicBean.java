package com.bobo520.newsreader.topic.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 求知自学网 on 2019/5/2. Copyright © Leon. All rights reserved.
 * Functions:  话题模块的对象模型
 */
public class TopicBean implements Serializable {


    /**
     * count : 209
     * page_size : 21
     * data : [{"id":55,"title":"[时事热点] 襄阳首个世界级遗产申报成功！恭喜！","image":"https://imag
     * page : 1
     * links : {"previous":null,"next":"http://www.pocketfunny.com/api/joke/topic/?page=2"}
     */

    private int count;
    private int page_size;
    private int page;
    private LinksBean links;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class LinksBean implements Serializable {
        /**
         * previous : null
         * next : http://www.pocketfunny.com/api/joke/topic/?page=2
         */

        private Object previous;
        private String next;

        public Object getPrevious() {
            return previous;
        }

        public void setPrevious(Object previous) {
            this.previous = previous;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }
    }

    public static class DataBean implements Serializable {
        /**
         * id : 55
         * title : [时事热点] 襄阳首个世界级遗产申报成功！恭喜！
         * image : https://image.pocketfunny.com/joke/topic/2018/10/u34153023531124347423fm173
         * desc : 北京时间8月14日早上8:45，当地时间8月13日18:45，2018世界灌溉工程遗产评选的
         * content : <div align="center" style="color:#444444;font-family:&quot;f
         <span><img width="600" height="400" class="zoom" id="aimg_pD7sQ" alt="" src="http://r
         </span>
         </div>
         <span style="background-color:#FFFFFF;"><br />
         </span><br />
         <span style="background-color:#FFFFFF;">北京时间8月14日早上8:45，当地时间
         <strong><span><br />
         </span></strong><br />
         <br />
         <div align="center" style="color:#444444;font-family:&quot;font-size:14px;font-style
         <span><img width="600" height="450" class="zoom" id="aimg_zGj91" alt="" src="
         </div>
         <span style="background-color:#FFFFFF;"><br />
         </span><br />
         <span style="background-color:#FFFFFF;">白起渠（又名长渠、荩忱渠），公元前279年，秦王以
         </span><br />
         <span style="background-color:#FFFFFF;"><br />
         </span><br />
         <div align="center" style="color:#444444;font-family:&quot;font-size:14px;font-style:norm
         <span><img width="530" height="355" class="zoom" id="aimg_gkqow" alt="" src="http://re
         </div>
         <span style="background-color:#FFFFFF;"><br />
         </span><br />
         <span style="background-color:#FFFFFF;">本次申遗，南漳三道河——襄阳市水利局——
         <span style="background-color:#FFFFFF;"><br />
         </span><br />
         <div align="center" style="color:#444444;font-family:&quot;font-size:14px;font-style:
         <span><img width="477" height="300" class="zoom" id="aimg_bAbM4" alt="" src="http:
         </div>
         <span style="background-color:#FFFFFF;"><br />
         </span><br />
         <span style="background-color:#FFFFFF;">白起渠的成功申遗，填补了我省世界灌溉工程
         <span style="background-color:#FFFFFF;"><br />
         </span><br />
         <div align="center" style="color:#444444;font-family:&quot;font-size:14px;font-style
         <span><img width="500" height="355" class="zoom" id="aimg_PGoEo" alt="" src="htt
         </div>
         <div align="center" style="color:#444444;font-family:&quot;font-size:14px;font-sty
         <span><br />
         </span>
         </div>
         <div align="center" style="color:#444444;font-family:&quot;font-size:14px;font-s
         <span>作为一名襄阳人</span>
         </div>
         <div align="center" style="color:#444444;font-family:&quot;font-size:14px
         <span>我们真是与有荣焉</span>
         </div>
         <div align="center" style="color:#444444;font-family:&quot;font-size:14px;f
         <span>为我大襄阳点个赞<br />
         </span>
         </div>
         <div align="center" style="color:#444444;font-family:&quot;font-size:14px;fo
         <span>相信襄阳明天会更好</span>
         </div>
         <div align="center" style="color:#444444;font-family:&quot;font-size:14px;font-style:n
         <br />
         </div>
         * view_num : 2192
         * deleted : false
         * create_time : 2018-11-06 09:48:00
         * update_time : 2018-11-06 09:48:00
         * user : null
         * tag : 12
         * categorys : null
         * sites : []
         */

        private int id;
        private String title;
        private String image;
        private String desc;
        private String content;
        private int view_num;
        private boolean deleted;
        private String create_time;
        private String update_time;
        private Object user;
        private int tag;
        private Object categorys;
        private List<?> sites;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getView_num() {
            return view_num;
        }

        public void setView_num(int view_num) {
            this.view_num = view_num;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public Object getUser() {
            return user;
        }

        public void setUser(Object user) {
            this.user = user;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public Object getCategorys() {
            return categorys;
        }

        public void setCategorys(Object categorys) {
            this.categorys = categorys;
        }

        public List<?> getSites() {
            return sites;
        }

        public void setSites(List<?> sites) {
            this.sites = sites;
        }
    }
}
