package com.codyy.charts.chartsapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.codyy.mobile.support.chart.Donuts;
import com.codyy.mobile.support.chart.DonutsChart;

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
        chart.setOnClickListener(new DonutsChart.OnClickListener() {
            @Override
            public void onClick(Donuts donuts) {
                Toast.makeText(DonutsActivity.this,donuts.getPercent(0)+"%",Toast.LENGTH_SHORT).show();
            }
        });

        DonutsChart chart2=findViewById(R.id.chart2);
        DonutsChart chart3=findViewById(R.id.chart3);
        chart3.setOnTextClickListener(new DonutsChart.OnTextClickListener() {
            @Override
            public void onClick(Donuts donuts) {
                Toast.makeText(DonutsActivity.this,donuts.getPercent(0)+"%",Toast.LENGTH_SHORT).show();
            }
        });
        List<Donuts> list=new ArrayList<>();
        Donuts donuts=new Donuts();
        donuts.setColor(Color.RED);
        donuts.setPercent(0.3f);
        donuts.setPercentTopText("待处理");
        list.add(donuts);
        donuts=new Donuts();
        donuts.setColor(Color.MAGENTA);
        donuts.setPercent(0.4f);
        donuts.setPercentTopText("已处理");
        list.add(donuts);
        donuts=new Donuts();
        donuts.setColor(Color.BLUE);
        donuts.setPercentTopText("处理中");
        donuts.setPercent(0.3f);
        list.add(donuts);
        chart.setData(list);
        List<Donuts> listInner=new ArrayList<>();
        donuts=new Donuts();
        donuts.setColor(Color.RED);
        donuts.setPercent(0.6f);
        donuts.setInnerText("严重");
        listInner.add(donuts);
        donuts=new Donuts();
        donuts.setColor(Color.DKGRAY);
        donuts.setPercent(0.4f);
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

        chart3.setData(list);

    }
}
