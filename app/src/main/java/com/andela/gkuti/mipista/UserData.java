package com.andela.gkuti.mipista;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserData(Context context) {
        sharedPreferences = context.getSharedPreferences("track", 0);
        editor = sharedPreferences.edit();
    }
    public void saveData(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }
    public int getData(String key) {
        return sharedPreferences.getInt(key, 5);
    }
}
