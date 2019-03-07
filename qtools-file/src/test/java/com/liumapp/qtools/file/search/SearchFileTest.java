package com.liumapp.qtools.file.search;

import com.liumapp.qtools.file.config.TestConfig;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import javax.sound.midi.SysexMessage;
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
        String[] names = SearchFileTool.hasFile(TestConfig.savePath, "test", ".txt");
        Assert.assertEquals(2, names.length);

        String[] names2 = SearchFileTool.hasFileWithBeginString(TestConfig.savePath, "test");
        Assert.assertEquals(3, names2.length);

        String[] names3 = SearchFileTool.hasFileWithEndString(TestConfig.savePath, ".txt");
        Assert.assertEquals(4, names3.length);
    }

    @Test
    public void getFileList () {
        File[] files = SearchFileTool.getFileList("./", "R");
        Assert.assertEquals(3, files.length);
    }

}
