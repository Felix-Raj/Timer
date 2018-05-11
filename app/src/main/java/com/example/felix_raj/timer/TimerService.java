package com.example.felix_raj.timer;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;


public class TimerService extends Service {
    private static final String TAG = "TimerService";
    static final String IDLE = "IDLE";
    static final String STARTED = "STARTED";
    static final String FINISHED = IDLE;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: TICK");
                //sendBroadcast(new Intent(TimerService.this, MainActivity.class).putExtra("current", String.valueOf(millisUntilFinished)));
                publishResult(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                publishResult(0);
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }

    void publishResult(long m){
        if(m==0){
            sendBroadcast(new Intent("com.example.felix_raj.timer").putExtra("current", FINISHED));
        } else {
            sendBroadcast(new Intent("com.example.felix_raj.timer").putExtra("current", String.valueOf(m)));
        }
    }
}
