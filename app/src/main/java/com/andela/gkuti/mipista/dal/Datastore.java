package com.andela.gkuti.mipista.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.andela.gkuti.mipista.util.Constants.*;

/**
 * Datastore class
 */
public class Datastore extends SQLiteOpenHelper {

    public Datastore(Context context) {
        super(context, DATABASE_NAME.getValue(), null, 1);
    }

    /**
     * called the first time the database is to be created
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME.getValue() + "(" + ID_COLUMN.getValue() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LOCATION_COLUMN.getValue() + " VARCHAR, " + START_TIME_COLUMN.getValue() + " VARCHAR, " +
                END_TIME_COLUMN.getValue() + " VARCHAR, " + DATE_COLUMN.getValue() + " VARCHAR, " +
                DURATION_COLUMN.getValue() + " INTEGER);");
    }

    /**
     * called to upgrade the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // This method is empty since no database upgrade is being done for now in the project.
    }

    /**
     * method to save a new location track data
     *
     * @param location  the location for the trail
     * @param startTime the time the trial started
     * @param endTime   the time the trail stopped
     * @param date      the date it was recorded
     * @param duration  the duration it took
     * @return the row id if the operation was successful else -1
     */
    public long saveData(String location, String startTime, String endTime, String date, int duration) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOCATION_COLUMN.getValue(), location);
        cv.put(START_TIME_COLUMN.getValue(), startTime);
        cv.put(END_TIME_COLUMN.getValue(), endTime);
        cv.put(DATE_COLUMN.getValue(), date);
        cv.put(DURATION_COLUMN.getValue(), duration);
        long status = sqLiteDatabase.insert(TABLE_NAME.getValue(), null, cv);
        sqLiteDatabase.close();
        return status;
    }

    /**
     * the method returns data fetched using the date provided
     *
     * @param date the specified date
     * @return cursor object of the data
     */
    public Cursor getHistoryByDate(String date) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {LOCATION_COLUMN.getValue(), DURATION_COLUMN.getValue()};
        String[] selectionArgs = {date};
        return sqLiteDatabase.query(TABLE_NAME.getValue(), columns, DATE_COLUMN.getValue() + " =?", selectionArgs, null, null, null);
    }

    /**
     * the method return data fetched using the location and date provided
     *
     * @param location the specified location
     * @param date     the specified date
     * @return cursor object of the dat
     */
    public Cursor getHistoryByLocation(String location, String date) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {START_TIME_COLUMN.getValue(), END_TIME_COLUMN.getValue(), DURATION_COLUMN.getValue()};
        String[] selectionArgs = {location, date};
        return sqLiteDatabase.query(TABLE_NAME.getValue(), columns, LOCATION_COLUMN.getValue() + " =? AND " + DATE_COLUMN.getValue() + " =?", selectionArgs, null, null, null);
    }

    /**
     * the method delete a row with the specified location data
     *
     * @param location the specified location
     * @return the total number of rows deleted
     */
    public int deleteLocation(String location) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String[] whereArgs = {location};
        return sqLiteDatabase.delete(TABLE_NAME.getValue(), LOCATION_COLUMN.getValue() + "=?", whereArgs);
    }
}
