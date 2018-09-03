package com.liumapp.qtools.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * author liumapp
 * file DateTool.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/12
 */
public class DateTool {

    /**
     * return a new date according to year number
     * @param year year number
     * @return end date
     */
    public static Date getEndDateByYearNumber(int year) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        date = calendar.getTime();
        return date;
    }

    public static String getDateString (Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     *  yyyyMMddHH 2018090115
     */
    public static String getSimpleDateString (Date date) {
        return getDateString(date, "yyyyMMddHH");
    }

    /**
     * yyyyMMddHH 2018090108
     */
    public static String getSimpleUTCDateString () {
        return getSimpleDateString(getUTCDate());
    }

    public static Date getUTCDate () {
        Calendar cal = Calendar.getInstance();
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }

    public static String getBeiJingLocalTimeFromUTC (String utcDate, String pattern) throws ParseException {
        DateFormat format = new SimpleDateFormat(pattern);
        Date UTCDate = format.parse(utcDate);
        format.setTimeZone(TimeZone.getTimeZone("GMT-8")) ;
        String localTime = format.format(UTCDate) ;
        return localTime ;
    }

}
