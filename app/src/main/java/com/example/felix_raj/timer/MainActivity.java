package com.example.felix_raj.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    String TAG = "MainActivity";
    private String TIMER_STATUS = TimerService.IDLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restoreTimerStatus();

        textView = findViewById(R.id.displayTimer);

        findViewById(R.id.startTimerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Button Clicked");
                startTimer();
            }
        });

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive: Received");
                if(intent.getStringExtra("current").equals(TimerService.FINISHED)){
                    textView.setText(TimerService.FINISHED);
                    TIMER_STATUS=TimerService.FINISHED;
                }else {
                    textView.setText("TO FINISH "+intent.getStringExtra("current"));
                }
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter("com.example.felix_raj.timer"));
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        saveTimerStatus();
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // this doesnot work
        outState.putString("TIMER_STATUS", TIMER_STATUS);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // this too
        TIMER_STATUS = savedInstanceState.getString("TIMER_STATUS");
        super.onRestoreInstanceState(savedInstanceState);
    }

    void saveTimerStatus(){
        getSharedPreferences("felix-raj-timer", MODE_PRIVATE).edit().putString("TIMER_STATE", TIMER_STATUS).apply();
    }

    void restoreTimerStatus(){
        TIMER_STATUS = getSharedPreferences("felix-raj-timer", MODE_PRIVATE).getString("TIMER_STATE", TimerService.IDLE);
    }

    void startTimer(){
        switch (TIMER_STATUS){
            case TimerService.IDLE:{
                Toast.makeText(this, "Starting Timer", Toast.LENGTH_SHORT).show();
                startService(new Intent(getBaseContext(), TimerService.class));
                TIMER_STATUS = TimerService.STARTED;
                break;
            }
            case TimerService.STARTED:{
                Toast.makeText(this, "Timer Is already running", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

}
