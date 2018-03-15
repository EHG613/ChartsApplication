package com.codyy.charts.chartsapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codyy.mobile.support.chart.GraphChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lijian on 2018/3/12.
 */

public class GraphChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_chart);
        GraphChart chart = findViewById(R.id.chart);
        Log.d("Graph", dp2px(150f) + "");
        int x = dp2px(2f);
        List<GraphChart.Point> mPoints = new ArrayList<>();
        for (int i = 0; i < 157; i++) {
            int yVal =  new Random().nextInt(1500);
            int y = dp2px(yVal / 10);
            int y1Val =  new Random().nextInt(1500);
            int y1 = dp2px(y1Val / 10);
            int y2Val =  new Random().nextInt(1500);
            int y2 = dp2px(y2Val / 10);
            mPoints.add(new GraphChart.Point(x * i,i, y, y1, y2, yVal, y1Val, y2Val));
        }
        chart.setData(mPoints);

    }

    private int dp2px(float dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }


}
