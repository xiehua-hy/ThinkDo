package com.thinkdo.net;

import android.os.Handler;

/**
 * Created by Administrator on 2015/6/25.
 */
public class HttpSocket extends SocketClient {
    public HttpSocket(Handler handler) {
        this(handler, true);
    }

    public HttpSocket(Handler handler, boolean listen) {
        super(handler, listen);
    }

    @Override
    protected void sendToHandler(String msg) {
        super.sendToHandler(msg);
        onStop();
    }
}
