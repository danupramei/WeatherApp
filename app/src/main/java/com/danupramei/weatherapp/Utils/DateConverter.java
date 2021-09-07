package com.danupramei.weatherapp.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {

    public static String toDay(Integer tanggal) {
        long time = tanggal * (long) 1000;
        Date date = new Date(time);
        String res = "";
        if (tanggal != 0) {
            try {
                SimpleDateFormat sdf2 = new SimpleDateFormat("EEE", new Locale("en", "US"));
                sdf2.setTimeZone(TimeZone.getDefault());
                res = sdf2.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static String toDate(Integer tanggal) {
        long time = tanggal * (long) 1000;
        Date date = new Date(time);
        String res = "";
        if (tanggal != 0) {
            try {
                SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE, MMM yyyy", new Locale("en", "US"));
                sdf2.setTimeZone(TimeZone.getDefault());
                res = sdf2.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

}
