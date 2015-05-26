package com.thinkdo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkdo.activity.R;
import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.UnitEnum;
import com.thinkdo.entity.ValuesPair;

/**
 * Created by Administrator on 2015/5/21.
 */
public class BarDefAdd extends LinearLayout {
    private TextView tv;
    private EditText min, max;

    public BarDefAdd(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BarDefAdd);
        String title = array.getString(R.styleable.BarDefAdd_DBarTitle);
        array.recycle();

        LayoutInflater.from(context).inflate(R.layout.view_def_add, this);
        tv = (TextView) findViewById(R.id.show_title);
        min = (EditText) findViewById(R.id.editText_min);
        max = (EditText) findViewById(R.id.editText_max);

        tv.setText(title);
    }

    public void setTitleText(String text) {
        tv.setText(text);
    }

    public void setMinText(int resid) {
        min.setText(resid);
    }

    public void setMinText(String text) {
        min.setText(text);
    }

    public void setMaxText(int resid) {
        max.setText(resid);
    }

    public void setMaxText(String text) {
        max.setText(text);
    }

    public String getMinText() {
        String str = min.getText().toString();
        if (str.equals("")) {
            return GloVariable.initValue;
        }
        return str;
    }

    public String getMaxText() {
        String str = max.getText().toString();
        if (str.equals("")) {
            return GloVariable.initValue;
        }
        return str;
    }

    public void setContextTextColor(int color) {
        min.setTextColor(color);
        max.setTextColor(color);
    }

    public void setMaxEnabled(boolean enabled) {
        max.setEnabled(enabled);
        max.setBackgroundColor(Color.GRAY);
    }

    public ValuesPair getAllValues(UnitEnum unit) {
        String min = getMinText();
        String max = getMaxText();
        return new ValuesPair(min, max, unit);
    }
}
