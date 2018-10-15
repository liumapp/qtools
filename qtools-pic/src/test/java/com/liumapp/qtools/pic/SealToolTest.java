package com.liumapp.qtools.pic;

import com.liumapp.qtools.file.basic.FileTool;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

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
    public void testGenerateCompanySearl () throws IOException {
        //生成宋体签章
        SealTool.generateSealFile("浙江葫芦娃网络集团有限公司", savePath + "test.png");
        Assert.assertEquals(true, FileTool.isFileExists(savePath + "test.png"));



        try {
            SealTool.generateSealFile("某某企业2号", savePath + "test.jpg");
        } catch (IOException e) {
            Assert.assertEquals("save file must be a png file", e.getMessage());
        }

        try {
            SealTool.generateSealFile("很长很长很长很长很长很长很长很长很长的企业名称呀呀呀呀", savePath + "test.png");
        } catch (IOException e) {
            Assert.assertEquals("company name can not exceed 25 chars", e.getMessage());
        }

    }

}
