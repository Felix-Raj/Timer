package com.example.felix_raj.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

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
                }else {
                    textView.setText("TO FINISH "+intent.getStringExtra("current"));
                }
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter("com.example.felix_raj.timer"));
    }

    void startTimer(){
        Log.d(TAG, "startTimer: runningFlag "+String.valueOf(TimerService.runningFlag));
        if (TimerService.runningFlag == 1){
            Toast.makeText(this, "Timer Is Already Running", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Starting Timer", Toast.LENGTH_SHORT).show();
            startService(new Intent(getBaseContext(), TimerService.class));
        }
    }

}
