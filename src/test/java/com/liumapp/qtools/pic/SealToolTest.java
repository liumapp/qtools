package com.liumapp.qtools.pic;

import com.liumapp.qtools.file.ignores.FileTool;
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
        SealTool.generateSealFile("浙江某某科技有限公司", savePath + "/test.png");
        Assert.assertEquals(true, FileTool.isFileExists(savePath + "/test.png"));

        //生成系统默认字体签章
        SealTool.generateDefaultSealFile("浙江宋体某某科技有限公司", savePath + "/test2.png");
        Assert.assertEquals(true, FileTool.isFileExists(savePath + "/test2.png"));

        //生成自定义字体签章
        SealTool.generateSealFileWithSpecifiedFonts("浙江黑体某某科技有限公司", savePath + "/test3.png", "黑体");
        Assert.assertEquals(true, FileTool.isFileExists(savePath + "/test3.png"));

        try {
            SealTool.generateSealFile("某某企业2号", savePath + "/test.jpg");
        } catch (IOException e) {
            Assert.assertEquals("save file must be a png file", e.getMessage());
        }

        try {
            SealTool.generateSealFile("很长很长很长很长很长很长很长很长很长的企业名称呀呀呀呀", savePath + "/test.png");
        } catch (IOException e) {
            Assert.assertEquals("company name can not exceed 25 chars", e.getMessage());
        }

    }

}
