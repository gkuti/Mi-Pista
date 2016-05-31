package com.andela.gkuti.mipista.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Date class
 */
public class Date {
    /**
     * the method for getting current date
     *
     * @return string of date
     */
    public static String getDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sd = new SimpleDateFormat("dd/M/yyyy");
        return sd.format(calendar.getTime());
    }

    /**
     * the method for getting current time
     *
     * @return string of time
     */
    public static String getTime() {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        return sd.format(date);
    }
}
