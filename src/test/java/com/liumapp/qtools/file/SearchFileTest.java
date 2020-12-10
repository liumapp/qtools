package com.liumapp.qtools.file;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

/**
 * file SearchFileTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/29
 */
public class SearchFileTest {

    @Test
    public void testFindFiles () {
        String[] names = SearchFileTool.hasFile("../data/", "test", ".txt");
        Assert.assertEquals(2, names.length);

        String[] names2 = SearchFileTool.hasFileWithBeginString("../data/", "test");
        Assert.assertEquals(8, names2.length);

        String[] names3 = SearchFileTool.hasFileWithEndString("../data/", ".txt");
        Assert.assertEquals(4, names3.length);
    }

    @Test
    public void getFileList () {
        File[] files = SearchFileTool.getFileList("./", "R");
        Assert.assertEquals(3, files.length);
    }

}
