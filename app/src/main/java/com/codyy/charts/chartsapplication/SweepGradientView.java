package com.codyy.charts.chartsapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lijian on 2018/2/26.
 */

public class SweepGradientView extends View {
    private Paint mPaint;
    private Shader mShader;

    public SweepGradientView(Context context) {
        this(context, null);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 省略构造方法
private RectF mRectF;
    private void init() {
        mRectF=new RectF(100, 100, 900, 900);
//        mShader = new SweepGradient(500, 500, Color.parseColor("#57C0FA"), Color.parseColor("#1209FC"));
        mShader = new SweepGradient(500, 500, new int[]{Color.parseColor("#57C0FA"), Color.parseColor("#1209FC")},null);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15f);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setShader(mShader);
        canvas.rotate(180, 500, 500);
        canvas.drawArc(mRectF, 0, 180, false, mPaint);
//        canvas.drawCircle(500, 500, 400, mPaint);
    }
}
