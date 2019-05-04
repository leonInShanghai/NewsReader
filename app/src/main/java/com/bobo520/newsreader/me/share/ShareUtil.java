package com.bobo520.newsreader.me.share;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by 求知自学网 on 2019/5/4. Copyright © Leon. All rights reserved.
 * Functions:
 */
public class ShareUtil {

    /**
     * @param activity
     * @param platform        平台：1 qq 2 qzone 3 wechat 4 wechatCircle
     * @param title           分享的标题
     * @param url             分享的url
     * @param describe        分享的描述
     * @param thumb_img       分享的缩略图
     * @param umShareListener 分享的回调
     */
    public static void ShareWeb(Activity activity, String platform, String title, String url,
                                String describe, int thumb_img,
                                UMShareListener umShareListener) {
        SHARE_MEDIA share_media = SHARE_MEDIA.QQ;
        if (platform.equals("2")) {
            share_media = SHARE_MEDIA.QZONE;
        } else if (platform.equals("3")) {
            share_media = SHARE_MEDIA.WEIXIN;
        } else if (platform.equals("4")) {
            share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
        }
        UMImage thumb = new UMImage(activity, thumb_img);
        UMWeb web = new UMWeb(url);
        web.setThumb(thumb);
        web.setDescription(describe);
        web.setTitle(title);
        new ShareAction(activity).withMedia(web)
                .setPlatform(share_media)
                .setCallback(umShareListener).share();
    }
}
