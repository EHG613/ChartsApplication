package com.codyy.charts.chartsapplication;

import java.math.BigDecimal;

/**
 * Created by lijian on 2018/3/1.
 */

public class CalcUtil {
    //// 点到直线的最短距离的判断 点（x0,y0） 到由两点组成的线段（x1,y1） ,( x2,y2 )
    public static double pointToLine(float x1, float y1, float x2, float y2, float x0,
                                     float y0) {
        double space = 0;
        double a, b, c;
        a = lineSpace(x1, y1, x2, y2);// 线段的长度
        b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离
        c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离
        if (c <= 0.000001 || b <= 0.000001) {
            space = 0;
            return space;
        }
        if (a <= 0.000001) {
            space = b;
            return space;
        }
        if (c * c >= a * a + b * b) {
            space = b;
            return space;
        }
        if (b * b >= a * a + c * c) {
            space = c;
            return space;
        }
        double p = (a + b + c) / 2;// 半周长
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
        space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
        return space;
    }

    // 计算两点之间的距离
    public static double lineSpace(float x1, float y1, float x2, float y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)
                * (y1 - y2));
        return lineLength;
    }
//    a^2+b^2-2abcosC=c^2
//    a^2+c^2-2accosB=b^2
//    c^2+b^2-2cbcosA=a^2

    /**
     * 已知三点坐标,求三角形各个夹角角度
     * @return 夹角数组
     */
    public static float[] calcAngle(float x1, float y1, float x2, float y2,float x3,float y3){
        double a=lineSpace(x1,y1,x2,y2);
        double b=lineSpace(x2,y2,x3,y3);
        double c=lineSpace(x1,y1,x3,y3);
        double cosa=(Math.pow(b,2)+Math.pow(c,2)-Math.pow(a,2))/(c*b*2);
        double cosb=(Math.pow(a,2)+Math.pow(c,2)-Math.pow(b,2))/(c*a*2);
        double cosc=(Math.pow(b,2)+Math.pow(a,2)-Math.pow(c,2))/(a*b*2);
        float A= convertCoordinates(0,Math.acos(cosa)*180/Math.PI);
        float B= convertCoordinates(0,Math.acos(cosb)*180/Math.PI);
        float C= convertCoordinates(0,Math.acos(cosc)*180/Math.PI);
        return new float[]{A,B,C};
    }
    public static class Point {
        public float x, y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * 已知圆心(x,y)和半径radius,计算圆上任意角度的坐标
     */
    public static class Circle {
        private float mPointX, mPointY;
        private float mRadius;

        public Circle(float x, float y, float radius) {
            mPointX = x;
            mPointY = y;
            mRadius = radius;
        }

        public Point computeCoordinates(float angle) {
            return new Point(mPointX + mRadius * convertCoordinates(1,Math.cos(angle * Math.PI / 180)),
                    mPointY + mRadius * convertCoordinates(1,Math.sin(angle * Math.PI / 180)));
        }
    }

    /**
     * double 转float,四舍五入
     * @param scale 保留小数位数
     * @param d double value
     * @return float
     */
    private static float convertCoordinates(int scale,double d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
