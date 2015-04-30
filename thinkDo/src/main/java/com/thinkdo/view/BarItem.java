package com.thinkdo.view;


import com.thinkdo.activity.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BarItem extends RelativeLayout {
    private ImageView imageLeft, imageRight;
    private TextView tv;
    private RelativeLayout relativeLay;

    public BarItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BarItem);
        int icon = array.getResourceId(R.styleable.BarItem_barIcon, 0);
        int nextIcon = array.getResourceId(R.styleable.BarItem_barNextIcon, R.drawable.ic_action_next_item);
        String title = array.getString(R.styleable.BarItem_barTitle);
        int color = array.getColor(R.styleable.BarItem_barTextColor, getResources().getColor(R.color.font_black));
        int bg = array.getResourceId(R.styleable.BarItem_barBg, R.drawable.selector_bar);
        array.recycle();

        LayoutInflater.from(context).inflate(R.layout.view_set_bar, this);
        relativeLay = (RelativeLayout) findViewById(R.id.relativeLayout);
        imageLeft = (ImageView) findViewById(R.id.imageView1);
        imageRight = (ImageView) findViewById(R.id.imageView2);
        tv = (TextView) findViewById(R.id.textView);
        imageLeft.setImageResource(icon);
        imageRight.setImageResource(nextIcon);
        tv.setText(title);
        tv.setTextColor(color);
        relativeLay.setBackgroundResource(bg);

    }

    public void setIcon(int id) {
        if (id == 0) return;
        imageLeft.setImageResource(id);
    }

    public void setNextIcon(int id) {
        imageRight.setImageResource(id);
    }

    public void setTitle(String text) {
        tv.setText(text);
    }

    public void setTextColor(int color) {
        tv.setTextColor(color);
    }

    public void setBackgroundResource(int resid) {
        relativeLay.setBackgroundResource(resid);
    }


}
