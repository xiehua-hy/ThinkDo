package com.thinkdo.net;

import com.thinkdo.application.MainApplication;
import com.thinkdo.util.CommonUtil;

/**
 * Created by Administrator on 2015/5/25.
 */
public class NetQuest extends Thread {
    private int quest;
    private int param;
    private String msg;

    public NetQuest(int quest) {
        this(quest, 0);
    }

    public NetQuest(int quest, int param) {
        this(quest, param, null);
    }

    public NetQuest(int quest, int param, String msg) {
        this.quest = quest;
        this.param = param;
        this.msg = msg;
        start();
    }

    @Override
    public void run() {
        String hostIp = CommonUtil.getIp(MainApplication.ip);
        if (hostIp != null) {
            SocketClient socket = new SocketClient(null, false);
            socket.send(quest, param, msg);
            socket.onStop();
        }
    }
}
