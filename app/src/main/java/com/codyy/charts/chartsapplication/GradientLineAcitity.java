package com.codyy.charts.chartsapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.codyy.mobile.support.chart.GradientLineChart;

/**
 * Created by lijian on 2018/3/2.
 */

public class GradientLineAcitity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_line);
        GradientLineChart chart=findViewById(R.id.chart);
        GradientLineChart chart2=findViewById(R.id.chart2);
        GradientLineChart chart3=findViewById(R.id.chart3);
        chart.setPercent(0.5f);
        chart2.setPercent(0.9f);
        chart3.setPercent(0.1f);
    }
}
