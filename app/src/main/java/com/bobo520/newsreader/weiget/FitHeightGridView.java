package com.bobo520.newsreader.weiget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by 求知自学网 on 2019/4/5. Copyright © Leon. All rights reserved.
 * Functions: 处理 GridView 在scrollview只显示一行的问题 调整高度
 */
public class FitHeightGridView extends GridView {
    public FitHeightGridView(Context context) {
        super(context);
    }

    public FitHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int modeH = MeasureSpec.getMode(heightMeasureSpec);

        //这里获取到sizeH 是0 这样是不可以的
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);

        //如果高度模式为 MeasureSpec.UNSPECIFIED,强制让其变为MeasureSpec.AT_MOST模式进行测量
        if (modeH == MeasureSpec.UNSPECIFIED){

            //模式改变为 MeasureSpec.AT_MOST  最大的int 类型小数点右移2位：Integer.MAX_VALUE>>2
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
