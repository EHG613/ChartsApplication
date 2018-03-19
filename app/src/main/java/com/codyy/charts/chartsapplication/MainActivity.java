package com.codyy.charts.chartsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import com.codyy.mobile.support.chart.DisplayUtil;

/**
 * Created by lijian on 2018/3/2.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m);
        TextPaint textPaint=new TextPaint();
        textPaint.setTextSize(DisplayUtil.dip2px(this,16f));
        Log.d("MainActivity",Math.abs(textPaint.getFontMetrics().ascent)+Math.abs(textPaint.getFontMetrics().descent)+"");
    }

    public void circlePercent(View view) {
        startActivity(new Intent(this, CirclePercentChartActivity.class));
    }

    public void dashboard(View view) {
        startActivity(new Intent(this, DashBoardActivity.class));
    }

    public void dashboardRate(View view) {
        startActivity(new Intent(this, DashBoardRateChartActivity.class));
    }

    public void gradientRoundedRectangle(View view) {
        startActivity(new Intent(this, GradientRoundedRectangleActivity.class));
    }

    public void gradientLine(View view) {
        startActivity(new Intent(this, GradientLineAcitity.class));
    }

    public void donuts(View view) {
        startActivity(new Intent(this, DonutsActivity.class));
    }

    public void graph(View view) {
        startActivity(new Intent(this, GraphChartActivity.class));
    }

    public void bar(View view) {
        startActivity(new Intent(this, BarChartActivity.class));
    }

    public void poly(View view) {
        startActivity(new Intent(this,PolyChartActivity.class));
    }
}
