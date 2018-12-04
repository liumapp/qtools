package com.liumapp.qtools.http;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
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
     * download file from url to local file by NIO
     */
    public static void NIODownload (String url, String savefile) throws IOException {
        InputStream in = new URL(url).openStream();
        ReadableByteChannel readableByteChannel = Channels.newChannel(in);
        FileOutputStream fileOutputStream = new FileOutputStream(savefile);
        try {
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } finally {
            in.close();
            fileOutputStream.close();
        }

    }

    /**
     * download file from url to local file
     * jkd1.7 or above
     */
    public static void IODownload (String url, String saveFile) throws IOException {
        InputStream in = new URL(url).openStream();
        try {
            Files.copy(in, Paths.get(saveFile), StandardCopyOption.REPLACE_EXISTING);
        } finally {
            in.close();
        }

    }

    /**
     * download file from url to local file
     * works fine in jdk1.6
     */
    public static void IODownloadForJDK6 (String url, String saveFile) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        try {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } finally {
            in.close();
            fileOutputStream.close();
        }
    }

}
