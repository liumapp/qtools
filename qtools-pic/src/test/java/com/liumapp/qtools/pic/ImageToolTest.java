package com.liumapp.qtools.pic;

import com.liumapp.qtools.file.base64.Base64FileTool;
import com.liumapp.qtools.file.basic.FileTool;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * file ImageToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/3/8
 */
public class ImageToolTest {

    @Test
    public void rotate90() throws IOException {
        String picBase64 = Base64FileTool.filePathToBase64("../data/me.jpg");

        picBase64 = ImageTool.rotate90(picBase64);
        Base64FileTool.saveBase64File(picBase64, "./result.jpg");
    }

    @Test
    public void testLoadPic () throws IOException {
        String picBase64 = Base64FileTool.filePathToBase64("../data/me.jpg");


    }
}