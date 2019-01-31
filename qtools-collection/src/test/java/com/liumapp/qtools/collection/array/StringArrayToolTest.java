package com.liumapp.qtools.collection.array;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * file StringArrayToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/1/31
 */
public class StringArrayToolTest {

    @Test
    public void stringArrayToCommaLine() {
        String[] array = {"aaa" , "bbb" , "ccc", "ddd"};
        String str = StringArrayTool.StringArrayToCommaLine(array);
        assertEquals("'aaa','bbb','ccc','ddd'", str);
    }
}