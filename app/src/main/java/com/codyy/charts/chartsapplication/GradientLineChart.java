package com.codyy.charts.chartsapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lijian on 2018/3/2.
 */

public class GradientLineChart extends View {
    private int startColor;
    private int endColor;
    private int lineHeight;

    public GradientLineChart(Context context) {
        this(context, null);
    }

    public GradientLineChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientLineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GradientLineChart, defStyleAttr, 0);
        startColor = array.getColor(R.styleable.GradientLineChart_gradientLineStartColor, Color.parseColor("#53C5FD"));
        endColor = array.getColor(R.styleable.GradientLineChart_gradientLineEndColor, Color.parseColor("#5390FC"));
        lineHeight=array.getDimensionPixelSize(R.styleable.GradientLineChart_gradientLineHeight,dip2px(5f));
        array.recycle();
        init();
    }

    private LinearGradient mLinearGradient;
    private Paint mPaint;

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#F6F6F6"));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(lineHeight);
    }

    private float mPercent;

    public void setPercent(@FloatRange(from = 0f,to = 1f) float percent) {
        this.mPercent = percent;
        post(new Runnable() {
            @Override
            public void run() {
                mLinearGradient = new LinearGradient(dip2px(8f), height / 2, (width - dip2px(8f)) * mPercent, height / 2, startColor, endColor, Shader.TileMode.CLAMP);
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(dip2px(8f), height / 2, width - dip2px(8f), height / 2, mPaint);
        mPaint.setShader(mLinearGradient);
        canvas.drawLine(dip2px(8f), height / 2, (width - dip2px(8f)) * mPercent, height / 2, mPaint);
        mPaint.setShader(null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int width;
    private int height;

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int viewWidth;
        if (specMode == MeasureSpec.EXACTLY) {
            viewWidth = specSize;
        } else {
            viewWidth = dip2px(50f);
            if (specMode == MeasureSpec.AT_MOST) {
                viewWidth = Math.max(viewWidth, specSize);
            }
        }
        width = viewWidth;
        return viewWidth;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int viewHeight;
        if (specMode == MeasureSpec.EXACTLY) {
            viewHeight = specSize;
        } else {
            viewHeight = dip2px(25f);
            if (specMode == MeasureSpec.AT_MOST) {
                viewHeight = Math.max(viewHeight, specSize);
            }
        }
        height = viewHeight;
        return viewHeight;
    }

    private int px2dip(float px) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
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
