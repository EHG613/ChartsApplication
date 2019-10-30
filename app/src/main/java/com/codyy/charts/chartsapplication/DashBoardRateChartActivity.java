package com.codyy.charts.chartsapplication;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.codyy.mobile.support.chart.DashBoardRateChart;

/**
 * Created by lijian on 2018/3/1.
 */

public class DashBoardRateChartActivity extends AppCompatActivity {
    DashBoardRateChart chart;
    EditText mEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_rate);
        chart=findViewById(R.id.chart);
        mEditText=findViewById(R.id.et);
        chart.setBottomText("设备使用率");
        chart.setPercentText(100f);
    }

    public void refresh(View view) {
        if(TextUtils.isEmpty(mEditText.getText()))return;
        chart.setPercentText(Float.parseFloat(mEditText.getText().toString()),0);
    }
}
