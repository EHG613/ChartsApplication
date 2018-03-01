package com.codyy.charts.chartsapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;

/**
 * Created by lijian on 2018/2/28.
 */

public class DoubleCirclePercentChart extends View {
    private int startColor;
    private int endColor;
    private int startColor2;
    private int endColor2;
    private int topTextColor;
    private int bottomTextColor;
    private int circleBackgroundColor;
    private String mBottomText;
    private String mTopText;
    private boolean isGradient;

    public DoubleCirclePercentChart(Context context) {
        this(context, null);
    }

    public DoubleCirclePercentChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleCirclePercentChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DoubleCirclePercentChart, defStyleAttr, 0);
        startColor = typedArray.getColor(R.styleable.DoubleCirclePercentChart_doubleCirclePercentStartColor, Color.parseColor("#FD6097"));
        endColor = typedArray.getColor(R.styleable.DoubleCirclePercentChart_doubleCirclePercentEndColor, Color.parseColor("#FDA571"));
        startColor2 = typedArray.getColor(R.styleable.DoubleCirclePercentChart_doubleCirclePercentStartColor2, Color.parseColor("#FD6097"));
        endColor2 = typedArray.getColor(R.styleable.DoubleCirclePercentChart_doubleCirclePercentEndColor2, Color.parseColor("#FDA571"));
        circleBackgroundColor = typedArray.getColor(R.styleable.DoubleCirclePercentChart_doubleCircleBackgroundColor, Color.parseColor("#E6E7E9"));
        topTextColor = typedArray.getColor(R.styleable.DoubleCirclePercentChart_doubleCircleTopTextColor, Color.parseColor("#626262"));
        bottomTextColor = typedArray.getColor(R.styleable.DoubleCirclePercentChart_doubleCircleBottomTextColor, Color.parseColor("#666666"));
        mBottomText = typedArray.getString(R.styleable.DoubleCirclePercentChart_doubleCirclePercentBottomText);
        isGradient = typedArray.getBoolean(R.styleable.DoubleCirclePercentChart_doubleCircleIsGradient, true);
        typedArray.recycle();
        init();
    }

    private Paint mPaint;
    private Paint mPaintC;
    private RectF mRectF;
    private TextPaint mTextPaintBottom;
    private Paint mSmallCirclePaint;
    private BlurMaskFilter mBlurMaskFilter;

    private void init() {
        mBlurMaskFilter = new BlurMaskFilter(dip2px(1f), BlurMaskFilter.Blur.SOLID);
        mRectF = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(dip2px(5f));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(circleBackgroundColor);
        mSmallCirclePaint = new Paint();
        mSmallCirclePaint.setStrokeWidth(2f);
        mSmallCirclePaint.setAntiAlias(true);
        mSmallCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintC = new Paint();
        mPaintC.setStrokeWidth(dip2px(5f));
        mPaintC.setAntiAlias(true);
        mPaintC.setStyle(Paint.Style.STROKE);
        mPaintC.setStrokeCap(Paint.Cap.ROUND);
        mTextPaintBottom = new TextPaint();
        mTextPaintBottom.setAntiAlias(true);
        mTextPaintBottom.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.left = mCenterX - mRadius;
        mRectF.top = mCenterY - mRadius;
        mRectF.right = mCenterX + mRadius;
        mRectF.bottom = mCenterY + mRadius;
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
        canvas.drawCircle(mCenterX, mCenterY, mRadius2, mPaint);
//        canvas.save();
        if (!isGradient) {
//            mPaint.setShadowLayer(2, 1, 0, circleBackgroundColor);
//            mPaint.setStrokeWidth(dip2px(2f));
//            mPaint.setColor(Color.RED);
//            mPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.INNER));
            //设置浮雕滤镜效果，参数1：光源指定方向；参数2:环境光亮度，取值0-1,值越小越暗；参数3：镜面高光反射系数，值越小反射越强；参数4：模糊延伸半径
            mPaint.setMaskFilter(mEmbossMaskFilter);
            setLayerType(LAYER_TYPE_SOFTWARE, mPaint);
            canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
            canvas.drawCircle(mCenterX, mCenterY, mRadius2, mPaint);
        }
        mTextPaintBottom.setColor(bottomTextColor);
        mTextPaintBottom.setTextSize(dip2px(14f));
        canvas.drawText(TextUtils.isEmpty(mBottomText) ? "" : mBottomText, mCenterX, mCenterY + dip2px(12f), mTextPaintBottom);
        if (isGradient) {
            mPaintC.setShader(mShader);
        } else {
            mPaintC.setColor(startColor);
            mSmallCirclePaint.setColor(startColor);
            mSmallCirclePaint.setMaskFilter(mBlurMaskFilter);
            float x1 = (float) (mCenterX + mRadius * Math.cos((sweepAngle - 90) * Math.PI / 180));
            float y1 = (float) (mCenterY + mRadius * Math.sin((sweepAngle - 90) * Math.PI / 180));
            canvas.drawCircle(x1, y1, dip2px(5f), mSmallCirclePaint);
        }

        canvas.drawArc(mRectF, -90, sweepAngle, false, mPaintC);
        if (isGradient) {
            mPaintC.setShader(mShader2);
        } else {
            mPaintC.setColor(startColor2);
            mSmallCirclePaint.setColor(startColor2);
            mSmallCirclePaint.setMaskFilter(mBlurMaskFilter);
            float x1 = (float) (mCenterX + mRadius2 * Math.cos((sweepAngle2 - 90) * Math.PI / 180));
            float y1 = (float) (mCenterY + mRadius2 * Math.sin((sweepAngle2 - 90) * Math.PI / 180));
            canvas.drawCircle(x1, y1, dip2px(5f), mSmallCirclePaint);
        }
        mRectF.left = mCenterX - mRadius2;
        mRectF.top = mCenterY - mRadius2;
        mRectF.right = mCenterX + mRadius2;
        mRectF.bottom = mCenterY + mRadius2;
        canvas.drawArc(mRectF, -90, sweepAngle2, false, mPaintC);
        if (!TextUtils.isEmpty(mTopText)) {
            mTextPaintBottom.setTextSize(dip2px(20f));
            mTextPaintBottom.setColor(topTextColor);
            canvas.drawText(TextUtils.isEmpty(mTopText) ? "" : mTopText, mCenterX, mCenterY - dip2px(10f), mTextPaintBottom);
        }
    }

    private float sweepAngle;
    private float sweepAngle2;

    public void setText(String topText, float percent1, float percent2) {
        mTopText = TextUtils.isEmpty(topText) ? "" : topText;
        percent1=percent1>100?100:(percent1<0?0:percent1);
        percent2=percent2>100?100:(percent2<0?0:percent2);
        sweepAngle = percent1 / 100 * 360;
        sweepAngle2 = percent2 / 100 * 360;
        invalidate();
    }

    private int mRadius;
    private int mRadius2;
    Shader mShader;
    Shader mShader2;
    private EmbossMaskFilter mEmbossMaskFilter;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        mRadius = Math.min(measureWidth, measureHeight) / 2 - dip2px(10f);
        mRadius2 = Math.min(measureWidth, measureHeight) / 2 - dip2px(20f);
        setMeasuredDimension(measureWidth, measureHeight);
        mShader = new LinearGradient(mCenterX, mCenterY - mRadius, mCenterX, mCenterY + mRadius, startColor, endColor, Shader.TileMode.CLAMP);
        mShader2 = new LinearGradient(mCenterX, mCenterY - mRadius2, mCenterX, mCenterY + mRadius2, startColor2, endColor2, Shader.TileMode.CLAMP);
        mEmbossMaskFilter = new EmbossMaskFilter(new float[]{0, -1, -1}, 0.99f, 0f, mRadius);
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
