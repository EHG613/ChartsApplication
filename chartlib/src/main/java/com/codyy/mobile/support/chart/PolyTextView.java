package com.codyy.mobile.support.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动计算文字宽度,自适应换行
 * Created by lijian on 2018/3/17.
 */

public class PolyTextView extends View {
    public PolyTextView(Context context) {
        this(context, null);
    }

    public PolyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PolyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PolyTextView, defStyleAttr, 0);
        columns = array.getInteger(R.styleable.PolyTextView_polyTextColumns, 2);
        textColor = array.getColor(R.styleable.PolyTextView_polyTextColor, Color.parseColor("#939fbe"));
        textSize = array.getDimensionPixelSize(R.styleable.PolyTextView_polyTextSize, sp2px(12f));
        textPadding = array.getDimensionPixelSize(R.styleable.PolyTextView_polyTextPadding, dip2px(8f));
        textOutPadding = array.getDimensionPixelSize(R.styleable.PolyTextView_polyTextOutPadding, dip2px(8f));
        textOrientation = array.getInt(R.styleable.PolyTextView_polyTextOrientation, 0);
        drawablePadding = array.getDimensionPixelSize(R.styleable.PolyTextView_polyTextDrawablePadding, dip2px(6f));
        showBackground = array.getBoolean(R.styleable.PolyTextView_polyTextShowBackground, false);
        backgroundColor = array.getColor(R.styleable.PolyTextView_polyTextBackgroundColor, Color.parseColor("#f6f6f6"));
        backgroundRadius = array.getDimensionPixelSize(R.styleable.PolyTextView_polyTextBackgroundRadius, dip2px(4f));
        array.recycle();
        circleRadius = dip2px(4f);
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeWidth(1f);
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setColor(textColor);
        textTop = Math.abs(mTextPaint.getFontMetrics().top);
        textBottom = Math.abs(mTextPaint.getFontMetrics().bottom);
        textHeight = textTop + textBottom;
        mRectF=new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(showBackground){
            mRectF.left=0;
            mRectF.top=0;
            mRectF.right=width;
            mRectF.bottom=height;
            mCirclePaint.setColor(backgroundColor);
            canvas.drawRoundRect(mRectF,backgroundRadius,backgroundRadius,mCirclePaint);
        }
        for (PolyText text : mPolyTexts) {
            mCirclePaint.setColor(text.getCircleColor());
            canvas.drawCircle(text.getCircleCx(), text.getCircleCy(), circleRadius, mCirclePaint);
            canvas.drawText(text.getText(), text.getTextStartX(), text.getTextBaseLineY(), mTextPaint);
            canvas.drawText(text.getTextSecond(), text.getTextSecondStartX(), text.getTextBaseLineY(), mTextPaint);
        }
    }

    private boolean showBackground;
    private float backgroundRadius;
    private int backgroundColor;
    private float circleRadius;
    private float textHeight;
    private float textTop;
    private float textBottom;
    private int drawablePadding;
    private int textOrientation;
    private Paint mCirclePaint;
    private TextPaint mTextPaint;
    private RectF mRectF;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureWidth(widthMeasureSpec);
        height = measureHeight();
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
            viewWidth = DisplayUtil.getScreenWidth(getContext());
            if (specMode == MeasureSpec.AT_MOST) {
                viewWidth = Math.max(viewWidth, specSize);
            }
        }
        width = viewWidth;
        return viewWidth;
    }

    private int measureHeight() {
        int viewHeight = 0;
        if (mPolyTexts.size() > 0) {
            int count = 1;
            while (columns > 2 && count > 0) {
                count = 0;
                int viewWidth = width / columns - textOutPadding * 2;
                for (PolyText text : mPolyTexts) {
                    float viewRealWidth = circleRadius * 2 + drawablePadding + mTextPaint.measureText(text.getText());
                    if (!TextUtils.isEmpty(text.getTextSecond())) {
                        viewRealWidth += mTextPaint.measureText(text.getTextSecond());
                        viewRealWidth += textPadding;
                    }
                    if (viewRealWidth > viewWidth) {
                        count++;
                    }
                }
                if (count > 0) {
                    columns -= 1;
                }
            }
            viewHeight = (int) (Math.ceil(mPolyTexts.size() / (columns * 1f)) * (textHeight + textPadding * 2));
            int viewWidth = width / columns;
            float startCx = textOutPadding + circleRadius;
            float startCy = textHeight / 2 + textPadding;
            float startTextY = textPadding + textTop;
            float translateY = textBottom + textPadding * 2 + textTop;
            for (int i = 0; i < mPolyTexts.size(); i++) {
                mPolyTexts.get(i).setCircleCx(startCx);
                mPolyTexts.get(i).setCircleCy(startCy);
                mPolyTexts.get(i).setTextStartX(startCx + circleRadius + drawablePadding);
                if (!TextUtils.isEmpty(mPolyTexts.get(i).getTextSecond())) {
                    mPolyTexts.get(i).setTextSecondStartX(startCx + circleRadius + drawablePadding + mTextPaint.measureText(mPolyTexts.get(i).getText()) + textPadding * 2);
                }
                mPolyTexts.get(i).setTextBaseLineY(startTextY);
                if (textOrientation == 0) {//水平方向
                    if (startCx + viewWidth > width) {//换行显示
                        startCx = textOutPadding + circleRadius;
                        startTextY += translateY;
                        startCy += translateY;
                    } else {
                        startCx += viewWidth;
                    }
                } else {//垂直方向
                    if (startTextY + translateY > viewHeight) {//换列显示
                        startTextY = textPadding + textTop;
                        startCx += viewWidth;
                        startCy = textHeight / 2 + textPadding;
                    } else {//继续
                        startTextY += translateY;
                        startCy += translateY;
                    }
                }
            }
        }
        return viewHeight;
    }

    private List<PolyText> mPolyTexts = new ArrayList<>();

    public void setPolyTexts(List<PolyText> polyTexts) {
        mPolyTexts.clear();
        mPolyTexts = polyTexts;
        invalidate();
    }

    /*列数*/
    private int columns;
    /*文字颜色*/
    private int textColor;
    /*文字大小*/
    private int textSize;
    /*文字内间距*/
    private int textPadding;
    /*文字外间距*/
    private int textOutPadding;

    public static class PolyText {
        /*前缀小圆点颜色*/
        private int circleColor;
        /*圆心坐标*/
        private float circleCx;
        private float circleCy;
        private String text;
        private float textStartX;
        private float textBaseLineY;
        private float textSecondStartX;
        private String textSecond;

        public PolyText() {
        }

        public PolyText(int circleColor, String text) {
            this.circleColor = circleColor;
            this.text = text;
        }

        public PolyText(int circleColor, String text, String textSecond) {
            this.circleColor = circleColor;
            this.text = text;
            this.textSecond = textSecond;
        }

        public float getCircleCx() {
            return circleCx;
        }

        public void setCircleCx(float circleCx) {
            this.circleCx = circleCx;
        }

        public float getCircleCy() {
            return circleCy;
        }

        public void setCircleCy(float circleCy) {
            this.circleCy = circleCy;
        }

        public float getTextStartX() {
            return textStartX;
        }

        public void setTextStartX(float textStartX) {
            this.textStartX = textStartX;
        }

        public float getTextBaseLineY() {
            return textBaseLineY;
        }

        public void setTextBaseLineY(float textBaseLineY) {
            this.textBaseLineY = textBaseLineY;
        }

        public float getTextSecondStartX() {
            return textSecondStartX;
        }

        public void setTextSecondStartX(float textSecondStartX) {
            this.textSecondStartX = textSecondStartX;
        }

        public int getCircleColor() {
            return circleColor;
        }

        public void setCircleColor(int circleColor) {
            this.circleColor = circleColor;
        }

        public String getText() {
            return TextUtils.isEmpty(text) ? "" : text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTextSecond() {
            return TextUtils.isEmpty(textSecond) ? "" : textSecond;
        }

        public void setTextSecond(String textSecond) {
            this.textSecond = textSecond;
        }
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
