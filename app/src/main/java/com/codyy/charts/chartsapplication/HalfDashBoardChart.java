package com.codyy.charts.chartsapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lijian on 2018/2/26.
 */

public class HalfDashBoardChart extends View {
    private int mRadius;
    private int mPadding;
    private int mDashLength;
    private int mCenterX;
    private int mCenterY;

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
    private TextPaint  mPaintText;
    private TextPaint  mPaintTextSmall;
    private RectF mRectF;
private StaticLayout mStaticLayout;
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRectF = new RectF();
        mPaintText=new TextPaint();
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setColor(Color.parseColor("#989EBD"));
        mPaintText.setTextSize(sp2px(10f));
        mPaintTextSmall=new TextPaint();
        mPaintTextSmall.setAntiAlias(true);
        mPaintTextSmall.setStyle(Paint.Style.FILL);
        mPaintTextSmall.setTextSize(sp2px(10f));
        mPaintTextSmall.setTextAlign(Paint.Align.RIGHT);
        mPaintTextSmall.setColor(Color.parseColor("#57C0FA"));
        mStaticLayout = new StaticLayout("设备\n在线率", mPaintTextSmall, 500, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
    }

    SweepGradient mShader;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
        mPaint.setColor(Color.parseColor("#E8E8E8"));
        mPaint.setStrokeWidth(15f);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, 179, 182, false, mPaint);//小弧形
        mPaint.setStrokeWidth(15f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShader(mShader);
        canvas.rotate(179, mCenterX, mCenterY);
        canvas.drawArc(mRectF, 0, 180, false, mPaint);//小弧形
        canvas.rotate(181, mCenterX, mCenterY);
        mPaint.setShader(null);
        float x1 = (float) (mCenterX + mRadius * Math.cos(0 * Math.PI / 180));
        float y1 = (float) (mCenterY + mRadius * Math.sin(0 * Math.PI / 180));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5f);
        canvas.drawCircle(x1, y1, 15, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#57C0FA"));
        canvas.drawCircle(x1, y1, 15, mPaint);
        int smallRadius = mRadius - mPadding;
        mRectF.set(mCenterX - smallRadius, mCenterY - smallRadius, mCenterX + smallRadius, mCenterY + smallRadius);
        mPaint.setStrokeWidth(10f);
        mPaint.setColor(Color.parseColor("#E8E8E8"));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, 179, 182, false, mPaint);
//        canvas.drawLine(0,mCenterY,getWidth(),mCenterY,mPaint);
        int startX = mCenterX - mRadius + mPadding;
        int startY = mCenterY;
        mPaint.setColor(Color.parseColor("#E8E8E8"));
        mPaint.setStrokeWidth(2f);
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 61; i++) {
            canvas.drawLine(startX, startY, startX + mDashLength, startY, mPaint);
            canvas.rotate(3f, mCenterX, mCenterY);
        }
        canvas.rotate(177, mCenterX, mCenterY);
        canvas.drawText("设备总量",mCenterX,mCenterY,mPaintText);
        mPaintText.setTextSize(sp2px(22f));
        mPaintText.setColor(Color.parseColor("#666666"));
        canvas.drawText("23435",mCenterX,mCenterY-sp2px(18f),mPaintText);
        mPaintText.setTextSize(sp2px(10f));
        mPaintText.setColor(Color.parseColor("#666666"));
        canvas.save();
        canvas.translate(mCenterX-mRadius-sp2px(5f),mCenterY-sp2px(20f));
        mStaticLayout.draw(canvas);
        canvas.restore();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("mRadius", mRadius + "");
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        mShader = new SweepGradient(mCenterX, mCenterY, new int[]{Color.parseColor("#57C0FA"), Color.parseColor("#1209FC")}, null);
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
            mViewHeight = mRadius + 2 * mPadding;
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
    public int sp2px(float sp){
        final float scale=getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp*scale+0.5f);
    }
}
