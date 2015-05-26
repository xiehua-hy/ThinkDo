package com.thinkdo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkdo.activity.R;
import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.ValuesPair;

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
        if (text == null) return;
        if (text.equals(GloVariable.initValue)) {
            left.setText(R.string.default_signal);
        } else {
            left.setText(text);
        }
    }

    public void setMiddleText(String text) {
        if (text == null) return;
        if (text.equals(GloVariable.initValue)) {
            mid.setText(R.string.default_signal);
        } else {
            mid.setText(text);
        }
    }

    public void setRightText(String text) {
        if (text == null) return;
        if (text.equals(GloVariable.initValue)) {
            right.setText(R.string.default_signal);
        } else {
            right.setText(text);
        }
    }

    public void setResultText(String text) {
        if (text == null) return;
        if (text.equals(GloVariable.initValue)) {
            result.setText(R.string.default_signal);
        } else {
            result.setText(text);
        }
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


    public void setAllValues(ValuesPair values) {
        this.setAllValues(values, true);
    }

    /**
     * @param ascent true 表示左边的值小,右边的值大;
     *               false 左边的大,右边的值小
     */
    public void setAllValues(ValuesPair values, boolean ascent) {
        if (values == null) return;
        if (ascent) {
            setLeftText(values.getMin());
            setRightText(values.getMax());
        } else {
            setLeftText(values.getMax());
            setRightText(values.getMin());
        }
        setMiddleText(values.getMid());
    }
}
