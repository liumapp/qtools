package com.liumapp.qtools.security;

import org.junit.Assert;
import org.junit.Test;

/**
 * file Sha1ToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/1
 */
public class Sha1ToolTest {

    @Test
    public void testSHA1 () {
        String result = Sha1Tool.toSHA1("2018010110_W3JySHIhnxdrVuu");
        System.out.println(result);
        Assert.assertEquals("e4155a708323d32863a29eb2290e815fdfe8cde0", result);

    }

}
