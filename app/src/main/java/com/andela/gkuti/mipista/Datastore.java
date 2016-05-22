package com.andela.gkuti.mipista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Datastore extends SQLiteOpenHelper {

    public Datastore(Context context) {
        super(context, Constants.DATABASE_NAME.getValue(), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + Constants.TABLE_NAME.getValue() + "(" + Constants.ID_COLUMN.getValue() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.LOCATION_COLUMN.getValue() + " VARCHAR, " + Constants.START_TIME_COLUMN.getValue() + " VARCHAR, " +
                Constants.END_TIME_COLUMN.getValue() + " VARCHAR, " + Constants.DATE_COLUMN.getValue() + " VARCHAR, " +
                Constants.DURATION_COLUMN.getValue() + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveData(String location, String startTime, String endTime, String date, int duration) {
        SQLiteDatabase SQ = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.LOCATION_COLUMN.getValue(), location);
        cv.put(Constants.START_TIME_COLUMN.getValue(), startTime);
        cv.put(Constants.END_TIME_COLUMN.getValue(), endTime);
        cv.put(Constants.DATE_COLUMN.getValue(), date);
        cv.put(Constants.DURATION_COLUMN.getValue(), duration);
        SQ.insert(Constants.TABLE_NAME.getValue(), null, cv);
        SQ.close();
    }

    public Cursor getHistoryByDate(String date) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {Constants.LOCATION_COLUMN.getValue(), Constants.DURATION_COLUMN.getValue()};
        String[] selectionArgs = new String[]{date};
        return sqLiteDatabase.query(Constants.TABLE_NAME.getValue(), columns, "Date =?", selectionArgs, null, null, null);
    }

    public Cursor getHistoryByLocation(String location) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {Constants.START_TIME_COLUMN.getValue(), Constants.END_TIME_COLUMN.getValue(), Constants.DURATION_COLUMN.getValue()};
        String[] selectionArgs = new String[]{location};
        return sqLiteDatabase.query(Constants.TABLE_NAME.getValue(), columns, "Location =?", selectionArgs, null, null, null);
    }
}
