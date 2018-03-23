package com.codyy.charts.chartsapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.codyy.mobile.support.chart.CirclePercentChart;
import com.codyy.mobile.support.chart.DoubleCirclePercentChart;

/**
 * Created by lijian on 2018/2/28.
 */

public class CirclePercentChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
        CirclePercentChart circlePercentChart=findViewById(R.id.chart);
        CirclePercentChart circlePercentChart4=findViewById(R.id.chart4);
        DoubleCirclePercentChart doubleCirclePercentChart=findViewById(R.id.chart2);
        DoubleCirclePercentChart doubleCirclePercentChart3=findViewById(R.id.chart3);
        circlePercentChart.setPercent(88);
        circlePercentChart4.setPercent(88,"18:00-21:00","760");
        doubleCirclePercentChart.setText("23546",80f,60f);
        doubleCirclePercentChart3.setText("23546",80f,60f);
    }
}
