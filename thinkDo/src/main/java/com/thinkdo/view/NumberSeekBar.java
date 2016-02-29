package com.thinkdo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.thinkdo.activity.R;

/**
 * Created by Administrator on 2015/6/24.
 */
public class NumberSeekBar extends LinearLayout {
    private final int maxProgress = 400;
    private SeekBar seekBar;
    private TextView tv;

    public NumberSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NumberSeekBar);
        String str = array.getString(R.styleable.NumberSeekBar_SeeKBarTitle);
        array.recycle();

        LayoutInflater.from(context).inflate(R.layout.view_seek_bar, this);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        seekBar.setMax(maxProgress);
        tv_title.setText(str);
        tv = (TextView) findViewById(R.id.textView1);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setProgress(int progress) {
        progress = progress > maxProgress ? maxProgress : progress;
        progress = progress < 0 ? 0 : progress;
        seekBar.setProgress(progress);
    }

    public int getProgress() {
        return seekBar.getProgress();
    }

}
