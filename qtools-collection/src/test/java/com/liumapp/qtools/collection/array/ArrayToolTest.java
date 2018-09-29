package com.liumapp.qtools.collection.array;

import com.liumapp.qtools.collection.config.TestConfig;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.File;

/**
 * file ArrayToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/29
 */
public class ArrayToolTest extends TestCase {

    public void testClearStringArray () {
        String[] testArray = {"test1", "test2", null, "", "test3", null};
        String[] cleanArray = (String[]) ArrayTool.clearArray(testArray);
        Assert.assertEquals("test1", cleanArray[0]);
        Assert.assertEquals("test2", cleanArray[1]);
        Assert.assertEquals("test3", cleanArray[2]);
        Assert.assertEquals(3, cleanArray.length);
    }

    public void testClearFileArray () {
        File[] testArray = {
                new File(TestConfig.savePath + "/me.jpg"),
                null,
                null,
                new File(TestConfig.savePath + "/qr.jpg")
        };
        File[] cleanArray = (File[]) ArrayTool.clearArray(testArray);
        Assert.assertEquals(2, cleanArray.length);
    }

    public void testClearObjectArray () {
        
    }

}
