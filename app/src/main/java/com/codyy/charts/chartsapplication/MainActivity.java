package com.codyy.charts.chartsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mEditText;
    private HalfDashBoardChart mHalfDashBoardChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board2);
        mEditText = findViewById(R.id.et);
        mHalfDashBoardChart = findViewById(R.id.half_view);
//        Log.d("MainActivity",DisplayUtil.getScreenWidth(this)+":"+DisplayUtil.getScreenHeight(this));
    }

    public void refresh(View view) {
        mHalfDashBoardChart.setFloatSweepAngle(Float.parseFloat(mEditText.getText().toString()));
    }
}
