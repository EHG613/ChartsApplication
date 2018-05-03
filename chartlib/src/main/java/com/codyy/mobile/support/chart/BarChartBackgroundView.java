package com.codyy.mobile.support.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lijian on 2018/3/13.
 */

public class BarChartBackgroundView extends View {
    public BarChartBackgroundView(Context context) {
        this(context, null);
    }

    public BarChartBackgroundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChartBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dimensionPixel30dp = dip2px(30f);
        dimensionPixel150dp = dip2px(150f);
        dimensionPixel6dp = dip2px(6f);
        dimensionPixel312dp = dip2px(312f);
        colorDivider = Color.parseColor("#E1E1E1");
        colorXYCoordinateText = Color.parseColor("#939fbe");
        init();
    }

    private Paint mPaint;
    int ySpace = 300;
    int ySpace2 = 20;
    private void init() {
        mPaint = new Paint();
        mTextPaintCoordinate = new TextPaint();
        mTextPaintCoordinate.setAntiAlias(true);
    }

    private int width;
    private int height;
    private int dimensionPixel6dp;
    private int dimensionPixel30dp;
    private int dimensionPixel150dp;
    private int dimensionPixel312dp;
    private int colorDivider;
    private int colorXYCoordinateText;
    private TextPaint mTextPaintCoordinate;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaintCoordinate.setColor(colorXYCoordinateText);
        mTextPaintCoordinate.setTextSize(sp2px(9f));
        mPaint.setStrokeWidth(dip2px(1f));
        mPaint.setColor(colorDivider);
        canvas.translate(dimensionPixel30dp, height - dimensionPixel30dp);
        int xStopX = width-dimensionPixel30dp- dimensionPixel30dp/2;
//        int xStopY = 0;
//        int yStopX = 0;
//        int yStopY = -dimensionPixel150dp;
        int coordinateSpaceY = dimensionPixel30dp;
        for (int i = 0; i < 6; i++) {
            canvas.drawLine(0, -coordinateSpaceY * i, xStopX - dimensionPixel30dp/2, -coordinateSpaceY * i, mPaint);
            canvas.drawText(ySpace * i + "", -dimensionPixel6dp - mTextPaintCoordinate.measureText(ySpace * i + ""), -coordinateSpaceY * i + sp2px(9f) / 2, mTextPaintCoordinate);
            canvas.drawText(ySpace2 * i + "%", xStopX-dimensionPixel30dp/2+dimensionPixel6dp, -coordinateSpaceY * i + sp2px(9f) / 2, mTextPaintCoordinate);
        }
    }

    public void setySpace(int ySpace) {
        this.ySpace = ySpace;
        invalidate();
    }
    public void setySpaces(int ySpace,int ySpace2) {
        this.ySpace = ySpace;
        this.ySpace2=ySpace2;
        invalidate();
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
