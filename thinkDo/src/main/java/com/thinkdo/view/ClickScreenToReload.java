package com.thinkdo.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.net.NetQuest;
import com.thinkdo.net.NetSingleConnect;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;

/**
 * Created by Administrator on 2015/6/24.
 */
public class ClickScreenToReload extends FrameLayout {
    private LinearLayout layout;
    private ScrollView scrollView;
    private NumberSeekBar bar1, bar2, bar3, bar4, bar5;
    private Button btn;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    setLoadFail();
                    break;
                case 1:
                    String reply = msg.getData().getString(MainApplication.head);
                    if (reply != null) {
                        int backCode = CommonUtil.getQuestCode(reply);
                        if (backCode == MainApplication.getShine) {
                            setLoad(reply);
                        }
                    }
                    break;
            }
            return true;
        }
    });


    public ClickScreenToReload(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_clickroreload, this);
        layout = (LinearLayout) findViewById(R.id.linearLayout_reload);
        scrollView = (ScrollView) findViewById(R.id.load);
        bar1 = (NumberSeekBar) findViewById(R.id.bar1);
        bar2 = (NumberSeekBar) findViewById(R.id.bar2);
        bar3 = (NumberSeekBar) findViewById(R.id.bar3);
        bar4 = (NumberSeekBar) findViewById(R.id.bar4);
        bar5 = (NumberSeekBar) findViewById(R.id.bar5);
        btn = (Button) findViewById(R.id.btn_submit);

        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new NetSingleConnect(handler, MainApplication.getShine);
            }
        });
        new NetSingleConnect(handler, MainApplication.getShine);
    }

    public void questHost() {
        int num1 = bar1.getVisibility() == View.GONE ? -1 : bar1.getProgress();
        int num2 = bar2.getVisibility() == View.GONE ? -1 : bar2.getProgress();
        int num3 = bar3.getVisibility() == View.GONE ? -1 : bar3.getProgress();
        int num4 = bar4.getVisibility() == View.GONE ? -1 : bar4.getProgress();
        int num5 = bar5.getVisibility() == View.GONE ? -1 : bar5.getProgress();
        String data = String.format("%d|%d|%d|%d|%d", num1, num2, num3, num4, num5);
        new NetQuest(MainApplication.shineSet, 0, data);
    }

    public void setOnSubmitListener(OnClickListener listener) {
        btn.setOnClickListener(listener);
    }

    public void setLoad(String reply) {
        NumberSeekBar[] bars = {bar1, bar2, bar3, bar4, bar5};

        String[] array = SocketClient.parseData(reply);
        if (array.length != 5) return;
        int[] numArray = {-1, -1, -1, -1, -1};

        for (int i = 0; i < array.length; i++) {
            try {
                numArray[i] = Integer.parseInt(array[i]);
            } catch (NumberFormatException e) {

            }
            if (numArray[i] != -1) {
                bars[i].setProgress(numArray[i]);
                bars[i].setVisibility(VISIBLE);
            }
        }
        layout.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }

    public void setLoadFail() {
        layout.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
    }

}
