package com.liumapp.qtools.http;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
    public static void IODownload (String url, String saveFile) throws IOException {
        InputStream in = new URL(url).openStream();
        Files.copy(in, Paths.get(saveFile), StandardCopyOption.REPLACE_EXISTING);
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
