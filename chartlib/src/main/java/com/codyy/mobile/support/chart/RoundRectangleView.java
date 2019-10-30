package com.codyy.mobile.support.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;

/**
 * 圆角矩形背景
 * Created by lijian on 2018/3/19.
 */

public class RoundRectangleView extends View {
    public RoundRectangleView(Context context) {
        this(context, null);
    }

    public RoundRectangleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundRectangleView, defStyleAttr, 0);
        roundRadius = array.getDimensionPixelSize(R.styleable.RoundRectangleView_roundRectangleRadius, dip2px(5f));
        color = array.getColor(R.styleable.RoundRectangleView_roundRectangleColor, Color.GRAY);
        array.recycle();
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mRectF = new RectF();
    }

    private int color;
    private float roundRadius;
    private Paint mPaint;
    private RectF mRectF;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.right = getMeasuredWidth();
        mRectF.left = 0;
        mRectF.top = 0;
        mRectF.bottom = getMeasuredHeight();
        canvas.drawRoundRect(mRectF, roundRadius, roundRadius, mPaint);
    }


    private int dip2px(float dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public int sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
