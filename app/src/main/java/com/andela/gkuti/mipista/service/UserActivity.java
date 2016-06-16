package com.andela.gkuti.mipista.service;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.andela.gkuti.mipista.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

/**
 * UserActivity class
 */
public class UserActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private Context context;

    /**
     * Constructor for UserActivity class
     *
     * @param context
     */
    public UserActivity(Activity context) {
        this.context = context;
    }

    /**
     * called when connection was successful
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent(context, ActivityRecognitionService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(googleApiClient, 0, pendingIntent);
    }

    /**
     * called when the connection was suspended
     */
    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(context,context.getString(R.string.connection_suspended),Toast.LENGTH_SHORT).show();
    }

    /**
     * called when the connection failed
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(context,context.getString(R.string.connection_failed),Toast.LENGTH_SHORT).show();
    }

    /**
     * method for setting up a connection
     */
    public void connect() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    /**
     * method for disconnecting the connection
     */
    public void disconnect() {
        googleApiClient.disconnect();
    }
}
