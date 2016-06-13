package com.andela.gkuti.mipista.view.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.gkuti.mipista.R;
import com.andela.gkuti.mipista.service.LocationDetector;
import com.andela.gkuti.mipista.service.UserActivity;
import com.andela.gkuti.mipista.tracker.Tracker;
import com.andela.gkuti.mipista.util.Requirement;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * MainFragment class
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private Tracker tracker;
    private TextView status;
    private boolean isTracking;
    private FloatingActionButton fab;
    private View view;
    private UserActivity userActivity;
    private String location = "";
    private TextView userLocation;
    private LocationDetector locationDetector;
    private BroadcastReceiver locationUpdate;

    /**
     * method called when the view is about to be created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    /**
     * method called after the view has been created
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        this.view = view;
        checkPermission();
        initializeComponent();
        registerLocationUpdates();
    }

    /**
     * handler for the tracker button
     */
    @Override
    public void onClick(View view) {
        if (!isTracking) {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop_white_24dp));
            startTracking();
        } else {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_location_white_24dp));
            stopTracking();
        }
    }

    /**
     * method for initializing class components
     */
    private void initializeComponent() {
        Requirement requirement = new Requirement(activity);
        tracker = new Tracker(activity);
        userActivity = new UserActivity(activity);
        locationDetector = new LocationDetector(activity);
        status = (TextView) view.findViewById(R.id.status);
        userLocation = (TextView) view.findViewById(R.id.user_location);
        Typeface face = Typeface.createFromAsset(activity.getAssets(), "Secrets.ttf");
        userLocation.setTypeface(face);
        fab = (FloatingActionButton) view.findViewById(R.id.tracking_button);
        fab.setOnClickListener(this);
        requirement.check();
        locationDetector.connect();
    }

    /**
     * Callback for the result from requesting permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(activity, "Need your location!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * method for starting the tracker
     */
    public void startTracking() {
        tracker.startTracker();
        userActivity.connect();
        isTracking = true;
        status.setText("TRACKING");
    }

    /**
     * method called for stopping the tracker
     */
    private void stopTracking() {
        tracker.stopTracker();
        userActivity.disconnect();
        isTracking = false;
        status.setText("NOT TRACKING");
    }

    /**
     * method for displaying location
     */
    private void setLocation() {
        userLocation.setText(location);
        YoYo.with(Techniques.BounceIn).duration(2000).playOn(view.findViewById(R.id.user_location));
    }

    /**
     * method for registering intent broadcast
     */
    private void registerLocationUpdates() {
        locationUpdate = new BroadcastReceiver() {
            /**
             * method called when an intent has been received from a broadcast
             */
            @Override
            public void onReceive(Context context, Intent intent) {
                location = intent.getStringExtra("Location");
                setLocation();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("location");
        getActivity().registerReceiver(locationUpdate, filter);
    }

    /**
     * method called when the fragment is about to be destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        locationDetector.disconnect();
        getContext().unregisterReceiver(locationUpdate);
    }

    /**
     * method for checking if location permission is granted
     */
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}
