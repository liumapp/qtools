package com.liumapp.qtools.collection.array;

import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * file ArrayQToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/1/15
 */
public class ArrayQToolTest {

    @Test
    public void clearStringArray() {
        String[] strings = mock(String[].class);
        when(strings[0]).thenReturn("test1");
        when(strings[1]).thenReturn("test2");
        when(strings[2]).thenReturn(null);
        when(strings[3]).thenReturn("");
        when(strings[4]).thenReturn("test3");
        when(strings[5]).thenReturn(null);

        ArrayQTool arrayQTool = mock(ArrayQTool.class);

//        String[] cleanArray = (String[]) ArrayTool.clearArray(testArray);
//        Assert.assertEquals("test1", cleanArray[0]);
//        Assert.assertEquals("test2", cleanArray[1]);
//        Assert.assertEquals("test3", cleanArray[2]);
//        Assert.assertEquals(3, cleanArray.length);
    }

    @Test
    public void searchIndexFromTwoDimensionalIntArray() {
    }
}