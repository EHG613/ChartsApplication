package com.codyy.mobile.support.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lijian on 2018/2/24.
 */

public class DashBoardView extends View {
    private Paint mDashStrokePaint;
    private Paint mDashFillPaint;
    private Paint mPaint;
    private PathEffect mPathEffect;
    /**
     * 半径 dp
     **/
    private int mRadiusDashStroke = 167;
    private int mRadiusDashFill;
    private int mRadius;
    /**
     * 虚线高度 dp
     **/
    private int mDashHeight = 9;
    /**
     * 虚线宽度 dp
     **/
    private int mDashWidth = 1;
    private int mViewWidth; // 控件宽度
    private int mViewHeight; // 控件高度
    private int mStartAngle; // 起始角度
    private int mSweepAngle; // 绘制角度
    private int mTopOffset; // 顶部偏移量

    public DashBoardView(Context context) {
        this(context, null);
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DashboardView, defStyleAttr, 0);

        try {
            this.mRadius = a.getDimensionPixelSize(R.styleable.DashboardView_radius, dip2px(80));
            mRadiusDashStroke = mRadius - dip2px(10f);
            mRadiusDashFill = mRadius - dip2px(10f);
            mStartAngle = a.getInteger(R.styleable.DashboardView_startAngle, 180);
            mSweepAngle = a.getInteger(R.styleable.DashboardView_sweepAngle, 180);
            mDashHeight = a.getDimensionPixelSize(R.styleable.DashboardView_dashHeight, dip2px(9));
            mDashWidth = a.getDimensionPixelSize(R.styleable.DashboardView_dashWidth, dip2px(1));
            mTopOffset = a.getDimensionPixelSize(R.styleable.DashboardView_topOffset, 30);
        } catch (Exception e) {

        } finally {
            a.recycle();
        }
        this.init();
    }

    private RectF mRectFStroke;
    private RectF mRectFFill;
    private RectF mRectF;
    private Paint mPaintPoint;

    private void init() {
        mPaintPoint = new Paint();
        mPaintPoint.setColor(Color.parseColor("#57C0FA"));
        mPaintPoint.setAntiAlias(true);
        mPaintPoint.setStrokeWidth(5f);
        mPaintPoint.setStyle(Paint.Style.STROKE);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#E8E8E8"));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(20f);
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mDashFillPaint = new Paint();
        mDashFillPaint.setColor(Color.parseColor("#E8E8E8"));
        mDashFillPaint.setAntiAlias(true);
        mDashFillPaint.setStrokeWidth(10f);
        mDashFillPaint.setStyle(Paint.Style.STROKE);//设置空心
        mDashStrokePaint = new Paint();
        mDashStrokePaint.setColor(Color.parseColor("#E8E8E8"));
        mDashStrokePaint.setAntiAlias(true);
        mDashStrokePaint.setStyle(Paint.Style.STROKE);//设置空心
        Path path = new Path();
        path.addRect(0, 0, mDashWidth, mDashHeight, Path.Direction.CCW);
        //定义路径虚线的样式,参数1：path；参数2：实线的长度；参数3：虚实线间距
        mPathEffect = new PathDashPathEffect(path, (int) Math.PI * mRadiusDashStroke / 101, 0, PathDashPathEffect.Style.ROTATE);
        mDashStrokePaint.setPathEffect(mPathEffect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectFStroke = new RectF(mViewWidth / 2 - mRadiusDashStroke, mRadius - mRadiusDashStroke + mTopOffset, mViewWidth / 2 + mRadiusDashStroke, mRadius + mRadiusDashStroke + mTopOffset);
        mRectFFill = new RectF(mViewWidth / 2 - mRadiusDashFill, mRadius - mRadiusDashFill + mTopOffset, mViewWidth / 2 + mRadiusDashFill, mRadius + mRadiusDashFill + mTopOffset);
        mRectF = new RectF(mViewWidth / 2 - mRadius, mTopOffset, mViewWidth / 2 + mRadius, mRadius * 2 + mTopOffset);
        canvas.drawArc(mRectFStroke, mStartAngle, mSweepAngle, false, mDashStrokePaint);//小弧形
        canvas.drawArc(mRectFFill, mStartAngle, mSweepAngle, false, mDashFillPaint);//小弧形
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mPaint);//小弧形
//        SweepGradient sweepGradient=new SweepGradient(mViewWidth/2,mViewHeight+mTopOffset,Color.parseColor("#57C0FA"),Color.parseColor("#1209FC"));
//        Shader mShader = new LinearGradient(mViewWidth / 2 - mRadius, mViewHeight, mViewWidth, mViewHeight, Color.parseColor("#57C0FA"), Color.parseColor("#1209FC"), Shader.TileMode.CLAMP);
        Shader mShader =new SweepGradient(mViewWidth / 2, mViewHeight+mTopOffset, new int[]{Color.parseColor("#57C0FA"), Color.parseColor("#1209FC")},null);
        mPaint.setShader(mShader);
        canvas.rotate(180,mViewWidth / 2, mViewHeight+mTopOffset);
        canvas.drawArc(mRectF, 0, 160, false, mPaint);//小弧形
        canvas.rotate(180,mViewWidth / 2, mViewHeight+mTopOffset);
        //（r*cosm+a，r*sinm+b）
        float x1 = (float) (mViewWidth / 2 + mRadius * Math.cos(-20*Math.PI/180));
        float y1 = (float) (mViewHeight+mTopOffset + mRadius * Math.sin(-20*Math.PI/180));
        mPaintPoint.setStyle(Paint.Style.FILL);
        mPaintPoint.setColor(Color.WHITE);
        canvas.drawCircle(x1, y1, 15, mPaintPoint);
        mPaintPoint.setStyle(Paint.Style.STROKE);
        mPaintPoint.setColor(Color.parseColor("#57C0FA"));
        canvas.drawCircle(x1, y1, 15, mPaintPoint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mViewWidth = specSize;
        } else {
            mViewWidth = dip2px(250f);
            if (specMode == MeasureSpec.AT_MOST) {
                mViewWidth = Math.min(mViewWidth, specSize);
            }
        }
        return mViewWidth;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mViewHeight = specSize;
        } else {
            mViewHeight = dip2px(250f);
            if (specMode == MeasureSpec.AT_MOST) {
                mViewHeight = Math.min(mViewHeight, specSize);
            }
        }
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
}
