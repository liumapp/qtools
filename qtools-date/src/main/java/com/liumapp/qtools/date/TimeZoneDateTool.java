package com.liumapp.qtools.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
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

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * get shang hai current time with custom dateFormat
     */
    public static String getShangHaiCurrentTime (String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai"));
        return getFormatDate(formatter, timeZone);
    }

    /**
     * get shang hai current time with dateFormat of yyyy-MM-dd HH:mm:ss
     */
    public static String getSimpleShangHaiCurrentTime () {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai"));
        return getFormatDate(formatter, timeZone);
    }

    /**
     * get custom zone current time with custom date format
     */
    public static String getCustomZoneCurrentTime (String dateFormat, TimeZone timeZone) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return getFormatDate(formatter, timeZone);
    }

    /**
     * get custom zone current time with dateFormat of yyyy-MM-dd HH:mm:ss
     */
    public static String getSimpleCustomZoneCurrentTime (TimeZone timeZone) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return getFormatDate(formatter, timeZone);
    }

    private static String getFormatDate (SimpleDateFormat formatter, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
        formatter.setTimeZone(timeZone);
        return formatter.format(calendar.getTime());
    }

}
