package com.liumapp.qtools.http;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * file UrlDownloadTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/4
 */
public class UrlDownloadTool {

    /**
     * download file from url to local file
     * jkd1.7 or above
     */
    public static void IODownload (String url, String saveFile) {

    }

    /**
     * download file from url to local file
     * works fine in jdk1.6
     */
    public static void IODownloadForJDK6 (String url, String saveFile) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
    }

}
