package com.andela.gkuti.mipista;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

public class ActivityRecognitionService extends IntentService {
    int confidence = 0;
    String ac = "", co = "";

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

        Intent i = new Intent("Mi Pista");
        i.putExtra("Activity", ac);
        i.putExtra("Confidence", co);
        sendBroadcast(i);
    }

    private void getStatus(int type, DetectedActivity activity) {
        if ((type == DetectedActivity.UNKNOWN || type == DetectedActivity.STILL || type == DetectedActivity.TILTING) && (confidence < activity.getConfidence())) {
            ac = "STI";
            confidence = activity.getConfidence();
            co = String.valueOf(activity.getConfidence());
        } else if (confidence < activity.getConfidence()) {
            ac = "MOV";
            confidence = activity.getConfidence();
            co = String.valueOf(activity.getConfidence());
        }
    }
}
