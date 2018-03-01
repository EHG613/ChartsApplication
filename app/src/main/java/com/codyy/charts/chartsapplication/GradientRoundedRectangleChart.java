package com.codyy.charts.chartsapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lijian on 2018/2/28.
 */

public class GradientRoundedRectangleChart extends View {
    private int startColor;
    private int endColor;
    private String mBottomText;
    private String mTopText;

    public GradientRoundedRectangleChart(Context context) {
        this(context, null);
    }

    public GradientRoundedRectangleChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientRoundedRectangleChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientRoundedRectangleChart, defStyleAttr, 0);
        startColor = typedArray.getColor(R.styleable.GradientRoundedRectangleChart_gradientRoundedRectangleStartColor, Color.parseColor("#FD6097"));
        endColor = typedArray.getColor(R.styleable.GradientRoundedRectangleChart_gradientRoundedRectangleEndColor, Color.parseColor("#FDA571"));
        mBottomText = typedArray.getString(R.styleable.GradientRoundedRectangleChart_gradientRoundedRectangleBottomText);
        typedArray.recycle();
        init();
    }

    private Paint mPaint;
    private RectF mRectF;
    private TextPaint mTextPaintTop;
    private TextPaint mTextPaintBottom;

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRectF = new RectF();
        mTextPaintBottom = new TextPaint();
        mTextPaintBottom.setAntiAlias(true);
        mTextPaintBottom.setColor(Color.WHITE);
        mTextPaintBottom.setTextAlign(Paint.Align.CENTER);
        mTextPaintBottom.setTextSize(dip2px(12f));
        mTextPaintTop = new TextPaint();
        mTextPaintTop.setAntiAlias(true);
        mTextPaintTop.setColor(Color.WHITE);
        mTextPaintTop.setTextAlign(Paint.Align.CENTER);
        mTextPaintTop.setTextSize(dip2px(18f));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.left = 0;
        mRectF.top = 0;
        mRectF.right = getWidth();
        mRectF.bottom = getHeight();
        mPaint.setShader(mShader);
        canvas.drawRoundRect(mRectF, dip2px(5f), dip2px(5f), mPaint);
        canvas.drawText(mBottomText, mCenterX, mCenterY + dip2px(18f), mTextPaintBottom);
        if (!TextUtils.isEmpty(mTopText)) {
//            mTextPaintTop.setShadowLayer(2,2,2,Color.WHITE);
//            setLayerType(LAYER_TYPE_SOFTWARE,mTextPaintTop);
            canvas.drawText(mTopText, mCenterX, mCenterY - dip2px(2f), mTextPaintTop);
        }
    }

    public void setTopText(String topText) {
        mTopText = topText;
        invalidate();
    }

    Shader mShader;

    //    x3=（x1ctgA2+x2ctgA1+y2-y1）/（ctgA1+ctgA2）
//    y3=（y1ctgA2+y2ctgA1+x1-x2）/（ctgA1+ctgA2）
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
//        1/Math.tan(theta)
//        mShader = new LinearGradient(0 + getMeasuredWidth() / 4, mCenterY - getMeasuredWidth() / 2, getMeasuredWidth() - getMeasuredWidth() / 4, getMeasuredHeight() + getMeasuredWidth() / 2, startColor, endColor, Shader.TileMode.CLAMP);
//        mShader = new LinearGradient(0, 0, getMeasuredWidth(), getMeasuredHeight(), startColor, endColor, Shader.TileMode.CLAMP);
        CalcUtil.Circle circle = new CalcUtil.Circle(mCenterX, mCenterY, (float) CalcUtil.lineSpace(mCenterX, mCenterY, mCenterX * 2, 0));
        CalcUtil.Point point = circle.computeCoordinates((CalcUtil.calcAngle(mCenterX, mCenterY, mCenterX * 2, 0, mCenterX * 2, mCenterY)[1] + 90) * -1);
        CalcUtil.Point point2 = circle.computeCoordinates(90-CalcUtil.calcAngle(mCenterX, mCenterY, mCenterX * 2, 0, mCenterX * 2, mCenterY)[1]);
        mShader = new LinearGradient(point.x, point.y, point2.x,point2.y, startColor, endColor, Shader.TileMode.CLAMP);
//        float x1=0,y1=0,x2=mCenterX,y2=mCenterY;
//        int A1=15,A2=75;
//        float ctg= (float) (1f/Math.tan(15f));
//        float x3 =(x1*ctg*A2 + x2*ctg*A1 + y2 - y1)/(ctg*A1 + ctg*A2);
//        float y3 =(y1*ctg*A2 + y2*ctg*A1 + x1 - x2)/(ctg*A1 + ctg*A2);
//        x1=getMeasuredWidth();y1=getMeasuredHeight();
//        float x4 =(x1*ctg*A2 + x2*ctg*A1 + y2 - y1)/(ctg*A1 + ctg*A2);
//        float y4 =(y1*ctg*A2 + y2*ctg*A1 + x1 - x2)/(ctg*A1 + ctg*A2);
//        mShader = new LinearGradient(x3,y3,x4,y4, startColor, endColor, Shader.TileMode.CLAMP);
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
