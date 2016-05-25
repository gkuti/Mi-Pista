package com.andela.gkuti.mipista.dal;

import android.content.Context;
import android.content.SharedPreferences;

import com.andela.gkuti.mipista.util.Constants;

public class UserData {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserData(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.DATA_FILENAME.getValue(), 0);
        editor = sharedPreferences.edit();
    }

    public void saveData(int value) {
        editor.putInt("delay", value);
        editor.commit();
    }

    public int getData() {
        return sharedPreferences.getInt("delay", 5);
    }

    public void saveCurrentDate(String key, String date) {
        editor.putString(key, date);
        editor.commit();
    }

    public String getCurrentDate(String key) {
        return sharedPreferences.getString(key, "");
    }
}
