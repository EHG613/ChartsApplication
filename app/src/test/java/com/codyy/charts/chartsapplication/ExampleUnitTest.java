package com.codyy.charts.chartsapplication;

import org.junit.Test;

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
    }
}