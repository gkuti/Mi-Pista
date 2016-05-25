package com.andela.gkuti.mipista.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
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

import com.andela.gkuti.mipista.service.LocationDetector;
import com.andela.gkuti.mipista.R;
import com.andela.gkuti.mipista.tracker.Tracker;
import com.andela.gkuti.mipista.service.UserActivity;
import com.andela.gkuti.mipista.util.Requirement;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private Tracker tracker;
    private TextView status;
    private Handler handler;
    private Thread thread;
    private boolean isTracking;
    private FloatingActionButton fab;
    private View view;
    private UserActivity userActivity;
    private String location = "";
    private TextView userLocation;
    private LocationDetector locationDetector;
    private BroadcastReceiver locationUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        this.view = view;
        checkPermission();
        initializeComponent();
        registerLocationUpdates();
    }

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

    private void initializeComponent() {
        Requirement requirement = new Requirement(activity);
        tracker = new Tracker(activity);
        userActivity = new UserActivity(activity);
        locationDetector = new LocationDetector(activity);
        status = (TextView) view.findViewById(R.id.status);
        userLocation = (TextView) view.findViewById(R.id.user_location);
        handler = new Handler();
        fab = (FloatingActionButton) view.findViewById(R.id.tracking_button);
        fab.setOnClickListener(this);
        requirement.check();
        locationDetector.connect();
    }

    public void initthread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isTracking) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            animate();
                        }
                    });
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
    }

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

    public void animate() {
        YoYo.with(Techniques.Pulse).duration(2000).playOn(view.findViewById(R.id.pulse));
    }

    public void startTracking() {
        tracker.startTracker();
        userActivity.connect();
        isTracking = true;
        initthread();
        thread.start();
        status.setText("TRACKING");
    }

    public void stopTracking() {
        tracker.stopTracker();
        userActivity.disconnect();
        isTracking = false;
        status.setText("NOT TRACKING");
    }

    public void setLocation() {
        userLocation.setText(location);
        YoYo.with(Techniques.BounceIn).duration(2000).playOn(view.findViewById(R.id.user_location));
    }

    public void registerLocationUpdates() {
        locationUpdate = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                location = intent.getStringExtra("Location");
                setLocation();
                Toast.makeText(context, location, Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("location");
        getActivity().registerReceiver(locationUpdate, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationDetector.disconnect();
        getContext().unregisterReceiver(locationUpdate);
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}
