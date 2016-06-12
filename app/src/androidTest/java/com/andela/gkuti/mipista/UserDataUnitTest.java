package com.andela.gkuti.mipista;

import android.test.AndroidTestCase;

import com.andela.gkuti.mipista.dal.UserData;

public class UserDataUnitTest extends AndroidTestCase {

    public void testSaveData() throws Exception {
        UserData userData = new UserData(mContext);
        userData.saveData("test", 20);
        assertEquals(20, userData.getData("test"));
    }

    public void testGetData() throws Exception {
        UserData userData = new UserData(mContext);
        userData.saveData("test2", 13);
        int result = userData.getData("test2");
        assertEquals(13, result);
    }

    public void testSaveCurrentDate() throws Exception {
        UserData userData = new UserData(mContext);
        userData.saveCurrentDate("date", "12/02/1995");
        assertEquals("12/02/1995", userData.getCurrentDate("date"));
    }

    public void testGetCurrentDate() {
        UserData userData = new UserData(mContext);
        userData.saveCurrentDate("date2", "12/06/1996");
        String result = userData.getCurrentDate("date2");
        assertEquals("12/06/1996", result);
    }
    public void testDeleteData(){
        UserData userData = new UserData(mContext);
        userData.saveData("test3", 20);
        userData.deleteData("test3");
        int result = userData.getData("test3");
        assertEquals(0, result);
    }
}