package com.codyy.mobile.support.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;

/**
 * Created by lijian on 2018/3/1.
 */

public class DashBoardRateChart extends View {
    public DashBoardRateChart(Context context) {
        this(context, null);
    }

    public DashBoardRateChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashBoardRateChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DashBoardRateChart, defStyleAttr, 0);
        startColor = array.getColor(R.styleable.DashBoardRateChart_dashboardRateStartColor, Color.parseColor("#54c0fa"));
        endColor = array.getColor(R.styleable.DashBoardRateChart_dashboardRateStartColor, Color.parseColor("#4d91fe"));
        baseColor = array.getColor(R.styleable.DashBoardRateChart_dashboardRateBaseColor, Color.parseColor("#bec5d8"));
        array.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPath = new Path();
        mTextPaint = new TextPaint();
        mPercentTextPaint = new TextPaint();
        dimension10dp = (int) (dip2px(10f) + tableWidth / 2);
        setLayerType(LAYER_TYPE_SOFTWARE, mPercentTextPaint);
    }

    private int dimension10dp;
    private TextPaint mTextPaint;
    private TextPaint mPercentTextPaint;
    private Paint mPaint;
    private Path mPath;
    private RectF mTableRectF;
    private RectF mTableRectFO;
    private float tableWidth = 34f;
    //把路径分成虚线段的
    private DashPathEffect dashPathEffect;
    //给路径上色
    private SweepGradient mColorShader;
    private SweepGradient mColorShaderBase;
    private int mRadius;
    private int startColor;
    private int endColor;
    private int baseColor;
    private String bottomText;
    private String percentText;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //油表的位置方框
        mTableRectF = new RectF(mCenterX - mRadius + dimension10dp, mCenterY - mRadius + dimension10dp, mCenterX + mRadius - dimension10dp, mCenterY + mRadius - dimension10dp);
        mTableRectFO = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
        mPath.reset();
        //在油表路径中增加一个从起始弧度
        mPath.addArc(mTableRectF, 45, 270);
        //计算路径的长度
        PathMeasure pathMeasure = new PathMeasure(mPath, false);
        float length = pathMeasure.getLength();
        float step = length / 80;
        dashPathEffect = new DashPathEffect(new float[]{step / 2, step / 2}, 0);
        mColorShader = new SweepGradient(mCenterX, mCenterY, startColor, endColor);
        mColorShaderBase = new SweepGradient(mCenterX, mCenterY, baseColor, baseColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1f);
        mPaint.setColor(Color.parseColor("#38adff"));
        canvas.drawArc(mTableRectFO, 135, 269, false, mPaint);
        //旋转画布
        canvas.rotate(90, mCenterX, mCenterY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(tableWidth);
        mPaint.setPathEffect(dashPathEffect);
        mPaint.setShader(mColorShaderBase);
        //在油表路径中增加一个从起始弧度
        mPath.reset();
        mPath.addArc(mTableRectF, 45, 270);
        canvas.drawPath(mPath, mPaint);
        canvas.save();
        mPath.reset();
        mPath.addArc(mTableRectF, 45, sweepAngle);
        mPaint.setShader(mColorShader);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        mPaint.setPathEffect(null);
        mPaint.setShader(null);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.rotate(-90, mCenterX, mCenterY);
        if (!TextUtils.isEmpty(bottomText)) {
            mTextPaint.setTextSize(sp2px(15f));
            mTextPaint.setAntiAlias(true);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setColor(Color.parseColor("#939fbe"));
            canvas.drawText(bottomText, mCenterX, mCenterY + dip2px(24f), mTextPaint);
        }
        if (!TextUtils.isEmpty(percentText)) {
            mPercentTextPaint.setTextSize(sp2px(60f));
            mPercentTextPaint.setAntiAlias(true);
            mPercentTextPaint.setTextAlign(Paint.Align.CENTER);
            mPercentTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPercentTextPaint.setColor(Color.parseColor("#38adff"));
            mPercentTextPaint.setShadowLayer(dip2px(4f), 0, dip2px(4f), Color.parseColor("#5938adff"));
            float textWidth = mPercentTextPaint.measureText(percentText) / 2;
            canvas.drawText(percentText, mCenterX, mCenterY, mPercentTextPaint);
//            mTextPaint.clearShadowLayer();
            mTextPaint.setTypeface(null);
            mTextPaint.setTextSize(sp2px(15f));
            mTextPaint.setTextAlign(Paint.Align.LEFT);
            mTextPaint.setColor(Color.parseColor("#939fbe"));
            canvas.drawText("%", mCenterX + textWidth, mCenterY, mTextPaint);
        }
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
        invalidate();
    }

    private float sweepAngle;

    public void setPercentText(@FloatRange(from = 0f, to = 100f) float percentText) {
        this.percentText = percentText + "";
        sweepAngle = percentText * 270 / 100;
        invalidate();
    }

    public void setPercentText(@FloatRange(from = 0f, to = 100f) float percent, int scale) {
        this.percentText = percent + "";
        float last = sweepAngle;
        sweepAngle = percent * 270 / 100;
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(last, sweepAngle);
        progressAnimator.setDuration(500L);
        progressAnimator.setInterpolator(new FastOutSlowInInterpolator());
//        progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percentText = getPercentText((Float) animation.getAnimatedValue() * 100 / 270, 1);
                sweepAngle = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }

    private String getPercentText(float p, int scale) {
        BigDecimal bigDecimal = new BigDecimal(p);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
//        mRadius = Math.min(measureWidth, measureHeight) / 2 - dip2px(10f);
        mRadius = Math.min(measureWidth, measureHeight) / 2;
        setMeasuredDimension(measureWidth, measureHeight);
    }

    private int mCenterX;
    private int mCenterY;

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int viewWidth;
        if (specMode == MeasureSpec.EXACTLY) {
            viewWidth = specSize;
        } else {
            viewWidth = dip2px(62f);
            if (specMode == MeasureSpec.AT_MOST) {
                viewWidth = Math.min(viewWidth, specSize);
            }
        }
        mCenterX = viewWidth / 2;
        return viewWidth;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int viewHeight;
        if (specMode == MeasureSpec.EXACTLY) {
            viewHeight = specSize;
        } else {
            viewHeight = dip2px(62f);
            if (specMode == MeasureSpec.AT_MOST) {
                viewHeight = Math.min(viewHeight, specSize);
            }
        }
        mCenterY = viewHeight / 2;
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
