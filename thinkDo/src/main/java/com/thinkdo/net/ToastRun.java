package com.thinkdo.net;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/5/25.
 */
public class ToastRun implements Runnable {
    public static Context preContext;
    private Context context;
    private String msg;

    public ToastRun(Context context, String msg) {
        this.context = context;
        this.msg = msg;
    }

    @Override
    public void run() {
        if (context != null && context != preContext) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            preContext = context;
        }
    }
}
