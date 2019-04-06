package com.bobo520.newsreader.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.bobo520.newsreader.R;



/**
 * Created by 求知自学网 on 2019/4/5. Copyright © Leon. All rights reserved.
 * Functions: 首页 白屏上半（长按排序或删除以上） 的GridView 的适配器
 */
public class ShowTitleAdapter extends MyBaseAdapter<String> {

    /**是否需要显示mIvChangeTitle item左上角×的变量 默认为发false*/
    private boolean mIsShowDelete = false;

    /**用户当前所在的tabhost position 索引会变后来改成 string了  默认等于0*/
    private String noDeletePos = "";

    //不可删除item的标题string 默认为空
    private String noDeleteStr = "";

    /**tabHost数据源数组*/
    private String[] mStrings;

    /**自增变量-解决用户添加后非4个根目录也变色的问题*/
    private int isNotAdd = 0;

    public ShowTitleAdapter(ArrayList list,String[] strings) {
        super(list);
        this.mStrings = strings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //通过打气筒实例化控件-固定的写法
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = View.inflate(parent.getContext(),R.layout.item_change_title,null);
            viewHolder = new ViewHolder();
            viewHolder.mTvChangeTitle = convertView.findViewById(R.id.tv_change_title);
            viewHolder.mIvChangeTitle = convertView.findViewById(R.id.iv_change_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        //设置text view的 数据
        changeUI(viewHolder,mList.get(position),position);

        return convertView;
    }


    private void changeUI(ViewHolder viewHolder, String s, int position) {
        viewHolder.mTvChangeTitle.setText(s);

        //用户无论有没有选择 4个根目录颜色都能和其他不一样
        if (isNotAdd <= mStrings.length && mList.get(position).equals(mStrings[position])){
            viewHolder.mTvChangeTitle.setBackgroundResource(R.drawable.brilliancy_title_selector);

            //首次加载会进来mStrings.length次 确保数组里的内容能变色 其他不变
            isNotAdd++;
        }

        //判断是否需要显示删除图片
        if (mIsShowDelete){
            //即使需要显示，但是第一个item是头条不让删除，就还是让其不显示
            /**
             * 删除的规则 用户当前所在的位置item 不可删除
             * 4个根目录不可删除
             */
            if (position < mStrings.length && mList.get(position).equals(mStrings[position])){
                viewHolder.mIvChangeTitle.setVisibility(View.GONE);
            }else if (mList.get(position).equals(noDeletePos)){
                viewHolder.mIvChangeTitle.setVisibility(View.GONE);
                noDeleteStr = viewHolder.mTvChangeTitle.getText().toString();
            }else{
                viewHolder.mIvChangeTitle.setVisibility(View.VISIBLE);
            }
        }else{
            viewHolder.mIvChangeTitle.setVisibility(View.GONE);
        }
    }

    //公开 的设置是否显示左上角删除图片的方法
    public void setShowDelete(boolean isShowDelete,String positionStr){

        //优化如果外面的值和当前相等 返回什么都不做
        if (this.mIsShowDelete == isShowDelete && this.noDeletePos.equals(positionStr)){return;}

        //将外面传递过来用户所在的position 赋值给自己
        this.noDeletePos = positionStr;
        
        //将外面传递的值赋予给自己
        this.mIsShowDelete = isShowDelete;

        //记住一定要你刷新否者看不到效果
        notifyDataSetChanged();
    }

    /**当前item是否有显示左上角的删除图片*/
    public boolean isShowDelete(String index,boolean isLongClick){


        /**
         * 删除规则用户所在的位置不要删除
         * 4个根目录不要删除
         */
        if (isLongClick){

            //四个根目录return false
            for (String str : mStrings){
                if (index.equals(str)){
                    return false;
                }
            }

            //当前位置return false
            return noDeleteStr.equals(index) ? false : true;

        }else{
            return mIsShowDelete;
        }
        // return mIsShowDelete;
       // return viewHolder.mIvChangeTitle.getVisibility() == View.VISIBLE;

    }

    /**增item的方法*/
    public void addItem(String text){

        mList.add(text);
        //记住一定要你刷新否者看不到效果
        notifyDataSetChanged();
    }

    /**删item的方法*/
    public String deleteItem(int index){

        /**根据索引删除集合中的对象并获取到删除的对象*/
        String remove = mList.remove(index);

        //记住一定要你刷新否者看不到效果
        notifyDataSetChanged();

        return remove;
    }

    //this is 固定写法  内部类 ViewHolder
    private class ViewHolder{

        //标题
        private TextView mTvChangeTitle;

        /**标题左上角的X*/
        private ImageView mIvChangeTitle;

    }

    /**根据索引返回对应的string*/
    public String getLisStr(int index){
        return mList.get(index);
    }
}
