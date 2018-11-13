package com.liumapp.qtools.str;

import com.liumapp.qtools.str.random.StrRandomTool;
import junit.framework.TestCase;

/**
 * author liumapp
 * file StrRandomToolTest.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/15
 */
public class StrRandomToolTest extends TestCase {

    public void testGetUUid () {
        System.out.println(StrRandomTool.getUuid());
        System.out.println(StrRandomTool.getUuid(true));
    }

}
