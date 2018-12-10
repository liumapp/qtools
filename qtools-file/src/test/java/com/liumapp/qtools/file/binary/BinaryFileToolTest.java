package com.liumapp.qtools.file.binary;

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

    /**
     *
     * @throws IOException
     */
    @Test
    public void testReadFileToBinaryBytes () throws IOException {
        byte[] fileBytes = BinaryFileTool.readFileByBytes(dataPath + "/me.jpg");
        System.out.println(fileBytes.length);//B
    }

    public void testConvertBinaryFileBytesToBase64 () throws IOException {
        byte[] fileBytes = BinaryFileTool.readFileByBytes(dataPath + "/me.jpg");
        
    }

}
