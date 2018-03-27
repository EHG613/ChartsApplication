package com.codyy.charts.chartsapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.codyy.mobile.support.chart.GradientRoundedRectangleChart;

/**
 * Created by lijian on 2018/2/28.
 */

public class GradientRoundedRectangleActivity extends AppCompatActivity {
    private GradientRoundedRectangleChart mGradientRoundedRectangleChart;
    private GradientRoundedRectangleChart mGradientRoundedRectangleChart2;
    private GradientRoundedRectangleChart mGradientRoundedRectangleChart3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_rounded_rectangle);
        mGradientRoundedRectangleChart=findViewById(R.id.chart);
        mGradientRoundedRectangleChart2=findViewById(R.id.chart2);
        mGradientRoundedRectangleChart3=findViewById(R.id.chart3);
        mGradientRoundedRectangleChart.setTopText("27583");
        mGradientRoundedRectangleChart2.setTopText("28412");
        mGradientRoundedRectangleChart3.setTopText("28412");
    }
}
