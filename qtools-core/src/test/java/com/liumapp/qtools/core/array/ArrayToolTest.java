package com.liumapp.qtools.core.array;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ArrayToolTest {

    //清空String数组中的null与empty值
    @Test
    public void testClearStringArray () {
        String[] testArray = {"test1", "test2", null, "", "test3", null};
        String[] cleanArray = (String[]) ArrayTool.clearArray(testArray);
        Assert.assertEquals("test1", cleanArray[0]);
        Assert.assertEquals("test2", cleanArray[1]);
        Assert.assertEquals("test3", cleanArray[2]);
        Assert.assertEquals(3, cleanArray.length);
    }

    //清空File数组中的null值
    @Test
    public void testClearFileArray () {

        File[] testArray = {
                new File(this.getClass().getResource("/me.jpg").getFile()),
                null,
                null,
                new File(this.getClass().getResource("/qr.jpg").getFile())
        };
        File[] cleanArray = (File[]) ArrayTool.clearArray(testArray);
        Assert.assertEquals(2, cleanArray.length);
        Assert.assertEquals("me.jpg", cleanArray[0].getName());
        Assert.assertEquals("qr.jpg", cleanArray[1].getName());
    }

    /**
     * 清空Object数组中的null值
     * 请注意，这个方法不能清空empty值
     */
    @Test
    public void testClearObjectArray () {
        Object[] testArray = {
                "test1",
                null,
                "",
                new File(this.getClass().getResource("/me.jpg").getFile())
        };
        Object[] cleanArray = ArrayTool.clearArray(testArray);
        File fileObject = (File) cleanArray[2];
        Assert.assertEquals(3, cleanArray.length);
        Assert.assertEquals("test1", cleanArray[0]);
        Assert.assertEquals("", cleanArray[1]);
        Assert.assertEquals("me.jpg", fileObject.getName());
    }

    @Test
    public void testSearchIndexFromTwoDimensionalIntArray () {
        int[][] array = {{1,2,3,4}, {4,5,6,7,8,9}};
        int value = 9;
        int[] position = ArrayTool.searchIndexFromTwoDimensionalIntArray(array, value);
        Assert.assertEquals(1, position[0]);
        Assert.assertEquals(5, position[1]);
    }

    @Test
    public void convertStringsToString() {
        String[] array = {"aaa" , "bbb" , "ccc", "ddd"};
        String str = ArrayTool.convertStringsToString(array);
        assertEquals("'aaa','bbb','ccc','ddd'", str);
    }
}