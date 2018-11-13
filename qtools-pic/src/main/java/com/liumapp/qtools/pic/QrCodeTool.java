package com.liumapp.qtools.pic;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.liumapp.qtools.pic.config.MatrixToLogoImageConfig;
import com.liumapp.qtools.pic.logo.MatrixToImageWriterEx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Hashtable;

import static com.google.zxing.BarcodeFormat.QR_CODE;

/**
 * author liumapp
 * file QrCodeTool.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/13
 */
public class QrCodeTool {

    private QrCodeTool() {
        throw new UnsupportedOperationException("not allowed to initialize");
    }

    /**
     * 生成二维码
     *
     * @param content  条码文本内容
     * @param width    条码宽度
     * @param height   条码高度
     * @param fileType 文件类型，如png
     * @param savePath 保存路径
     */
    @SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
    public static void encode(String content, int width, int height, String fileType, String savePath) throws IOException, WriterException {
        Charset charset = Charset.forName("UTF-8");
        CharsetEncoder encoder = charset.newEncoder();

        byte[] b = encoder.encode(CharBuffer.wrap(content)).array();
        String data = new String(b, "iso8859-1");

        Writer writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(data, QR_CODE, width, height);
        File file = new File(savePath);
        MatrixToImageWriter.writeToFile(matrix, fileType, file);
    }


    /**
     * 生成带logo的二维码
     *
     * @param content  条码文本内容
     * @param width    条码宽度
     * @param height   条码高度
     * @param logoPath 条码中logo的路径
     * @param fileType 文件类型，如png
     * @param savePath 保存路径
     */
    public static void encodeLogo(String content, int width, int height, String logoPath, String fileType, String savePath) throws IOException {
        BitMatrix matrix = MatrixToImageWriterEx.createQRCode(content, width, height);
        MatrixToLogoImageConfig logoConfig = new MatrixToLogoImageConfig(Color.BLUE, 4);
        MatrixToImageWriterEx.writeToFile(matrix, fileType, savePath, logoPath, logoConfig);
    }

    /**
     * 解码
     *
     * @param filePath
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String decode(String filePath) throws IOException, NotFoundException {
        BufferedImage image = ImageIO.read(new File(filePath));
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        Result result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }
}
