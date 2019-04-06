package com.bobo520.newsreader.news.model;

import java.util.List;

/**
 * Created by 求知自学网 on 2019/3/30. Copyright © Leon. All rights reserved.
 * Functions:
 */
public class NewsCommentBean {

    /**
     * against : 0
     * anonymous : false
     * buildLevel : 1
     * commentId : 56415914
     * commentType : 0
     * content : 真不知道这哥们帅在哪
     * createTime : 2019-03-23 09:29:44
     * deviceInfo : {"deviceName":"iPhone XR"}
     * favCount : 0
     * ip : 117.136.*.*
     * isDel : false
     * postId : EAUJCTQV05346RBY_56415914
     * productKey : a2869674571f77b5a0867c3d71db5856
     * shareCount : 0
     * siteName : 网易
     * source : ph
     * unionState : false
     * user : {"avatar":"http://cms-bucket.nosdn.127.net/2018/08/13/078ea9f65d954410b62a52ac773875a1.jpeg","id":"eWQuMWU1ZGFkMjE2NjBjNGFhZDlAMTYzLmNvbQ==","incentiveInfoList":[],"location":"黑龙江省","nickname":"有态度网友0g39Xd","redNameInfo":[],"userId":269262541,"userType":1}
     * vote : 324
     */

    private int against;
    private boolean anonymous;
    private int buildLevel;
    private int commentId;
    private int commentType;
    private String content;
    private String createTime;
    private DeviceInfoBean deviceInfo;
    private int favCount;
    private String ip;
    private boolean isDel;
    private String postId;
    private String productKey;
    private int shareCount;
    private String siteName;
    private String source;
    private boolean unionState;
    private UserBean user;
    private int vote;

    public int getAgainst() {
        return against;
    }

    public void setAgainst(int against) {
        this.against = against;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public int getBuildLevel() {
        return buildLevel;
    }

    public void setBuildLevel(int buildLevel) {
        this.buildLevel = buildLevel;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public DeviceInfoBean getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoBean deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public int getFavCount() {
        return favCount;
    }

    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isIsDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isUnionState() {
        return unionState;
    }

    public void setUnionState(boolean unionState) {
        this.unionState = unionState;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public static class DeviceInfoBean {
        /**
         * deviceName : iPhone XR
         */

        private String deviceName;

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }
    }

    public static class UserBean {
        /**
         * avatar : http://cms-bucket.nosdn.127.net/2018/08/13/078ea9f65d954410b62a52ac773875a1.jpeg
         * id : eWQuMWU1ZGFkMjE2NjBjNGFhZDlAMTYzLmNvbQ==
         * incentiveInfoList : []
         * location : 黑龙江省
         * nickname : 有态度网友0g39Xd
         * redNameInfo : []
         * userId : 269262541
         * userType : 1
         */

        private String avatar;
        private String id;
        private String location;
        private String nickname;
        private int userId;
        private int userType;
        private List<?> incentiveInfoList;
        private List<?> redNameInfo;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public List<?> getIncentiveInfoList() {
            return incentiveInfoList;
        }

        public void setIncentiveInfoList(List<?> incentiveInfoList) {
            this.incentiveInfoList = incentiveInfoList;
        }

        public List<?> getRedNameInfo() {
            return redNameInfo;
        }

        public void setRedNameInfo(List<?> redNameInfo) {
            this.redNameInfo = redNameInfo;
        }
    }
}
