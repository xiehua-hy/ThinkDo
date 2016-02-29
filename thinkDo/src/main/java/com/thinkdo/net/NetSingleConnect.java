package com.thinkdo.net;

import android.os.Handler;

/**
 * Created by Administrator on 2015/6/8.
 */
public class NetSingleConnect extends Thread {
    protected HttpSocket client;
    protected Handler handler;
    protected int questCode;
    private int param;
    private String msg;

    public NetSingleConnect(Handler handler, int questCode) {
        this(handler, questCode, 0, null);
    }


    public NetSingleConnect(Handler handler, int questCode, int param, String msg) {
        this.handler = handler;
        this.questCode = questCode;
        this.param = param;
        this.msg = msg;
        start();

    }

    @Override
    public void run() {
        client = new HttpSocket(handler);
        client.send(questCode, param, msg);
    }

    public void close() {
        if (client != null) client.onStop();
    }
}
