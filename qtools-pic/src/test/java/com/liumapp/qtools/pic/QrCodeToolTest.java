package com.liumapp.qtools.pic;

import com.google.zxing.WriterException;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

/**
 * author liumapp
 * file QrCodeToolTest.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/13
 */
public class QrCodeToolTest extends TestCase {

    private String savePath = "/usr/local/tomcat/project/qtools/data/";

    @Test
    public void testGenerateQrCode () throws IOException, WriterException {
        QrCodeTool.encode("http://www.liumapp.com", 150, 150, "jpg", savePath + "qr.jpg");
    }

}
