package com.codyy.charts.chartsapplication;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by sudan on 2018/2/23.
 */

public class DisplayUtil {

    public static int px2dip(Context context,float px){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int) (px/scale+0.5f);
    }
    public static int dip2px(Context context,float dip){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int) (dip*scale+0.5f);
    }
    public static int px2sp(Context context,float px){
        final float scale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px/scale+0.5f);
    }
    public static int sp2px(Context context,float dip){
        final float scale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (dip*scale+0.5f);
    }
    public static int dp2px(Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
    public static int sp2px(Context context, int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,context.getResources().getDisplayMetrics());
    }
    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
