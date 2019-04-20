package com.bobo520.newsreader.weiget;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Leon on 2019/4/17 Copyright  Leon. All rights reserved.
 * Functions: 为了左滑删除自定义listView
 */
public class SlideListView extends ListView {


    /**leon记录用户X方向起始位置*/
    private float mStartX = 0;

    /**leon记录用户Y方向起始位置*/
    private float mStartY = 0;

    /**leon用户x方向偏移量之和*/
    private float mSumDx = 0;

    /**leon用户y方向偏移量之和*/
    private float mSumDy = 0;

    private String TAG = getClass().getSimpleName();

    private int mScreenWidth;
    private int mDownX;
    private int mDownY;
    private int mMenuWidth;

    private boolean isMenuShow;
    private boolean isMoving;

    private int mOperatePosition = -1;
    private ViewGroup mPointChild;
    private LinearLayout.LayoutParams mLayoutParams;

    public SlideListView(Context context) {
        super(context);
        getScreenWidth(context);
    }

    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getScreenWidth(context);
    }

    public SlideListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getScreenWidth(context);
    }

    private void getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN://用户手指按下
                performActionDown(event);
                mStartX = event.getX();
                mStartY = event.getY();
                //避免先前的触摸的偏移量的影响，将以前的偏移量清空
                mSumDx = 0;
                mSumDy = 0;
                break;
            case MotionEvent.ACTION_MOVE://用户手指移动
                performActionMove(event);
                float newX = event.getX();
                float newY = event.getY();
                float dx = newX - mStartX;
                float dy = newY - mStartY;
                mSumDx += dx;
                mSumDy += dy;

                mStartX = newX;
                mStartY = newY;
                break;
            case MotionEvent.ACTION_UP://用户手指抬起
                performActionUp();
                break;
        }

        /**  解决横向滑动和点击冲突  以后会用到
         *比较X方向和Y方向的偏移量哪个大（取绝对值即负数转为正数再比较）
         *X方向绝对值大于Y方向绝对值 && X方向偏移量之和大于用户触摸的距离
         *安卓官方方法其实就是 == 8 ViewConfiguration.getTouchSlop()
         * private static final int TOUCH_SLOP = 8;
         */
        if (Math.abs(mSumDx) > Math.abs(mSumDy) &&Math.abs(mSumDx) > 8){
            //确定用户滑动的屏幕
            return true;
        }else{
            return super.onTouchEvent(event);
        }
    }

    private void performActionDown(MotionEvent ev) {
        mDownX = (int) ev.getX();
        mDownY = (int) ev.getY();
        //如果点击的不是同一个item，则关掉正在显示的菜单
        int position = pointToPosition(mDownX, mDownY);
        if (isMenuShow && position != mOperatePosition) {
            turnToNormal();
        }
        mOperatePosition = position;
        mPointChild = (ViewGroup) getChildAt(position - getFirstVisiblePosition());
        if (mPointChild != null) {
            mMenuWidth = mPointChild.getChildAt(1).getLayoutParams().width;
            mLayoutParams = (LinearLayout.LayoutParams) mPointChild.getChildAt(0).getLayoutParams();
            mLayoutParams.width = mScreenWidth;
            setChildLayoutParams();
        }
    }

    private boolean performActionMove(MotionEvent ev) {
        int nowX = (int) ev.getX();
        int nowY = (int) ev.getY();
//    if (isMoving) {
//      if (Math.abs(nowY - mDownY) > 0) {
//        Log.e(TAG, "kkkkkkk");
//        onInterceptTouchEvent(ev);
//      }
//    }
        if (Math.abs(nowX - mDownX) > 0) {
            //左滑 显示菜单
            if (nowX < mDownX) {
                if (isMenuShow) {
                    mLayoutParams.leftMargin = -mMenuWidth;
                } else {
                    //计算显示的宽度
                    int scroll = (nowX - mDownX);
                    if (-scroll >= mMenuWidth) {
                        scroll = -mMenuWidth;
                    }
                    mLayoutParams.leftMargin = scroll;
                }
            }
            //右滑 如果菜单显示状态，则关闭菜单
            if (isMenuShow && nowX > mDownX) {
                int scroll = nowX - mDownX;
                if (scroll >= mMenuWidth) {
                    scroll = mMenuWidth;
                }
                mLayoutParams.leftMargin = scroll - mMenuWidth;
            }
            setChildLayoutParams();
            isMoving = true;
            return true;
        }

        return super.onTouchEvent(ev);
    }

    private void performActionUp() {
        //超过一半时，显示菜单，否则隐藏
        if (-mLayoutParams.leftMargin >= mMenuWidth / 2) {
            mLayoutParams.leftMargin = -mMenuWidth;
            setChildLayoutParams();
            isMenuShow = true;
        } else {
            turnToNormal();
        }
        isMoving = false;
    }

    private void setChildLayoutParams(){
        if(mPointChild != null){
            mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
        }
    }

    /**
     * 正常显示
     */
    public void turnToNormal() {
        mLayoutParams.leftMargin = 0;
        mOperatePosition = -1;
        setChildLayoutParams();
        isMenuShow = false;
    }
}