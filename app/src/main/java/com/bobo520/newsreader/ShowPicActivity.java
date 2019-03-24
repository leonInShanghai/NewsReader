package com.bobo520.newsreader;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo520.newsreader.bean.NewsDetailBean;
import com.bobo520.newsreader.util.ImageUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class ShowPicActivity extends AppCompatActivity {

    /**用来哦传递图片集合的键（名称）*/
    public static final String IMG_LIST = "IMG_LIST";

    /**用来哦传递图片索引的键（名称）*/
    public static final String IMG_INDEX = "IMG_INDEX";

   /**当前是第几张图片的指示器文本*/
    private TextView mTvIndex;

    /**显示一共有多少图片的文本*/
    private TextView mTvTotal;

    /**多张图片时用来切换的view pager*/
    private ViewPager mViewPagerShowPic;

    /**图片数据集合对象*/
    private List<NewsDetailBean.ImgBean> mImgList;

    /**上一页面用户点击的第几张图片*/
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        //Leon设置了最近淘宝等各大app流行的沉浸式状态栏
        LETrtStBarUtil.setTransparentToolbar(this);

        //获取图片的集合
        Intent intent = getIntent();

        if (intent != null){
            mImgList = (List<NewsDetailBean.ImgBean>)intent.
                    getSerializableExtra(IMG_LIST);
            //获取图片的索引第二个参数是默认值为0
            mIndex = intent.getIntExtra(IMG_INDEX, 0);
        }

        //初始化UI控件
        initView();

        //初始化数据
        initData();
    }

    //初始化UI控件
    private void initView(){
        mTvIndex = (TextView)findViewById( R.id.tv_index );
        mTvTotal = (TextView)findViewById( R.id.tv_total );
        mViewPagerShowPic = (ViewPager)findViewById( R.id.viewPager_show_pic );
    }

    /**初始化数据*/
    private void initData(){
        //创建一个imageView的集合
        ArrayList<ImageView> imageViews = new ArrayList<>();

        //遍历数据集合 根据数据的长度创建imageview
        for (int i = 0; i < mImgList.size(); i++) {
            NewsDetailBean.ImgBean imgBean = mImgList.get(i);

            //imageView 不能随意放大图片
            //ImageView imageView = new ImageView(getApplicationContext());

            //后面使用了PhotoView-1.3.0.aar 可以随意放大图片的第三方框架
            PhotoView photoView = new PhotoView(getApplicationContext());

            ImageUtil.getSinstance().displayPic(imgBean.getSrc(),photoView);
            imageViews.add(photoView);
        }

        //创建viewpager的适配器
        MyImgAdapter myImgAdapter = new MyImgAdapter(imageViews);

        //设置viewpager的适配器
        mViewPagerShowPic.setAdapter(myImgAdapter);

        //设置(添加)viewpager翻页的监听
        mViewPagerShowPic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                //当页面选中发生位置变化时调用这个方法-修改文本
                //注意数组是从0开始的现实生活中页面是从1开始的
                mTvIndex.setText(String.valueOf(position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //进来的时候，让对应的index的位置上的图片进行显示
        mViewPagerShowPic.setCurrentItem(mIndex);
        mTvIndex.setText(String.valueOf(mIndex + 1));

        //设置总页数文本框的内容
        mTvTotal.setText(" / "+mImgList.size());
    }

    /**自定义内部类继承自PagerAdapter*/
    private class MyImgAdapter extends PagerAdapter{

        /**image view （已经设置好图片）的集合*/
        private ArrayList<ImageView> imageViews;

        public MyImgAdapter(ArrayList<ImageView> imageViews){
            this.imageViews = imageViews;
        }

        @Override
        public int getCount() {
            return imageViews == null ? 0 : imageViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            //instantiate 实例化方法
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //破坏，摧毁item的时候调用这个方法
            container.removeView((View)object);
        }
    }
}
