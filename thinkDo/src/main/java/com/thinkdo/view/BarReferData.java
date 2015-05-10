package com.thinkdo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkdo.activity.R;
import com.thinkdo.entity.ValuesPair;

/**
 * Created by xh on 15/5/10.
 */
public class BarReferData extends LinearLayout {
    private TextView title, leftMin, leftMid, leftMax, rightMin, rightMid, rightMax;

    public BarReferData(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BarReferData);
        int color = array.getColor(R.styleable.BarReferData_referTitleFontColor, Color.BLACK);
        String sTitle = array.getString(R.styleable.BarReferData_referTitle);

        String sLeftMin = array.getString(R.styleable.BarReferData_referLeftMinText);
        String sLeftMid = array.getString(R.styleable.BarReferData_referLeftMidText);
        String sLeftMax = array.getString(R.styleable.BarReferData_referLeftMaxText);

        String sRightMin = array.getString(R.styleable.BarReferData_referRightMinText);
        String sRightMid = array.getString(R.styleable.BarReferData_referRightMidText);
        String sRightMax = array.getString(R.styleable.BarReferData_referRightMaxText);

        boolean bgFlag = array.getBoolean(R.styleable.BarReferData_referDefaultBg, false);
        array.recycle();

        LayoutInflater.from(context).inflate(R.layout.view_refer_data_bar, this);
        title = (TextView) findViewById(R.id.tv_title);

        leftMin = (TextView) findViewById(R.id.tv_leftMin);
        leftMid = (TextView) findViewById(R.id.tv_leftMid);
        leftMax = (TextView) findViewById(R.id.tv_leftMax);

        rightMin = (TextView) findViewById(R.id.tv_rightMin);
        rightMid = (TextView) findViewById(R.id.tv_rightMid);
        rightMax = (TextView) findViewById(R.id.tv_rightMax);

        title.setText(sTitle);

        leftMin.setText(sLeftMin);
        leftMid.setText(sLeftMid);
        leftMax.setText(sLeftMax);

        rightMin.setText(sRightMin);
        rightMid.setText(sRightMid);
        rightMax.setText(sRightMax);

        title.setTextColor(color);
        if (bgFlag) {
            leftMin.setBackgroundColor(Color.WHITE);
            leftMid.setBackgroundColor(Color.WHITE);
            leftMax.setBackgroundColor(Color.WHITE);
            rightMin.setBackgroundColor(Color.WHITE);
            rightMid.setBackgroundColor(Color.WHITE);
            rightMax.setBackgroundColor(Color.WHITE);
        }
    }

    public void setTitleText(int resId) {
        title.setText(resId);
    }

    public void setLeftMinText(String text) {
        leftMin.setText(text);
    }

    public void setLeftMidText(String text) {
        leftMid.setText(text);
    }

    public void setLeftMaxText(String text) {
        leftMax.setText(text);
    }

    public void setRightMinText(String text) {
        rightMin.setText(text);
    }

    public void setRightMidText(String text) {
        rightMid.setText(text);
    }

    public void setRightMaxText(String text) {
        rightMax.setText(text);
    }

    public void setAllValues(ValuesPair left, ValuesPair right) {
        if (left != null) {
            setLeftMinText(left.getMin());
            setLeftMidText(left.getMid());
            setLeftMaxText(left.getMax());

        }
        if (right != null) {
            setRightMinText(right.getMin());
            setRightMidText(right.getMid());
            setRightMaxText(right.getMax());

        }
    }

}
