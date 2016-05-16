package com.andela.gkuti.mipista;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

public class ActivityRecognitionService extends IntentService {

    private String TAG = this.getClass().getSimpleName();

    public ActivityRecognitionService() {
        super("My Activity Recognition Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        int confidence = 0;
        String ac = "", co = "";
        for (DetectedActivity activity : probableActivities) {
            int type = activity.getType();
            if (type == DetectedActivity.UNKNOWN || type == DetectedActivity.STILL || type == DetectedActivity.TILTING) {
                if (confidence < activity.getConfidence()) {
                    ac = "STI";
                    confidence = activity.getConfidence();
                    co = String.valueOf(activity.getConfidence());
                }
            } else {
                if (confidence < activity.getConfidence()) {
                    ac = "MOV";
                    confidence = activity.getConfidence();
                    co = String.valueOf(activity.getConfidence());
                }
            }
        }

        Intent i = new Intent("example");
        i.putExtra("Activity", ac);
        i.putExtra("Confidence", co);
        sendBroadcast(i);
    }
}
