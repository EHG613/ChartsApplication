package com.codyy.mobile.support.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijian on 2018/3/5.
 */

public class DonutsChart extends View {
    public DonutsChart(Context context) {
        this(context, null);
    }

    public DonutsChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DonutsChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DonutsChart, defStyleAttr, 0);
        mRadius = array.getDimensionPixelSize(R.styleable.DonutsChart_donutsRadius, dip2px(50f));
        mRadiusStrokeWidth = array.getDimensionPixelSize(R.styleable.DonutsChart_donutsRadiusStrokeWidth, 1);
        mRadiusInner = array.getDimensionPixelSize(R.styleable.DonutsChart_donutsRadiusInnerCircle, 0);
        mRadiusInnerStrokeWidth = array.getDimensionPixelSize(R.styleable.DonutsChart_donutsRadiusInnerCircleStrokeWidth, dip2px(1f));
        mRadiusInnerStyle = array.getInt(R.styleable.DonutsChart_donutsRadiusInnerCircleStyle, 1);
        mRadiusOuter = array.getDimensionPixelSize(R.styleable.DonutsChart_donutsRadiusOuterCircle, 0);
        mRadiusOuterPoint = array.getDimensionPixelSize(R.styleable.DonutsChart_donutsRadiusOuterCirclePoint, dip2px(2f));
        mRadiusOuterStrokeWidth = array.getDimensionPixelSize(R.styleable.DonutsChart_donutsRadiusOuterCircleStrokeWidth, dip2px(1f));
        mRadiusOuterStyle = array.getInt(R.styleable.DonutsChart_donutsRadiusOuterCircleStyle, 1);
        mRadiusOuterColor = array.getColor(R.styleable.DonutsChart_donutsRadiusOuterCircleColor, Color.BLACK);
        mRadiusOuterPointColor = array.getColor(R.styleable.DonutsChart_donutsRadiusOuterCirclePointColor, Color.parseColor("#35AEFE"));
        mRadiusInnerCircleShowDivider = array.getBoolean(R.styleable.DonutsChart_donutsRadiusInnerCircleShowDivider, false);
        mRadiusOuterCircleShowStroke = array.getBoolean(R.styleable.DonutsChart_donutsRadiusOuterCircleShowStroke, false);
        mRadiusOuterCircleShowPoint = array.getBoolean(R.styleable.DonutsChart_donutsRadiusOuterCircleShowPoint, false);
        mRadiusOuterCircleShowText = array.getBoolean(R.styleable.DonutsChart_donutsRadiusOuterCircleShowText, false);
        mRadiusShowOuterLineText = array.getBoolean(R.styleable.DonutsChart_donutsRadiusShowOuterLineText, false);
        array.recycle();
        init();
    }

    private boolean mRadiusShowOuterLineText;
    private boolean mRadiusInnerCircleShowDivider;
    private boolean mRadiusOuterCircleShowStroke;
    private boolean mRadiusOuterCircleShowPoint;
    private boolean mRadiusOuterCircleShowText;
    private float mRadius;
    private float mRadiusStrokeWidth;
    private float mRadiusInner;
    private float mRadiusInnerStrokeWidth;
    private int mRadiusInnerStyle;
    private float mRadiusOuter;
    private int mRadiusOuterColor;
    private int mRadiusOuterPointColor;
    private float mRadiusOuterPoint;
    private float mRadiusOuterStrokeWidth;
    private int mRadiusOuterStyle;
    private RectF mRectF;
    private Paint mPaint;
    private Paint mPaintLine;
    private TextPaint mTextPaint;

    private void init() {
        mPaint = new Paint();
        mPaintLine = new Paint();
        mPaintLine.setStrokeWidth(dip2px(1f));
        mPaintLine.setAntiAlias(true);
        mRectF = new RectF();
        mTextPaint = new TextPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    private List<Donuts> mDonuts = new ArrayList<>();

    public void setData(@NonNull List<Donuts> donuts) {
//        float total = 0;
//        for (Donuts f : donuts) {
//            total += f.getPercent();
//        }
//        if (new BigDecimal(total).setScale(2,BigDecimal.ROUND_FLOOR).floatValue() != 1f) throw new IllegalArgumentException("sum value !=1f,please check the list");
        mDonuts.clear();
        mDonuts.addAll(donuts);
        invalidate();
    }

    private List<Donuts> mDonutsInner = new ArrayList<>();

    public void setInnerData(@NonNull List<Donuts> donuts) {
//        float total = 0;
//        for (Donuts f : donuts) {
//            total += f.getPercent();
//        }
//        if (new BigDecimal(total).setScale(2,BigDecimal.ROUND_FLOOR).floatValue() != 1f) throw new IllegalArgumentException("sum value !=1f,please check the list");
        mDonutsInner.clear();
        mDonutsInner.addAll(donuts);
        invalidate();
    }

    private String centerText;
    private int centerTextColor = Color.BLACK;
    private float centerTextSize = 16f;
    private int outerTextColor = Color.BLACK;
    private float outerTextSize = 12f;

    public void setOuterTextColor(int outerTextColor) {
        this.outerTextColor = outerTextColor;
    }

    public void setOuterTextSize(float outerTextSize) {
        this.outerTextSize = outerTextSize;
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
        invalidate();
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
    }

    public void setCenterTextSize(float centerTextSize) {
        this.centerTextSize = centerTextSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mRadiusStrokeWidth);
        mPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setAntiAlias(true);
        mRectF.left = mCenterX - mRadius;
        mRectF.top = mCenterY - mRadius;
        mRectF.right = mCenterX + mRadius;
        mRectF.bottom = mCenterY + mRadius;
        float startAngle = drawDonutsArc(canvas);
        drawInnerWhiteCircle(canvas);//画白色内圆
        drawDonutsOuterStroke(canvas);//画甜甜圈外部的边框
        startAngle = drawDonutsOuterCirclePoint(canvas, startAngle);//绘制甜甜圈和外边框之间的点
        drawOuterText(canvas, startAngle);//绘制外部百分比文本
        if (mRadiusInner > 0) {
            float startInnerAngle = -90f;
            mRectF.left = mCenterX - mRadiusInner;
            mRectF.top = mCenterY - mRadiusInner;
            mRectF.right = mCenterX + mRadiusInner;
            mRectF.bottom = mCenterY + mRadiusInner;
            for (Donuts donut : mDonutsInner) {
                mPaint.setStyle(mRadiusInnerStyle == 1 ? Paint.Style.STROKE : Paint.Style.FILL);
                mPaint.setStrokeWidth(mRadiusInnerStrokeWidth);
                mPaint.setColor(donut.getColor());
                float sweepAngle = donut.getPercent() * 360;
                if (donut.getPercent() == 1f) {
                    canvas.drawCircle(mCenterX, mCenterY, mRadiusInner, mPaint);
                } else {
                    canvas.drawArc(mRectF, startInnerAngle, sweepAngle, mRadiusInnerStyle != 1, mPaint);
                }
                startInnerAngle += sweepAngle;
            }
            for (Donuts donut : mDonutsInner) {
                if (mDonutsInner.size()>1) {
                    mPaint.setStrokeWidth(dip2px(2f));
                    mPaint.setColor(Color.WHITE);
                    float[] coordinates = CalcUtil.circleTheCoordinatesOfThePoint(mCenterX, mCenterY, mRadiusInner, startInnerAngle);
                    canvas.drawLine(mCenterX, mCenterY, coordinates[0], coordinates[1], mPaint);
                }
                if (!TextUtils.isEmpty(donut.getInnerText())) {
                    mTextPaint.setColor(donut.getInnerTextColor());
                    mTextPaint.setTextSize(dip2px(donut.getInnerTextSize()));
                    if(mDonutsInner.size()==1){
                        canvas.drawText(donut.getInnerText(), mCenterX, mCenterY + dip2px(donut.getInnerTextSize()) / 4, mTextPaint);
                    }else {
                        float[] coordinatesText = CalcUtil.circleTheCoordinatesOfThePoint(mCenterX, mCenterY, mRadiusInner / 2, startInnerAngle + donut.getPercent() * 180);
                        canvas.drawText(donut.getInnerText(), coordinatesText[0], coordinatesText[1] + dip2px(donut.getInnerTextSize()) / 4, mTextPaint);
                    }
                }
                startInnerAngle += donut.getPercent() * 360;
            }
        }
        if (!TextUtils.isEmpty(centerText)) {
            mTextPaint.setColor(centerTextColor);
            mTextPaint.setTextSize(dip2px(centerTextSize));
            canvas.drawText(centerText, mCenterX, mCenterY + dip2px(centerTextSize) / 4, mTextPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                Log.d("坐标:", "圆心:" + mCenterX + "," + mCenterY + ",半径:" + mRadius + "点:" + event.getX() + "," + event.getY());
                float angle = CalcUtil.calcAngleOnArc(mCenterX, mCenterY, mRadius, mRadius - mRadiusStrokeWidth, event.getX(), event.getY());
                if (!(angle < 0)) {
                    for (Donuts donut : mDonuts) {
                        if (donut.getStartAngle() <= angle && angle < donut.getEndAngle() && mOnClickListener != null) {
                            mOnClickListener.onClick(donut);
                        }
                    }
                }
                for (Donuts donut : mDonuts) {
                    if (event.getX() >= donut.getTextStartX() && event.getX() <= donut.getTextStopX() && event.getY() >= donut.getTextStartY() && event.getY() <= donut.getTextStopY() && mOnTextClickListener != null) {
                        mOnTextClickListener.onClick(donut);
                    }
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(Donuts donuts);
    }

    private OnTextClickListener mOnTextClickListener;

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        mOnTextClickListener = onTextClickListener;
    }

    public interface OnTextClickListener {
        void onClick(Donuts donuts);
    }

    private void drawOuterText(Canvas canvas, float startAngle) {
        if (mRadiusOuterCircleShowText) {
            for (Donuts donut : mDonuts) {
                float[] coordinates = CalcUtil.circleTheCoordinatesOfThePoint(mCenterX, mCenterY, mRadiusOuter + dip2px(4f), startAngle + donut.getPercent() * 180);
                mTextPaint.setColor(outerTextColor);
//                mTextPaint.setStyle(Paint.Style.FILL);
                mTextPaint.setTextSize(dip2px(outerTextSize));
                if (coordinates[0] > mCenterX) {
                    mTextPaint.setTextAlign(Paint.Align.LEFT);
                } else if (coordinates[0] < mCenterX) {
                    mTextPaint.setTextAlign(Paint.Align.RIGHT);
                } else {
                    mTextPaint.setTextAlign(Paint.Align.CENTER);
                    if (coordinates[1] > mCenterY) {
                        coordinates[1] += Math.abs(mTextPaint.getFontMetrics().top);
                    } else {
                        coordinates[1] -= Math.abs(mTextPaint.getFontMetrics().bottom);
                    }
                }
                canvas.drawText(donut.getPercent(1) + "%", coordinates[0], coordinates[1], mTextPaint);
                startAngle += donut.getPercent() * 360;
                mTextPaint.setTextAlign(Paint.Align.CENTER);
            }
        }
        canvas.restore();
    }

    private float drawDonutsOuterCirclePoint(Canvas canvas, float startAngle) {
        if (mRadiusOuterCircleShowPoint) {
            for (Donuts donut : mDonuts) {
                float[] coordinates = CalcUtil.circleTheCoordinatesOfThePoint(mCenterX, mCenterY, (mRadius + mRadiusOuter) / 2, startAngle + donut.getPercent() * 180);
                mPaint.setColor(mRadiusOuterPointColor);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(coordinates[0], coordinates[1], mRadiusOuterPoint, mPaint);
                startAngle += donut.getPercent() * 360;
            }
        }
        return startAngle;
    }

    private void drawDonutsOuterStroke(Canvas canvas) {
        if (mRadiusOuterCircleShowStroke) {
            mPaint.setColor(mRadiusOuterColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mRadiusOuterStrokeWidth);
            canvas.drawCircle(mCenterX, mCenterY, mRadiusOuter, mPaint);
        }
    }

    private float drawDonutsArc(Canvas canvas) {
        float startAngle = -90f;
        for (int i = 0; i < mDonuts.size(); i++) {
            Donuts donut = mDonuts.get(i);
            mPaint.setColor(donut.getColor());
            float sweepAngle = donut.getPercent() * 360;
            mDonuts.get(i).setStartAngle(startAngle + 90f);
            mDonuts.get(i).setEndAngle(startAngle + 90f + sweepAngle);
            canvas.drawArc(mRectF, startAngle, sweepAngle, true, mPaint);
            if (mRadiusShowOuterLineText) {
                float[] coordinates = CalcUtil.circleTheCoordinatesOfThePoint(mCenterX, mCenterY, mRadius + dip2px(24f), startAngle + donut.getPercent() * 180);
                float[] coordinates1 = CalcUtil.circleTheCoordinatesOfThePoint(mCenterX, mCenterY, mRadius, startAngle + donut.getPercent() * 180);
                mPaintLine.setColor(donut.getColor());
                canvas.drawLine(coordinates1[0], coordinates1[1], coordinates[0], coordinates[1], mPaintLine);
                mTextPaint.setColor(donut.getColor());
                mTextPaint.setTextSize(dip2px(outerTextSize));
                if ((startAngle + donut.getPercent() * 180) / 90 > 1 && (startAngle + donut.getPercent() * 180) / 90 < 3) {//象限为负
                    canvas.drawLine(coordinates[0], coordinates[1], coordinates[0] - dip2px(10f), coordinates[1], mPaintLine);
                    canvas.drawText(donut.getPercent(1) + "%", coordinates[0] - mTextPaint.measureText(donut.getPercent(1) + "%"), coordinates[1]+(Math.abs(mTextPaint.getFontMetrics().descent)), mTextPaint);
                    if (!TextUtils.isEmpty(donut.getPercentTopText()))
                        canvas.drawText(donut.getPercentTopText(), coordinates[0] -mTextPaint.measureText(donut.getPercent(1) + "%"), coordinates[1]-Math.abs(mTextPaint.getFontMetrics().ascent), mTextPaint);
                    mDonuts.get(i).setTextStartX(coordinates[0] - dip2px(16f) - mTextPaint.measureText(donut.getPercent(1) + "%"));
                    mDonuts.get(i).setTextStopX(coordinates[0] - dip2px(16f));
                    mDonuts.get(i).setTextStartY(coordinates[1] - mTextPaint.getTextSize());
                    mDonuts.get(i).setTextStopY(coordinates[1] + dip2px(5f));
//                    canvas.drawRect(mDonuts.get(i).getTextStartX(), mDonuts.get(i).getTextStartY(), mDonuts.get(i).getTextStopX(), mDonuts.get(i).getTextStopY(), mPaint);
                } else {//象限为正
                    canvas.drawLine(coordinates[0], coordinates[1], coordinates[0] + dip2px(10f), coordinates[1], mPaintLine);
                    canvas.drawText(donut.getPercent(1) + "%", coordinates[0] + mTextPaint.measureText(donut.getPercent(1) + "%"), coordinates[1]+(Math.abs(mTextPaint.getFontMetrics().descent)), mTextPaint);
                    if (!TextUtils.isEmpty(donut.getPercentTopText()))
                        canvas.drawText(donut.getPercentTopText(), coordinates[0] + mTextPaint.measureText(donut.getPercent(1) + "%"), coordinates[1]-Math.abs(mTextPaint.getFontMetrics().ascent), mTextPaint);
                    mDonuts.get(i).setTextStartX(coordinates[0] + dip2px(16f));
                    mDonuts.get(i).setTextStopX(coordinates[0] + dip2px(16f) + mTextPaint.measureText(donut.getPercent(1) + "%"));
                    mDonuts.get(i).setTextStartY(coordinates[1] - mTextPaint.getTextSize());
                    mDonuts.get(i).setTextStopY(coordinates[1] + dip2px(5f));
//                    canvas.drawRect(mDonuts.get(i).getTextStartX(), mDonuts.get(i).getTextStartY(), mDonuts.get(i).getTextStopX(), mDonuts.get(i).getTextStopY(), mPaint);
                }
            }
            startAngle += sweepAngle;
        }
        canvas.save();
        return startAngle;
    }

    private void drawInnerWhiteCircle(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenterX, mCenterY, mRadius - mRadiusStrokeWidth, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
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
            if (mRadiusShowOuterLineText) {
                viewWidth = (int) (mRadius * 3);
            } else {
                viewWidth = (int) ((mRadiusOuter == 0f ? mRadius : mRadiusOuter) * 2 + dip2px(mRadiusOuterCircleShowText ? 25f : 10f));
            }
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
            if (mRadiusShowOuterLineText) {
                viewHeight = (int) (mRadius * 3);
            } else {
                viewHeight = (int) ((mRadiusOuter == 0f ? mRadius : mRadiusOuter) * 2 + dip2px(mRadiusOuterCircleShowText ? 25f : 10f));
            }
            if (specMode == MeasureSpec.AT_MOST) {
                viewHeight = Math.min(viewHeight, specSize);
            }
        }
        mCenterY = viewHeight / 2;
        return viewHeight + dip2px(4f);
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
