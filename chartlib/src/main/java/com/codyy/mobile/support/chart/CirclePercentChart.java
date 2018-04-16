package com.codyy.mobile.support.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.FloatRange;
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

/**
 * Created by lijian on 2018/2/28.
 */

public class CirclePercentChart extends View {
    private int startColor;
    private int endColor;
    private String mBottomText;
    private String mTopText;
    private int style;
    private int mTopTextSize;
    private int mTopTextColor;
    private int mBottomTextSize;
    private int mBottomTextColor;


    public CirclePercentChart(Context context) {
        this(context, null);
    }

    public CirclePercentChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePercentChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclePercentChart, defStyleAttr, 0);
        startColor = typedArray.getColor(R.styleable.CirclePercentChart_circlePercentStartColor, Color.parseColor("#FD6097"));
        endColor = typedArray.getColor(R.styleable.CirclePercentChart_circlePercentEndColor, Color.parseColor("#FDA571"));
        mBottomText = typedArray.getString(R.styleable.CirclePercentChart_circlePercentBottomText);
        style = typedArray.getInt(R.styleable.CirclePercentChart_circlePercentStyle, 0);
        mTopTextColor = typedArray.getColor(R.styleable.CirclePercentChart_circlePercentTopTextColor, Color.BLACK);
        mBottomTextColor = typedArray.getColor(R.styleable.CirclePercentChart_circlePercentBottomTextColor, Color.BLACK);
        mTopTextSize = typedArray.getDimensionPixelSize(R.styleable.CirclePercentChart_circlePercentTopTextSize, 15);
        mBottomTextSize = typedArray.getDimensionPixelSize(R.styleable.CirclePercentChart_circlePercentBottomTextSize, 10);
        typedArray.recycle();
        init();
    }

    private Paint mPaint;
    private Paint mPaintC;
    private RectF mRectF;
    private TextPaint mTextPaintBottom;
    private SpannableStringBuilder mSpannableStringBuilder;
    private StaticLayout mStaticLayoutPercent;
    private TextPaint mPaintPercentText;
    private TextPaint mTextPaintOuter;

    private void init() {
        mRectF = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(dip2px(5f));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#E6E7E8"));
        mPaintC = new Paint();
        mPaintC.setStrokeWidth(dip2px(5f));
        mPaintC.setAntiAlias(true);
        mPaintC.setStyle(Paint.Style.STROKE);
        mPaintC.setStrokeCap(Paint.Cap.ROUND);
        mTextPaintBottom = new TextPaint();
        mTextPaintBottom.setAntiAlias(true);
        mTextPaintBottom.setTextAlign(Paint.Align.CENTER);
        mPaintPercentText = new TextPaint();
        mPaintPercentText.setAntiAlias(true);
        mPaintPercentText.setStyle(Paint.Style.FILL);
        mPaintPercentText.setTextSize(sp2px(10f));
        mPaintPercentText.setTextAlign(Paint.Align.LEFT);
        mTextPaintOuter = new TextPaint();
        mTextPaintOuter.setAntiAlias(true);
        mTextPaintOuter.setTextSize(sp2px(14f));
        mTextPaintOuter.setTextAlign(Paint.Align.CENTER);
        mTextPaintOuter.setColor(Color.parseColor("#666666"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.left = mCenterX - mRadius;
        mRectF.top = mCenterY - mRadius;
        mRectF.right = mCenterX + mRadius;
        mRectF.bottom = mCenterY + mRadius;
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
        mTextPaintBottom.setTextSize(mBottomTextSize);
        mTextPaintBottom.setColor(mBottomTextColor);
        canvas.drawText(TextUtils.isEmpty(mBottomText) ? "" : mBottomText, mCenterX, mCenterY + dip2px(12f), mTextPaintBottom);
        mPaintC.setShader(mShader);
        canvas.drawArc(mRectF, -90, sweepAngle, false, mPaintC);
        if (!TextUtils.isEmpty(mTopText)) {
            canvas.save();
            if (style == 0) {
                canvas.translate(mCenterX - mPaintPercentText.measureText(mTopText) / 2.5f, mCenterY - sp2px(14f));
                mStaticLayoutPercent.draw(canvas);
                canvas.translate(-(mCenterX - mPaintPercentText.measureText(mTopText) / 2.5f), -(mCenterY - sp2px(14f)));
            } else {
                mTextPaintBottom.setTextSize(mTopTextSize);
                mTextPaintBottom.setColor(mTopTextColor);
                mPaintPercentText.setColor(mTopTextColor);
                float height = Math.abs(mTextPaintOuter.getFontMetrics().descent) + Math.abs(mTextPaintOuter.getFontMetrics().ascent);
                canvas.drawText(mTopText, mCenterX, mCenterY - mTopTextSize / 2, mTextPaintBottom);
                canvas.drawText(percent + "", mCenterX-mTextPaintOuter.measureText(percent + "")/2f, mCenterY - mRadius - dip2px(8f) - Math.abs(mTextPaintOuter.getFontMetrics().descent), mTextPaintOuter);
                canvas.drawText(minutes, mCenterX-mTextPaintOuter.measureText(minutes)/2f, mCenterY + mRadius + dip2px(8f) + Math.abs(mTextPaintOuter.getFontMetrics().ascent), mTextPaintOuter);
                canvas.drawText("%", mCenterX + dip2px(2f), mCenterY - mRadius - dip2px(8f) - Math.abs(mPaintPercentText.getFontMetrics().bottom), mPaintPercentText);
                canvas.drawText("min", mCenterX + dip2px(2f), mCenterY + mRadius + dip2px(8f) + (height - Math.abs(mPaintPercentText.getFontMetrics().bottom)), mPaintPercentText);
            }
            canvas.restore();
        }
    }

    private float sweepAngle;

    public void setPercent(@FloatRange(from = 0f, to = 100f) float percent) {
        sweepAngle = percent / 100 * 360;
        setTopText(percent + "%");
    }

    private float percent;

    public void setPercent(@FloatRange(from = 0f, to = 100f) float percent, String topText) {
        this.percent = percent;
        sweepAngle = percent / 100f * 360f;
        setTopText(topText);
    }

    private String minutes = "";

    public void setPercent(@FloatRange(from = 0f, to = 100f) float percent, String topText, String minutes) {
        this.minutes = minutes;
        this.percent = percent;
        sweepAngle = percent / 100f * 360f;
        setTopText(topText);
    }

    private void setTopText(String topText) {
        mTopText = TextUtils.isEmpty(topText) ? "" : topText;
        initSpannableString(mTopText);
        invalidate();
    }

    private void initSpannableString(String topText) {
        mSpannableStringBuilder = new SpannableStringBuilder();
        mSpannableStringBuilder.append(topText);
        if (style == 0) {
            mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(dip2px(10f)), mSpannableStringBuilder.length() - 1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new RelativeSizeSpan(0.5f), mSpannableStringBuilder.length()-1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new SubscriptSpan(), mSpannableStringBuilder.length()-1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mSpannableStringBuilder.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), mSpannableStringBuilder.length() - 1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(dip2px(20f)), 0, mSpannableStringBuilder.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mSpannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), mSpannableStringBuilder.length() - 1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mSpannableStringBuilder.setSpan(new ForegroundColorSpan(mTopTextColor), 0, mSpannableStringBuilder.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mStaticLayoutPercent = new StaticLayout(mSpannableStringBuilder, mPaintPercentText, 1000, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }
    }

    private int mRadius;
    Shader mShader;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        if (style == 1) {
            measureHeight += (Math.abs(mTextPaintOuter.getFontMetrics().bottom) + Math.abs(mTextPaintOuter.getFontMetrics().top)) * 2;
            mCenterY = measureHeight / 2;
        }
        mRadius = Math.min(measureWidth, measureHeight) / 2 - dip2px(10f);
        setMeasuredDimension(measureWidth, measureHeight);
        mShader = new LinearGradient(mCenterX, mCenterY - mRadius, mCenterX, mCenterY + mRadius, startColor, endColor, Shader.TileMode.CLAMP);
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
