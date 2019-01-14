package com.liumapp.qtools.pic;

import com.liumapp.qtools.file.basic.FileTool;
import com.liumapp.qtools.str.suffix.SuffixTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * file SealTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/10/11
 */
public class SealTool {

    private static final int canvasWidth = 400;

    private static final int canvasHeight = 400;

    private static final double lineArc = 80 * (Math.PI/180);//角度转弧度

    private static final String center = "";

    public static InputStream getSealInputStream (String companyName, String fontName) {
        BufferedImage bi = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        bi = g2d.getDeviceConfiguration().createCompatibleImage(canvasWidth, canvasHeight, Transparency.TRANSLUCENT);
        g2d = bi.createGraphics();
        //设置画笔
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(canvasWidth, canvasWidth, canvasWidth, canvasWidth);
        int circleRadius = Math.min(canvasWidth,canvasHeight)/2;
        //draw circle
        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(10));//设置画笔的粗度（也就是圆的）
        Shape circle = new Arc2D.Double(10,10,circleRadius*2-20,circleRadius*2-20,0,360,Arc2D.OPEN);
        g2d.draw(circle);
        //draw line
        double halfHeight = circleRadius * (Math.cos(lineArc));
        double halfWidth = circleRadius * (Math.sin(lineArc));

        //draw string
        int fontSize = 30;
        Font f = new Font(fontName, Font.PLAIN, fontSize);
        FontRenderContext context = g2d.getFontRenderContext();
        //绘制中间的五角星
        g2d.setFont(new Font(fontName, Font.BOLD, 150));
        int CENTERX = canvasWidth/2;//画图所出位置
        int CENTERY = canvasHeight/2;//画图所处位置
        g2d.drawString("★", CENTERX-(120/2)-15, CENTERY+(120/2));

        //draw string head
        fontSize = 50;
        if (companyName.length() > 18) {
            fontSize = 40;
        }
        f = new Font(fontName, Font.BOLD,fontSize);
        context = g2d.getFontRenderContext();
        Rectangle2D bounds = f.getStringBounds(companyName,context);
        double msgWidth = bounds.getWidth();
        int countOfMsg = companyName.length();
        double interval = msgWidth/(countOfMsg-1)-10;//计算印章字的间距 减的越多 字靠的越近


        double newRadius = circleRadius + bounds.getY()-15;//bounds.getY()是负数，这样可以将弧形文字固定在圆内了。-15目的是印章的字离圆环稍远一点
        double radianPerInterval = 2 * Math.asin(interval / (2 * newRadius));//每个间距对应的角度
        //第一个元素的角度
        double firstAngle;
        if(countOfMsg % 2 == 1){//奇数
            firstAngle = (countOfMsg-1)*radianPerInterval/2.0 + Math.PI/2+0.08;
        }else{//偶数
            firstAngle = (countOfMsg/2.0-1)*radianPerInterval + radianPerInterval/2.0 +Math.PI/2+0.08;
        }

        for(int i = 0;i<countOfMsg;i++){
            double aa = firstAngle - i*radianPerInterval;
            double ax = newRadius * Math.sin(Math.PI/2 - aa);//小小的trick，将【0，pi】区间变换到[pi/2,-pi/2]区间
            double ay = newRadius * Math.cos(aa-Math.PI/2);//同上类似，这样处理就不必再考虑正负的问题了
            AffineTransform transform = AffineTransform .getRotateInstance(Math.PI/2 - aa);// ,x0 + ax, y0 + ay);
            Font f2 = f.deriveFont(transform);
            g2d.setFont(f2);
            g2d.drawString(companyName.substring(i,i+1), (float) (circleRadius+ax),  (float) (circleRadius - ay));
        }
        g2d.dispose();//销毁资源
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }

    /**
     * generate a seal pic with simsun font .
     */
    public static boolean generateSealFile (String companyName, String savePath) throws IOException {
        if (checkParams(companyName, savePath)) {
            InputStream is = getSealInputStream(companyName, "宋体");
            FileTool.createFileFromInputStream(is, savePath);
        }

        return true;
    }

    /**
     * generate a seal pic with default font
     */
    public static boolean generateDefaultSealFile (String companyName, String savePath) throws IOException {
        if (checkParams(companyName, savePath)) {
            InputStream is = getSealInputStream(companyName, null);
            FileTool.createFileFromInputStream(is, savePath);
        }

        return true;
    }

    /**
     * generate a seal pic with specified font
     */
    public static boolean generateSealFileWithSpecifiedFonts (String companyName, String savePath, String fontName) throws IOException {
        if (checkParams(companyName, savePath)) {
            InputStream is = getSealInputStream(companyName, fontName);
            FileTool.createFileFromInputStream(is, savePath);
        }

        return true;
    }

    private static boolean checkParams (String companyName, String savePath) throws IOException {
        if (companyName == null || savePath == null)
            throw new IOException("company name and save path can not be empty");

        if (companyName.length() > 25) {
            throw new IOException("company name can not exceed 25 chars");
        }

        if (SuffixTool.checkStringHasSuffix(savePath) && SuffixTool.checkStringSuffix(savePath, "png")) {
            return true;
        } else {
            throw new IOException("save file must be a png file");
        }
    }

}
