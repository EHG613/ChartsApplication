package com.codyy.mobile.support.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijian on 2018/3/13.
 */

public class BarChart extends View {
    public BarChart(Context context) {
        this(context, null);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dimensionPixel30dp = dip2px(30f);
        dimensionPixel150dp = dip2px(150f);
        dimensionPixel6dp = dip2px(6f);
        dimensionPixel312dp = dip2px(312f);
        dimensionPixel10dp = dip2px(10f);
        dimensionPixel8dp = dip2px(8f);
        colorDivider = Color.parseColor("#E1E1E1");
        colorXYCoordinateText = Color.parseColor("#939fbe");
        colorVerticalLine = Color.parseColor("#bde1fb");
        init();
    }

    private Paint mPaint;
    private Paint mPaintPressedPoint;

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mTextPaintCoordinate = new TextPaint();
        mTextPaintCoordinate.setAntiAlias(true);
        mPaintPressedPoint = new Paint();
        mPaintPressedPoint.setAntiAlias(true);
    }

    private int width;
    private int height;
    private int dimensionPixel6dp;
    private int dimensionPixel8dp;
    private int dimensionPixel10dp;
    private int dimensionPixel30dp;
    private int dimensionPixel150dp;
    private int dimensionPixel312dp;

    private int colorDivider;
    private int colorVerticalLine;
    private int colorXYCoordinateText;
    private TextPaint mTextPaintCoordinate;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private List<Point> mPoints = new ArrayList<>();

    public void setPoints(List<Point> points) {
        if (points == null) return;
        mPoints.clear();
        mPoints.addAll(points);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaintCoordinate.setColor(colorXYCoordinateText);
        mTextPaintCoordinate.setTextSize(sp2px(9f));
        mPaint.setStrokeWidth(dip2px(1f));
        mPaint.setColor(colorDivider);
        mPaint.setShader(null);
        canvas.translate(0, height - dimensionPixel30dp);
        int color1 = 0;
        int color2 = 0;
        int color3 = 0;
        int color4 = 0;
        for (int i = 0; i < mPoints.size(); i++) {
            Point point = mPoints.get(i);
            if (i == 0) {
                if (point.getY1() != -1) {
                    color1 = point.getY1Color();
                }
                if (point.getY2() != -1) {
                    color2 = point.getY2Color();
                }
                if (point.getY3() != -1) {
                    color3 = point.getY3Color();
                }
                if (point.getY4() != -1) {
                    color4 = point.getY4Color();
                }
            }
            mTextPaintCoordinate.setTextAlign(Paint.Align.CENTER);
            mPaint.setShader(point.getLinearGradient());
            canvas.drawText(point.getxAbbrText().length() > 4 ? point.getxAbbrText().substring(0, 4) + getContext().getResources().getString(R.string.text_ellipsize) : point.getxAbbrText(), point.getX(), dimensionPixel10dp + dip2px(9f) / 2, mTextPaintCoordinate);
            canvas.drawPath(point.getPath(), mPaint);
        }
        if (color1 != 0 && mPoints.size() > 1) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(dip2px(1f));
            mPaint.setShader(null);
            mPaint.setColor(color1);
            for (int i = 0; i < mPoints.size() - 1; i++) {
                canvas.drawLine(mPoints.get(i).getX(), mPoints.get(i).getY1(), mPoints.get(i + 1).getX(), mPoints.get(i + 1).getY1(), mPaint);
            }
        }
        if (color2 != 0&& mPoints.size() > 1) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(dip2px(1f));
            mPaint.setShader(null);
            mPaint.setColor(color2);
            for (int i = 0; i < mPoints.size() - 1; i++) {
                canvas.drawLine(mPoints.get(i).getX(), mPoints.get(i).getY2(), mPoints.get(i + 1).getX(), mPoints.get(i + 1).getY2(), mPaint);
            }
        }
        if (color3 != 0&& mPoints.size() > 1) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(dip2px(1f));
            mPaint.setShader(null);
            mPaint.setColor(color3);
            for (int i = 0; i < mPoints.size() - 1; i++) {
                canvas.drawLine(mPoints.get(i).getX(), mPoints.get(i).getY3(), mPoints.get(i + 1).getX(), mPoints.get(i + 1).getY3(), mPaint);
            }
        }
        if (color4 != 0&& mPoints.size() > 1) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(dip2px(1f));
            mPaint.setShader(null);
            mPaint.setColor(color4);
            for (int i = 0; i < mPoints.size() - 1; i++) {
                canvas.drawLine(mPoints.get(i).getX(), mPoints.get(i).getY4(), mPoints.get(i + 1).getX(), mPoints.get(i + 1).getY4(), mPaint);
            }
        }
        mPaint.setStyle(Paint.Style.FILL);
        if (currentPressed != -1) {
            mPaint.setStrokeWidth(dip2px(1f));
            mPaint.setShader(null);
            mPaint.setColor(colorVerticalLine);
            canvas.drawLine(mPoints.get(currentPressed).getX(), 0, mPoints.get(currentPressed).getX(), -dimensionPixel150dp, mPaint);
        }
        for (int i = 0; i < mPoints.size(); i++) {
            Point point = mPoints.get(i);

            if (point.getY1() != -1) {
                drawPointCircle(canvas, point.getX(), point.getY1(), point.getY1Color(), dimensionPixel8dp / 2);
                drawPointCircle(canvas, point.getX(), point.getY1(), Color.WHITE, dimensionPixel8dp / 4);
            }
            if (point.getY2() != -1) {
                drawPointCircle(canvas, point.getX(), point.getY2(), point.getY2Color(), dimensionPixel8dp / 2);
                drawPointCircle(canvas, point.getX(), point.getY2(), Color.WHITE, dimensionPixel8dp / 4);
            }
            if (point.getY3() != -1) {
                drawPointCircle(canvas, point.getX(), point.getY3(), point.getY3Color(), dimensionPixel8dp / 2);
                drawPointCircle(canvas, point.getX(), point.getY3(), Color.WHITE, dimensionPixel8dp / 4);
            }
            if (point.getY4() != -1) {
                drawPointCircle(canvas, point.getX(), point.getY4(), point.getY4Color(), dimensionPixel8dp / 2);
                drawPointCircle(canvas, point.getX(), point.getY4(), Color.WHITE, dimensionPixel8dp / 4);
            }
        }

        if (currentPressed != -1) {

            if (color1 != 0) {
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY1(), color1, dimensionPixel8dp, true);
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY1(), color1, dimensionPixel8dp / 2);
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY1(), Color.WHITE, dimensionPixel8dp / 4);
            }
            if (color2 != 0) {
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY2(), color2, dimensionPixel8dp, true);
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY2(), color2, dimensionPixel8dp / 2);
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY2(), Color.WHITE, dimensionPixel8dp / 4);
            }
            if (color3 != 0) {
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY3(), color3, dimensionPixel8dp, true);
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY3(), color3, dimensionPixel8dp / 2);
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY3(), Color.WHITE, dimensionPixel8dp / 4);
            }
            if (color4 != 0) {
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY4(), color4, dimensionPixel8dp, true);
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY4(), color4, dimensionPixel8dp / 2);
                drawPointCircle(canvas, mPoints.get(currentPressed).getX(), mPoints.get(currentPressed).getY4(), Color.WHITE, dimensionPixel8dp / 4);
            }


        }
    }

    private void drawPointCircle(Canvas canvas, int cx, float cy, int colorBlue, int radius, boolean isNeedAlpha) {
        mPaintPressedPoint.setColor(colorBlue);
        if (isNeedAlpha) {
            mPaintPressedPoint.setAlpha(128);//半透明
        }
        canvas.drawCircle(cx, cy, radius/2, mPaintPressedPoint);
        if (isNeedAlpha) {
            mPaintPressedPoint.setAlpha(255);//值越小,越透明:0-255
        }
    }

    private void drawPointCircle(Canvas canvas, int cx, float cy, int colorBlue, int radius) {
        mPaintPressedPoint.setColor(colorBlue);
        canvas.drawCircle(cx, cy, radius/2, mPaintPressedPoint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                checkPointer(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                restorePointer();
                return true;
            case MotionEvent.ACTION_CANCEL:
                restorePointer();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private int currentPressed = -1;

    private void restorePointer() {
        currentPressed = -1;
        cancel();
        invalidate();
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnClickListener {
        /*点击*/
        void onClick(Point point);

        /*取消点击*/
        void cancel();
    }

    private void checkPointer(MotionEvent event) {
        int curX = (int) event.getX();
        for (int i = 0; i < mPoints.size(); i++) {
//            Log.d("point","curx="+curX+";min="+(point.getX() - point.getBarWidth() / 2)+";max="+(point.getX() + point.getBarWidth() / 2));
            if ((mPoints.get(i).getX() - mPoints.get(i).getBarWidth() / 2) < curX && curX < (mPoints.get(i).getX() + (mPoints.get(i).getBarWidth() / 2))) {
                currentPressed = i;
//                Log.d("point", "pointX=" + point.getX() + ";" + count + "(" + (int) event.getX() + "," + event.getY() + ")");
            }
//            RectF bounds = new RectF();
//            point.getPressedPath().computeBounds(bounds, true);
//            Region region = new Region();
//            region.setPath(point.getPressedPath(), new Region((int)bounds.left, (int)bounds.top,(int)bounds.right, (int)bounds.bottom));
//            if (region.contains((int) event.getX(),-100)) {
            //do sth.

//            }
        }
        if (currentPressed != -1) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(mPoints.get(currentPressed));
            }
            invalidate();
        } else {
            cancel();
        }

    }

    private void cancel() {
        if (mOnClickListener != null) {
            mOnClickListener.cancel();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = measureWidth(widthMeasureSpec);
        setMeasuredDimension(measureWidth, heightMeasureSpec);
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int viewWidth;
        if (specMode == MeasureSpec.EXACTLY) {
            viewWidth = specSize;
        } else {
            viewWidth = dip2px(52) * mPoints.size();
            if (specMode == MeasureSpec.AT_MOST) {
                viewWidth = Math.min(viewWidth, specSize);
            }
        }
        return dip2px(52) * mPoints.size();
    }


    private int dip2px(float dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public int sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    public static class Point {
        /**
         * X轴文本
         */
        private String xText = "";
        /**
         * X轴缩略文本(X轴默认显示此文本)
         */
        private String xAbbrText = "";
        /**
         * x轴坐标
         */
        private int x;
        /**
         * x轴原始值
         */
        private int xVal;
        private int y;
        private int yVal;
        private int y1 = -1;
        private int y1Val;
        private int y1Color = Color.parseColor("#38adff");
        private int y2 = -1;
        private int y2Val;
        private int y2Color = Color.parseColor("#3ed5aa");
        private int y3 = -1;
        private int y3Val;
        private int y3Color = Color.parseColor("#8940fa");
        private int y4 = -1;
        private int y4Val;
        private int y4Color = Color.parseColor("#24c8f3");
        private Path path;
        private LinearGradient linearGradient;
        private int barWidth;

        public int getBarWidth() {
            return barWidth;
        }

        public void setBarWidth(int barWidth) {
            this.barWidth = barWidth;
        }

        public Point() {
        }

        public Point(String xText, String xAbbrText, int x, int xVal, int y, int yVal, Path path, LinearGradient linearGradient) {
            this.xText = xText;
            this.xAbbrText = xAbbrText;
            this.x = x;
            this.xVal = xVal;
            this.y = y;
            this.yVal = yVal;
            this.path = path;
            this.linearGradient = linearGradient;
        }

        public int getY1() {
            return y1;
        }

        public void setY1(int y1) {
            this.y1 = y1;
        }

        public int getY1Val() {
            return y1Val;
        }

        public void setY1Val(int y1Val) {
            this.y1Val = y1Val;
        }

        public int getY1Color() {
            return y1Color;
        }

        public void setY1Color(int y1Color) {
            this.y1Color = y1Color;
        }

        public int getY2() {
            return y2;
        }

        public void setY2(int y2) {
            this.y2 = y2;
        }

        public int getY2Val() {
            return y2Val;
        }

        public void setY2Val(int y2Val) {
            this.y2Val = y2Val;
        }

        public int getY2Color() {
            return y2Color;
        }

        public void setY2Color(int y2Color) {
            this.y2Color = y2Color;
        }

        public int getY3() {
            return y3;
        }

        public void setY3(int y3) {
            this.y3 = y3;
        }

        public int getY3Val() {
            return y3Val;
        }

        public void setY3Val(int y3Val) {
            this.y3Val = y3Val;
        }

        public int getY3Color() {
            return y3Color;
        }

        public void setY3Color(int y3Color) {
            this.y3Color = y3Color;
        }

        public int getY4() {
            return y4;
        }

        public void setY4(int y4) {
            this.y4 = y4;
        }

        public int getY4Val() {
            return y4Val;
        }

        public void setY4Val(int y4Val) {
            this.y4Val = y4Val;
        }

        public int getY4Color() {
            return y4Color;
        }

        public void setY4Color(int y4Color) {
            this.y4Color = y4Color;
        }

        public String getxText() {
            return xText;
        }

        public void setxText(String xText) {
            this.xText = xText;
        }

        public String getxAbbrText() {
            return xAbbrText;
        }

        public void setxAbbrText(String xAbbrText) {
            this.xAbbrText = xAbbrText;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getxVal() {
            return xVal;
        }

        public void setxVal(int xVal) {
            this.xVal = xVal;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getyVal() {
            return yVal;
        }

        public void setyVal(int yVal) {
            this.yVal = yVal;
        }

        public Path getPath() {
            return path;
        }

        public void setPath(Path path) {
            this.path = path;
        }

        public LinearGradient getLinearGradient() {
            return linearGradient;
        }

        public void setLinearGradient(LinearGradient linearGradient) {
            this.linearGradient = linearGradient;
        }
    }
}
