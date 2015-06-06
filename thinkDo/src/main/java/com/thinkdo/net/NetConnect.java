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
    private boolean picFlag = false;

    public NetConnect(Context context, Handler handler, int questCode) {
        this(context, handler, questCode, false);
    }

    public NetConnect(Context context, Handler handler, int questCode, boolean isPic) {
        this.context = context;
        this.handler = handler;
        this.questCode = questCode;
        ToastRun.preContext = null;
        this.picFlag = isPic;
        start();
    }

    @Override
    public void run() {
        while (!isClose) {
            if (client == null || client.isClosed()) {
                if (!picFlag) client = new SocketClient(context, handler);
                else client = new SocketClientPic(context, handler);

                client.send(questCode);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void send(int questCode, int param, String data) {
        if (client != null && !client.isClosed()) {
            client.send(questCode, param, data);
        }
    }


    public void close() {
        isClose = true;
        if (client != null) client.onStop();
    }

}
