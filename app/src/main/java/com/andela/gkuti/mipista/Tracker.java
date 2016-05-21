package com.andela.gkuti.mipista;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

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
    private int duration;
    private LocationDetector locationDetector;
    private UserData userData;
    private long stime;
    private long etime;
    public static final String ACTION = "com.andela.gkuti.mipista.ACTIVITY_RECOGNITION_DATA";

    public Tracker(Context context) {
        this.context = context;
        datastore = new Datastore(context);
        locationDetector = new LocationDetector(context);
        userData = new UserData(context);
    }

    public void update() {
        updateListReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                activity = intent.getStringExtra("Activity");
                Toast.makeText(context, intent.getStringExtra("Activity") + " " + "Confidence : " + intent.getExtras().getString("Confidence"),
                        Toast.LENGTH_LONG).show();
                start();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);
        context.registerReceiver(updateListReceiver, filter);
    }

    private void start() {
        if (!isRunning) {
            initthread();
            date = Date.getDate();
            thread.start();
        }
        Toast.makeText(context, String.valueOf(seconds), Toast.LENGTH_LONG).show();
    }

    private void initthread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                initializeTracking();
                while (activity.equals("STI")) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    seconds++;
                }
                endTracking();
            }
        });
    }

    private void save(int seconds) {
        if (checkTime(seconds)) {
            String location = locationDetector.getLocation();
            datastore.saveData(location, startTime, endTime, date, duration);
            Log.d("save", "saved");
        }
    }

    private boolean checkTime(int seconds) {
        duration = (int) TimeUnit.MILLISECONDS.toSeconds(etime - stime);
        return seconds >= 5;
    }

    private void initializeTracking() {
        locationDetector.connect();
        seconds = 0;
        isRunning = true;
        startTime = Date.getTime();
        stime = System.currentTimeMillis();
    }

    private void endTracking() {
        etime = System.currentTimeMillis();
        endTime = Date.getTime();
        save(seconds);
        isRunning = false;
    }
}
