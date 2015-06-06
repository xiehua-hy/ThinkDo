package com.thinkdo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.UnitEnum;
import com.thinkdo.util.XmlInit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by xh on 15/5/9.
 */
public class InitActivity extends Activity {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

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

    private void init() {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(GloVariable.context);

        GloVariable.ip = shared.getString(GloVariable.hostIpKey, GloVariable.defaultIp);
        GloVariable.port = shared.getInt(GloVariable.hostPortKey, GloVariable.defaultPort);
        GloVariable.unit = UnitEnum.getUnitFromValue(shared.getString(GloVariable.unitKey, "1"));
        GloVariable.toeUnit = UnitEnum.getUnitFromValue(shared.getString(GloVariable.toeUnitKey, "1"));
        new XmlInit(this);

        File sqliteCustom = getDatabasePath(GloVariable.customSqliteName);
        File sqliteCar = getDatabasePath(GloVariable.carSqliteName);

        if (!sqliteCustom.exists()) {
            copy(GloVariable.customSqliteName, sqliteCustom);
        }

        if (!sqliteCar.exists()) {
            copy(GloVariable.carSqliteName, sqliteCar);
        }

    }

    private void copy(String assetsFileName, File des) {
        try {
            des.getParentFile().mkdirs();
            InputStream in = getAssets().open(assetsFileName);
            OutputStream out = new FileOutputStream(des);

            byte[] bytes = new byte[1024];
            int len;

            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class InitThread extends Thread {
        @Override
        public void run() {
            init();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);
        }
    }

}
