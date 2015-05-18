package com.caturday.app.util.helper;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {

    /**
     * Formats the a date string into a "time ago" format
     * @param date
     * @return
     */
    public static String formatDate(String date) {

        String howLongAgo = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date dateTime = format.parse(date);
            howLongAgo = new PrettyTime().format(dateTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return howLongAgo;
    }
}
