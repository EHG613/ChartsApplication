package com.codyy.charts.chartsapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.codyy.mobile.support.chart.GradientLineChart;
import com.codyy.mobile.support.chart.GradientLineEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijian on 2018/3/2.
 */

public class GradientLineAcitity extends AppCompatActivity {
    GradientLineChart chart2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_line);
        chart2 = findViewById(R.id.chart2);
//        List<GradientLineEntity> list=new ArrayList<>();
//        GradientLineEntity entity=new GradientLineEntity("语文",1200);
//        list.add(entity);
//        entity=new GradientLineEntity("数学",1500);
//        list.add(entity);
//        entity=new GradientLineEntity("毛泽东思想政治",1200);
//        list.add(entity);
//        entity=new GradientLineEntity("化学",600);
//        list.add(entity);
//        chart2.setList(list);
    }

    int count = 3;

    public void click(View view) {
        List<GradientLineEntity> list = new ArrayList<>();
        for (int i = 1; i < count; i++) {

            GradientLineEntity entity = new GradientLineEntity("语文" + i, 120 * i);
            list.add(entity);

        }chart2.setList(list);
        count++;
        chart2.post(new Runnable() {
            @Override
            public void run() {
                chart2.requestLayout();
            }
        });

    }
}
