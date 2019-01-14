package com.liumapp.qtools.pic;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * file ImageTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/1/14
 */
public class ImageTool {

    /**
     * 裁剪图片
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param srcImage  原始图片
     * @param distImage 目标图片
     * @throws IOException
     */
    public static void cutImage(int x, int y, int width, int height, InputStream srcImage, File distImage) throws IOException {
        ImageInputStream iis = null;
        try {
            ImageReader reader = ImageIO.getImageReadersByFormatName("jpg").next();//ImageReader声称能够解码指定格式
            iis = ImageIO.createImageInputStream(srcImage); //获取图片流
            reader.setInput(iis, true); //将iis标记为true（只向前搜索）意味着包含在输入源中的图像将只按顺序读取
            ImageReadParam param = reader.getDefaultReadParam(); //指定如何在输入时从 Java Image I/O框架的上下文中的流转换一幅图像或一组图像
            Rectangle rect = new Rectangle(x, y, width, height); //定义空间中的一个区域
            param.setSourceRegion(rect); //提供一个 BufferedImage，将其用作解码像素数据的目标。
            BufferedImage bi = reader.read(0, param); //读取索引imageIndex指定的对象
            ImageIO.write(bi, "jpg", distImage); //保存新图片
        } finally {
            if (srcImage != null) {
                srcImage.close();
            }
            if (iis != null) {
                iis.close();
            }
        }
    }

}
