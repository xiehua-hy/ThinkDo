package com.thinkdo.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import com.thinkdo.activity.R;

/**
 * Created by Administrator on 2015/5/27.
 */
public class MyDialog {
    private Context context;
    private Dialog dialog;

    public MyDialog(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public void show(String msg) {
        if (msg == null || msg.equals("")) return;

        if (dialog != null) dialog.dismiss();
        dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.error)
                .setMessage(msg)
                .create();
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null) dialog.dismiss();

    }

    public boolean isShow() {
        return dialog.isShowing();
    }
}
