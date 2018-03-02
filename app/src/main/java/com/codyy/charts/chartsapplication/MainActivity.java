package com.codyy.charts.chartsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lijian on 2018/3/2.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m);
    }

    public void circlePercent(View view) {
        startActivity(new Intent(this,CirclePercentChartActivity.class));
    }

    public void dashboard(View view) {
        startActivity(new Intent(this,DashBoardActivity.class));
    }

    public void dashboardRate(View view) {
        startActivity(new Intent(this,DashBoardRateChartActivity.class));
    }

    public void gradientRoundedRectangle(View view) {
        startActivity(new Intent(this,GradientRoundedRectangleActivity.class));
    }

    public void gradientLine(View view) {
    }
}
