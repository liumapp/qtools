package com.liumapp.qtools.file.base64;

import com.liumapp.qtools.file.basic.FileTool;
import com.liumapp.qtools.file.config.TestConfig;
import com.liumapp.qtools.file.load.LoadFileTool;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * author liumapp
 * file Base64FileToolTest.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/8
 */
public class Base64FileToolTest extends TestCase {

    public void testFilePathToBase64 () throws IOException {
        System.out.println(Base64FileTool.filePathToBase64(TestConfig.savePath + "me.jpg"));
    }

    public void testJPGToBase64ThanSaveToPNG () throws IOException {
        Base64FileTool.saveBase64File(Base64FileTool.filePathToBase64(TestConfig.savePath + "me.jpg"), TestConfig.savePath + "/mock/me.png");
    }

    public void testReadBase64FromFile () throws IOException {
        String results = FileTool.readFileAsString(TestConfig.savePath + "base64.txt");
        Base64FileTool.saveBase64File(results, TestConfig.savePath + "base64.png");
    }

    public void testNIOFilePath () throws IOException {
        System.out.println(Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(TestConfig.savePath + "me.jpg"))));
    }

    public void testBase64StringToFile () throws IOException {
        String base64File = Base64FileTool.filePathToBase64(TestConfig.savePath + "test.pdf");
        Base64FileTool.saveBase64File(base64File, TestConfig.savePath + "/tt/dd/out.pdf");
        Assert.assertEquals(true, FileTool.isFileExists(TestConfig.savePath + "/tt/dd/out.pdf"));
        FileTool.deleteDir(TestConfig.savePath + "/tt");
        Assert.assertEquals(false, FileTool.isFileExists(TestConfig.savePath + "/tt/dd/out.pdf"));
    }

    public void testInputStreamToBase64 () throws IOException {
        InputStream is = LoadFileTool.loadInputStream("test.pdf");
        try {
            String base64Content = Base64FileTool.inputStreamToBase64(is);
            Base64FileTool.saveBase64File(base64Content, TestConfig.savePath + "/tt/dd/out.pdf");
            Assert.assertEquals(true, FileTool.isFileExists(TestConfig.savePath + "/tt/dd/out.pdf"));
            FileTool.deleteDir(TestConfig.savePath + "/tt");
            Assert.assertEquals(false, FileTool.isFileExists(TestConfig.savePath + "/tt/dd/out.pdf"));
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public void testCheckBase64 () {
        TestBase64Content testBase64Content = new TestBase64Content();
        Assert.assertEquals(true, CheckBase64Tool.checkHeader(testBase64Content.getTestBase64WithHeader()));
        Assert.assertEquals(false, CheckBase64Tool.checkHeader(testBase64Content.getTestBase64WithoutHeader()));
    }

}
