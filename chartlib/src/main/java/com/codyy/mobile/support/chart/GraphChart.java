package com.codyy.mobile.support.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijian on 2018/3/12.
 */

public class GraphChart extends View {
    private int colorBlue;
    private int colorRed;
    private int colorYellow;
    private int colorVerticalLine;
    private int colorTopTipText;
    private int colorTopPressedText;
    private int colorXYCoordinateText;
    private int colorDivider;

    public GraphChart(Context context) {
        this(context, null);
    }

    public GraphChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GraphChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        colorBlue = Color.parseColor("#38adff");
        colorRed = Color.parseColor("#ff6d90");
        colorYellow = Color.parseColor("#f6b449");
        colorVerticalLine = Color.parseColor("#bde1fb");
        colorTopTipText = Color.parseColor("#999999");
        colorTopPressedText = Color.parseColor("#666666");
        colorXYCoordinateText = Color.parseColor("#939fbe");
        colorDivider = Color.parseColor("#E1E1E1");
        dimensionPixel6dp = dip2px(6f);
        dimensionPixel10dp = dip2px(10f);
        dimensionPixel30dp = dip2px(30f);
        dimensionPixel150dp = dip2px(150f);
        xWidth = getScreenWidth(getContext()) - dimensionPixel30dp - dimensionPixel30dp;
        dimensionPixel8dp = dip2px(8f);
        dimensionPixel2dp = dip2px(2f);
        xyCoordinateTextCenter = dip2px(xyTextSize) / 2;
        init();
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    private int xyCoordinateTextCenter;
    private int dimensionPixel2dp;
    private int dimensionPixel8dp;
    private int dimensionPixel6dp;
    private int dimensionPixel10dp;
    private int dimensionPixel30dp;
    private int dimensionPixel150dp;
    private int xWidth;
    private int width;
    private int height;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private Paint mPaint;
    private Paint mPaintPressedPoint;
    private Paint mPaintLine;
    private Paint mPaintLine1;
    private Paint mPaintLine2;
    private TextPaint mTextPaintCoordinate;
    private Paint mPaintCoordinateBg;
    private RectF mRectFCoordinateBg;
    private float xyTextSize = 9f;
    private Path mPath;
    private Path mPath1;
    private Path mPath2;

    private void init() {
        CornerPathEffect cornerPathEffect = new CornerPathEffect(30);
        mPaint = new Paint();
        mPaintPressedPoint = new Paint();
        mPaintPressedPoint.setAntiAlias(true);
        mPaintLine = new Paint();
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setStrokeWidth(dip2px(1f));
        mPaintLine.setAntiAlias(true);
        mPaintLine.setDither(true);
        mPaintLine.setPathEffect(cornerPathEffect);
        mPaintLine1 = new Paint();
        mPaintLine1.setStyle(Paint.Style.STROKE);
        mPaintLine1.setStrokeWidth(dip2px(1f));
        mPaintLine1.setAntiAlias(true);
        mPaintLine1.setDither(true);
        mPaintLine1.setPathEffect(cornerPathEffect);
        mPaintLine2 = new Paint();
        mPaintLine2.setStyle(Paint.Style.STROKE);
        mPaintLine2.setStrokeWidth(dip2px(1f));
        mPaintLine2.setAntiAlias(true);
        mPaintLine2.setDither(true);
        mPaintLine2.setPathEffect(cornerPathEffect);
        mPath = new Path();
        mPath1 = new Path();
        mPath2 = new Path();
        mTextPaintCoordinate = new TextPaint();
        mTextPaintCoordinate.setAntiAlias(true);
        mPaintCoordinateBg = new Paint();
        mRectFCoordinateBg = new RectF();
        mPaintCoordinateBg.setColor(Color.parseColor("#f6fbff"));
    }

    int ySpace = 300;

    public void setySpace(int ySpace) {
        this.ySpace = ySpace;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaintCoordinate.setColor(colorXYCoordinateText);
        mTextPaintCoordinate.setTextSize(sp2px(xyTextSize));
        mPaint.setStrokeWidth(dip2px(1f));
        mPaint.setColor(colorDivider);
        canvas.translate(dimensionPixel30dp, height - dimensionPixel30dp);
        int xStopX = xWidth;
        int yStopY = -dimensionPixel150dp;
        int coordinateSpaceY = dimensionPixel30dp;
        int coordinateSpaceX = 0;
        if (mGraphEntities.size() <= 7 && mGraphEntities.size() > 0) {
            coordinateSpaceX = (mGraphEntities.size() == 1 ? xWidth : xWidth / (mGraphEntities.size()));
            for (int i = 0; i < mGraphEntities.size(); i++) {
                mGraphEntities.get(i).setX(coordinateSpaceX * i);
            }
        }
        if (mGraphEntities.size() == 0) {
            coordinateSpaceX = xWidth / 7;
        }
        if (mGraphEntities.size() > 7) {
            coordinateSpaceX = (int) (xWidth*1f / (Math.ceil(mGraphEntities.size() / Math.ceil(mGraphEntities.size() / 7f))));
            for (int i = 0; i < mGraphEntities.size(); i++) {
                mGraphEntities.get(i).setX((int) (coordinateSpaceX/Math.ceil(mGraphEntities.size() / 7f) * i));
            }
        }
//        if (mGraphEntities.size() >= 7 || mGraphEntities.size() == 0) 0
//            coordinateSpaceX = dimensionPixel48dp;
//        } else {
//            coordinateSpaceX = (mGraphEntities.size() == 1 ? xWidth : xWidth / (mGraphEntities.size()));
//        }
        mRectFCoordinateBg.left = 0;
        mRectFCoordinateBg.top = -coordinateSpaceY;
        mRectFCoordinateBg.bottom = 0;
        mRectFCoordinateBg.right = xStopX;
        canvas.drawRect(mRectFCoordinateBg, mPaintCoordinateBg);
        mRectFCoordinateBg.left = 0;
        mRectFCoordinateBg.top = -coordinateSpaceY * 3;
        mRectFCoordinateBg.bottom = -coordinateSpaceY * 2;
        mRectFCoordinateBg.right = xStopX;
        canvas.drawRect(mRectFCoordinateBg, mPaintCoordinateBg);
        mRectFCoordinateBg.left = 0;
        mRectFCoordinateBg.top = -coordinateSpaceY * 5;
        mRectFCoordinateBg.bottom = -coordinateSpaceY * 4;
        mRectFCoordinateBg.right = xStopX;
        canvas.drawRect(mRectFCoordinateBg, mPaintCoordinateBg);
        for (int i = 0; i < 6; i++) {
            canvas.drawLine(0, -coordinateSpaceY * i, xStopX, -coordinateSpaceY * i, mPaint);
            canvas.drawText(ySpace * i + "", -dimensionPixel6dp - mTextPaintCoordinate.measureText(ySpace * i + ""), -coordinateSpaceY * i + sp2px(xyTextSize) / 2, mTextPaintCoordinate);
        }
        int xyTextY = dimensionPixel10dp + xyCoordinateTextCenter;
        if (mGraphEntities.size() > 0 && mGraphEntities.size() <= 7) {
            for (int i = 0; i < mGraphEntities.size(); i++) {
                canvas.drawLine(coordinateSpaceX * i, 0, coordinateSpaceX * i, yStopY, mPaint);
                canvas.drawText(mGraphEntities.get(i).getxText(), coordinateSpaceX * i, xyTextY, mTextPaintCoordinate);
            }
        } else if (mGraphEntities.size() == 0) {
            for (int i = 0; i < 7; i++) {
                canvas.drawLine(coordinateSpaceX * i, 0, coordinateSpaceX * i, yStopY, mPaint);
                switch (i) {
                    case 0:
                        canvas.drawText("8:00", coordinateSpaceX * i, xyTextY, mTextPaintCoordinate);
                        break;
                    case 1:
                        canvas.drawText("10:00", coordinateSpaceX * i, xyTextY, mTextPaintCoordinate);
                        break;
                    case 2:
                        canvas.drawText("12:00", coordinateSpaceX * i, xyTextY, mTextPaintCoordinate);
                        break;
                    case 3:
                        canvas.drawText("14:00", coordinateSpaceX * i, xyTextY, mTextPaintCoordinate);
                        break;
                    case 4:
                        canvas.drawText("16:00", coordinateSpaceX * i, xyTextY, mTextPaintCoordinate);
                        break;
                    case 5:
                        canvas.drawText("18:00", coordinateSpaceX * i, xyTextY, mTextPaintCoordinate);
                        break;
                    case 6:
                        canvas.drawText("20:00", coordinateSpaceX * i, xyTextY, mTextPaintCoordinate);
                        break;
                }
            }
        } else {
            int blank = (int) Math.ceil(mGraphEntities.size() / 7f);
            int space = (int) Math.ceil(mGraphEntities.size()*1f / blank);
//           space= (int) Math.ceil(mGraphEntities.size()*1f/space);
            for (int i = 0; i < space; i++) {
                canvas.drawLine(coordinateSpaceX * i, 0, coordinateSpaceX * i, yStopY, mPaint);
                canvas.drawText(mGraphEntities.get(i*blank).getxText(), coordinateSpaceX * i, xyTextY, mTextPaintCoordinate);
            }
        }

        canvas.drawLine(xStopX, 0, xStopX, yStopY, mPaint);
        //y轴1=0.1dp,x轴2dp=5分钟;
        mPath.reset();
        mPath1.reset();
        mPath2.reset();
        if (mGraphEntities.size() > 0) {
            mPath.moveTo(mGraphEntities.get(0).getX(), mGraphEntities.get(0).getY());
            mPath1.moveTo(mGraphEntities.get(0).getX(), mGraphEntities.get(0).getY1());
            mPath2.moveTo(mGraphEntities.get(0).getX(), mGraphEntities.get(0).getY2());
        }

        for (Point entity : mGraphEntities) {
            mPaintLine.setColor(colorBlue);
            mPaintLine1.setColor(colorRed);
            mPaintLine2.setColor(colorYellow);
            mPath.lineTo(entity.getX(), entity.getY());
            mPath1.lineTo(entity.getX(), entity.getY1());
            mPath2.lineTo(entity.getX(), entity.getY2());
        }
        canvas.drawPath(mPath, mPaintLine);
        canvas.drawPath(mPath1, mPaintLine1);
        canvas.drawPath(mPath2, mPaintLine2);
        if (mPointPressed != null) {
            mPaint.setColor(colorVerticalLine);
            canvas.drawLine(mPointPressed.getX(), 0, mPointPressed.getX(), -dimensionPixel150dp, mPaint);
            float startX = 0f;
            float startY = -dimensionPixel150dp - xyCoordinateTextCenter;
            drawTopTipText(canvas, startX, startY, "使用数: ", colorTopPressedText);
            startX += mTextPaintCoordinate.measureText("使用数: ");
            drawTopTipText(canvas, startX, startY, mPointPressed.getyVal() + "", colorBlue);
            startX += mTextPaintCoordinate.measureText(mPointPressed.getyVal() + "");
            drawTopTipText(canvas, startX, startY, ", 严重告警: ", colorTopPressedText);
            startX += mTextPaintCoordinate.measureText(", 严重告警: ");
            drawTopTipText(canvas, startX, startY, mPointPressed.getY1Val() + "", colorRed);
            startX += mTextPaintCoordinate.measureText(mPointPressed.getY1Val() + "");
            drawTopTipText(canvas, startX, startY, ", 一般告警: ", colorTopPressedText);
            startX += mTextPaintCoordinate.measureText(", 一般告警: ");
            drawTopTipText(canvas, startX, startY, mPointPressed.getY2Val() + "", colorYellow);
            startX = coordinateSpaceX * 6;
//            int startTime = 8;
//            int total = mPointPressed.getxVal() * 5;
//            int h = total / 60;
//            startTime += h;
//            String timeText = "";
//            if (total % 60 == 0) {
//                timeText += startTime + ":00";
//            } else {
//                timeText += startTime + ":" + ((total - h * 60) < 10 ? "0" + (total - h * 60) : (total - h * 60));
//            }
            drawTopTipText(canvas, startX, startY, mPointPressed.getxText(), colorTopTipText);
            drawPointCircle(canvas, mPointPressed.getX(), mPointPressed.getY(), colorBlue, dimensionPixel8dp / 2);
            drawPointCircle(canvas, mPointPressed.getX(), mPointPressed.getY(), Color.WHITE, dimensionPixel8dp / 4);
            drawPointCircle(canvas, mPointPressed.getX(), mPointPressed.getY1(), colorRed, dimensionPixel8dp / 2);
            drawPointCircle(canvas, mPointPressed.getX(), mPointPressed.getY1(), Color.WHITE, dimensionPixel8dp / 4);
            drawPointCircle(canvas, mPointPressed.getX(), mPointPressed.getY2(), colorYellow, dimensionPixel8dp / 2);
            drawPointCircle(canvas, mPointPressed.getX(), mPointPressed.getY2(), Color.WHITE, dimensionPixel8dp / 4);
        } else {
            float startX = 0f;
            float startY = -dimensionPixel150dp - xyCoordinateTextCenter;
            if(mGraphEntities.size()==0){
                drawTopTipText(canvas, startX, startY, "暂无数据", colorTopTipText);
            }else {
                drawTopTipText(canvas, startX, startY, "手指移至下方曲线图上,可查看具体内容", colorTopTipText);
            }
        }
    }

    private void drawPointCircle(Canvas canvas, int cx, float cy, int colorBlue, int radius) {
        mPaintPressedPoint.setColor(colorBlue);
        canvas.drawCircle(cx, cy, radius, mPaintPressedPoint);
    }

    private void drawTopTipText(Canvas canvas, float startX, float startY, String text, int color) {
        mTextPaintCoordinate.setColor(color);
        canvas.drawText(text, startX, startY, mTextPaintCoordinate);
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
                checkPointer(event);
                return true;
            case MotionEvent.ACTION_UP:
                resetDataState();
                return true;
            case MotionEvent.ACTION_CANCEL:
                resetDataState();
                return true;
            default:
                return super.onTouchEvent(event);
        }

    }

    private Point mPointPressed;

    private void checkPointer(MotionEvent event) {
        float coordinate = event.getX() - dimensionPixel30dp;
        int x = new BigDecimal(coordinate).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        for (int i = 0; i < mGraphEntities.size(); i++) {
            if (mGraphEntities.get(i).getX() == x) {
                mPointPressed = mGraphEntities.get(i);
            }
        }
        invalidate();
    }

    private void resetDataState() {
        mPointPressed = null;
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

    private List<Point> mGraphEntities = new ArrayList<>();

    public void setData(List<Point> graphEntities) {
        mGraphEntities = graphEntities;
        invalidate();
    }


    public static class Point {
        private int x;
        private int xVal;
        private float y;
        private float y1;
        private float y2;
        private int yVal;
        private int y1Val;
        private int y2Val;
        private boolean isPressed;
        private String xText;

        public Point(int x, float y, float y1, float y2) {
            this.x = x;
            this.y = -y;
            this.y1 = -y1;
            this.y2 = -y2;
        }

        public Point(int x, int xVal, float y, float y1, float y2, int yVal, int y1Val, int y2Val) {
            this.x = x;
            this.xVal = xVal;
            this.y = -y;
            this.y1 = -y1;
            this.y2 = -y2;
            this.yVal = yVal;
            this.y1Val = y1Val;
            this.y2Val = y2Val;
        }

        public Point(int x, int xVal, float y, float y1, float y2, int yVal, int y1Val, int y2Val, String xText) {
            this.x = x;
            this.xVal = xVal;
            this.y = -y;
            this.y1 = -y1;
            this.y2 = -y2;
            this.yVal = yVal;
            this.y1Val = y1Val;
            this.y2Val = y2Val;
            this.xText = xText;
        }

        public String getxText() {
            return xText;
        }

        public void setxText(String xText) {
            this.xText = xText;
        }

        public int getxVal() {
            return xVal;
        }

        public void setxVal(int xVal) {
            this.xVal = xVal;
        }

        public int getyVal() {
            return yVal;
        }

        public void setyVal(int yVal) {
            this.yVal = yVal;
        }

        public int getY1Val() {
            return y1Val;
        }

        public void setY1Val(int y1Val) {
            this.y1Val = y1Val;
        }

        public int getY2Val() {
            return y2Val;
        }

        public void setY2Val(int y2Val) {
            this.y2Val = y2Val;
        }

        public boolean isPressed() {
            return isPressed;
        }

        public void setPressed(boolean pressed) {
            isPressed = pressed;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getY1() {
            return y1;
        }

        public void setY1(float y1) {
            this.y1 = y1;
        }

        public float getY2() {
            return y2;
        }

        public void setY2(float y2) {
            this.y2 = y2;
        }
    }
}
