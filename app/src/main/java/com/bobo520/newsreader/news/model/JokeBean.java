package com.bobo520.newsreader.news.model;

import java.util.List;

/**
 * Created by 求知自学网 on 2019/4/21. Copyright © Leon. All rights reserved.
 * Functions:
 */
public class JokeBean {


    /**
     * info : {"vendor":"node77","count":2000,"page":100,"maxid":"1546606081","maxtime":"1546606081"}
     * list : [{"id":"29241752","type":"29","text":"我想问一下各位姐夫: 男朋友过来了，但是他不想让你的父……
     */

    private InfoBean info;
    private List<ListBean> list;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    //
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
         * maxid : 1546606081
         * maxtime : 1546606081
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
         * id : 29241752
         * type : 29
         * text : 我想问一下各位姐夫: 男朋友过来了，但是他不想让你的父母知道，只是过来看看你。 这种属于什么心理啊？
         * user_id : 20962862
         * name : 心飞机
         * screen_name : 心飞机
         * profile_image : http://wimg.spriteapp.cn/profile/large/2019/03/22/5c94d9c40ed80_mini.jpg
         * created_at : 2019-02-25 15:32:01
         * create_time : 2019-02-24 03:47:02
         * passtime : 2019-02-25 15:32:01
         * love : 754
         * hate : 102
         * comment : 223
         * repost : 1
         * bookmark : 5
         * bimageuri :
         * voiceuri :
         * voicetime : 0
         * voicelength : 0
         * status : 4
         * theme_id : 56781
         * theme_name : 情感社区
         * theme_type : 1
         * videouri :
         * videotime : 0
         * original_pid : 0
         * cache_version : 2
         * cai : 102
         * top_cmt : [{"data_id":"29241752","status":"0","id":"12515286","content":"他只是单纯的想陪你睡个
         * weixin_url : http://a.f.zk111.cn/share/29241752.html?wx.qq.com&appname=
         * themes : []
         * width : 0
         * height : 0
         * tag :
         * t : 1551079921
         * ding : 754
         * favourite : 5
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
        private String cai;
        private String weixin_url;
        private String width;
        private String height;
        private String tag;
        private int t;
        private String ding;
        private String favourite;
        private List<TopCmtBean> top_cmt;
        private List<?> themes;
        //private ThemsBean themes;

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

        public String getVideouri() {
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

        public List<TopCmtBean> getTop_cmt() {
            return top_cmt;
        }

        public void setTop_cmt(List<TopCmtBean> top_cmt) {
            this.top_cmt = top_cmt;
        }

        public List<?> getThemes() {
            return themes;
        }

        public void setThemes(List<?> themes) {
            this.themes = themes;
        }

        public static class TopCmtBean {
            /**
             * data_id : 29241752
             * status : 0
             * id : 12515286
             * content : 他只是单纯的想陪你睡个觉，睡完了就走那种
             * ctime : 2019-02-24 13:38:21
             * precid : 0
             * preuid : 0
             * like_count : 1074
             * voiceuri :
             * voicetime :
             * cmt_type : 29
             * user : {"id":"22894759","username":"菲儿","sex":"f","pr
             * precmt : []
             */

            private String data_id;
            private String status;
            private String id;
            private String content;
            private String ctime;
            private String precid;
            private String preuid;
            private String like_count;
            private String voiceuri;
            private String voicetime;
            private String cmt_type;
            private UserBean user;

            //--------------------------------------------------------------------------------------
            //有类型转换异常的问题  gson typeadapter
            //com.google.gson.JsonSyntaxException: java.lang.IllegalStateException:
            // Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 31126 path $.list[15].top_cmt[0].precmt
            //private List<ThemsBean> themes;
           // private List<?> precmt;
           // private List<PrecmtBean> precmt;

            public String getData_id() {
                return data_id;
            }

            public void setData_id(String data_id) {
                this.data_id = data_id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getPrecid() {
                return precid;
            }

            public void setPrecid(String precid) {
                this.precid = precid;
            }

            public String getPreuid() {
                return preuid;
            }

            public void setPreuid(String preuid) {
                this.preuid = preuid;
            }

            public String getLike_count() {
                return like_count;
            }

            public void setLike_count(String like_count) {
                this.like_count = like_count;
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

            public String getCmt_type() {
                return cmt_type;
            }

            public void setCmt_type(String cmt_type) {
                this.cmt_type = cmt_type;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

//            public List<?> getPrecmt() {
//                return precmt;
//            }


//            public void setPrecmt(List<?> precmt) {
//                this.precmt = precmt;
//            }



            public static class UserBean {
                /**
                 * id : 22894759
                 * username : 菲儿
                 * sex : f
                 * profile_image : http://thirdwx.qlogo.cn/m
                 * weibo_uid :
                 * qq_uid :
                 * qzone_uid :
                 * is_vip : false
                 * personal_page :
                 * total_cmt_like_count : 7934
                 */

                private String id;
                private String username;
                private String sex;
                private String profile_image;
                private String weibo_uid;
                private String qq_uid;
                private String qzone_uid;
                private boolean is_vip;
                private String personal_page;
                private String total_cmt_like_count;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public String getProfile_image() {
                    return profile_image;
                }

                public void setProfile_image(String profile_image) {
                    this.profile_image = profile_image;
                }

                public String getWeibo_uid() {
                    return weibo_uid;
                }

                public void setWeibo_uid(String weibo_uid) {
                    this.weibo_uid = weibo_uid;
                }

                public String getQq_uid() {
                    return qq_uid;
                }

                public void setQq_uid(String qq_uid) {
                    this.qq_uid = qq_uid;
                }

                public String getQzone_uid() {
                    return qzone_uid;
                }

                public void setQzone_uid(String qzone_uid) {
                    this.qzone_uid = qzone_uid;
                }

                public boolean isIs_vip() {
                    return is_vip;
                }

                public void setIs_vip(boolean is_vip) {
                    this.is_vip = is_vip;
                }

                public String getPersonal_page() {
                    return personal_page;
                }

                public void setPersonal_page(String personal_page) {
                    this.personal_page = personal_page;
                }

                public String getTotal_cmt_like_count() {
                    return total_cmt_like_count;
                }

                public void setTotal_cmt_like_count(String total_cmt_like_count) {
                    this.total_cmt_like_count = total_cmt_like_count;
                }
            }



        //-------------------------PrecmtBean-------------------------------------------------------------------

        public static class PrecmtBean {


            /**
             * id : 28769169
             * type : 29
             * text : 鲁迅说：
             人本身就穷，折腾对了就成了富人，折腾不对，大不了还是穷人。
             如果不折腾、一辈子都是穷人！
             鲁迅还说:
             到了一定年龄，
             必须扔掉四样东西：
             没意义的酒局， 不爱你的人， 看不起你的亲戚，虚情假义的朋友……
             必须拥有四样东西：
             扬在脸上的自信，
             长在心里的善良，
             融进血液的骨气，
             刻在生命里的坚强
             * user_id : 21512054
             * name : 劫后
             * screen_name : 劫后
             * profile_image :
             * created_at : 2018-10-27 13:54:02
             * create_time : 2018-10-26 16:37:28
             * passtime : 2018-10-27 13:54:02
             * love : 3061
             * hate : 142
             * comment : 219
             * repost : 111
             * bookmark : 283
             * bimageuri :
             * voiceuri :
             * voicetime : 0
             * voicelength : 0
             * status : 4
             * theme_id : 63674
             * theme_name : 原创段子手
             * theme_type : 1
             * videouri :
             * videotime : 0
             * original_pid : 0
             * cache_version : 2
             * cai : 142
             * top_cmt : [{"data_id":"28769169","status":"0","id":"12703882","content":"不，你说过","ctime":"2019-03-09 20:59:05","precid":"9604063","preuid":"8345129","like_count":"308","voiceuri":"","voicetime":"","cmt_type":"29","user":{"id":"15243636","username":"好名字12138","sex":"m","profile_image":"http://wimg.spriteapp.cn/p/jie.jpg","weibo_uid":"","qq_uid":"","qzone_uid":"","is_vip":false,"personal_page":"","total_cmt_like_count":"231710"},"precmt":{"data_id":"28769169","status":"0","id":"9604063","content":"鲁迅：我没说过","ctime":"2018-10-26 18:38:38","precid":"0","preuid":"0","like_count":"310","voiceuri":"","voicetime":"","cmt_type":"29","user":{"id":"8345129","username":"不得姐用户","sex":"m","profile_image":"http://wimg.spriteapp.cn/p/jie.jpg","weibo_uid":"","qq_uid":"","qzone_uid":"4635BFA41097206BA5E73744905E16ED","is_vip":false,"personal_page":"http://user.qzone.qq.com/4635BFA41097206BA5E73744905E16ED","total_cmt_like_count":"345"}}}]
             * weixin_url : http://a.f.zk111.cn/share/28769169.html?wx.qq.com&appname=
             * themes : []
             * width : 0
             * height : 0
             * tag :
             * t : 1540619642
             * ding : 3061
             * favourite : 283
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
            private String cai;
            private String weixin_url;
            private String width;
            private String height;
            private String tag;
            private int t;
            private String ding;
            private String favourite;
            //private List<TopCmtBean> top_cmt;
            private List<?> themes;

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

            public String getVideouri() {
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

//            public List<TopCmtBean> getTop_cmt() {
//                return top_cmt;
//            }
//
//            public void setTop_cmt(List<TopCmtBean> top_cmt) {
//                this.top_cmt = top_cmt;
//            }

            public List<?> getThemes() {
                return themes;
            }

            public void setThemes(List<?> themes) {
                this.themes = themes;
            }

//            public static class TopCmtBean {
//                /**
//                 * data_id : 28769169
//                 * status : 0
//                 * id : 12703882
//                 * content : 不，你说过
//                 * ctime : 2019-03-09 20:59:05
//                 * precid : 9604063
//                 * preuid : 8345129
//                 * like_count : 308
//                 * voiceuri :
//                 * voicetime :
//                 * cmt_type : 29
//                 * user : {"id":"15243636","username":"好名字12138","sex":"m","profile_image":"http://wimg.spriteapp.cn/p/jie.jpg","weibo_uid":"","qq_uid":"","qzone_uid":"","is_vip":false,"personal_page":"","total_cmt_like_count":"231710"}
//                 * precmt : {"data_id":"28769169","status":"0","id":"9604063","content":"鲁迅：我没说过","ctime":"2018-10-26 18:38:38","precid":"0","preuid":"0","like_count":"310","voiceuri":"","voicetime":"","cmt_type":"29","user":{"id":"8345129","username":"不得姐用户","sex":"m","profile_image":"http://wimg.spriteapp.cn/p/jie.jpg","weibo_uid":"","qq_uid":"","qzone_uid":"4635BFA41097206BA5E73744905E16ED","is_vip":false,"personal_page":"http://user.qzone.qq.com/4635BFA41097206BA5E73744905E16ED","total_cmt_like_count":"345"}}
//                 */
//
//                private String data_id;
//                private String status;
//                private String id;
//                private String content;
//                private String ctime;
//                private String precid;
//                private String preuid;
//                private String like_count;
//                private String voiceuri;
//                private String voicetime;
//                private String cmt_type;
//                private com.bobo520.newsreader.news.model.JokeCompleteBean.TopCmtBean.UserBean user;
//                private com.bobo520.newsreader.news.model.JokeCompleteBean.TopCmtBean.PrecmtBean precmt;
//
//                public String getData_id() {
//                    return data_id;
//                }
//
//                public void setData_id(String data_id) {
//                    this.data_id = data_id;
//                }
//
//                public String getStatus() {
//                    return status;
//                }
//
//                public void setStatus(String status) {
//                    this.status = status;
//                }
//
//                public String getId() {
//                    return id;
//                }
//
//                public void setId(String id) {
//                    this.id = id;
//                }
//
//                public String getContent() {
//                    return content;
//                }
//
//                public void setContent(String content) {
//                    this.content = content;
//                }
//
//                public String getCtime() {
//                    return ctime;
//                }
//
//                public void setCtime(String ctime) {
//                    this.ctime = ctime;
//                }
//
//                public String getPrecid() {
//                    return precid;
//                }
//
//                public void setPrecid(String precid) {
//                    this.precid = precid;
//                }
//
//                public String getPreuid() {
//                    return preuid;
//                }
//
//                public void setPreuid(String preuid) {
//                    this.preuid = preuid;
//                }
//
//                public String getLike_count() {
//                    return like_count;
//                }
//
//                public void setLike_count(String like_count) {
//                    this.like_count = like_count;
//                }
//
//                public String getVoiceuri() {
//                    return voiceuri;
//                }
//
//                public void setVoiceuri(String voiceuri) {
//                    this.voiceuri = voiceuri;
//                }
//
//                public String getVoicetime() {
//                    return voicetime;
//                }
//
//                public void setVoicetime(String voicetime) {
//                    this.voicetime = voicetime;
//                }
//
//                public String getCmt_type() {
//                    return cmt_type;
//                }
//
//                public void setCmt_type(String cmt_type) {
//                    this.cmt_type = cmt_type;
//                }
//
//                public com.bobo520.newsreader.news.model.JokeCompleteBean.TopCmtBean.UserBean getUser() {
//                    return user;
//                }
//
//                public void setUser(com.bobo520.newsreader.news.model.JokeCompleteBean.TopCmtBean.UserBean user) {
//                    this.user = user;
//                }
//
//                public com.bobo520.newsreader.news.model.JokeCompleteBean.TopCmtBean.PrecmtBean getPrecmt() {
//                    return precmt;
//                }
//
//                public void setPrecmt(com.bobo520.newsreader.news.model.JokeCompleteBean.TopCmtBean.PrecmtBean precmt) {
//                    this.precmt = precmt;
//                }
//
//                public static class UserBean {
//                    /**
//                     * id : 15243636
//                     * username : 好名字12138
//                     * sex : m
//                     * profile_image : http://wimg.spriteapp.cn/p/jie.jpg
//                     * weibo_uid :
//                     * qq_uid :
//                     * qzone_uid :
//                     * is_vip : false
//                     * personal_page :
//                     * total_cmt_like_count : 231710
//                     */
//
//                    private String id;
//                    private String username;
//                    private String sex;
//                    private String profile_image;
//                    private String weibo_uid;
//                    private String qq_uid;
//                    private String qzone_uid;
//                    private boolean is_vip;
//                    private String personal_page;
//                    private String total_cmt_like_count;
//
//                    public String getId() {
//                        return id;
//                    }
//
//                    public void setId(String id) {
//                        this.id = id;
//                    }
//
//                    public String getUsername() {
//                        return username;
//                    }
//
//                    public void setUsername(String username) {
//                        this.username = username;
//                    }
//
//                    public String getSex() {
//                        return sex;
//                    }
//
//                    public void setSex(String sex) {
//                        this.sex = sex;
//                    }
//
//                    public String getProfile_image() {
//                        return profile_image;
//                    }
//
//                    public void setProfile_image(String profile_image) {
//                        this.profile_image = profile_image;
//                    }
//
//                    public String getWeibo_uid() {
//                        return weibo_uid;
//                    }
//
//                    public void setWeibo_uid(String weibo_uid) {
//                        this.weibo_uid = weibo_uid;
//                    }
//
//                    public String getQq_uid() {
//                        return qq_uid;
//                    }
//
//                    public void setQq_uid(String qq_uid) {
//                        this.qq_uid = qq_uid;
//                    }
//
//                    public String getQzone_uid() {
//                        return qzone_uid;
//                    }
//
//                    public void setQzone_uid(String qzone_uid) {
//                        this.qzone_uid = qzone_uid;
//                    }
//
//                    public boolean isIs_vip() {
//                        return is_vip;
//                    }
//
//                    public void setIs_vip(boolean is_vip) {
//                        this.is_vip = is_vip;
//                    }
//
//                    public String getPersonal_page() {
//                        return personal_page;
//                    }
//
//                    public void setPersonal_page(String personal_page) {
//                        this.personal_page = personal_page;
//                    }
//
//                    public String getTotal_cmt_like_count() {
//                        return total_cmt_like_count;
//                    }
//
//                    public void setTotal_cmt_like_count(String total_cmt_like_count) {
//                        this.total_cmt_like_count = total_cmt_like_count;
//                    }
//                }
//
//                public static class PrecmtBean {
//                    /**
//                     * data_id : 28769169
//                     * status : 0
//                     * id : 9604063
//                     * content : 鲁迅：我没说过
//                     * ctime : 2018-10-26 18:38:38
//                     * precid : 0
//                     * preuid : 0
//                     * like_count : 310
//                     * voiceuri :
//                     * voicetime :
//                     * cmt_type : 29
//                     * user : {"id":"8345129","username":"不得姐用户","sex":"m","profile_image":"http://wimg.spriteapp.cn/p/jie.jpg","weibo_uid":"","qq_uid":"","qzone_uid":"4635BFA41097206BA5E73744905E16ED","is_vip":false,"personal_page":"http://user.qzone.qq.com/4635BFA41097206BA5E73744905E16ED","total_cmt_like_count":"345"}
//                     */
//
//                    private String data_id;
//                    private String status;
//                    private String id;
//                    private String content;
//                    private String ctime;
//                    private String precid;
//                    private String preuid;
//                    private String like_count;
//                    private String voiceuri;
//                    private String voicetime;
//                    private String cmt_type;
//                    private com.bobo520.newsreader.news.model.JokeCompleteBean.TopCmtBean.PrecmtBean.UserBeanX user;
//
//                    public String getData_id() {
//                        return data_id;
//                    }
//
//                    public void setData_id(String data_id) {
//                        this.data_id = data_id;
//                    }
//
//                    public String getStatus() {
//                        return status;
//                    }
//
//                    public void setStatus(String status) {
//                        this.status = status;
//                    }
//
//                    public String getId() {
//                        return id;
//                    }
//
//                    public void setId(String id) {
//                        this.id = id;
//                    }
//
//                    public String getContent() {
//                        return content;
//                    }
//
//                    public void setContent(String content) {
//                        this.content = content;
//                    }
//
//                    public String getCtime() {
//                        return ctime;
//                    }
//
//                    public void setCtime(String ctime) {
//                        this.ctime = ctime;
//                    }
//
//                    public String getPrecid() {
//                        return precid;
//                    }
//
//                    public void setPrecid(String precid) {
//                        this.precid = precid;
//                    }
//
//                    public String getPreuid() {
//                        return preuid;
//                    }
//
//                    public void setPreuid(String preuid) {
//                        this.preuid = preuid;
//                    }
//
//                    public String getLike_count() {
//                        return like_count;
//                    }
//
//                    public void setLike_count(String like_count) {
//                        this.like_count = like_count;
//                    }
//
//                    public String getVoiceuri() {
//                        return voiceuri;
//                    }
//
//                    public void setVoiceuri(String voiceuri) {
//                        this.voiceuri = voiceuri;
//                    }
//
//                    public String getVoicetime() {
//                        return voicetime;
//                    }
//
//                    public void setVoicetime(String voicetime) {
//                        this.voicetime = voicetime;
//                    }
//
//                    public String getCmt_type() {
//                        return cmt_type;
//                    }
//
//                    public void setCmt_type(String cmt_type) {
//                        this.cmt_type = cmt_type;
//                    }
//
//                    public com.bobo520.newsreader.news.model.JokeCompleteBean.TopCmtBean.PrecmtBean.UserBeanX getUser() {
//                        return user;
//                    }
//
//                    public void setUser(com.bobo520.newsreader.news.model.JokeCompleteBean.TopCmtBean.PrecmtBean.UserBeanX user) {
//                        this.user = user;
//                    }
//
//                    public static class UserBeanX {
//                        /**
//                         * id : 8345129
//                         * username : 不得姐用户
//                         * sex : m
//                         * profile_image : http://wimg.spriteapp.cn/p/jie.jpg
//                         * weibo_uid :
//                         * qq_uid :
//                         * qzone_uid : 4635BFA41097206BA5E73744905E16ED
//                         * is_vip : false
//                         * personal_page : http://user.qzone.qq.com/4635BFA41097206BA5E73744905E16ED
//                         * total_cmt_like_count : 345
//                         */
//
//                        private String id;
//                        private String username;
//                        private String sex;
//                        private String profile_image;
//                        private String weibo_uid;
//                        private String qq_uid;
//                        private String qzone_uid;
//                        private boolean is_vip;
//                        private String personal_page;
//                        private String total_cmt_like_count;
//
//                        public String getId() {
//                            return id;
//                        }
//
//                        public void setId(String id) {
//                            this.id = id;
//                        }
//
//                        public String getUsername() {
//                            return username;
//                        }
//
//                        public void setUsername(String username) {
//                            this.username = username;
//                        }
//
//                        public String getSex() {
//                            return sex;
//                        }
//
//                        public void setSex(String sex) {
//                            this.sex = sex;
//                        }
//
//                        public String getProfile_image() {
//                            return profile_image;
//                        }
//
//                        public void setProfile_image(String profile_image) {
//                            this.profile_image = profile_image;
//                        }
//
//                        public String getWeibo_uid() {
//                            return weibo_uid;
//                        }
//
//                        public void setWeibo_uid(String weibo_uid) {
//                            this.weibo_uid = weibo_uid;
//                        }
//
//                        public String getQq_uid() {
//                            return qq_uid;
//                        }
//
//                        public void setQq_uid(String qq_uid) {
//                            this.qq_uid = qq_uid;
//                        }
//
//                        public String getQzone_uid() {
//                            return qzone_uid;
//                        }
//
//                        public void setQzone_uid(String qzone_uid) {
//                            this.qzone_uid = qzone_uid;
//                        }
//
//                        public boolean isIs_vip() {
//                            return is_vip;
//                        }
//
//                        public void setIs_vip(boolean is_vip) {
//                            this.is_vip = is_vip;
//                        }
//
//                        public String getPersonal_page() {
//                            return personal_page;
//                        }
//
//                        public void setPersonal_page(String personal_page) {
//                            this.personal_page = personal_page;
//                        }
//
//                        public String getTotal_cmt_like_count() {
//                            return total_cmt_like_count;
//                        }
//
//                        public void setTotal_cmt_like_count(String total_cmt_like_count) {
//                            this.total_cmt_like_count = total_cmt_like_count;
//                        }
//                    }
//                }
//            }
        }



        //---------------------------------------------------------------------------------------------



        }

    }
}
