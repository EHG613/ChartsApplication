package com.codyy.charts.chartsapplication;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.codyy.mobile.support.chart.BarChart;
import com.codyy.mobile.support.chart.BarChartBackgroundView;
import com.codyy.mobile.support.chart.DisplayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lijian on 2018/3/12.
 */

public class BarChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        BarChart barChart = findViewById(R.id.bar);
        final TextView textView = findViewById(R.id.tv_tip);
        textView.setText("手指按压图表条目,可查看具体内容");
        Log.e("150f=", DisplayUtil.dip2px(this, 150f)+"");
        BarChartBackgroundView view = findViewById(R.id.chart);
        view.setySpace(10);
        int radius = dp2px(2f);
        List<BarChart.Point> points = new ArrayList<>();
        int barWidth = dp2px(12f);
        int xSpace = dp2px(40f);
        int currentX = dp2px(15f);
        int height = -dp2px(150f);
        int ySpace = -dp2px(150f) / 100;
        for (int i = 0; i < 365; i++) {
            BarChart.Point point = new BarChart.Point();
            point.setxAbbrText("坐标坐标坐标" + i);
            int yVal = new Random().nextInt(1500);
            int y = -dp2px(yVal / 10);
            int y1Val = new Random().nextInt(100);
            int y1 = ySpace * y1Val;
            point.setY1(y1);
            point.setY1Val(y1Val + "");
            int y2Val = new Random().nextInt(100);
            int y2 = ySpace * y2Val;
            point.setY2(y2);
            point.setY2Val(y2Val + "");
            int y3Val = new Random().nextInt(100);
            int y3 = ySpace * y3Val;
            point.setY3(y3);
            point.setY3Val(y3Val + "");
            int y4Val = new Random().nextInt(100);
            int y4 = ySpace * y4Val;
            point.setY4(y4);
            point.setY4Val(y4Val + "");

            point.setX(currentX + barWidth / 2);
            point.setBarWidth(barWidth);
            point.setY(y);
            point.setyVal(yVal + "");
            Path path = new Path();
            RectF f = new RectF();
            f.left = currentX;
            f.top = y;
            f.bottom = 0;
            currentX += barWidth;
            f.right = currentX;
            path.addRoundRect(f, radius, radius, Path.Direction.CCW);
            path.close();
            point.setPath(path);
            LinearGradient linearGradient = new LinearGradient(0, 0, 0, y, Color.parseColor("#25beb8"), Color.parseColor("#43e795"), Shader.TileMode.CLAMP);
//            LinearGradient linearGradient = new LinearGradient(0, 0, 0, y, Color.parseColor("#f26d81"), Color.parseColor("#ffb78a"), Shader.TileMode.CLAMP);
            point.setLinearGradient(linearGradient);
            currentX += xSpace;
            points.add(point);
        }
        barChart.setPoints(points);
        barChart.setOnClickListener(new BarChart.OnClickListener() {
            @Override
            public void onClick(BarChart.Point point) {
                textView.setText("设备数:" + point.getyVal() + ",故障率:" + point.getY1Val() + "%,维修率:" + point.getY2Val() + "%,不良率:" + point.getY3Val() + "%,淘汰率:" + point.getY4Val());
            }

            @Override
            public void cancel() {
                textView.setText("手指按压图表条目,可查看具体内容");
            }
        });
    }

    private int dp2px(float dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }


}
