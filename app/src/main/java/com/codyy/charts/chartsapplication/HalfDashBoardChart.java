package com.codyy.charts.chartsapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
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
    private Paint mPaintTip;
    private TextPaint mPaintText;
    private TextPaint mPaintPercentText;
    private RectF mRectF;
    private StaticLayout mStaticLayout;
    private StaticLayout mStaticLayoutPercent;
    private Path mPathTip;
    private float mFloatSweepAngle;
    private SpannableStringBuilder mSpannableStringBuilder;

    private void init() {
        mSpannableStringBuilder = new SpannableStringBuilder("");
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRectF = new RectF();
        mPaintText = new TextPaint();
        mPaintText.setTextAlign(Paint.Align.LEFT);
        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setColor(Color.parseColor("#989EBD"));
        mPaintText.setTextSize(sp2px(10f));
        TextPaint paintTextSmall = new TextPaint();
        paintTextSmall.setAntiAlias(true);
        paintTextSmall.setStyle(Paint.Style.FILL);
        paintTextSmall.setTextSize(sp2px(10f));
        paintTextSmall.setTextAlign(Paint.Align.RIGHT);
        paintTextSmall.setColor(Color.parseColor("#57C0FA"));
        mStaticLayout = new StaticLayout("设备\n在线率", paintTextSmall, 500, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        mPaintPercentText = new TextPaint();
        mPaintPercentText.setAntiAlias(true);
        mPaintPercentText.setStyle(Paint.Style.FILL);
        mPaintPercentText.setTextSize(sp2px(8f));
        mPaintPercentText.setTextAlign(Paint.Align.CENTER);
        mPaintPercentText.setColor(Color.WHITE);
        mPathTip = new Path();
        mPaintTip = new Paint();
        mPaintTip.setStyle(Paint.Style.FILL);
        mPaintTip.setAntiAlias(true);
        mPaintTip.setColor(Color.parseColor("#929FBE"));
        mFloatSweepAngle = 0;
        mBigDecimal = new BigDecimal(mFloatSweepAngle / 180 * 100);
    }

    private ValueAnimator progressAnimator;

    public void setFloatSweepAngle(float floatSweepAngle) {
        if (floatSweepAngle < mMinVal) {
            floatSweepAngle = mMinVal;
        }
        if (floatSweepAngle > mMaxVal) {
            floatSweepAngle = mMaxVal;
        }
        float last = mFloatSweepAngle;
        mFloatSweepAngle = floatSweepAngle / 100 * 180f;
        progressAnimator = ValueAnimator.ofFloat(last, floatSweepAngle / 100 * 180f);
        progressAnimator.setDuration((long) (Math.abs(last - (floatSweepAngle / 100 * 180f)) / 180 * 1000));
//        progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                BigDecimal bg = new BigDecimal((Float) animation.getAnimatedValue());
                mFloatSweepAngle = bg.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                mBigDecimal = new BigDecimal(mFloatSweepAngle / 180 * 100);
//                Log.d("value", mFloatSweepAngle + "");
                mPathTip.reset();
                invalidate();
            }
        });
        progressAnimator.start();
    }

    public void setTopText(String topText) {
        mTopText = TextUtils.isEmpty(topText) ? "" : topText;
        initSpannableString(mTopText);
        invalidate();
    }

    private void initSpannableString(String topText) {
        mSpannableStringBuilder=new SpannableStringBuilder();
        mSpannableStringBuilder.append(topText);
        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(dip2px(10f)), mSpannableStringBuilder.length()-1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new RelativeSizeSpan(0.5f), mSpannableStringBuilder.length()-1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new SubscriptSpan(), mSpannableStringBuilder.length()-1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpannableStringBuilder.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), mSpannableStringBuilder.length()-1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(dip2px(20f)), 0, mSpannableStringBuilder.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), mSpannableStringBuilder.length()-1, mSpannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mStaticLayoutPercent = new StaticLayout(mSpannableStringBuilder, mPaintText, 1000, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
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
        mPaint.setColor(Color.parseColor("#E8E8E8"));
        mPaint.setStrokeWidth(15f);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, 179, 182, false, mPaint);
        mPaint.setStrokeWidth(15f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShader(mShader);
        canvas.rotate(179, mCenterX, mCenterY);
        canvas.drawArc(mRectF, 0, mFloatSweepAngle, false, mPaint);//绘制进度
        canvas.rotate(181, mCenterX, mCenterY);
        mPaint.setShader(null);
        float x1 = (float) (mCenterX + mRadius * Math.cos((mFloatSweepAngle - 180) * Math.PI / 180));
        float y1 = (float) (mCenterY + mRadius * Math.sin((mFloatSweepAngle - 180) * Math.PI / 180));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(6f);
        canvas.drawCircle(x1, y1, 15, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(ColorUtil.getCurrentColor("#57C0FA", "#1209FC", mFloatSweepAngle / 180 * 100f)));
        canvas.drawCircle(x1, y1, 15, mPaint);
        int smallRadius = mRadius - dip2px(10f);
        mRectF.set(mCenterX - smallRadius, mCenterY - smallRadius, mCenterX + smallRadius, mCenterY + smallRadius);
        mPaint.setStrokeWidth(10f);
        mPaint.setColor(Color.parseColor("#E8E8E8"));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, 179, 182, false, mPaint);
//        canvas.drawLine(0,mCenterY,getWidth(),mCenterY,mPaint);
        int startX = mCenterX - mRadius + dip2px(10f);
        int startY = mCenterY;
        mPaint.setColor(Color.parseColor("#E8E8E8"));
        mPaint.setStrokeWidth(2f);
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 61; i++) {
            canvas.drawLine(startX, startY, startX + mDashLength, startY, mPaint);
            canvas.rotate(3f, mCenterX, mCenterY);
        }
        canvas.rotate(177, mCenterX, mCenterY);
        canvas.drawText(mBottomText, mCenterX-mPaintText.measureText(mBottomText)/2, mCenterY, mPaintText);
        mPaintText.setTextSize(sp2px(22f));
        mPaintText.setColor(Color.parseColor("#666666"));
        if(mShowText) {
            canvas.drawText(mTopText, mCenterX-mPaintText.measureText(mTopText)/2, mCenterY - sp2px(18f), mPaintText);
        }else{
            canvas.translate(mCenterX-mPaintText.measureText(mTopText)/(mTopText.length()==4?2.5f:3f),mCenterY-sp2px(25f));
            mStaticLayoutPercent.draw(canvas);
            canvas.translate(-(mCenterX-mPaintText.measureText(mTopText)/(mTopText.length()==4?2.5f:3f)),-(mCenterY-sp2px(25f)));
        }
        mPaintText.setTextSize(sp2px(10f));
        mPaintText.setColor(Color.parseColor("#666666"));
        canvas.save();
        if (mShowText) {
            canvas.translate(mCenterX - mRadius - sp2px(5f), mCenterY - sp2px(20f));
            mStaticLayout.draw(canvas);
            canvas.translate(-(mCenterX - mRadius - sp2px(5f)), -(mCenterY - sp2px(20f)));
            canvas.restore();
            float x2 = (float) (mCenterX + (mRadius + dip2px((mFloatSweepAngle <= 60 || mFloatSweepAngle >= 120) ? 25f : 18f)) * Math.cos((mFloatSweepAngle - 180) * Math.PI / 180));
            float y2 = (float) (mCenterY + (mRadius + dip2px((mFloatSweepAngle <= 60 || mFloatSweepAngle >= 120) ? 25f : 18f)) * Math.sin((mFloatSweepAngle - 180) * Math.PI / 180));
            int len = dip2px(15f);
            mPathTip.moveTo(x2 - len, y2 - len / 2);
            mPathTip.lineTo(x2 + len, y2 - len / 2);
            mPathTip.lineTo(x2 + len, y2 + len / 2);
            mPathTip.lineTo(x2 + len / 3, y2 + len / 2);
            mPathTip.lineTo(x2, (float) (y2 + len / 2 * 1.5));
            mPathTip.lineTo(x2 - len / 3, y2 + len / 2);
            mPathTip.lineTo(x2 - len, y2 + len / 2);
            mPathTip.lineTo(x2 - len, y2 - len / 2);
            y2 += len / 4;
            canvas.drawPath(mPathTip, mPaintTip);
            canvas.drawText(mBigDecimal.setScale(mScale, BigDecimal.ROUND_DOWN).toString() + "%", x2, y2, mPaintPercentText);
        }

    }

    private BigDecimal mBigDecimal;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.d("mRadius", mRadius + "");
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
//        mShader = new SweepGradient(mCenterX, mCenterY, new int[]{Color.parseColor("#57C0FA"), Color.parseColor("#1209FC")}, null);
        mShader = new LinearGradient(mCenterX - mRadius, mCenterY, mCenterX + mRadius, mCenterY, Color.parseColor("#1209FC"), Color.parseColor("#57C0FA"), Shader.TileMode.CLAMP);
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
