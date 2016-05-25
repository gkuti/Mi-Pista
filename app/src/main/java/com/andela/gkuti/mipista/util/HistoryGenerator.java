package com.andela.gkuti.mipista.util;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateUtils;

import com.andela.gkuti.mipista.dal.Datastore;
import com.andela.gkuti.mipista.model.Location;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryGenerator {
    private ArrayList<Location> locationsList;
    private ArrayList<String> locationList;
    private HashMap<String, Integer> locationDuration;
    private int totalDuration;
    private Datastore datastore;

    public HistoryGenerator(Context context) {
        locationsList = new ArrayList<>();
        datastore = new Datastore(context);
    }

    public ArrayList<Location> getList(String date) {
        createData(date);
        return locationsList;
    }

    private void createData(String date) {
        locationsList.clear();
        locationList = new ArrayList<>();
        locationDuration = new HashMap<>();
        Cursor cursor = datastore.getHistoryByDate(date);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            bindData(cursor);
            cursor.moveToNext();
        }
        generateList();
    }

    private void generateList() {
        for (int i = 0; i < locationList.size(); i++) {
            String loc = locationList.get(i);
            String dur = DateUtils.formatElapsedTime(locationDuration.get(loc));
            Location location = new Location(loc, dur);
            locationsList.add(location);
        }
    }

    private void bindData(Cursor cursor) {
        String Location = cursor.getString(cursor.getColumnIndex(Constants.LOCATION_COLUMN.getValue()));
        int duration = cursor.getInt(cursor.getColumnIndex(Constants.DURATION_COLUMN.getValue()));
        if (locationList.contains(Location)) {
            totalDuration += duration;
            locationDuration.put(Location, this.totalDuration);
        } else {
            totalDuration = duration;
            locationList.add(Location);
            locationDuration.put(Location, duration);
        }
    }
}
