package com.codyy.charts.chartsapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lijian on 2018/2/24.
 */
public class PaintCanvas extends View {
    private Paint mPaint;
    // 路径对象
    private Path mPath;
    private PathEffect[] pathEffects = new PathEffect[7];
    private float mPhase = 5;

    public PaintCanvas(Context context) {
        super(context);
        init();
    }

    public PaintCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaintCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    //省略构造方法
    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        initPath();
    }

    private void initPath() {
        // 实例化路径
        mPath = new Path();
        // 定义路径的起点
        mPath.moveTo(10, 50);
        // 定义路径的各个点
        for (int i = 0; i <= 30; i++) {
            mPath.lineTo(i * 35, (float) (Math.random() * 100));
        }
        //什么都不处理
        pathEffects[0] = null;
        //参数1：线段之间的圆滑程度
        pathEffects[1] = new CornerPathEffect(10);
        //参数1：间隔线条长度(length>=2)，如float[] {20, 10}的偶数参数20定义了我们第一条实线的长度，
        //而奇数参数10则表示第一条虚线的长度，后面不再有数据则重复第一个数以此往复循环；参数2：虚实线间距
        pathEffects[2] = new DashPathEffect(new float[]{20, 10}, mPhase);
        //参数1:值越小杂点越密集；参数2:杂点突出的大小，值越大突出的距离越大
        pathEffects[3] = new DiscretePathEffect(5.0f, 10.0f);
        Path path = new Path();
        path.addRect(0, 0, 8, 8, Path.Direction.CCW);
        //定义路径虚线的样式,参数1：path；参数2：实线的长度；参数3：虚实线间距
        pathEffects[4] = new PathDashPathEffect(path, 20, mPhase, PathDashPathEffect.Style.ROTATE);
        pathEffects[5] = new ComposePathEffect(pathEffects[2], pathEffects[4]);
        //ComposePathEffect和SumPathEffect都可以用来组合两种路径效果,具体区别（不知道如何描述）小伙伴们自己试试
        pathEffects[6] = new SumPathEffect(pathEffects[4], pathEffects[3]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
         /*
         * 绘制路径
         */
        for (int i = 0; i < pathEffects.length; i++) {
            if (pathEffects[i] != null)
                mPaint.setPathEffect(pathEffects[i]);
            canvas.drawPath(mPath, mPaint);
            // 每绘制一条将画布向下平移250个像素
            canvas.translate(0, 250);
        }
    }
}