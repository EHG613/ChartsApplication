package com.codyy.charts.chartsapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

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
        final EditText editText=findViewById(R.id.et);

        final GraphChart chart = findViewById(R.id.chart);
        chart.setySpace((int) (Math.ceil(49 / 50f)*50f/5f));
        final float yy = (float) (Math.ceil(49 / 50f) * 50 / 150f);
        Log.d("Graph", dp2px(150f) + "");
        setData(chart, yy,7);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE&& !TextUtils.isEmpty(editText.getText())) {
                    setData(chart, yy,Integer.parseInt(editText.getText().toString()));
                    return true;
                }
                return false;
            }
        });
    }

    private void setData(GraphChart chart, float yy,int size) {
        int x=0;
//        if(size>0&&size<7) {
//            x= dp2px(312) / (size);
//        }else if(size>=7){
//            x= (int) (dp2px(48)/(Math.ceil(size/7f)));
//        }
        List<GraphChart.Point> mPoints = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int yVal =  new Random().nextInt(50);
            int y = dp2px(yVal / yy);
            int y1Val =  new Random().nextInt(50);
            int y1 = dp2px(y1Val / yy);
            int y2Val =  new Random().nextInt(50);
            int y2 = dp2px(y2Val / yy);
            mPoints.add(new GraphChart.Point(x * i,i, y, y1, y2, yVal, y1Val, y2Val,i+""));
        }
        chart.setData(mPoints);
    }

    private int dp2px(float dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }


}
