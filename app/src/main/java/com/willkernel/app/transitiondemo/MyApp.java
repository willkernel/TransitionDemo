package com.willkernel.app.transitiondemo;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

/**
 * Created by admin on 2016/9/22.
 * mail:willkerneljc@gmail.com
 */

public class MyApp extends Application {
    public final String TAG = getClass().getSimpleName();
    private static MyApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        Log.e(TAG, "onCreate()");
    }

    public static synchronized MyApp getInstance() {
        return myApp;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e(TAG, "onLowMemory()");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e(TAG, "onTrimMemory()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged()");
    }
}
