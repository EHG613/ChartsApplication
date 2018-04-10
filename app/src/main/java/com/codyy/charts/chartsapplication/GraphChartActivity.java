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
        chart.setySpace((int) (Math.ceil(49 / 50f)*50f/5f));
        float yy = (float) (Math.ceil(49 / 50f) * 50 / 150f);
        Log.d("Graph", dp2px(150f) + "");
        int x = dp2px(2f);
        List<GraphChart.Point> mPoints = new ArrayList<>();
        for (int i = 0; i < 157; i++) {
            int yVal =  new Random().nextInt(50);
            int y = dp2px(yVal / yy);
            int y1Val =  new Random().nextInt(50);
            int y1 = dp2px(y1Val / yy);
            int y2Val =  new Random().nextInt(50);
            int y2 = dp2px(y2Val / yy);
            mPoints.add(new GraphChart.Point(x * i,i, y, y1, y2, yVal, y1Val, y2Val));
        }
        chart.setData(mPoints);

    }

    private int dp2px(float dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }


}
