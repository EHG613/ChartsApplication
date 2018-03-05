package com.codyy.charts.chartsapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijian on 2018/3/5.
 */

public class DonutsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donuts);
        DonutsChart chart=findViewById(R.id.chart);
        DonutsChart chart2=findViewById(R.id.chart2);
        List<Donuts> list=new ArrayList<>();
        Donuts donuts=new Donuts();
        donuts.setColor(Color.RED);
        donuts.setPercent(0.5f);
        list.add(donuts);
        donuts=new Donuts();
        donuts.setColor(Color.MAGENTA);
        donuts.setPercent(0.3f);
        list.add(donuts);
        donuts=new Donuts();
        donuts.setColor(Color.BLUE);
        donuts.setPercent(0.2f);
        list.add(donuts);
        chart.setData(list);
        List<Donuts> listInner=new ArrayList<>();
        donuts=new Donuts();
        donuts.setColor(Color.RED);
        donuts.setPercent(0.5f);
        donuts.setInnerText("严重");
        listInner.add(donuts);
        donuts=new Donuts();
        donuts.setColor(Color.DKGRAY);
        donuts.setPercent(0.5f);
        donuts.setInnerText("预警");
        listInner.add(donuts);
        chart.setInnerData(listInner);
        chart2.setData(list);
        listInner=new ArrayList<>();
        donuts=new Donuts();
        donuts.setPercent(1f);
        donuts.setColor(Color.BLUE);
        listInner.add(donuts);
        chart2.setInnerData(listInner);
        chart2.setCenterTextSize(20f);
        chart2.setCenterText("设备");

    }
}
