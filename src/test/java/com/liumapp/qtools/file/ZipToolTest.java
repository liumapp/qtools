package com.liumapp.qtools.file;

import com.liumapp.qtools.file.ignores.FileTool;
import com.liumapp.qtools.file.ignores.ZipTool;
import net.lingala.zip4j.exception.ZipException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * file ZipToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/3/7
 */
public class ZipToolTest {

    @Test
    public void zip() throws ZipException, IOException {
        ZipTool.zip("../data", "123");
        Assert.assertEquals(true, FileTool.isFileExists("./data.zip"));
        FileTool.deleteFile("./data.zip");
    }

    @Test
    public void zip1() throws ZipException, IOException {
        ZipTool.zip("../data", "../tmp/data.zip", null);
        Assert.assertEquals(true, FileTool.isFileExists("../tmp/data.zip"));
        FileTool.deleteDir("../tmp");
    }

    @Test
    public void unzip() throws ZipException, IOException {
        ZipTool.zip("../data", "../tmp/data.zip", "123456");
        Assert.assertEquals(true, FileTool.isFileExists("../tmp/data.zip"));
        ZipTool.unzip("../tmp/data.zip", "../tmp/","123456");
        Assert.assertEquals(true, FileTool.isFileExists("../tmp/data/"));
        Assert.assertEquals(true, FileTool.isDirectory("../tmp/data/"));
        FileTool.deleteDir("../tmp");
    }


}