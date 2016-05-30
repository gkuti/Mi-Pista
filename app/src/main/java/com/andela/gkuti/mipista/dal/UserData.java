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

    public void saveData(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getData(String key) {
        if (key.equals("delay")) {
            return sharedPreferences.getInt(key, 5);
        }
        return sharedPreferences.getInt(key, 0);
    }

    public void saveCurrentDate(String key, String date) {
        editor.putString(key, date);
        editor.commit();
    }

    public String getCurrentDate(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void deleteData(String key) {
        editor.remove(key);
        editor.commit();
    }
}
