package com.liumapp.qtools.ignores.pic;

import com.liumapp.qtools.file.ignores.Base64FileTool;
import com.liumapp.qtools.file.ignores.FileTool;
import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        File file = new File("./result.jpg");
        Assert.assertEquals(true, FileTool.isFileExists(file));
        FileTool.deleteFile(file);
    }

    @Test
    public void rotate () throws IOException {
        String picBase64 = Base64FileTool.filePathToBase64("../data/me.jpg");
        picBase64 = ImageTool.rotate(picBase64, 120);
        Base64FileTool.saveBase64File(picBase64, "./result.jpg");
        File file = new File("./result.jpg");
        Assert.assertEquals(true, FileTool.isFileExists(file));
        FileTool.deleteFile(file);
    }

    @Test
    public void rotateFromBase64 () throws IOException {
        String picBase64 = Base64FileTool.filePathToBase64("../data/download.png");
        picBase64 = ImageTool.rotate(picBase64, 135);
        Base64FileTool.saveBase64File(picBase64, "./result2.png");
        File file = new File("./result2.png");
        Assert.assertEquals(true, FileTool.isFileExists(file));
        FileTool.deleteFile(file);
    }

    @Test
    public void testLoadPic () throws IOException {
        String picBase64 = Base64FileTool.filePathToBase64("../data/me.jpg");
        BufferedImage image = ImageTool.readBase64Image(picBase64);
        String base64 = ImageTool.convertBufferedImageToBase64(image, "png");
        Base64FileTool.saveBase64File(base64, "./result.jpg");
        Assert.assertEquals(true, FileTool.isFileExists(new File("./result.jpg")));
        FileTool.deleteFile(new File("./result.jpg"));
    }

}