package com.andela.gkuti.mipista;

import android.database.Cursor;
import android.test.AndroidTestCase;

import com.andela.gkuti.mipista.dal.Datastore;
import com.andela.gkuti.mipista.util.Constants;

public class DatastoreUnitTest extends AndroidTestCase {

    public void testSaveData() throws Exception {
        Datastore datastore = new Datastore(mContext);
        String location = "Abule";
        String startTime = "12:34:05";
        String endTime = "12:54:23";
        String date = "16/11/2016";
        int duration = 56;
        long result = datastore.saveData(location, startTime, endTime, date, duration);
        assertTrue(result != -1);
    }

    public void testGetHistoryByDate() throws Exception {
        Datastore datastore = new Datastore(mContext);
        String location = "Abule";
        String startTime = "02:14:22";
        String endTime = "04:54:23";
        String date = "16/11/2013";
        int duration = 56;
        datastore.saveData(location, startTime, endTime, date, duration);
        Cursor cursor = datastore.getHistoryByDate(date);
        cursor.moveToFirst();
        assertEquals(location, cursor.getString(cursor.getColumnIndex(Constants.LOCATION_COLUMN.getValue())));
    }

    public void testGetHistoryByLocation() throws Exception {
        Datastore datastore = new Datastore(mContext);
        String location = "Somolu";
        String startTime = "04:14:22";
        String endTime = "04:54:23";
        String date = "24/06/2014";
        int duration = 25;
        datastore.saveData(location, startTime, endTime, date, duration);
        Cursor cursor = datastore.getHistoryByLocation(location, date);
        assertNotNull(cursor);
    }

    public void testDeleteLocation() {
        Datastore datastore = new Datastore(mContext);
        String location = "Ijebu";
        String startTime = "04:14:22";
        String endTime = "04:54:23";
        String date = "24/06/2014";
        int duration = 25;
        datastore.saveData(location, startTime, endTime, date, duration);
        int result = datastore.deleteLocation(location);
        assertEquals(1, result);
    }
}