package com.codyy.mobile.support.chart;

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
    private float startAngle;
    private float endAngle;
    private String id;
    private float textStartX;
    private float textStopX;
    private float textStartY;
    private float textStopY;

    public float getTextStartX() {
        return textStartX;
    }

    public void setTextStartX(float textStartX) {
        this.textStartX = textStartX;
    }

    public float getTextStopX() {
        return textStopX;
    }

    public void setTextStopX(float textStopX) {
        this.textStopX = textStopX;
    }

    public float getTextStartY() {
        return textStartY;
    }

    public void setTextStartY(float textStartY) {
        this.textStartY = textStartY;
    }

    public float getTextStopY() {
        return textStopY;
    }

    public void setTextStopY(float textStopY) {
        this.textStopY = textStopY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(float endAngle) {
        this.endAngle = endAngle;
    }

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
        BigDecimal bigDecimal = new BigDecimal(percent * 100);
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
