package com.andela.gkuti.mipista;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Date {
    public static String getDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sd = new SimpleDateFormat("dd/M/yyyy");
        return sd.format(calendar.getTime());
    }

    public static String getTime() {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        return sd.format(date);
    }
}
