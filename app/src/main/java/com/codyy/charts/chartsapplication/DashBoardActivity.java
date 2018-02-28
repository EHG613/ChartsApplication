package com.codyy.charts.chartsapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class DashBoardActivity extends AppCompatActivity {
    private EditText mEditText;
    private HalfDashBoardChart mHalfDashBoardChart;
    private HalfDashBoardChart mHalfDashBoardChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board2);
        mEditText = findViewById(R.id.et);
        mHalfDashBoardChart = findViewById(R.id.half_view);
        mHalfDashBoardChart2 = findViewById(R.id.half_view2);
        mHalfDashBoardChart.setBottomText("设备总量");
        mHalfDashBoardChart.setTopText("344555");
        mHalfDashBoardChart2.setBottomText("周使用率");
        mHalfDashBoardChart2.setTopText("98%");
//        Log.d("DashBoardActivity",DisplayUtil.getScreenWidth(this)+":"+DisplayUtil.getScreenHeight(this));
    }

    public void refresh(View view) {
        mHalfDashBoardChart.setFloatSweepAngle(Float.parseFloat(TextUtils.isEmpty(mEditText.getText())?"0":mEditText.getText().toString()));
        mHalfDashBoardChart2.setFloatSweepAngle(Float.parseFloat(TextUtils.isEmpty(mEditText.getText())?"0":mEditText.getText().toString()));
        mHalfDashBoardChart2.setTopText(mEditText.getText().toString()+"%");
    }
}
