package com.andela.gkuti.mipista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Datastore extends SQLiteOpenHelper{

    public Datastore(Context context) {
        super(context, "Mydb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Test(_id INTEGER PRIMARY KEY AUTOINCREMENT, Location VARCHAR, StartTime VARCHAR, EndTime VARCHAR, Date VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void saveData(String location, String startTime, String endTime, String date) {
        SQLiteDatabase SQ = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Location", location);
        cv.put("StartTime", startTime);
        cv.put("EndTime", endTime);
        cv.put("Date", date);
        SQ.insert("Test", null, cv);
        SQ.close();
    }
    public Cursor getHistoryByDate(String date) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {"Location"};
        String[] selectionArgs = new String[] {date};
        Cursor cursor = sqLiteDatabase.query("Test",columns,"Date =?",selectionArgs,null,null,null);
        return cursor;
    }
    public Cursor getHistoryByLocation(String location) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {"StartTime", "EndTime"};
        String[] selectionArgs = new String[] {location};
        Cursor cursor = sqLiteDatabase.query("Test",columns,"Location =?",selectionArgs,null,null,null);
        return cursor;
    }
}
