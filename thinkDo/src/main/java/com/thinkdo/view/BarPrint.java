package com.thinkdo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkdo.activity.R;

public class BarPrint extends LinearLayout {
    private TextView title, before, after, max, min;

    public BarPrint(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BarPrint);
        String str = array.getString(R.styleable.BarPrint_PrintTitle);
        int color = array.getColor(R.styleable.BarPrint_PrintContentBg, Color.rgb(0xFF, 0xFF, 0xCC));
        array.recycle();

        LayoutInflater.from(context).inflate(R.layout.view_print_bar, this);
        title = (TextView) findViewById(R.id.tv_title);
        before = (TextView) findViewById(R.id.tv_before);
        after = (TextView) findViewById(R.id.tv_after);
        min = (TextView) findViewById(R.id.tv_min);
        max = (TextView) findViewById(R.id.tv_max);

        title.setText(str);
        setContextBg(color);
    }

    public void setTitleText(String text) {
        title.setText(text);
    }

    public void setTitleText(int resId) {
        title.setText(resId);
    }

    public void setBeforeText(String text) {
        before.setText(text);
    }

    public void setBeforeText(int resId) {
        before.setText(resId);
    }

    public void setAfterText(String text) {
        after.setText(text);
    }

    public void setAfterText(int resId) {
        after.setText(resId);
    }

    public void setMinText(String text) {
        min.setText(text);
    }

    public void setMinText(int resId) {
        min.setText(resId);
    }

    public void setMaxText(String text) {
        max.setText(text);
    }

    public void setMaxText(int resId) {
        max.setText(resId);
    }

    public void setContextBg(int color) {
        before.setBackgroundColor(color);
        after.setBackgroundColor(color);
        min.setBackgroundColor(color);
        max.setBackgroundColor(color);
    }

}
