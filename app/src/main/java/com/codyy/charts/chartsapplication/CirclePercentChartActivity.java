package com.codyy.charts.chartsapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lijian on 2018/2/28.
 */

public class CirclePercentChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
        CirclePercentChart circlePercentChart=findViewById(R.id.chart);
        circlePercentChart.setPercent(88);
    }
}
