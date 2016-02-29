package com.thinkdo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkdo.activity.R;

public class ButtonRaise extends LinearLayout {
    private TextView tv;
    private Button btn;
    private String textInUp, textInDown;
    private boolean mChecked = false;  //true表示已举起车身

    public ButtonRaise(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ButtonRaise);
        textInUp = array.getString(R.styleable.ButtonRaise_buttonTextInUp);
        textInDown = array.getString(R.styleable.ButtonRaise_buttonTextInDown);

        array.recycle();
        LayoutInflater.from(context).inflate(R.layout.view_raise_btn, this);
        tv = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.button);

        btn.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int down, up;
                if (mChecked) {
                    down = R.drawable.ib_arrow_down_press1;
                    up = R.drawable.ib_arrow_down1;
                } else {
                    down = R.drawable.ib_arrow_up_press1;
                    up = R.drawable.ib_arrow_up1;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn.setBackgroundResource(down);
                        break;

                    case MotionEvent.ACTION_UP:
                        btn.setBackgroundResource(up);
                        break;
                }
                return false;
            }
        });

        tv.setText(textInDown);
    }

    public void setChecked(boolean checked) {
        if (checked == mChecked)
            return;

        if (checked) {
            btn.setBackgroundResource(R.drawable.ib_arrow_down1);
            tv.setText(textInUp);
        } else {
            btn.setBackgroundResource(R.drawable.ib_arrow_up1);
            tv.setText(textInDown);
        }
        mChecked = checked;
    }

    public void setBtnEnable(boolean enable) {
        btn.setEnabled(enable);
    }

    public boolean isBtnEnable() {
        return btn.isEnabled();
    }
    /**
     * @return true表示已举起车身
     *         false 表示已放下车身
     * */
    public boolean isChecked() {
        return mChecked;
    }

    public void setOnClickListener(OnClickListener listener) {
        btn.setOnClickListener(listener);
    }

    public void changeChecked() {
        setChecked(!mChecked);
    }
}
