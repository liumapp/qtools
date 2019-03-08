package com.liumapp.qtools.pic;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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

    /**
     * 图片旋转90度
     * @param base64 pic file base64 to convertion
     * @return String base64 after convertion
     */
    public static String rotate90(String base64) throws IOException {
        BufferedImage image = null;
        byte[] imageByte;
        BASE64Decoder decoder = new BASE64Decoder();
        imageByte = decoder.decodeBuffer(base64);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis);
        bis.close();
//        image = roateImage(image, 270);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        String result = bos.toString();
        String result2 = new String(bos.toByteArray(), "UTF-8");
        return new BASE64Encoder().encode(new String(bos.toByteArray(), "UTF-8").getBytes());
//        byte[] bytes = null;
//
////        String code = base64.replace(' ', '+');
//        bytes = Base64.getDecoder().decode(base64);
//        BufferedImage bufferedImage = null;
//        bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes)); // 读取该图片
//        //顺时针旋转90度
////        bufferedImage = roateImage(bufferedImage, 270);
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, "png", os);//写入流中
//
//        return Base64.getEncoder().encodeToString(os.toByteArray());
    }

    /**
     *
     * @param pic1 BufferedImage to rotate
     * @param angle angle to rotate
     * @return BufferedImage
     * @throws IOException IOException
     */
    public static BufferedImage roateImage(BufferedImage pic1, double angle) throws IOException {
        int width = pic1.getWidth(null);
        int height = pic1.getHeight(null);

        angle = Math.toRadians(angle);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double x0 = 0.5 * (width - 1);     // point to rotate about
        double y0 = 0.5 * (height - 1);     // center of image

        WritableRaster inRaster = pic1.getRaster();
        BufferedImage pic2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster outRaster = pic2.getRaster();
        int[] pixel = new int[3];

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

    /**
     * 对图片进行旋转
     *
     * @param src   被旋转图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    public static BufferedImage rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);

        System.out.println(src_height   +""+ src_width);
        // 计算旋转后图片的尺寸
        Rectangle rect_des = calcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);
        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        res = g2.getDeviceConfiguration().createCompatibleImage(src_height,src_width,Transparency.TRANSLUCENT);
        g2.dispose();
        g2 = res.createGraphics();
        // 进行转换
        g2.translate((rect_des.width - src_width) / 2,
                (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

        g2.drawImage(src, null, null);
        return res;
    }

    /**
     * 计算旋转后的图片
     *
     * @param src   被旋转的图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    public static Rectangle calcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }

}
