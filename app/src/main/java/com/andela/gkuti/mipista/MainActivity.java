package com.andela.gkuti.mipista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {
    private Thread thread;
    private boolean isTracking;
    private Handler handler;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.status);
        handler = new Handler();
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.tracking_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isTracking) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop_white_24dp));
                    startTracking();
                    initthread();
                    thread.start();
                    textView.setText("TRACKING");
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_location_white_24dp));
                    isTracking = false;
                    textView.setText("NOT TRACKING");
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
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void startTracking() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        UserActivity  userActivity = new UserActivity(this);
        userActivity.connect();
        Tracker tracker = new Tracker(this);
        tracker.update();
        isTracking = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_history) {
            Intent intent = new Intent(this, LocationActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void animate() {
        YoYo.with(Techniques.Pulse).duration(2000).playOn(findViewById(R.id.outer_pulse));
        YoYo.with(Techniques.Pulse).duration(2000).playOn(findViewById(R.id.pulse_image));
        YoYo.with(Techniques.Pulse).duration(2000).playOn(findViewById(R.id.status));
    }
}
