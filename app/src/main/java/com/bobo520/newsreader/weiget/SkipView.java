package com.bobo520.newsreader.weiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Leon on 2019/1/6. Copyright © Leon. All rights reserved.
 * Functions:SkipView 的view
 */
public class SkipView extends View {

    //准备三支画笔-内部的字体画笔
    Paint mTextPaint;
    //内部灰色圆的画笔
    Paint mInnerCriclePaint;
    //外部圆弧的画笔
    Paint mOutArcPaint;

    /**
     * 需要确定的参数
     * ①外部圆弧的宽度
     * ②内部的字体大小
     * ③内部灰色圆的半径
     */

    /**①外部圆弧的宽度*/
    public static final int ARC_WIDTH = 5;

    /**②内部的字体大小36*/
    public static final int TEXT_SIZE = 36;

    /**③文字距离内部灰色圆边框的间距*/
    public static final int INNER_PADDING = 10;

    //内部灰色圆的半径
    private float mInnerCircleRadius;

    //外部圆弧的半径 ：内部圆的直径 +  2 * 外部圆弧的宽度
    private float mOutArcRadius;

    /**画外部圆弧时需要传递的矩形*/
    private RectF rectF;

    //通过画笔计算文字的宽度（计算内部灰色圆直径要用到的）
    float mMeasuresTextWidth;

    /**角度变化的时间*/
    private int mCurrentTime = 0;

    /**角度变化的总时间*/
    private int mTotalTime = 3000;

    private Handler mHandler;

    //发送每秒改变角度动画的消息
    private final int SPACE_TIME_ANGLE = 20190106;

    /**在控件中准备一个成员变量作为OnSkipListener接口的实例*/
    private OnSkipListener mOnSkipListener;


    public SkipView(Context context) {
        super(context);
    }

    public SkipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        //准备三支画笔-内部的字体画笔
        mTextPaint = new Paint();//创建画笔
        mTextPaint.setTextSize(TEXT_SIZE);//设置字体大小
        mTextPaint.setColor(Color.WHITE);//设置画笔颜色白色
        //内部灰色圆的画笔
        mInnerCriclePaint = new Paint();//创建画笔
        mInnerCriclePaint.setColor(Color.GRAY);//设置颜色灰色
        mInnerCriclePaint.setAntiAlias(true);//设置抗锯齿
        //外部圆弧的画笔
        mOutArcPaint = new Paint();//创建画笔
        mOutArcPaint.setColor(Color.RED);//设置颜色灰色
        mOutArcPaint.setAntiAlias(true);//设置抗锯齿
        mOutArcPaint.setStrokeWidth(ARC_WIDTH);//设置圆弧的宽度
        mOutArcPaint.setStyle(Paint.Style.STROKE);//画空心圆

        //通过画笔计算文字的宽度（计算内部灰色圆直径要用到的）
        mMeasuresTextWidth = mTextPaint.measureText("跳过");

        //内部灰色圆的半径 ：2 * 内部灰色圆边框的间距 + 内部字体的宽度 - 空心圆的内部
        mInnerCircleRadius = (2 * INNER_PADDING + mMeasuresTextWidth) / 2;

        //外部圆弧的半径   = (内部圆的直径+2*外部圆弧的笔画宽度)/2
        mOutArcRadius = (mInnerCircleRadius * 2 + 2 * ARC_WIDTH)/2;

        //绘制圆弧时用的的矩形
        rectF = new RectF(0 + ARC_WIDTH / 2 + 1, 0  + ARC_WIDTH / 2 + 1,
                mOutArcRadius * 2 - ARC_WIDTH / 2 - 1, mOutArcRadius * 2  - ARC_WIDTH / 2 - 1);

        //用handler做角度变化的动画效果
        mHandler =  new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //修改当前时间，改变角度
                mCurrentTime += 100;
                //开始重绘
                invalidate();
                if (mCurrentTime >= mTotalTime){
                    //该停止了
                    //Toast.makeText(getContext(),"该跳转页面了",Toast.LENGTH_SHORT).show();

                    //避免空指针异常
                    if (mOnSkipListener != null){
                        //跳转到某个具体页面，具体逻辑咱么这里不写，交给使用了这个控件了类去实现
                        mOnSkipListener.onSkip();
                    }
                    return;
                }
                //注意不return这里会一直循环下去 记得销毁handler 避免内存泄漏-发送延时消息 延时单位 毫秒
                mHandler.sendEmptyMessageDelayed(SPACE_TIME_ANGLE,100);
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        switch (eventAction){
            case MotionEvent.ACTION_DOWN://用户按下的this
                setAlpha(0.5f);//设置this的透明度为0.5F
                break;
            case MotionEvent.ACTION_MOVE://用户手指在this上移动

                break;
            case MotionEvent.ACTION_UP://用户抬起的手指
                //让handler中的消息循环停止避免跳转2次
                mHandler.removeCallbacksAndMessages(null);
                setAlpha(1.0f);//设置this的透明度为正常
                //要跳转页面-避免空指针异常
                if (mOnSkipListener != null){
                    //跳转到某个具体页面，具体逻辑咱么这里不写，交给使用了这个控件了类去实现
                    mOnSkipListener.onSkip();
                }
                break;
        }

        return true;
    }

    /**倒计时加载完毕跳转的接口**/
    public interface OnSkipListener{
        void onSkip();
    }

    /**给接口成员变量赋值的set方法*/
    public void setmOnSkipListener(OnSkipListener skipListener){
        this.mOnSkipListener = skipListener;
    }

    public void start(){
        /**这里发送后会调用延时消息 开始每秒钟循环发送消息 开始不断的重绘*/
        mHandler.sendEmptyMessage(SPACE_TIME_ANGLE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        //AT_MOST: 度量规格模式：子级可以任意大小，最多可以达到指定的大小 - wrap_content
        if (widthMode == MeasureSpec.AT_MOST){
            widthSize = (int)(mOutArcRadius * 2);
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST){
            heightSize = (int)(mOutArcRadius * 2);
        }

        setMeasuredDimension(widthSize,heightSize);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //将整个画布旋转（直接修起始角也可以）
        canvas.save();//存档
        canvas.rotate(-90,getMeasuredWidth() / 2,getMeasuredHeight() / 2);//旋转
        //角度变化计算 起始时间 / 总时间 * 360
        float spaceTime = mCurrentTime * 1f / mTotalTime * 360;
        //绘制 外部的圆弧，内部的灰色的圆，中间文字 先传递一个矩形，起始角，结束角， 连接useCenter ，画笔
        canvas.drawArc(rectF,0,spaceTime,false,mOutArcPaint);
        canvas.restore();//读档-这样做是为了不影响其他控件，否者会全部被旋转

        //画内部灰色的圆 cx:圆心x坐标 cy:圆心Y坐标
        canvas.drawCircle(getMeasuredWidth() / 2,getMeasuredHeight() / 2,mInnerCircleRadius,mInnerCriclePaint);

        //获得文字高度上的基线
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();

        /**
         * 文字的坐标是按照baseline 以上为负 以下为正
         * 注意没有  fontMetrics.baseline 这样的属性
         */

        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        float ascet = fontMetrics.ascent;
        float descent = fontMetrics.descent;

        int baseLine = getMeasuredHeight() / 2;
        //公式：用来计算出baseLine在Y轴居中的公式（推导过程不再赘述）
        baseLine = (int) (baseLine - (top + bottom) / 2);

        //画内部的文字-注意一定要按照这样的顺序不然后画的会把先画的挡到 这里的的xy代表文字的左下角坐标
        canvas.drawText("跳过",getMeasuredWidth() / 2 - mMeasuresTextWidth / 2,baseLine,mTextPaint);
    }


    /**
     * 当View离开附着的窗口时触发，比如在Activity调用onDestroy方法时View就会离开窗口。
     * 和一开始的AttachedToWindow相对
     */
    @Override
    protected void onDetachedFromWindow() {
        if (mHandler != null){
            //安卓性能优化之清除Handler的Message和Runnable
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDetachedFromWindow();
    }
}
