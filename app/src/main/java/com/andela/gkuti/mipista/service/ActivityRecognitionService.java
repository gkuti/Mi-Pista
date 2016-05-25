package com.andela.gkuti.mipista.service;

import android.app.IntentService;
import android.content.Intent;

import com.andela.gkuti.mipista.util.Constants;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

public class ActivityRecognitionService extends IntentService {
    private int confidence = 0;
    private String activity;

    public ActivityRecognitionService() {
        super("Recognition Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for (DetectedActivity activity : probableActivities) {
            getStatus(activity.getType(), activity);
        }
        Intent i = new Intent(Constants.ACTION.getValue());
        i.putExtra("Activity", activity);
        sendBroadcast(i);
    }

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
