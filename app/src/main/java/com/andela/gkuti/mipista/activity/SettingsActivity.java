package com.andela.gkuti.mipista.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.andela.gkuti.mipista.callback.NumberPickCallback;
import com.andela.gkuti.mipista.dialog.NumberPickerDialog;
import com.andela.gkuti.mipista.R;
import com.andela.gkuti.mipista.dal.UserData;

public class SettingsActivity extends AppCompatActivity {
    Button button;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        userData = new UserData(this);
        button = (Button) findViewById(R.id.delay_button);
        button.setOnClickListener(onClickListener);
        loadUserSettings();
    }

    private void loadUserSettings() {
        int delay = userData.getData();
        updateView(String.valueOf(delay) + " mins");
    }

    private void updateView(String text) {
        button.setText(text);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            launchDialog();
        }
    };
    private NumberPickCallback numberPickCallback = new NumberPickCallback() {
        @Override
        public void onNumberPick(int value) {
            userData.saveData(value);
            updateView(String.valueOf(value) + " mins");
        }
    };

    public void launchDialog() {
        NumberPickerDialog numberPickerDialog = new NumberPickerDialog();
        numberPickerDialog.setCallback(numberPickCallback);
        numberPickerDialog.show(getSupportFragmentManager(), "");
    }
}
