package com.andela.gkuti.mipista.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.andela.gkuti.mipista.callback.NumberPickCallback;
import com.andela.gkuti.mipista.view.dialog.NumberPickerDialog;
import com.andela.gkuti.mipista.R;
import com.andela.gkuti.mipista.dal.UserData;

/**
 * SettingsActivity class
 */
public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Button button;
    UserData userData;
    private Switch updateSwitch;

    /**
     * method called when the activity is about to start
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        userData = new UserData(this);
        button = (Button) findViewById(R.id.delay_button);
        button.setOnClickListener(onClickListener);
        updateSwitch = (Switch) findViewById(R.id.switch1);
        updateSwitch.setOnCheckedChangeListener(this);
        loadUserSettings();
    }

    /**
     * the method loads user settings
     */
    private void loadUserSettings() {
        int delay = userData.getData("delay");
        updateView(String.valueOf(delay) + " mins");
        if (userData.getData("unknown") == 0) {
            updateSwitch.setChecked(true);
        } else {
            updateSwitch.setChecked(false);
        }
    }

    /**
     * updates the text for user delay time
     *
     * @param text the text to set
     */
    private void updateView(String text) {
        button.setText(text);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        /**
         * triggered by click on the delay button
         */
        @Override
        public void onClick(View view) {
            launchDialog();
        }
    };
    private NumberPickCallback numberPickCallback = new NumberPickCallback() {
        /**
         * triggered when you change the delay time
         */
        @Override
        public void onNumberPick(int value) {
            userData.saveData("delay", value);
            updateView(String.valueOf(value) + " mins");
        }
    };

    /**
     * the method for launching a number picker dialog
     */
    public void launchDialog() {
        NumberPickerDialog numberPickerDialog = new NumberPickerDialog();
        numberPickerDialog.setCallback(numberPickCallback);
        numberPickerDialog.show(getSupportFragmentManager(), "");
    }

    /**
     * method triggered when a check action happen on the switch
     *
     * @param compoundButton the switch that was checked
     * @param checked        the boolean value for the switch
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (checked) {
            userData.saveData("unknown", 0);
        } else {
            userData.saveData("unknown", 1);
        }
    }
}
