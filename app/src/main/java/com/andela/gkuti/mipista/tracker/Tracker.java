package com.andela.gkuti.mipista.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.andela.gkuti.mipista.dal.Datastore;
import com.andela.gkuti.mipista.dal.UserData;
import com.andela.gkuti.mipista.util.Constants;
import com.andela.gkuti.mipista.util.Date;

import java.util.concurrent.TimeUnit;

/**
 * Tracker class
 */
public class Tracker extends BroadcastReceiver {
    private BroadcastReceiver updateListReceiver;
    private Thread thread;
    private String activity;
    private Context context;
    private boolean isRunning = false;
    private Datastore datastore;
    private String startTime;
    private String endTime;
    private String date;
    private int duration;
    private UserData userData;
    private long stime;
    private long etime;
    private IntentFilter activityFilter;
    private String location = "unknown";
    private boolean stop;
    private BroadcastReceiver locationUpdate;

    /**
     * Constructor for Tracker class
     *
     * @param context
     */
    public Tracker(Context context) {
        this.context = context;
        datastore = new Datastore(context);
        userData = new UserData(context);
        activity = "STI";
        updateListReceiver = this;
        locationUpdate = this;
        activityFilter = new IntentFilter();
        activityFilter.addAction(Constants.ACTION.getValue());
        registerLocationUpdates();
    }

    private void registerLocationUpdates() {
        locationUpdate = new BroadcastReceiver() {
            /**
             * method called when an intent has been received from a broadcast
             */
            @Override
            public void onReceive(Context context, Intent intent) {
                location = intent.getStringExtra("Location");
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("location");
        context.registerReceiver(locationUpdate, filter);
    }

    /**
     * method for starting the tracker
     */
    public void startTracker() {
        stop = false;
        start();
        context.registerReceiver(updateListReceiver, activityFilter);
    }

    /**
     * method for initializing a new tracking thread
     */
    private void start() {
        if (!isRunning) {
            initThread();
            initializeTracking();
            thread.start();
        }
    }

    /**
     * method that initializes the thread
     */
    private void initThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (activity.equals("STI") && !stop) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
                endTracking();
            }
        });
    }

    /**
     * method for save a new tracking profile
     */
    private void save() {
        if (shouldSave()) {
            datastore.saveData(location, startTime, endTime, date, duration);
        }
    }

    /**
     * method for checking if a tracking profile should be saved
     *
     * @return true if the profile should be saved else false
     */
    private boolean shouldSave() {
        if (isUnknown()) {
            return checkTime();
        } else if (!location.equals("unknown")) {
            return checkTime();
        }
        return false;
    }

    /**
     * method to check if the saving of unknown location is allowed
     *
     * @return true if it is allowed else false
     */
    private boolean isUnknown() {
        if (location.equals("unknown") && userData.getData("unknown") == 0) {
            return true;
        }
        return false;
    }

    /**
     * method for checking if a tracking profile has lasted up to the required delay time
     *
     * @return true if it has else false
     */
    private boolean checkTime() {
        duration = (int) TimeUnit.MILLISECONDS.toSeconds(etime - stime);
        return duration >= (userData.getData("delay") * 60);
    }

    /**
     * method for initializing a new tracking profile
     */
    private void initializeTracking() {
        isRunning = true;
        date = Date.getDate();
        startTime = Date.getTime();
        stime = System.currentTimeMillis();
    }

    /**
     * method for ending a tracking profile
     */
    private void endTracking() {
        isRunning = false;
        etime = System.currentTimeMillis();
        endTime = Date.getTime();
        save();
    }

    /**
     * method for stopping the tracker
     */
    public void stopTracker() {
        try {
            stop = true;
            context.unregisterReceiver(updateListReceiver);
        } catch (Exception e) {
        }
    }

    /**
     * method called when an intent has been received from a broadcast
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        activity = intent.getStringExtra("Activity");
        start();
    }
}
