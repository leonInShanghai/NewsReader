package com.bobo520.newsreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bobo520.newsreader.bean.NewsDetailBean;

import java.io.Serializable;
import java.util.List;

public class ShowPicActivity extends AppCompatActivity {

    /**用来哦传递图片集合的键（名称）*/
    public static final String IMG_LIST = "IMG_LIST";

    /**用来哦传递图片索引的键（名称）*/
    public static final String IMG_INDEX = "IMG_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        //Leon设置了最近淘宝等各大app流行的沉浸式状态栏
        LETrtStBarUtil.setTransparentToolbar(this);

        //获取图片的集合
        Intent intent = getIntent();

        if (intent != null){
            List<NewsDetailBean.ImgBean> imgList = (List<NewsDetailBean.ImgBean>)intent.
                    getSerializableExtra(IMG_LIST);
            //获取图片的索引第二个参数是默认值为0
            int intExtra = intent.getIntExtra(IMG_INDEX, 0);
        }


    }
}
