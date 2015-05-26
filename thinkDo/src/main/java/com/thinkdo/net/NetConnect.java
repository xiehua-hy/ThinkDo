package com.thinkdo.net;

import android.content.Context;
import android.os.Handler;


/**
 * Created by Administrator on 2015/5/25.
 */
public class NetConnect extends Thread {
    private SocketClient client;
    private Handler handler;
    private int questCode;
    private boolean isClose = false;
    private Context context;

    public NetConnect(Context context, Handler handler, int questCode) {
        this.context = context;
        this.handler = handler;
        this.questCode = questCode;
        ToastRun.preContext = null;
    }

    @Override
    public void run() {
        while (!isClose) {
            if (!client.isClose()) {
                client = new SocketClient(context, handler);
                client.start();
                client.send(questCode);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public void close() {
        isClose = true;
        if (!client.isClose()) client.onStop();
    }

}
