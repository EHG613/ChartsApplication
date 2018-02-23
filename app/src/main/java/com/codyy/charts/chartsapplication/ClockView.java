package com.codyy.charts.chartsapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sudan on 2018/2/23.
 */

public class ClockView extends View {
    private int mWidth=1080;
    private int mHeight=1920;
    public ClockView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if(!isInEditMode()){
            mWidth= DisplayUtil.getScreenWidth(getContext());
            mHeight=DisplayUtil.getScreenHeight(getContext());
        }
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paintCircle=new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(5f);
        canvas.drawCircle(mWidth/2,mHeight/2,mWidth/2,paintCircle);
        Paint paintDegree=new Paint();
        paintDegree.setStrokeWidth(3);
        paintDegree.setAntiAlias(true);
        for(int i=0;i<24;i++){
            if(i==0||i==6||i==12||i==18){
                paintDegree.setStrokeWidth(5);
                paintDegree.setTextSize(30);
                canvas.drawLine(mWidth/2,mHeight/2-mWidth/2,mWidth/2,mHeight/2-mWidth/2+60,paintDegree);
                canvas.drawText(String.valueOf(i),mWidth/2-paintDegree.measureText(String.valueOf(i))/2,mHeight/2-mWidth/2+90,paintDegree);
            }else{
                paintDegree.setStrokeWidth(3);
                paintDegree.setTextSize(22);
                canvas.drawLine(mWidth/2,mHeight/2-mWidth/2,mWidth/2,mHeight/2-mWidth/2+30,paintDegree);
                canvas.drawText(String.valueOf(i),mWidth/2-paintDegree.measureText(String.valueOf(i))/2,mHeight/2-mWidth/2+60,paintDegree);
            }
            canvas.rotate(15,mWidth/2,mHeight/2);
        }
        Paint paintHour=new Paint();
        paintHour.setStrokeWidth(20);
        Paint paintMin=new Paint();
        paintMin.setStrokeWidth(10);
        canvas.save();
        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawLine(0,0,100,100,paintHour);
        canvas.drawLine(0,0,100,200,paintMin);
        canvas.restore();
    }
}
