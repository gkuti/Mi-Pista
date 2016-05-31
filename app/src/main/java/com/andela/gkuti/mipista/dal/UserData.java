package com.andela.gkuti.mipista.dal;

import android.content.Context;
import android.content.SharedPreferences;

import com.andela.gkuti.mipista.util.Constants;

/**
 * UserData class
 */
public class UserData {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * Constructor for UserData class
     *
     * @param context the activity context
     */
    public UserData(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.DATA_FILENAME.getValue(), 0);
        editor = sharedPreferences.edit();
    }

    /**
     * method for storing data to shared preferences
     *
     * @param key   the key
     * @param value the value to map to the key
     */
    public void saveData(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * method for retrieving data from the shared preferences
     *
     * @param key the key mapped to the value
     * @return int value
     */
    public int getData(String key) {
        if (key.equals("delay")) {
            return sharedPreferences.getInt(key, 5);
        }
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * method for storing date that was changed to by the user to shared preferences
     *
     * @param key  the key
     * @param date the value to map to the key
     */
    public void saveCurrentDate(String key, String date) {
        editor.putString(key, date);
        editor.commit();
    }

    /**
     * method for retrieving current date data from the shared preferences
     *
     * @param key the key mapped to the value
     * @return the date string
     */
    public String getCurrentDate(String key) {
        return sharedPreferences.getString(key, "");
    }

    /**
     * mehtod for removing data from the shared preferences
     *
     * @param key the key for the value to be removed
     */
    public void deleteData(String key) {
        editor.remove(key);
        editor.commit();
    }
}
