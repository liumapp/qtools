package com.liumapp.qtools.file.basic;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

/**
 * @author liumapp
 * @file FileToolTest.java
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2018/8/8
 */
public class FileToolTest extends TestCase {

    private String savePath = "/usr/local/tomcat/project/qtools/data/";

    public void testWriteStringToFile () throws IOException {
        String str = "sdfsdffqwfasdfawefsadfsdf{dfefd}";
        FileTool.writeStringToNewFile(new File(savePath + "test.txt"), str);
    }

}
