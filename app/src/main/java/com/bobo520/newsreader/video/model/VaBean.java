package com.bobo520.newsreader.video.model;

import java.util.List;


/**
 * Created by 求知自学网 on 2019/5/1. Copyright © Leon. All rights reserved.
 * Functions: 视频的数据模型类
 */
public class VaBean {


    /**
     * info : {"vendor":"node77","count":2000,"page":100,"maxid":"1556682362","maxtime":"1556682362"}
     * list : [{"id":"29438567","type":"41","text":"这得有多绝望呢","user_id":"119time":"2019-04-30
     */

    private InfoBean info;
    private List<ListBean> list;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class InfoBean {
        /**
         * vendor : node77
         * count : 2000
         * page : 100
         * maxid : 1556682362
         * maxtime : 1556682362
         */

        private String vendor;
        private int count;
        private int page;
        private String maxid;
        private String maxtime;

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getMaxid() {
            return maxid;
        }

        public void setMaxid(String maxid) {
            this.maxid = maxid;
        }

        public String getMaxtime() {
            return maxtime;
        }

        public void setMaxtime(String maxtime) {
            this.maxtime = maxtime;
        }
    }

    public static class ListBean {
        /**
         * id : 29438567
         * type : 41
         * text : 这得有多绝望呢
         * user_id : 11996791
         * name : Pescado
         * screen_name : Pescado
         * profile_image : http://wimg.spriteapp.cn/profile/large/2019/02/10/5c6015142adc7_mini.jpg
         * created_at : 2019-05-01 15:35:01
         * create_time : 2019-04-30 00:51:47
         * passtime : 2019-05-01 15:35:01
         * love : 84
         * hate : 6
         * comment : 13
         * repost : 0
         * bookmark : 0
         * bimageuri : http://wimg.spriteapp.cn/picture/2019/0430/5cc72ba4055d9__b.jpg
         * voiceuri :
         * voicetime : 0
         * voicelength : 0
         * status : 4
         * theme_id : 55163
         * theme_name : 主版块
         * theme_type : 1
         * videouri : http://wvideo.spriteapp.cn/video/2019/0430/5cc72ba419d31_wpd.mp4
         * videotime : 79
         * original_pid : 0
         * cache_version : 2
         * playcount : 1617
         * playfcount : 96
         * cai : 6
         * top_cmt : []
         * weixin_url : http://a.f.winapp111.net/share/29438567.html?wx.qq.com&appname=
         * themes : []
         * image1 : http://wimg.spriteapp.cn/picture/2019/0430/5cc72ba4055d9__b.jpg
         * image2 : http://wimg.spriteapp.cn/picture/2019/0430/5cc72ba4055d9__b.jpg
         * is_gif : 0
         * image0 : http://wimg.spriteapp.cn/picture/2019/0430/5cc72ba4055d9__b.jpg
         * image_small : http://wimg.spriteapp.cn/picture/2019/0430/5cc72ba4055d9__b.jpg
         * cdn_img : http://wimg.spriteapp.cn/picture/2019/0430/5cc72ba4055d9__b.jpg
         * width : 368
         * height : 640
         * tag :
         * t : 1556696101
         * ding : 84
         * favourite : 0
         */

        private String id;
        private String type;
        private String text;
        private String user_id;
        private String name;
        private String screen_name;
        private String profile_image;
        private String created_at;
        private String create_time;
        private String passtime;
        private String love;
        private String hate;
        private String comment;
        private String repost;
        private String bookmark;
        private String bimageuri;
        private String voiceuri;
        private String voicetime;
        private String voicelength;
        private String status;
        private String theme_id;
        private String theme_name;
        private String theme_type;
        private String videouri;
        private String videotime;
        private String original_pid;
        private int cache_version;
        private String playcount;
        private String playfcount;
        private String cai;
        private String weixin_url;
        private String image1;
        private String image2;
        private String is_gif;
        private String image0;
        private String image_small;
        private String cdn_img;
        private String width;
        private String height;
        private String tag;
        private int t;
        private String ding;
        private String favourite;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScreen_name() {
            return screen_name;
        }

        public void setScreen_name(String screen_name) {
            this.screen_name = screen_name;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }

        public String getLove() {
            return love;
        }

        public void setLove(String love) {
            this.love = love;
        }

        public String getHate() {
            return hate;
        }

        public void setHate(String hate) {
            this.hate = hate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getRepost() {
            return repost;
        }

        public void setRepost(String repost) {
            this.repost = repost;
        }

        public String getBookmark() {
            return bookmark;
        }

        public void setBookmark(String bookmark) {
            this.bookmark = bookmark;
        }

        public String getBimageuri() {
            return bimageuri;
        }

        public void setBimageuri(String bimageuri) {
            this.bimageuri = bimageuri;
        }

        public String getVoiceuri() {
            return voiceuri;
        }

        public void setVoiceuri(String voiceuri) {
            this.voiceuri = voiceuri;
        }

        public String getVoicetime() {
            return voicetime;
        }

        public void setVoicetime(String voicetime) {
            this.voicetime = voicetime;
        }

        public String getVoicelength() {
            return voicelength;
        }

        public void setVoicelength(String voicelength) {
            this.voicelength = voicelength;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTheme_id() {
            return theme_id;
        }

        public void setTheme_id(String theme_id) {
            this.theme_id = theme_id;
        }

        public String getTheme_name() {
            return theme_name;
        }

        public void setTheme_name(String theme_name) {
            this.theme_name = theme_name;
        }

        public String getTheme_type() {
            return theme_type;
        }

        public void setTheme_type(String theme_type) {
            this.theme_type = theme_type;
        }

        public String  getVideouri() {
            return videouri;
        }

        public void setVideouri(String videouri) {
            this.videouri = videouri;
        }

        public String getVideotime() {
            return videotime;
        }

        public void setVideotime(String videotime) {
            this.videotime = videotime;
        }

        public String getOriginal_pid() {
            return original_pid;
        }

        public void setOriginal_pid(String original_pid) {
            this.original_pid = original_pid;
        }

        public int getCache_version() {
            return cache_version;
        }

        public void setCache_version(int cache_version) {
            this.cache_version = cache_version;
        }

        public String getPlaycount() {
            return playcount;
        }

        public void setPlaycount(String playcount) {
            this.playcount = playcount;
        }

        public String getPlayfcount() {
            return playfcount;
        }

        public void setPlayfcount(String playfcount) {
            this.playfcount = playfcount;
        }

        public String getCai() {
            return cai;
        }

        public void setCai(String cai) {
            this.cai = cai;
        }

        public String getWeixin_url() {
            return weixin_url;
        }

        public void setWeixin_url(String weixin_url) {
            this.weixin_url = weixin_url;
        }

        public String getImage1() {
            return image1;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public String getIs_gif() {
            return is_gif;
        }

        public void setIs_gif(String is_gif) {
            this.is_gif = is_gif;
        }

        public String getImage0() {
            return image0;
        }

        public void setImage0(String image0) {
            this.image0 = image0;
        }

        public String getImage_small() {
            return image_small;
        }

        public void setImage_small(String image_small) {
            this.image_small = image_small;
        }

        public String getCdn_img() {
            return cdn_img;
        }

        public void setCdn_img(String cdn_img) {
            this.cdn_img = cdn_img;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getT() {
            return t;
        }

        public void setT(int t) {
            this.t = t;
        }

        public String getDing() {
            return ding;
        }

        public void setDing(String ding) {
            this.ding = ding;
        }

        public String getFavourite() {
            return favourite;
        }

        public void setFavourite(String favourite) {
            this.favourite = favourite;
        }


    }
}
