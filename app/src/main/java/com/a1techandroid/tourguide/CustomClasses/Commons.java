package com.a1techandroid.tourguide.CustomClasses;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Commons {

    public static String SimpleGMTTimeFormat(String date){
        String timeFormat= date;
        String newDate = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zz yyy");
        Date d = null;
        try {
            d = inputFormat.parse(timeFormat);
            Log.i("dateGMT", "" + d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputFormat = null;
        outputFormat = new java.text.SimpleDateFormat("EE, dd MMM yyyy");
        newDate = outputFormat.format(d);
        Log.i("dateGMT22", "" + newDate);
        return newDate;
    }

}
