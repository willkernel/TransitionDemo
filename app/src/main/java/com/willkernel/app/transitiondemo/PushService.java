package com.willkernel.app.transitiondemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by admin on 2016/9/22.
 * mail:willkerneljc@gmail.com
 */

public class PushService extends Service {
    public final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind()");
        return null;
    }
}
