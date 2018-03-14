package com.codyy.charts.chartsapplication;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testColor()throws Exception{
//        ColorUtil.colorString2RGB("#FFE8E8E8");
//        System.out.println(ColorUtil.getCurrentColor("#57C0FA","#1209FC",100));
        System.out.println(Math.sin(Math.PI*30.0/180.0));
        System.out.println(Math.cos(Math.PI*30.0/180.0));
        CalcUtil.Circle circle=new CalcUtil.Circle(0,0,1);
        CalcUtil.Point point= circle.computeCoordinates(90);
        System.out.println(point.x+","+point.y);
        float [] angles=CalcUtil.calcAngle(0,0,1363.3268f,0,1363.3268f,405.4554f);
        double mRadius=CalcUtil.lineSpace(0,0,1363.3268f,405.4554f);
//        System.out.println(Arrays.toString(CalcUtil.calcAngle(0,0,1363.3268f,0,1363.3268f,405.4554f)));
        float x1 = (float) (0 + mRadius/2 * Math.cos((angles[1]) * Math.PI / 180));
        float y1 = (float) (0 + mRadius/2 * Math.sin((angles[1]) * Math.PI / 180));
        System.out.println(x1+";"+y1);
    }
    @Test
    public void testCoordinate() throws Exception{
//        圆心:540,315,半径:300.0点:752.38495,166.69455
//        圆心:540,315,半径:300.0点:691.38074,523.682
//        圆心:540,315,半径:300.0点:553.55646,577.90796
//        圆心:540,315,半径:300.0点:289.20502,211.88284
        int r=300;
        int cx=540;int cy=315; int sx=540;int sy=cy-r;

        float[] angles=CalcUtil.calcAngle(cx,cy,sx,sy,752,166);
        float[] angles1=CalcUtil.calcAngle(cx,cy,sx,sy,691,523);
        float[] angles2=CalcUtil.calcAngle(cx,cy,sx,sy,553,577);
        float[] angles3=CalcUtil.calcAngle(cx,cy,sx,sy,289,211);
        float[] angles4=CalcUtil.calcAngle(cx,cy,sx,sy,cx,cy+r);
        float[] angles5=CalcUtil.calcAngle(cx,cy,sx,sy,526,582);
        System.out.println(Arrays.toString(angles));
        System.out.println(Arrays.toString(angles1));
        System.out.println(Arrays.toString(angles2));
        System.out.println(Arrays.toString(angles3));
        System.out.println(Arrays.toString(angles4));
        System.out.println(Arrays.toString(angles5));
    }
}