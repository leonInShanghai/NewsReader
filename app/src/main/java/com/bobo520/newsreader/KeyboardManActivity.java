package com.bobo520.newsreader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bobo520.newsreader.customDialog.CfLoadingView;
import com.bobo520.newsreader.http.HttpHelper;
import com.bobo520.newsreader.http.OnResponseListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static com.bobo520.newsreader.NewsDetailActivity.NEWS_ID;

/**
 * Created by 求知自学网 on 2019/3/24 Copyright © Leon. All rights reserved.
 * Functions: 评论控制器
 */
public class KeyboardManActivity extends SwipeBackActivity {

    /**上一页面传递过来的新闻id*/
    private String mNewsId;

    /**左上角的返回按钮*/
    private ImageButton mBackImageButton;

    /**中间显示内容的list view*/
    private ListView mListViewReply;

    /**左下角的发送文本框 其实应该是按钮才对*/
    private TextView mTvSendReply;

    /**写评论内容的输入框*/
    private EditText mEtReply;

    /**网络加载时loding好看的动画*/
    private KProgressHUD mKProgressHUD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_man);

        //Leon设置了最近淘宝等各大app流行的沉浸式状态栏
        LETrtStBarUtil.setTransparentToolbar(this);

        Intent intent = getIntent();
        if (intent != null){
            mNewsId = intent.getStringExtra(NEWS_ID);
        }

        //实例化UI控件
        initView();

        //实例化数据
        initData();
    }

    //实例化数据-只实现热门跟帖
    private void initData(){

        //判断如果上一页面传递过来的新闻id为空就没有必要网络请求了
        if (TextUtils.isEmpty(mNewsId)){ return; }

        //开始网络请求-开始显示loading
        mKProgressHUD = KProgressHUD.create(KeyboardManActivity.this)
                .setCustomView(new CfLoadingView(KeyboardManActivity.this))
                .setLabel("Please wait", Color.GRAY)
                .setBackgroundColor(Color.WHITE)
                .show();

        //根据每条新闻的id动态生成请求的url
        String replyUrl = Constant.getNewsReplyUrl(mNewsId);

        //开始发起get请求
        HttpHelper.getInstance().requestGET(replyUrl, new OnResponseListener() {
            @Override
            public void onFail(IOException e) {
                //loading结束（无论成功失败本次发起请求已经结束）
                mKProgressHUD.dismiss();
            }

            @Override
            public void onSuccess(String response) {
                //loading结束（无论成功失败本次发起请求已经结束）
                mKProgressHUD.dismiss();
            }
        });


    }

    //实例化UI控件
    private void initView() {

        mBackImageButton = (ImageButton)findViewById( R.id.back_imageButton );
        mBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户点击了左上角返回按钮 返回上一页
                finish();
            }
        });

        mListViewReply = (ListView)findViewById( R.id.listView_reply );
        mTvSendReply = (TextView)findViewById( R.id.tv_send_reply );
        mEtReply = (EditText)findViewById( R.id.et_reply );
    }
}

/**
 * {"commentIds":["56415914","56415914,56426719","56645923,56668605","56415914,56429106","56415914,57116175","56424569","56582325","56594093","56450074","56916031,57034022"],"comments":{"56424569":{"against":0,"anonymous":false,"buildLevel":1,"commentId":56424569,"commentType":0,"content":"天天推送这些玩意干啥 你们就不能整点好点的段子","createTime":"2019-03-23 09:43:34","deviceInfo":{"deviceName":"OnePlus 5T"},"favCount":0,"ip":"111.25.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_56424569","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"avatar":"http://mobilepics.nosdn.127.net/netease_subject/516859b4974cc5ef42eeb38ad72b1e271505698413430677","id":"MTA1NDg0OTAwMkBxcS5jb20=","incentiveInfoList":[],"location":"吉林省","nickname":"马婊","redNameInfo":[{"image":"http://cms-bucket.nosdn.127.net/2018/11/16/0508815e77bb4ab68394742072e98d1f.png","info":"","titleId":"E0OJE08M","titleName":"公益启程","url":"https://wp.m.163.com/163/html/activity/20181011/index.html?spss=gentieicon"}],"title":{"image":"http://cms-bucket.nosdn.127.net/2018/11/16/0508815e77bb4ab68394742072e98d1f.png","info":"","titleId":"E0OJE08M","titleName":"公益启程","url":"https://wp.m.163.com/163/html/activity/20181011/index.html?spss=gentieicon"},"userId":44426740,"userType":1,"vipInfo":""},"vote":58},"56594093":{"against":0,"anonymous":false,"buildLevel":1,"commentId":56594093,"commentType":0,"content":"我越看他越像马英九","createTime":"2019-03-23 13:56:26","deviceInfo":{"deviceName":"iPhone 6s Plus"},"ext":{},"favCount":0,"ip":"101.204.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_56594093","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"avatar":"http://cms-bucket.nosdn.127.net/fc56fd101b94421b860b1f0e22aca8c520170109143645.jpg","id":"bG9uZ2xvbmdfMTAxNEAxNjMuY29t","incentiveInfoList":[],"location":"四川省成都市","nickname":"一路找不着北","redNameInfo":[],"userId":691130,"userType":1,"vipInfo":"vipw"},"vote":42},"56415914":{"against":0,"anonymous":false,"buildLevel":1,"commentId":56415914,"commentType":0,"content":"真不知道这哥们帅在哪","createTime":"2019-03-23 09:29:44","deviceInfo":{"deviceName":"iPhone XR"},"favCount":0,"ip":"117.136.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_56415914","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"avatar":"http://cms-bucket.nosdn.127.net/2018/08/13/078ea9f65d954410b62a52ac773875a1.jpeg","id":"eWQuMWU1ZGFkMjE2NjBjNGFhZDlAMTYzLmNvbQ==","incentiveInfoList":[],"location":"黑龙江省","nickname":"有态度网友0g39Xd","redNameInfo":[],"userId":269262541,"userType":1},"vote":324},"56668605":{"against":0,"anonymous":false,"buildLevel":2,"commentId":56668605,"commentType":0,"content":"赵丽颖那土鸡配这货不是正好[捂嘴笑]","createTime":"2019-03-23 16:00:39","deviceInfo":{"deviceName":"iPhone 7 Plus"},"favCount":0,"ip":"123.139.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_56668605","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"avatar":"http://cms-bucket.nosdn.127.net/c8e4f7ddbd624add910b67890f6f8b1d20161216032200.jpg","id":"NTc2OTIyMzY2QHFxLmNvbQ==","incentiveInfoList":[],"location":"陕西省西安","nickname":"九把刀","redNameInfo":[],"userId":43020289,"userType":1,"vipInfo":"viphy"},"vote":183},"56450074":{"against":0,"anonymous":false,"buildLevel":1,"commentId":56450074,"commentType":0,"content":"渣男","createTime":"2019-03-23 10:21:36","deviceInfo":{"deviceName":"iPhone 6s"},"ext":{},"favCount":0,"ip":"223.104.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_56450074","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"avatar":"http://imgm.ph.126.net/lRIvn8dkxicwUKriaqfXMQ==/1164743453646409918.jpg","id":"ZmxpZ2h0X3dyZkAxMjYuY29t","incentiveInfoList":[],"location":"上海市","nickname":"逍遥帝","redNameInfo":[],"userId":79879474,"userType":1},"vote":24},"56582325":{"against":0,"anonymous":false,"buildLevel":1,"commentId":56582325,"commentType":0,"content":"冯绍峰也真是够了,看着挺腼腆的一人","createTime":"2019-03-23 13:38:11","deviceInfo":{"deviceName":""},"favCount":0,"ip":"220.181.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_56582325","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"authInfo":"小冰官方帐号，最风靡的人工智能机器人","avatar":"http://cms-bucket.nosdn.127.net/1b159e4625a44ad89ace2eb0a467c63020170728135539.jpg","id":"bWljcm9zb2Z0X3hpYW9iaW5nQDE2My5jb20=","incentiveInfoList":[{"iconHref":"https://c.m.163.com/nc/qa/activity/verify/index.html","iconType":1,"info":"小冰官方帐号，最风靡的人工智能机器人","url":"http://cms-bucket.nosdn.127.net/2018/07/09/2c4b45c0a97342da9c2893773db8f3e3.png"}],"label":"[{\"endTime\":\"1539692769248\",\"startTime\":\"1539606369248\",\"tagKey\":\"103\"}]","location":"北京市海淀区","nickname":"微软小冰","redNameInfo":[],"userId":113413742,"userType":1},"vote":44},"57116175":{"against":0,"anonymous":false,"buildLevel":2,"commentId":57116175,"commentType":0,"content":"面瘫面的比李易峰帅","createTime":"2019-03-24 07:58:10","deviceInfo":{"deviceName":"三星 Galaxy S8+"},"ext":{},"favCount":0,"ip":"120.193.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_57116175","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"avatar":"http://imgm.ph.126.net/GefGYYLJVbsyDgyiXVvuYA==/6631715378050704519.jpg","id":"Z3VvemkyMDEwQHNvaHUuY29t","incentiveInfoList":[],"location":"山东省菏泽市","nickname":"高能板砖","redNameInfo":[],"userId":60319094,"userType":1,"vipInfo":"vipw"},"vote":73},"57034022":{"against":0,"anonymous":false,"buildLevel":2,"commentId":57034022,"commentType":0,"content":"同意","createTime":"2019-03-24 00:35:39","deviceInfo":{"deviceName":"三星Galaxy S8 "},"ext":{},"favCount":0,"ip":"58.63.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_57034022","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"avatar":"http://mobilepics.nosdn.127.net/netease_subject/416b287dfc3557c8e34725d3b2a6820e1510299054727588","id":"bTE3MDk4ODk5NTM4QDE2My5jb20=","incentiveInfoList":[],"location":"广东省广州市","nickname":"扫黄打非局秘书长","redNameInfo":[],"userId":120662710,"userType":1,"vipInfo":"vipw"},"vote":20},"56429106":{"against":0,"anonymous":false,"buildLevel":2,"commentId":56429106,"commentType":0,"content":"还可以吧  起码算成功人士  如果是普通老百姓  那真没我帅","createTime":"2019-03-23 09:47:31","deviceInfo":{"deviceName":"iPhone Xs Max"},"ext":{},"favCount":0,"ip":"36.23.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_56429106","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"avatar":"http://cms-bucket.nosdn.127.net/618796484498487fa53a804dee29f5f520170128234021.jpg","id":"NTUxODI2MTk3QDE2My5jb20=","incentiveInfoList":[],"location":"浙江省宁波市","nickname":"呵呵弟","redNameInfo":[{"image":"http://cms-bucket.nosdn.127.net/2018/11/16/0508815e77bb4ab68394742072e98d1f.png","info":"","titleId":"E0OJE08M","titleName":"公益启程","url":"https://wp.m.163.com/163/html/activity/20181011/index.html?spss=gentieicon"}],"title":{"image":"http://cms-bucket.nosdn.127.net/2018/11/16/0508815e77bb4ab68394742072e98d1f.png","info":"","titleId":"E0OJE08M","titleName":"公益启程","url":"https://wp.m.163.com/163/html/activity/20181011/index.html?spss=gentieicon"},"userId":4560822,"userType":1,"vipInfo":""},"vote":113},"56426719":{"against":0,"anonymous":true,"buildLevel":2,"commentId":56426719,"commentType":0,"content":"和到广州都叫男的靓仔一样[大笑]","createTime":"2019-03-23 09:45:12","deviceInfo":{"deviceName":"三星GALAXY Note 8"},"ext":{},"favCount":0,"ip":"106.34.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_56426719","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"location":"河南省洛阳市","userId":0},"vote":309},"56645923":{"against":0,"anonymous":true,"buildLevel":1,"commentId":56645923,"commentType":0,"content":"土，配不上颖宝","createTime":"2019-03-23 15:28:12","deviceInfo":{"deviceName":""},"ext":{},"favCount":0,"ip":"49.92.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_56645923","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"location":"江苏省","userId":0},"vote":9},"56916031":{"against":0,"anonymous":false,"buildLevel":1,"commentId":56916031,"commentType":0,"content":"不喜欢这货，不接受反驳","createTime":"2019-03-23 21:33:42","deviceInfo":{"deviceName":"iPhone X"},"ext":{},"favCount":0,"ip":"61.171.*.*","isDel":false,"postId":"EAUJCTQV05346RBY_56916031","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"avatar":"http://cms-bucket.nosdn.127.net/2018/08/13/078ea9f65d954410b62a52ac773875a1.jpeg","id":"eWQuMTU0M2U4OWQ1YzY0NDkyNzhAMTYzLmNvbQ==","incentiveInfoList":[],"location":"上海市","nickname":"为啥要审核","redNameInfo":[],"userId":267093239,"userType":1},"vote":19}},"threadInfo":{"audioLock":"1","againstLock":"0","isTagOff":"0","hideAd":false,"url":"http://dy.163.com/v2/article/detail/EAUJCTQV05346RBY.html"}}
 */
