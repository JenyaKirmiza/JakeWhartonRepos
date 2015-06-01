package com.github.jakewarthongithub.appmodel;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

public class CApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return CApplication.context;
    }

    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        CApplication.context = getApplicationContext();
    }
} 