package com.codyy.mobile.support.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
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
 * Created by lijian on 2018/2/26.
 */

public class HalfDashBoardChart extends View {
    private int mRadius;
    private int mPadding;
    private int mDashLength;
    private int mCenterX;
    private int mCenterY;
    private float mMinVal;//最小值
    private float mMaxVal;//最大值
    private int mScale;
    private boolean mShowText;//是否显示仪表盘周围文字
    private String mTopText;
    private String mBottomText;
    private int mTopTextSize;
    private int mBottomTextSize;
    private int mTopTextColor;
    private int mBottomTextColor;
    private String mBoardStartColor;
    private String mBoardEndColor;
    private String mTopTextSuffix;

    public HalfDashBoardChart(Context context) {
        this(context, null);
    }

    public HalfDashBoardChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HalfDashBoardChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.HalfDashBoardChart, defStyleAttr, 0);
            this.mRadius = a.getDimensionPixelSize(R.styleable.HalfDashBoardChart_halfRadius, dip2px(80f));
            this.mPadding = a.getDimensionPixelSize(R.styleable.HalfDashBoardChart_halfPadding, dip2px(10f));
            this.mDashLength = a.getDimensionPixelSize(R.styleable.HalfDashBoardChart_halfDashLength, dip2px(10f));
            this.mMaxVal = a.getFloat(R.styleable.HalfDashBoardChart_halfMaxValue, 100f);
            this.mMinVal = a.getFloat(R.styleable.HalfDashBoardChart_halfMinValue, 0f);
            this.mScale = a.getInteger(R.styleable.HalfDashBoardChart_halfScale, 1);
            this.mShowText = a.getBoolean(R.styleable.HalfDashBoardChart_halfShowText, false);
            this.mTopTextColor = a.getColor(R.styleable.HalfDashBoardChart_halfTopTextColor, Color.parseColor("#666666"));
            this.mTopTextSize = a.getDimensionPixelSize(R.styleable.HalfDashBoardChart_halfTopTextSize, sp2px(36f));
            this.mBottomTextColor = a.getColor(R.styleable.HalfDashBoardChart_halfBottomTextColor, Color.parseColor("#939fbe"));
            this.mBoardStartColor = a.getString(R.styleable.HalfDashBoardChart_halfBoardStartColor);
            if (TextUtils.isEmpty(this.mBoardStartColor)) {
                this.mBoardStartColor = "#54c0fa";
            }
            this.mBoardEndColor = a.getString(R.styleable.HalfDashBoardChart_halfBoardEndColor);
            if (TextUtils.isEmpty(this.mBoardEndColor)) {
                this.mBoardEndColor = "#4d91fe";
            }
            this.mBottomTextSize = a.getDimensionPixelSize(R.styleable.HalfDashBoardChart_halfBottomTextSize, sp2px(14f));
            this.mTopTextSuffix = a.getString(R.styleable.HalfDashBoardChart_halfTopTextSuffix);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        init();
    }

    private Paint mPaint;
    private Paint mPaintSuffix;
    private TextPaint mPaintText;
    private TextPaint mPaintPercentText;
    private RectF mRectF;
    private float mFloatSweepAngle;
    private SpannableStringBuilder mSpannableStringBuilder;

    private void init() {
        mSpannableStringBuilder = new SpannableStringBuilder("");
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaintSuffix = new Paint();
        mPaintSuffix.setAntiAlias(true);
        mPaintSuffix.setTextSize(sp2px(15f));
        mPaintSuffix.setColor(mBottomTextColor);
        mRectF = new RectF();
        mPaintText = new TextPaint();
        mPaintText.setTextAlign(Paint.Align.LEFT);
        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setColor(Color.parseColor("#939fbe"));
        mPaintText.setTextSize(sp2px(10f));
        mPaintPercentText = new TextPaint();
        mPaintPercentText.setAntiAlias(true);
        mPaintPercentText.setStyle(Paint.Style.FILL);
        mPaintPercentText.setTextSize(sp2px(8f));
        mPaintPercentText.setTextAlign(Paint.Align.CENTER);
        mPaintPercentText.setColor(Color.WHITE);
        mFloatSweepAngle = 0;
    }

    public void setPercent(@FloatRange(from = 0f, to = 100f) float percent) {
        float last = mFloatSweepAngle;
        mFloatSweepAngle = percent / 100 * 180f;
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(last, percent / 100 * 180f);
        progressAnimator.setDuration((long) (Math.abs(last - (percent / 100 * 180f)) / 180 * 1000));
        progressAnimator.setInterpolator(new FastOutSlowInInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                BigDecimal bg = new BigDecimal((Float) animation.getAnimatedValue());
                mFloatSweepAngle = bg.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }

    public void setTopText(String topText) {
        mTopText = TextUtils.isEmpty(topText) ? "" : topText;
//        initSpannableString(mTopText);
        invalidate();
    }

    private void initSpannableString(String topText) {
        mSpannableStringBuilder = new SpannableStringBuilder();
        mSpannableStringBuilder.append(topText);
        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(dip2px(15f)), mSpannableStringBuilder.length() - 1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new RelativeSizeSpan(0.5f), mSpannableStringBuilder.length()-1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new SubscriptSpan(), mSpannableStringBuilder.length()-1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpannableStringBuilder.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), mSpannableStringBuilder.length() - 1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(dip2px(20f)), 0, mSpannableStringBuilder.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(mBottomTextColor), mSpannableStringBuilder.length() - 1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        StaticLayout staticLayoutPercent = new StaticLayout(mSpannableStringBuilder, mPaintText, 1000, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
    }

    public void setBottomText(String bottomText) {
        mBottomText = TextUtils.isEmpty(bottomText) ? "" : bottomText;
        invalidate();
    }

    Shader mShader;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
        mPaint.setColor(Color.parseColor("#E6E7E8"));
        mPaint.setStrokeWidth(dip2px(6f));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, 179, 182, false, mPaint);
        mPaint.setStrokeWidth(dip2px(6f));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShader(mShader);
        canvas.rotate(179, mCenterX, mCenterY);
        canvas.drawArc(mRectF, 0, mFloatSweepAngle, false, mPaint);//绘制进度
        canvas.rotate(181, mCenterX, mCenterY);
        mPaint.setShader(null);
        float[] coordinates = CalcUtil.circleTheCoordinatesOfThePoint(mCenterX, mCenterY, mRadius, mFloatSweepAngle - 180);
        float x1 = coordinates[0];
        float y1 = coordinates[1];
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(dip2px(2f));
        canvas.drawCircle(x1, y1, dip2px(6f), mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(ColorUtil.getCurrentColor(mBoardStartColor, mBoardEndColor, mFloatSweepAngle / 180 * 100f)));
        canvas.drawCircle(x1, y1, dip2px(6f), mPaint);
        int smallRadius = mRadius - dip2px(16f);
        mRectF.set(mCenterX - smallRadius, mCenterY - smallRadius, mCenterX + smallRadius, mCenterY + smallRadius);
        mPaint.setStrokeWidth(dip2px(4f));
        mPaint.setColor(Color.parseColor("#E8E8E8"));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, 179, 182, false, mPaint);
//        canvas.drawLine(0,mCenterY,getWidth(),mCenterY,mPaint);
        int startX = mCenterX - mRadius + dip2px(16f);
        int startY = mCenterY;
        mPaint.setColor(Color.parseColor("#E8E8E8"));
        mPaint.setStrokeWidth(dip2px(1f));
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 61; i++) {
            canvas.drawLine(startX, startY, startX + mDashLength, startY, mPaint);
            canvas.rotate(3f, mCenterX, mCenterY);
        }
        canvas.rotate(177, mCenterX, mCenterY);
        mPaintText.setColor(mBottomTextColor);
        mPaintText.setTextSize(mBottomTextSize);
        float bottomTextHeight = Math.abs(mPaintText.getFontMetrics().top);
        canvas.drawText(mBottomText, mCenterX - mPaintText.measureText(mBottomText) / 2, mCenterY, mPaintText);
        mPaintText.setTextSize(mTopTextSize);
        mPaintText.setColor(mTopTextColor);
        if (!TextUtils.isEmpty(mTopTextSuffix)) {
            float textTopLen = mPaintText.measureText(mTopText);
            float textTopSuffix = mPaintSuffix.measureText(mTopTextSuffix);
            float textLen = textTopLen + textTopSuffix;
            float textTopY = mCenterY - bottomTextHeight - dip2px(10f) - (Math.abs(mPaintText.getFontMetrics().bottom - Math.abs(mPaintText.getFontMetrics().descent)));
            float textTopSuffixY = mCenterY - bottomTextHeight - dip2px(10f) - (Math.abs(mPaintSuffix.getFontMetrics().bottom - Math.abs(mPaintSuffix.getFontMetrics().descent)));
            canvas.drawText(mTopText, mCenterX - textLen / 2, textTopY, mPaintText);
            canvas.drawText(mTopTextSuffix, mCenterX + (textLen / 2 - textTopSuffix), textTopSuffixY, mPaintSuffix);
        } else {
            float textTopLen = mPaintText.measureText(mTopText);
            float textTopY = mCenterY - bottomTextHeight - dip2px(10f) - (Math.abs(mPaintText.getFontMetrics().bottom - Math.abs(mPaintText.getFontMetrics().descent)));
            canvas.drawText(mTopText, mCenterX - textTopLen / 2, textTopY, mPaintText);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        mShader = new LinearGradient(mCenterX - mRadius, mCenterY, mCenterX + mRadius, mCenterY, Color.parseColor(mBoardEndColor), Color.parseColor(mBoardStartColor), Shader.TileMode.CLAMP);
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int viewWidth;
        if (specMode == MeasureSpec.EXACTLY) {
            viewWidth = specSize;
        } else {
            viewWidth = dip2px(250f);
            if (specMode == MeasureSpec.AT_MOST) {
                viewWidth = Math.max(viewWidth, specSize);
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
            viewHeight = mRadius + mPadding + dip2px(15f);
            if (specMode == MeasureSpec.AT_MOST) {
                viewHeight = Math.min(viewHeight, specSize);
            }
        }
//        Log.d("View Height", viewHeight + "");
        mCenterY = mRadius + mPadding;
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
