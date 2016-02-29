package com.thinkdo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;

/**
 * Created by Administrator on 2015/5/7.
 */
public class WindowSpecial extends LinearLayout {
    private TextView title, result;
    private ImageView iv;

    public WindowSpecial(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WindowSpecial);
        String sTitle = array.getString(R.styleable.WindowSpecial_specialTitle);
        int imageId = array.getResourceId(R.styleable.WindowSpecial_specialSrc, R.drawable.if_wheelbase_1);
        array.recycle();

        LayoutInflater.from(context).inflate(R.layout.view_special_window, this);
        title = (TextView) findViewById(R.id.tv_title);
        result = (TextView) findViewById(R.id.tv_result);
        iv = (ImageView) findViewById(R.id.imageView);

        title.setText(sTitle);
        iv.setImageResource(imageId);
    }

    public void setTitleText(String msg) {
        title.setText(msg);
    }

    public void setTestText(String text) {
        if (text == null) return;
        if (text.equals(MainApplication.initValue)) {
            result.setText(R.string.default_signal);
        } else {
            result.setText(text);
        }
    }

    public void setImageSrc(int resourceId) {
        iv.setImageResource(resourceId);
    }
}
