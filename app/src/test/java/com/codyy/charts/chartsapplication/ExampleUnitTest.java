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
        System.out.println(ColorUtil.getCurrentColor("#57C0FA","#1209FC",100));
    }
}