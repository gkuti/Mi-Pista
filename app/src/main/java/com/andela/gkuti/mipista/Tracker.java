package com.andela.gkuti.mipista;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class Tracker extends BroadcastReceiver {
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
    private boolean runThread = true;
    private IntentFilter filter;

    public Tracker(Context context) {
        this.context = context;
        datastore = new Datastore(context);
        locationDetector = new LocationDetector(context);
        userData = new UserData(context);
        activity = "STI";
        updateListReceiver = this;
        filter = new IntentFilter();
        filter.addAction(Constants.ACTION.getValue());
    }

    public void startTracker() {
        start();
        context.registerReceiver(updateListReceiver, filter);
    }

    private void start() {
        if (!isRunning && runThread) {
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
                while (activity.equals("STI") && isRunning) {
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
        locationDetector.disconnect();
        save(seconds);
    }

    public void stopTracker() {
        try {
            isRunning = false;
            runThread = false;
            context.unregisterReceiver(updateListReceiver);
        } catch (Exception e) {

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        runThread = true;
        start();
    }
}
