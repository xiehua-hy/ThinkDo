package com.thinkdo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.thinkdo.application.MainApplication;
import com.thinkdo.net.HttpSocket;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;


/**
 * Created by xh on 15/5/9.
 */
public class InitActivity extends Activity {
    private HttpSocket client;
    private boolean acceptFlag = false;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (acceptFlag) return true;
            acceptFlag = true;
            switch (msg.what) {
                case -1:
                    Toast.makeText(InitActivity.this, R.string.tip_connect_failed, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    String reply = msg.getData().getString(MainApplication.head);
                    if (reply == null) return true;
                    int backCode = CommonUtil.getQuestCode(reply);
                    if (backCode == MainApplication.loginUrl) {
                        String[] data = SocketClient.parseData(reply);
                        if (data != null && data.length == 2) {
                            int i;
                            try {
                                i = Integer.parseInt(data[1]);
                            } catch (NumberFormatException e) {
                                i = 0;
                            }
                            MainApplication.device = data[0];
                            MainApplication.availableDay = i;
                            MainApplication.loginFlag = true;
                            Toast.makeText(InitActivity.this, R.string.tip_login_success, Toast.LENGTH_SHORT).show();
                        }
                    }
            }

            if (client != null) client.onStop();
            Intent it = new Intent(InitActivity.this, MenuActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(it);
            return true;
        }

    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        new InitThread().start();
    }


    class InitThread extends Thread {
        @Override
        public void run() {
            handler.sendEmptyMessageDelayed(2, 3000);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            client = new HttpSocket(handler);
            client.send(MainApplication.loginUrl);
        }
    }

}
