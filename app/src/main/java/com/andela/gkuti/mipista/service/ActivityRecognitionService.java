package com.andela.gkuti.mipista.service;

import android.app.IntentService;
import android.content.Intent;

import com.andela.gkuti.mipista.util.Constants;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

/**
 * ActivityRecognitionService class
 */
public class ActivityRecognitionService extends IntentService {
    private int confidence = 0;
    private String activity;

    /**
     * Constructor for ActivityRecognitionService class
     */
    public ActivityRecognitionService() {
        super("Recognition Service");
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());
        }
    }

    /**
     * method for handling Recognition Service
     *
     * @param probableActivities the list of activities suspected
     */
    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for (DetectedActivity activity : probableActivities) {
            getStatus(activity.getType(), activity);
        }
        Intent i = new Intent(Constants.ACTION.getValue());
        i.putExtra("Activity", activity);
        sendBroadcast(i);
    }

    /**
     * method for getting the highest possible activity
     *
     * @param type     activity type
     * @param activity detected activity
     */
    private void getStatus(int type, DetectedActivity activity) {
        if ((type == DetectedActivity.UNKNOWN || type == DetectedActivity.STILL || type == DetectedActivity.TILTING) && (confidence < activity.getConfidence())) {
            this.activity = "STI";
            confidence = activity.getConfidence();
        } else if (confidence < activity.getConfidence()) {
            this.activity = "MOV";
            confidence = activity.getConfidence();
        }
    }
}
