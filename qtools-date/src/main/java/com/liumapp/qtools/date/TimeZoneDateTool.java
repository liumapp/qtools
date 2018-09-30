package com.liumapp.qtools.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * file TimeZoneDateTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/30
 */
public class TimeZoneDateTool {

    private static final String DATE_FORMAT = "yyyy-M-dd HH:mm:ss";

    public static Date getShangHaiDate (String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return getTimeZoneDate(formatter);
    }

    public static Date getSimpleShangHaiDate () {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return getTimeZoneDate(formatter);
    }

    private static Date getTimeZoneDate (SimpleDateFormat formatter) {
        Date date = new Date();
        return date;
    }

    private static String getFormatDate (SimpleDateFormat formatter, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
        formatter.setTimeZone(timeZone);
        return formatter.format(calendar.getTime());
    }

}
