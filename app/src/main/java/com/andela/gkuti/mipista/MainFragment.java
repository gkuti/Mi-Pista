package com.andela.gkuti.mipista;


import android.Manifest;
import android.app.Activity;
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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private Tracker tracker;
    private TextView textView;
    private Handler handler;
    private Thread thread;
    private boolean isTracking;
    private FloatingActionButton fab;
    private View view;
    private UserActivity userActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        this.view = view;
        initializeComponent();
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
        tracker = new Tracker(activity);
        textView = (TextView) view.findViewById(R.id.status);
        handler = new Handler();
        fab = (FloatingActionButton) view.findViewById(R.id.tracking_button);
        fab.setOnClickListener(this);
        userActivity = new UserActivity(activity);
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(activity, "Need your location!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void animate() {
        YoYo.with(Techniques.Pulse).duration(2000).playOn(view.findViewById(R.id.pulse));
    }

    public void startTracking() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        tracker.startTracker();
        userActivity.connect();
        isTracking = true;
        initthread();
        thread.start();
        textView.setText("TRACKING");
    }

    public void stopTracking() {
        tracker.stopTracker();
        userActivity.disconnect();
        isTracking = false;
        textView.setText("NOT TRACKING");
    }
}
