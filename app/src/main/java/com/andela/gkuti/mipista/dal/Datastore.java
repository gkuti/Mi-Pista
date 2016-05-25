package com.andela.gkuti.mipista.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.andela.gkuti.mipista.util.Constants.*;

public class Datastore extends SQLiteOpenHelper {

    public Datastore(Context context) {
        super(context, DATABASE_NAME.getValue(), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME.getValue() + "(" + ID_COLUMN.getValue() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LOCATION_COLUMN.getValue() + " VARCHAR, " + START_TIME_COLUMN.getValue() + " VARCHAR, " +
                END_TIME_COLUMN.getValue() + " VARCHAR, " + DATE_COLUMN.getValue() + " VARCHAR, " +
                DURATION_COLUMN.getValue() + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveData(String location, String startTime, String endTime, String date, int duration) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOCATION_COLUMN.getValue(), location);
        cv.put(START_TIME_COLUMN.getValue(), startTime);
        cv.put(END_TIME_COLUMN.getValue(), endTime);
        cv.put(DATE_COLUMN.getValue(), date);
        cv.put(DURATION_COLUMN.getValue(), duration);
        sqLiteDatabase.insert(TABLE_NAME.getValue(), null, cv);
        sqLiteDatabase.close();
    }

    public Cursor getHistoryByDate(String date) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {LOCATION_COLUMN.getValue(), DURATION_COLUMN.getValue()};
        String[] selectionArgs = new String[]{date};
        return sqLiteDatabase.query(TABLE_NAME.getValue(), columns, "Date =?", selectionArgs, null, null, null);
    }

    public Cursor getHistoryByLocation(String location, String date) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {START_TIME_COLUMN.getValue(), END_TIME_COLUMN.getValue(), DURATION_COLUMN.getValue()};
        String[] selectionArgs = new String[]{location, date};
        return sqLiteDatabase.query(TABLE_NAME.getValue(), columns, "Location =? AND Date =?", selectionArgs, null, null, null);
    }
}
