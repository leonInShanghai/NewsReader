package com.bobo520.newsreader.customDialog;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bobo520.newsreader.R;

/**
 * Created by 求知自学网 on 2019/4/4 Copyright © Leon. All rights reserved.
 * Functions: 自定义 view 用作dialog 像城方那样的
 */
public class LEloadingView extends View {
    private Paint paint;
    private int mTotalWidth, mTotalHeight;
    private Bitmap bitmap;
    private int bitWidth;
    private int bitHeight;
    private int realBitH;
    private int realBitW;
    private Xfermode mXfermode;
    private PorterDuff.Mode mPorterDuffMode = PorterDuff.Mode.SRC_IN;
    private int sweepAngle = 0;
    private ValueAnimator valueAnimator;

    public LEloadingView(Context context) {
        this(context, null);
        //this.setBackgroundColor(Color.WHITE);
    }

    public LEloadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LEloadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bobologo);
        bitWidth = 100;
        realBitH = bitmap.getHeight();
        realBitW = bitmap.getWidth();
        bitHeight = bitWidth * realBitH / realBitW;
        mXfermode = new PorterDuffXfermode(mPorterDuffMode);
    }

    private void initPaint() {
        this.paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int dimension = DensityUtil.dip2px(getContext(), 40);
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = mTotalWidth / 2 - bitWidth / 2;
        int top = mTotalHeight / 2 - bitHeight / 2;
        RectF destRect = new RectF(left, top, left + bitWidth, top + bitHeight);
        int saveCount = canvas.saveLayer(destRect, paint, Canvas.ALL_SAVE_FLAG);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        //绘制目标图
        canvas.drawArc(destRect, 270, sweepAngle, true, paint);

        // 设置混合模式
        paint.setXfermode(mXfermode);
        paint.setColor(getResources().getColor(R.color.red_E83828));
        paint.setStyle(Paint.Style.FILL);
        // 绘制源图
        canvas.drawBitmap(bitmap, null, destRect, paint);
        // 清除混合模式
        paint.setXfermode(null);
        // 还原画布
        canvas.restoreToCount(saveCount);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
    }

    private void startAnim() {
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float percent = (float) valueAnimator.getAnimatedValue();
                sweepAngle = (int) (percent * 360);
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public void setBitmap(int drawableId) {
        bitmap = BitmapFactory.decodeResource(getResources(), drawableId);
    }

    public void start() {
        startAnim();
    }

    public void stop() {
        valueAnimator.end();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }
}
