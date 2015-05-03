package com.thinkdo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkdo.activity.R;

public class WindowRealTime extends LinearLayout {
    private TextView left, mid, right, title, result;
    private LinearLayout linearLayout;

    public WindowRealTime(Context context, AttributeSet attr) {
        super(context, attr);
        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.WindowRealTime);
        String str = array.getString(R.styleable.WindowRealTime_RealTimeTitle);
        array.recycle();
        LayoutInflater.from(context).inflate(R.layout.view_realtime_window, this);

        left = (TextView) findViewById(R.id.tv_left);
        mid = (TextView) findViewById(R.id.tv_middle);
        right = (TextView) findViewById(R.id.tv_right);

        title = (TextView) findViewById(R.id.tv_title);
        result = (TextView) findViewById(R.id.tv_result);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        title.setText(str);
    }

    public void setLeftText(String text) {
        left.setText(text);
    }

    public void setLeftText(int resId) {
        left.setText(resId);
    }

    public void setMiddleText(String text) {
        mid.setText(text);
    }

    public void setMiddleText(int resId) {
        mid.setText(resId);
    }

    public void setRightText(String text) {
        right.setText(text);
    }

    public void setRightText(int resId) {
        right.setText(resId);
    }

    public void setResultText(String text) {
        result.setText(text);
    }

    public void setResultColor(int color) {
        result.setTextColor(color);
    }

    public void setWindowBackground(Drawable drawable) {
        linearLayout.setBackgroundDrawable(drawable);
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }
}
