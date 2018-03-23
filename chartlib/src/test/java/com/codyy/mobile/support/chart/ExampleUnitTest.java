package com.codyy.mobile.support.chart;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        List<GradientLineEntity> list=new ArrayList<>();
        GradientLineEntity entity=new GradientLineEntity("语文",1200);
        list.add(entity);
        entity=new GradientLineEntity("数学",1500);
        list.add(entity);
        entity=new GradientLineEntity("英语",1200);
        list.add(entity);
        entity=new GradientLineEntity("化学",1600);
        list.add(entity);
        for(GradientLineEntity e:list){
            System.out.println(e.getSubject()+":"+e.getHours());
        }
        System.out.println("start sort");
        Collections.sort(list);
        for(GradientLineEntity e:list){
            System.out.println(e.getSubject()+":"+e.getHours());
        }
        String text="毛泽东思想政治";
        System.out.println(text.substring(0,5)+":"+text.substring(5,text.length()));
    }
}