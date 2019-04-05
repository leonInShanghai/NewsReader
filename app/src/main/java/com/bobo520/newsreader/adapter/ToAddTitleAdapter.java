package com.bobo520.newsreader.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.bobo520.newsreader.R;

/**
 * Created by 求知自学网 on 2019/4/5. Copyright © Leon. All rights reserved.
 * Functions:
 */
public class ToAddTitleAdapter extends MyBaseAdapter<String> {

    public ToAddTitleAdapter(ArrayList<String> list) {
        super(list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ToAddViewHolder viewHolder;

        if (convertView == null){
            convertView = View.inflate(parent.getContext(),R.layout.item_change_title,null);

            viewHolder = new ToAddViewHolder();
            viewHolder.mTvChangeTitle = convertView.findViewById(R.id.tv_change_title);
            viewHolder.mIvChangeTitle = convertView.findViewById(R.id.iv_change_title);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ToAddViewHolder)convertView.getTag();
        }

        changeUI(viewHolder,mList.get(position));

        return convertView;
    }

    private void changeUI(ToAddViewHolder viewHolder, String s) {
        viewHolder.mTvChangeTitle.setText(s);
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

    //固定写法viewholder
    private class ToAddViewHolder{

        TextView mTvChangeTitle;

        ImageView mIvChangeTitle;

    }
}
