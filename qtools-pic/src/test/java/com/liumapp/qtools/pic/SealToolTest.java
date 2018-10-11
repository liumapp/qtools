package com.liumapp.qtools.pic;

import com.liumapp.qtools.file.basic.FileTool;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * file SealToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/10/11
 */
public class SealToolTest extends TestCase {

    private String savePath = "/usr/local/tomcat/project/qtools/data/";

    @Test
    public void generateCompanySearl () {
        SealTool.generateSealFile("测试某某企业", savePath + "test.png");
        Assert.assertEquals(true, FileTool.isFileExists(savePath + "test.png"));
//        try {
//            SealTool.generateSealFile("第二次测试某某企业", savePath + "test.jpg");
//        } catch ()

    }

}
