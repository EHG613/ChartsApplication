package com.codyy.mobile.support.chart;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自动计算文字宽度,自适应换行
 * Created by lijian on 2018/3/17.
 */

public class PolyTextView extends View {
    public PolyTextView(Context context) {
        this(context,null);
    }

    public PolyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PolyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
