package com.thinkdo.application;

import android.app.Application;

import com.thinkdo.entity.GloVariable;

/**
 * Created by xh on 15/5/8.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GloVariable.context = getApplicationContext();
    }
}
