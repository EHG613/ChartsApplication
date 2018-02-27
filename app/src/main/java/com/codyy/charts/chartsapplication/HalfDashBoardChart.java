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
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;
import java.text.NumberFormat;

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

    public HalfDashBoardChart(Context context) {
        this(context, null);
    }

    public HalfDashBoardChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HalfDashBoardChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("chart", "start init");
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.HalfDashBoardChart, defStyleAttr, 0);
            this.mRadius = a.getDimensionPixelSize(R.styleable.HalfDashBoardChart_halfRadius, dip2px(80f));
            this.mPadding = a.getDimensionPixelSize(R.styleable.HalfDashBoardChart_halfPadding, dip2px(10f));
            this.mDashLength = a.getDimensionPixelSize(R.styleable.HalfDashBoardChart_halfDashLength, dip2px(10f));
            this.mMaxVal = a.getFloat(R.styleable.HalfDashBoardChart_halfMaxValue, 100f);
            this.mMinVal = a.getFloat(R.styleable.HalfDashBoardChart_halfMinValue, 0f);
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
    private Path mPathTip;
    private float mFloatSweepAngle;

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRectF = new RectF();
        mPaintText = new TextPaint();
        mPaintText.setTextAlign(Paint.Align.CENTER);
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
        mPaintPercentText.setTextSize(sp2px(10f));
        mPaintPercentText.setTextAlign(Paint.Align.CENTER);
        mPaintPercentText.setColor(Color.WHITE);
        mPathTip = new Path();
        mPaintTip = new Paint();
        mPaintTip.setStyle(Paint.Style.FILL);
        mPaintTip.setAntiAlias(true);
        mPaintTip.setColor(Color.parseColor("#929FBE"));
        mFloatSweepAngle = 90;
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
        progressAnimator.setDuration(600L);
//        progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                BigDecimal bg = new BigDecimal((Float) animation.getAnimatedValue());
                mFloatSweepAngle = bg.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                mBigDecimal = new BigDecimal(mFloatSweepAngle / 180 * 100);
                Log.d("value", mFloatSweepAngle + "");
                mPathTip.reset();
                invalidate();
            }
        });
        progressAnimator.start();
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
        canvas.drawText("设备总量", mCenterX, mCenterY, mPaintText);
        mPaintText.setTextSize(sp2px(22f));
        mPaintText.setColor(Color.parseColor("#666666"));
        canvas.drawText("23435", mCenterX, mCenterY - sp2px(18f), mPaintText);
        mPaintText.setTextSize(sp2px(10f));
        mPaintText.setColor(Color.parseColor("#666666"));
        canvas.save();
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

        canvas.drawText(mBigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue() + "%", x2, y2, mPaintPercentText);

    }

    private BigDecimal mBigDecimal;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("mRadius", mRadius + "");
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
//        mShader = new SweepGradient(mCenterX, mCenterY, new int[]{Color.parseColor("#57C0FA"), Color.parseColor("#1209FC")}, null);
        mShader = new LinearGradient(mCenterX - mRadius, mCenterY, mCenterX + mRadius, mCenterY, Color.parseColor("#1209FC"), Color.parseColor("#57C0FA"), Shader.TileMode.CLAMP);
    }

    private int mViewWidth; // 控件宽度
    private int mViewHeight; // 控件高度

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mViewWidth = specSize;
        } else {
            mViewWidth = dip2px(250f);
            if (specMode == MeasureSpec.AT_MOST) {
                mViewWidth = Math.max(mViewWidth, specSize);
            }
        }
        mCenterX = mViewWidth / 2;
        return mViewWidth;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mViewHeight = specSize;
        } else {
            mViewHeight = mRadius + mPadding + dip2px(10f);
            if (specMode == MeasureSpec.AT_MOST) {
                mViewHeight = Math.min(mViewHeight, specSize);
            }
        }
        Log.d("View Height", mViewHeight + "");
        mCenterY = mRadius + mPadding;
        return mViewHeight;
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