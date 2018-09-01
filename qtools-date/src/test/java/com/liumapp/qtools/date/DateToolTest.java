package com.liumapp.qtools.date;

import org.junit.Test;

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

}
