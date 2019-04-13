package com.liumapp.qtools.file.basic;

import com.liumapp.qtools.file.config.TestConfig;
import junit.framework.Test;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * author liumapp
 * file FileToolTest.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/8
 */
public class FileToolTest extends TestCase {

    public void testWriteStringToFile () throws IOException {
        String str = "sdfsdffqwfasdfawefsadfsdf{dfefd}";
        FileTool.writeStringToNewFile(new File("../data/test.txt"), str);
    }

    public void testCreateFileObject () throws IOException {
        FileTool.createFileObject("../data/qq/tt/", "aaa");
        Assert.assertEquals(true, FileTool.isFileExists("../data/qq/tt/aaa"));
        FileTool.deleteDir(TestConfig.savePath + "/qq");
        Assert.assertEquals(false, FileTool.isDirectory(TestConfig.savePath + "/qq"));
    }

    public void testInputSreamToFile () throws IOException {
        InputStream is = new FileInputStream(new File(TestConfig.savePath + "/me.jpg"));
        Assert.assertEquals(true, FileTool.createFileFromInputStream(is, TestConfig.savePath + "/qq/tt/t.jpg"));
        Assert.assertEquals(true, FileTool.isFileExists(TestConfig.savePath + "/qq/tt/t.jpg"));
        FileTool.deleteDir(TestConfig.savePath + "/qq");
        Assert.assertEquals(false, FileTool.isDirectory(TestConfig.savePath + "/qq"));
    }

    /**
     * 检查一个文件的父级目录是否存在
     * 不存在则创建
     * 创建失败则返回false
     *
     */
    public void testCheckFilePath () throws IOException {
        if (!FileTool.checkFilePath("/usr/local/tomcat/project/qtools/data/tt/a")) {
            System.out.println("check file path failed , plz check the permission");
        }
    }

    /**
     * 测试以字节数组的形式创建文件
     */
    public void testWriteFileAsBytes () throws IOException {
        byte[] bytes = new byte[ 1024 * 1024 * 2 ];
        File file = new File("test.txt");
        FileTool.writeFileAsBytes(file.getAbsolutePath(), bytes);
        if (FileTool.createFile(file)) {
            FileTool.deleteFile(file);
        }
    }

    public void testReadFileContentsFromInputStream () throws IOException {

    }

}
