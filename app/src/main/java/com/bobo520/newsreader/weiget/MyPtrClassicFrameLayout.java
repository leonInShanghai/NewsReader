package com.bobo520.newsreader.weiget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by 求知自学网 on 2019/3/2. Copyright © Leon. All rights reserved.
 * Functions: 继承PtrClassicFrameLayout 主要是为了解决上拉加载跟多和轮播图触摸事件冲突的问题
 */
public class MyPtrClassicFrameLayout extends PtrClassicFrameLayout {

    /**记录用户X方向起始位置*/
    private float mStartX = 0;

    /**记录用户Y方向起始位置*/
    private float mStartY = 0;

    /**用户x方向偏移量之和*/
    private float mSumDx = 0;

    /**用户y方向偏移量之和*/
    private float mSumDy = 0;

    public MyPtrClassicFrameLayout(Context context) {
        super(context);
    }

    public MyPtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 复写 解决 轮播图和下拉刷新冲突的问题
     *当手势的X方向偏移大于Y方向的偏移时，说明不是下拉刷新的手势，
     *将事件正常的分发下去（return dispatchTouchEventSupper）;
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        int eventAction = event.getAction();

        switch (eventAction){
            case MotionEvent.ACTION_DOWN://用户手指按下
                mStartX = event.getX();
                mStartY = event.getY();
                //避免先前的触摸的偏移量的影响，将以前的偏移量清空
                mSumDx = 0;
                mSumDy = 0;
                break;
            case MotionEvent.ACTION_MOVE://用户手指移动
                float newX = event.getX();
                float newY = event.getY();
                float dx = newX - mStartX;
                float dy = newY - mStartY;
                mSumDx += dx;
                mSumDy += dy;

                /**
                 *比较X方向和Y方向的偏移量哪个大（取绝对值即负数转为正数再比较）
                 *X方向绝对值大于Y方向绝对值 && X方向偏移量之和大于用户触摸的距离
                 *安卓官方方法其实就是 == 8 ViewConfiguration.getTouchSlop()
                 * private static final int TOUCH_SLOP = 8;
                 */
                if (Math.abs(mSumDx) > Math.abs(mSumDy) &&Math.abs(mSumDx) > 8){
                    //将事件正常的分发下去（return dispatchTouchEventSupper(event)）;
                    return dispatchTouchEventSupper(event);
                }
                mStartX = newX;
                mStartY = newY;
                break;
            case MotionEvent.ACTION_UP://用户手指抬起
                break;
        }


        return super.dispatchTouchEvent(event);
    }
}
