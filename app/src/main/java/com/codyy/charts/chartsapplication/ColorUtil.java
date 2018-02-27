package com.codyy.charts.chartsapplication;

import android.support.annotation.FloatRange;

/**
 * Created by lijian on 2018/2/27.
 */

public class ColorUtil {
    public static int[] colorString2RGB(String color) {
        if (color == null || color.length() == 0) {
            throw new IllegalArgumentException("color can not be null value");
        }
        if (color.length() < 6) {
            throw new IllegalArgumentException("the color is illegal,ex:#E8E8E8");
        }
        color = color.replace("#", "").replace("0x", "").replace("0X", "");
        int[] rgb = new int[3];
        if (color.length() == 6) {
            rgb[0] = Integer.parseInt(color.substring(0, 2), 16);
            rgb[1] = Integer.parseInt(color.substring(2, 4), 16);
            rgb[2] = Integer.parseInt(color.substring(4, 6), 16);
        }
        if (color.length() == 8) {
            rgb[0] = Integer.parseInt(color.substring(2, 4), 16);
            rgb[1] = Integer.parseInt(color.substring(4, 6), 16);
            rgb[2] = Integer.parseInt(color.substring(6, 8), 16);
        }
//        System.out.println(rgb[0] + "," + rgb[1] + "," + rgb[2]);
        return rgb;
    }

    public static String getCurrentColor(String startColor, String endColor, @FloatRange(from = 0, to = 100) float percent) {
        int[] startRgb = colorString2RGB(startColor);
        int[] endRgb = colorString2RGB(endColor);
        int grainR = (int) (startRgb[0] + (endRgb[0] - startRgb[0]) / 100.0f * percent);
        int grainG = (int) (startRgb[1] + (endRgb[1] - startRgb[1]) / 100.0f * percent);
        int grainB = (int) (startRgb[2] + (endRgb[2] - startRgb[2]) / 100.0f * percent);
//        System.out.println("" + grainR + "," + grainG + "," + grainB);
        return ("#" + (grainR > 9 ? Integer.toHexString(grainR) : "0" + Integer.toHexString(grainR)) + (grainG > 9 ? Integer.toHexString(grainG) : "0" + Integer.toHexString(grainG)) + (grainB > 9 ? Integer.toHexString(grainB) : "0" + Integer.toHexString(grainB))).toUpperCase();

    }

}
