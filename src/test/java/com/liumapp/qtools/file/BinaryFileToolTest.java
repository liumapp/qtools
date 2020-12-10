package com.liumapp.qtools.file;

import com.liumapp.qtools.file.ignores.Base64FileTool;
import com.liumapp.qtools.file.ignores.BinaryFileTool;
import org.junit.Test;

import java.io.IOException;

/**
 * file BinaryFileToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/10
 */
public class BinaryFileToolTest {

    private String dataPath = "/usr/local/tomcat/project/qtools/data/";

    @Test
    public void testReadFileToBinaryBytes () throws IOException {
        byte[] fileBytes = BinaryFileTool.readFileByBytes(dataPath + "/me.jpg");
        System.out.println(fileBytes.length);//B
    }

    @Test
    public void testConvertBinaryFileBytesToBase64 () throws IOException {
        byte[] fileBytes = BinaryFileTool.readFileByBytes(dataPath + "/me.jpg");
        String base64 = Base64FileTool.BinaryBytesToBase64(fileBytes);
        System.out.println(base64);
        System.out.println(base64.length());
    }

    @Test
    public void testBase64ToBinaryBytes () throws IOException {
        String base64 = Base64FileTool.filePathToBase64(dataPath + "/me.jpg");
        byte[] data = BinaryFileTool.Base64ToBinaryBytes(base64);
        System.out.println(data.length);
    }

}
