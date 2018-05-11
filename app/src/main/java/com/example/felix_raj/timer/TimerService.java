package com.example.felix_raj.timer;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class TimerService extends Service {
    private static final String TAG = "TimerService";
    static final String FINISHED = "FINISHED";
    static int runningFlag = 0;

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
        if(runningFlag==0) {
            new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d(TAG, "onTick: TICK");
                    publishResult(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    publishResult(0);
                }
            }.start();
            runningFlag = 1;
        }
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
