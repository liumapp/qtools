package com.liumapp.qtools.collection.array;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

/**
 * file ArrayToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/29
 */
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

}
