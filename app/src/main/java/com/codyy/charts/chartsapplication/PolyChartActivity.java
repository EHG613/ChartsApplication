package com.codyy.charts.chartsapplication;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codyy.mobile.support.chart.PolyTextView;

import java.util.ArrayList;
import java.util.List;

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
        PolyTextView.PolyText text=new PolyTextView.PolyText(Color.RED,"一年级一年级一年级一年级一年级一年级","19小时");
        list.add(text);
        text=new PolyTextView.PolyText(Color.BLUE,"二年级","19小时");
        list.add(text);
        text=new PolyTextView.PolyText(Color.GREEN,"三年级","19小时");
        list.add(text);
        text=new PolyTextView.PolyText(Color.MAGENTA,"四年级");
        list.add(text);
        textView.setPolyTexts(list);
        list=new ArrayList<>();
        text=new PolyTextView.PolyText(Color.RED,"111","1","50.0%");
        list.add(text);
        text=new PolyTextView.PolyText(Color.BLUE,"222","1","50.0%");
        list.add(text);
//        text=new PolyTextView.PolyText(Color.GREEN,"三年级2","19小时","10%");
//        list.add(text);
//        text=new PolyTextView.PolyText(Color.MAGENTA,"四年级2","11","10%");
//        list.add(text);
        textView2.setPolyTexts(list);
    }

    private int dp2px(float dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }


}
