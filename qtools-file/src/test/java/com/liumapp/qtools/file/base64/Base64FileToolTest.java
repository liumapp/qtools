package com.liumapp.qtools.file.base64;

import junit.framework.TestCase;

import java.io.IOException;

/**
 * @author liumapp
 * @file Base64FileToolTest.java
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2018/8/8
 */
public class Base64FileToolTest extends TestCase {

    private String savePath = "/usr/local/tomcat/project/qtools/data/";

    public void testFilePathToBase64 () throws IOException {
        System.out.println(Base64FileTool.filePathToBase64(savePath + "test.pdf"));
    }

}
