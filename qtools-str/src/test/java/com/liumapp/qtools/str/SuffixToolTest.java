package com.liumapp.qtools.str;

import com.liumapp.qtools.str.suffix.SuffixTool;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * file SuffixToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/10/11
 */
public class SuffixToolTest extends TestCase {

    public void testCheckFileSuffix () {
        boolean result = SuffixTool.checkStringSuffix("/usr/local/qqt.jpg", "jpg");
        Assert.assertEquals(true,
                SuffixTool.checkStringSuffix("dfsdfewfwe.jpg", "jpg"));
        Assert.assertEquals(true, SuffixTool.checkStringSuffix("/suer/dfief/dfsd.dfe", ".dfe"));
        Assert.assertEquals(false,
                SuffixTool.checkStringSuffix("/ef/dfdf.png", "jpg"));

    }

    public void testDeleteSuffix () {
        String name = "../data/test.pdf";
        String result = SuffixTool.deleteSuffix(name);
        assertEquals("../data/test", result);
    }

}
