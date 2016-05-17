package com.andela.gkuti.mipista;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

public class Tracker {
    private BroadcastReceiver updateListReceiver;
    private Thread thread;
    private String activity;
    private int seconds;
    private Context context;
    private boolean isRunning = false;
    private Datastore datastore;
    private String startTime;
    private String endTime;
    private String date;

    public Tracker(Context context) {
        this.context = context;
        datastore = new Datastore(context, "Mydb", 1);
    }

    public void start() {
        updateListReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                activity = intent.getStringExtra("Activity");
                Toast.makeText(context, intent.getStringExtra("Activity") + " " + "Confidence : " + intent.getExtras().getString("Confidence"),
                        Toast.LENGTH_LONG).show();
                startTimer();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("example");
        context.registerReceiver(updateListReceiver, filter);
    }

    private void startTimer() {
        if (!isRunning) {
            initthread();
            LocationDetector locationDetector = new LocationDetector(context);
            locationDetector.connect();
            date = Date.getDate();
            thread.start();
        }
        Toast.makeText(context, String.valueOf(seconds), Toast.LENGTH_LONG).show();
    }

    private void initthread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                seconds = 0;
                isRunning = true;
                startTime = Date.getTime();
                while (activity.equals("STI")) {
                    try {
                        Thread.sleep(999);
                    } catch (InterruptedException e) {
                    }
                    seconds++;
                }
                endTime = Date.getTime();
                save(seconds);
                isRunning = false;
            }
        });
    }

    private void save(int seconds) {
        if (checkTime(seconds)) {
            datastore.putInformation(datastore, "unknown", startTime, endTime, date);
            Log.d("save", "saved");
        }
    }

    private boolean checkTime(int seconds) {
        return seconds >= 10;
    }
}
