package com.liumapp.qtools.ignores.string;

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
        assertEquals(true,
                SuffixTool.checkStringSuffix("dfsdfewfwe.jpg", "jpg"));
        assertEquals(true, SuffixTool.checkStringSuffix("/suer/dfief/dfsd.dfe", ".dfe"));
        assertEquals(false,
                SuffixTool.checkStringSuffix("/ef/dfdf.png", "jpg"));

    }

    public void testDeleteSuffix () {
        String name = "../data/test.pdf";
        String result = SuffixTool.deleteSuffix(name);
        assertEquals("../data/test", result);
    }

}
