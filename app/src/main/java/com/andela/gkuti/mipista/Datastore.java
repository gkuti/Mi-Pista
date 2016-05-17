package com.andela.gkuti.mipista;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Datastore extends SQLiteOpenHelper{

    public Datastore(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Test(_id INTEGER PRIMARY KEY AUTOINCREMENT, Location VARCHAR, StartTime VARCHAR, EndTime VARCHAR, Date VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void putInformation(Datastore datastore, String location, String startTime,String endTime, String date){
        SQLiteDatabase SQ = datastore.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("Location", location);
        cv.put("StartTime", startTime);
        cv.put("EndTime", endTime);
        cv.put("Date", date);
        SQ.insert("Test", null, cv);
        SQ.close();
    }
}
