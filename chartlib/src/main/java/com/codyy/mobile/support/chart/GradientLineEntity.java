package com.codyy.mobile.support.chart;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * Created by lijian on 2018/3/23.
 */

public class GradientLineEntity implements Comparable<GradientLineEntity> {
    private String subject;
    private int hours;

    public GradientLineEntity(String subject, int hours) {
        this.subject = subject;
        this.hours = hours;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public static String getMaxLengthText(@NonNull List<GradientLineEntity> list) {
        String text = "";
        for (GradientLineEntity entity : list) {
            if (text.length() < entity.getSubject().length()) {
                text = entity.getSubject();
            }
        }
        return text;
    }

    public static String getMaxLengthHours(@NonNull List<GradientLineEntity> list, @NonNull String suffix) {
        return list.get(0).getHours() + " " + suffix;
    }

    public static int getMaxHours(@NonNull List<GradientLineEntity> list) {
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
