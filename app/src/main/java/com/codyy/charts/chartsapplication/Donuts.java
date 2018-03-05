package com.codyy.charts.chartsapplication;

import android.graphics.Color;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by lijian on 2018/3/5.
 */

public class Donuts {
    private float percent;
    private int color;
    private String innerText;
    private int innerTextColor = Color.WHITE;
    private float innerTextSize = 16f;

    public float getInnerTextSize() {
        return innerTextSize;
    }

    public void setInnerTextSize(float innerTextSize) {
        this.innerTextSize = innerTextSize;
    }

    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

    public int getInnerTextColor() {
        return innerTextColor;
    }

    public void setInnerTextColor(int innerTextColor) {
        this.innerTextColor = innerTextColor;
    }

    public float getPercent() {
        return percent;
    }
    public float getPercent(int scale) {
        BigDecimal bigDecimal=new BigDecimal(percent*100);
        return bigDecimal.setScale(scale, RoundingMode.HALF_UP).floatValue();
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
