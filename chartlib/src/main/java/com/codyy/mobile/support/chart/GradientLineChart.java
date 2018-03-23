package com.codyy.mobile.support.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lijian on 2018/3/2.
 */

public class GradientLineChart extends View {
    private int startColor;
    private int endColor;
    private int textColor;
    private int textSize;
    private int strokeWidth;
    private int textPadding;
    private String suffix = "小时";
    private int dimension10dp;
    private float textHeight;

    public GradientLineChart(Context context) {
        this(context, null);
    }

    public GradientLineChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientLineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GradientLineChart, defStyleAttr, 0);
        startColor = array.getColor(R.styleable.GradientLineChart_gradientLineStartColor, Color.parseColor("#54c0fa"));
        endColor = array.getColor(R.styleable.GradientLineChart_gradientLineEndColor, Color.parseColor("#4d91fe"));
        textColor = array.getColor(R.styleable.GradientLineChart_gradientLineTextColor, Color.parseColor("#666666"));
        textSize = array.getDimensionPixelSize(R.styleable.GradientLineChart_gradientLineTextSize, sp2px(12f));
        strokeWidth = array.getDimensionPixelSize(R.styleable.GradientLineChart_gradientLineHeight, dip2px(6f));
        textPadding = array.getDimensionPixelSize(R.styleable.GradientLineChart_gradientLineTextPadding, dip2px(8f));
        suffix = array.getString(R.styleable.GradientLineChart_gradientLineSuffix);
        array.recycle();
        dimension10dp = dip2px(10f);
        init();
    }

    private Paint mPaint;
    private TextPaint mTextPaint;
    private RectF mRectF;

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#F6F6F6"));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(strokeWidth);
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        textHeight = Math.abs(mTextPaint.getFontMetrics().bottom) + Math.abs(mTextPaint.getFontMetrics().top);
        mRectF = new RectF();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startY = textHeight;
        float endLineY = textHeight / 2f + strokeWidth;
        float startLineY = textHeight / 2f;
        for (GradientLineEntity entity : list) {
            float h = textHeight;
            mTextPaint.setTextAlign(Paint.Align.LEFT);
            if (entity.getSubject().length() > 5) {
                canvas.drawText(entity.getSubject().substring(0, 5), 0, startY, mTextPaint);
                canvas.drawText(entity.getSubject().substring(5, entity.getSubject().length()), 0, startY + textHeight, mTextPaint);
            } else {
                canvas.drawText(entity.getSubject(), 0, startY, mTextPaint);
            }
            mTextPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(entity.getHours() + suffix, width, entity.getSubject().length()>5?startY+textHeight/2f:startY, mTextPaint);
            mRectF.left = textLength + dimension10dp;
            mRectF.top = entity.getSubject().length()>5?startLineY+textHeight/2f:startLineY;
            mRectF.right = width - hoursLength - dimension10dp;
            mRectF.bottom = entity.getSubject().length()>5?endLineY+textHeight/2f:endLineY;
            canvas.drawRoundRect(mRectF, strokeWidth / 2f, strokeWidth / 2f, mPaint);
            float stopX = (width - hoursLength - dimension10dp) * (entity.getHours() * 1f / maxHour);
            mPaint.setShader(new LinearGradient(textLength + dimension10dp, entity.getSubject().length()>5?startLineY+textHeight/2f:startLineY, stopX, entity.getSubject().length()>5?startLineY+textHeight/2f:startLineY, startColor, endColor, Shader.TileMode.CLAMP));
            mRectF.right = stopX;
            canvas.drawRoundRect(mRectF, strokeWidth / 2f, strokeWidth / 2f, mPaint);
            mPaint.setShader(null);
            startY += textPadding * 2;
            startY += entity.getSubject().length()>5?h*2:h;
            startLineY += textPadding * 2;
            startLineY += entity.getSubject().length()>5?h*2:h;
            endLineY += textPadding * 2;
            endLineY += entity.getSubject().length()>5?h*2:h;

        }
    }

    private List<GradientLineEntity> list = new ArrayList<>();
    private float textLength;
    private float hoursLength;
    private int maxHour;

    public void setList(@NonNull List<GradientLineEntity> list) {
        this.list = list;
        Collections.sort(this.list);
        textLength = mTextPaint.measureText(GradientLineEntity.getMaxLengthText(this.list));
        hoursLength = mTextPaint.measureText(GradientLineEntity.getMaxLengthHours(this.list, suffix));
        maxHour = GradientLineEntity.getMaxHours(this.list);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureWidth(widthMeasureSpec);
//        lineLength = width - textLength - hoursLength - dimension10dp * 2;
        for (GradientLineEntity entity : list) {
            if (entity.getSubject().length() > 5) {
                height += textHeight * 2;
            }
            height += textPadding;
        }
        setMeasuredDimension(width, height);
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
