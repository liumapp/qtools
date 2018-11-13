package com.liumapp.qtools.date;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * file DateToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/1
 */
public class DateToolTest {

    @Test
    public void testSimpleDate () {
        String result = DateTool.getSimpleDateString(new Date());
        System.out.println(result);
    }

    @Test
    public void testGetSimpleUTCDate () {
        String result = DateTool.getSimpleUTCDateString();
        System.out.println(result);
    }

    @Test
    public void testGetLocalTimeFromUTC () throws ParseException {
        String result = DateTool.getBeiJingLocalTimeFromUTC(DateTool.getSimpleUTCDateString(), "yyyyMMddHH");
        System.out.println(result);
    }

    @Test
    public void testDateStringToDate () {
        String dateString = TimeZoneDateTool.getShangHaiCurrentTime(
                TimeZoneDateToolTest.DATE_FORMAT
        );
        System.out.println(dateString);
        System.out.println(DateTool.getDateByTimeString(
                dateString,
                TimeZoneDateToolTest.DATE_FORMAT
        ));
    }

}
