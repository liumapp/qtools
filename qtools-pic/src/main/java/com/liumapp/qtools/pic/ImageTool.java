package com.liumapp.qtools.pic;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.Base64;

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
     * todo
     * cute the pic
     */
    public static void cutImage(int x, int y, int width, int height, InputStream srcImage, File distImage) throws IOException {
        ImageInputStream iis = null;
        try {
            ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
            iis = ImageIO.createImageInputStream(srcImage);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, "png", distImage);
        } finally {
            if (srcImage != null) {
                srcImage.close();
            }
            if (iis != null) {
                iis.close();
            }
        }
    }

    /**
     * read base64 image to BufferedImage
     * @param base64Pic the base64 of image
     * @return BufferedImage object
     * @throws IOException io exception
     */
    public static BufferedImage readBase64Image (String base64Pic) throws IOException {
        BufferedImage image = null;
        byte[] imageByte;
        ByteArrayInputStream bis;
        imageByte = Base64.getDecoder().decode(base64Pic);

        bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis);
        bis.close();

        return image;
    }

    public static String convertBufferedImageToBase64 (BufferedImage image) throws IOException {
        return convertBufferedImageToBase64(image, "png");
    }

    /**
     * convert BufferedImage object to base64 string
     * @param image BufferedImage object
     * @param format png,jpeg,gif
     * @return the base64 string
     * @throws IOException IOException
     */
    public static String convertBufferedImageToBase64 (BufferedImage image, String format) throws IOException {
        String imageString = null;
        byte[] imageBytes;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ImageIO.write(image, format, bos);
        imageBytes = bos.toByteArray();
        bos.close();

        imageString = Base64.getEncoder().encodeToString(imageBytes);

        return imageString;
    }

    /**
     * rotate pic 90 angle
     * @param base64 pic file base64 to convertion
     * @return String base64 after convertion
     */
    public static String rotate90(String base64) throws IOException {
        BufferedImage image = readBase64Image(base64);
        BufferedImage imageRotated = rotateImage(image, 90);
        return convertBufferedImageToBase64(imageRotated);
    }

    /**
     * rotate pic with specified angle
     * @param base64 the base64 value of the pic
     * @param angle angle to be rotated
     * @return the base64 of rotated pic
     * @throws IOException io exception
     */
    public static String rotate (String base64, double angle) throws IOException {
        BufferedImage image = readBase64Image(base64);
        BufferedImage imageRotated = rotateImage(image, angle);
        return convertBufferedImageToBase64(imageRotated);
    }

    /**
     *
     * @param pic1 BufferedImage to rotate
     * @param angle angle to rotate
     * @return BufferedImage
     * @throws IOException IOException
     */
    public static BufferedImage rotateImage(BufferedImage pic1, double angle) throws IOException {
        int width = pic1.getWidth(null);
        int height = pic1.getHeight(null);
        //change angle to radians (弧度)
        angle = Math.toRadians(angle);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        // point to rotate about
        double x0 = 0.5 * (width - 1);
        // center of image
        double y0 = 0.5 * (height - 1);

        WritableRaster inRaster = pic1.getRaster();
        BufferedImage pic2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster outRaster = pic2.getRaster();
        int[] pixel = new int[4];

        // rotation
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double a = x - x0;
                double b = y - y0;
                int xx = (int) (+a * cos - b * sin + x0);
                int yy = (int) (+a * sin + b * cos + y0);

                if (xx >= 0 && xx < width && yy >= 0 && yy < height) {
                    outRaster.setPixel(x, y, inRaster.getPixel(xx, yy, pixel));
                }
            }
        }
        return pic2;
    }

}
