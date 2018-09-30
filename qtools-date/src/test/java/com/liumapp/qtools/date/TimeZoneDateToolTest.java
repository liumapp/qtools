package com.liumapp.qtools.date;

import junit.framework.TestCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * file TimeZoneDateToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/30
 */
public class TimeZoneDateToolTest extends TestCase {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss a";

    public void testGetSimpleShanghaiCurrentTime () {
        System.out.println(TimeZoneDateTool.getSimpleShangHaiCurrentTime());
    }

    public void testGetCustomShanghaiCurrentTime () {
        System.out.println(TimeZoneDateTool.getShangHaiCurrentTime(DATE_FORMAT));
    }

    public void testGetSimpleCustomCurrentTime () {
        System.out.println(
                TimeZoneDateTool.getSimpleCustomZoneCurrentTime(
                    TimeZone.getTimeZone(
                            ZoneId.of("America/Chicago")
                    )
                )
        );
    }

    public void testGetCustomCurrentTime () {
        System.out.println(
                TimeZoneDateTool.getCustomZoneCurrentTime(
                        DATE_FORMAT,
                        TimeZone.getTimeZone(
                                ZoneId.of("America/Chicago")
                        )
                )
        );
    }

    public void testDateTime () {
        Date date = new Date();
        System.out.println(date.getTime());
        System.out.println(date);
    }

    public void testGetLocalTime () {
        TimeZone newYork = TimeZone.getTimeZone(ZoneId.of("America/New_York"));
        TimeZone shangHai = TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai"));
        TimeZone ababa = TimeZone.getTimeZone(ZoneId.of("Africa/Addis_Ababa"));
        Calendar newYorkCalendar = Calendar.getInstance(newYork);
        Calendar shangHaiCalendar = Calendar.getInstance(shangHai);
        Calendar ababaCalendar = Calendar.getInstance(ababa);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        simpleDateFormat.setTimeZone(newYork);
        System.out.println(simpleDateFormat.format(newYorkCalendar.getTime()));
        simpleDateFormat.setTimeZone(shangHai);
        System.out.println(simpleDateFormat.format(shangHaiCalendar.getTime()));
        simpleDateFormat.setTimeZone(ababa);
        System.out.println(simpleDateFormat.format(ababaCalendar.getTime()));
    }

    /**
     * the wrong used way
     */
    public void testGetCurrentTimeByZonedDate () {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId shangHaiZoneId = ZoneId.of("Asia/Shanghai");
        ZoneId newYorkZondId = ZoneId.of("America/New_York");
        ZoneId singaPoreZondId = ZoneId.of("Asia/Singapore");
        ZonedDateTime shangHaiZonedDateTime = localDateTime.atZone(shangHaiZoneId);
        ZonedDateTime newYorkZonedDateTime = localDateTime.atZone(newYorkZondId);
        ZonedDateTime singaPoreZonedDateTime = localDateTime.atZone(singaPoreZondId);
        System.out.println(shangHaiZonedDateTime);
        System.out.println(newYorkZonedDateTime);
        System.out.println(singaPoreZonedDateTime);
    }

    public void testZonedDateTime () {

        String dateInString = "22-1-2015 10:15:55 AM";
        LocalDateTime ldt = LocalDateTime.parse(dateInString, DateTimeFormatter.ofPattern(DATE_FORMAT));

        ZoneId singaporeZoneId = ZoneId.of("Asia/Singapore");
        System.out.println("TimeZone : " + singaporeZoneId);

        //LocalDateTime + ZoneId = ZonedDateTime
        ZonedDateTime asiaZonedDateTime = ldt.atZone(singaporeZoneId);
        System.out.println("Date (Singapore) : " + asiaZonedDateTime);

        ZoneId newYokZoneId = ZoneId.of("America/New_York");
        System.out.println("TimeZone : " + newYokZoneId);

        ZonedDateTime nyDateTime = asiaZonedDateTime.withZoneSameInstant(newYokZoneId);
        System.out.println("Date (New York) : " + nyDateTime);

        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
        System.out.println("\n---DateTimeFormatter---");
        System.out.println("Date (Singapore) : " + format.format(asiaZonedDateTime));
        System.out.println("Date (New York) : " + format.format(nyDateTime));

    }

    public void testDate () throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        String dateInString = "22-01-2015 10:15:55 AM";
        Date date = formatter.parse(dateInString);
        TimeZone tz = TimeZone.getDefault();

        // From TimeZone Asia/Singaporex
        System.out.println("TimeZone : " + tz.getID() + " - " + tz.getDisplayName());
        System.out.println("TimeZone : " + tz);
        System.out.println("Date (Singapore) : " + formatter.format(date));

        // To TimeZone America/New_York
        SimpleDateFormat sdfAmerica = new SimpleDateFormat(DATE_FORMAT);
        TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
        sdfAmerica.setTimeZone(tzInAmerica);

        String sDateInAmerica = sdfAmerica.format(date); // Convert to String first
        Date dateInAmerica = formatter.parse(sDateInAmerica); // Create a new Date object

        System.out.println("\nTimeZone : " + tzInAmerica.getID() + " - " + tzInAmerica.getDisplayName());
        System.out.println("TimeZone : " + tzInAmerica);
        System.out.println("Date (New York) (String) : " + sDateInAmerica);
        System.out.println("Date (New York) (Object) : " + formatter.format(dateInAmerica));
    }

    public void testCalendar () throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        String dateInString = "22-01-2015 10:15:55 AM";
        Date date = formatter.parse(dateInString);
        TimeZone tz = TimeZone.getDefault();

        // From TimeZone Asia/Singapore
        System.out.println("TimeZone : " + tz.getID() + " - " + tz.getDisplayName());
        System.out.println("TimeZone : " + tz);
        System.out.println("Date (Singapore) : " + formatter.format(date));

        // To TimeZone America/New_York
        SimpleDateFormat sdfAmerica = new SimpleDateFormat(DATE_FORMAT);
        TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
        sdfAmerica.setTimeZone(tzInAmerica);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.setTimeZone(tzInAmerica);

        System.out.println("\nTimeZone : " + tzInAmerica.getID() + " - " + tzInAmerica.getDisplayName());
        System.out.println("TimeZone : " + tzInAmerica);

        //Wrong! It will print the date with the system default time zone
        System.out.println("Date (New York) (Wrong!): " + calendar.getTime());

        //Correct! need formatter
        System.out.println("Date (New York) (Correct!) : " + sdfAmerica.format(calendar.getTime()));

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR); // 12 hour clock
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int ampm = calendar.get(Calendar.AM_PM); //0 = AM , 1 = PM

        //Correct
        System.out.println("\nyear \t\t: " + year);
        System.out.println("month \t\t: " + month + 1);
        System.out.println("dayOfMonth \t: " + dayOfMonth);
        System.out.println("hour \t\t: " + hour);
        System.out.println("minute \t\t: " + minute);
        System.out.println("second \t\t: " + second);
        System.out.println("ampm \t\t: " + ampm);
    }

}
