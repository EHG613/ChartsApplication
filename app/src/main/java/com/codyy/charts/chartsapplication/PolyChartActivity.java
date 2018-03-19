package com.codyy.charts.chartsapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codyy.mobile.support.chart.GraphChart;
import com.codyy.mobile.support.chart.PolyTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lijian on 2018/3/12.
 */

public class PolyChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poly_chart);
        PolyTextView textView=findViewById(R.id.chart);
        PolyTextView textView2=findViewById(R.id.chart2);
        List<PolyTextView.PolyText> list=new ArrayList<>();
        PolyTextView.PolyText text=new PolyTextView.PolyText(Color.RED,"一年级","19小时");
        list.add(text);
        text=new PolyTextView.PolyText(Color.BLUE,"二年级","19小时");
        list.add(text);
        text=new PolyTextView.PolyText(Color.GREEN,"三年级","19小时");
        list.add(text);
        text=new PolyTextView.PolyText(Color.MAGENTA,"四年级");
        list.add(text);
        textView.setPolyTexts(list);
        list=new ArrayList<>();
        text=new PolyTextView.PolyText(Color.RED,"一年级2","19小时");
        list.add(text);
        text=new PolyTextView.PolyText(Color.BLUE,"二年级2","19小时");
        list.add(text);
        text=new PolyTextView.PolyText(Color.GREEN,"三年级2","19小时");
        list.add(text);
        text=new PolyTextView.PolyText(Color.MAGENTA,"四年级2");
        list.add(text);
        textView2.setPolyTexts(list);
    }

    private int dp2px(float dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }


}
