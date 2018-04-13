package com.codyy.mobile.support.chart;

import android.support.annotation.NonNull;
import android.text.TextPaint;

import java.util.List;

/**
 * Created by lijian on 2018/3/23.
 */

public class GradientLineEntity implements Comparable<GradientLineEntity> {
    private String subject;
    private float hours;

    public GradientLineEntity(String subject, float hours) {
        this.subject = subject;
        this.hours = hours;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public static float getMaxLengthText(@NonNull List<GradientLineEntity> list, TextPaint textPaint) {
        String text = "";
        for (int i=0;i< list.size();i++) {
            if(textPaint.measureText(text)<textPaint.measureText(list.get(i).getSubject())){
                text = list.get(i).getSubject();
            }
        }
        return  text.length() > 5 ? textPaint.measureText(text.substring(0, 5)) : textPaint.measureText(text);
    }

    public static String getMaxLengthHours(@NonNull List<GradientLineEntity> list, @NonNull String suffix) {
        return list.get(0).getHours() + " " + suffix;
    }

    public static float getMaxHours(@NonNull List<GradientLineEntity> list) {
        return list.get(0).getHours();
    }

    @Override
    public int compareTo(@NonNull GradientLineEntity o) {
        if (this.hours < o.getHours()) {
            return 1;
        } else if (this.hours > o.getHours()) {
            return -1;
        }
        return 0;
    }
}
