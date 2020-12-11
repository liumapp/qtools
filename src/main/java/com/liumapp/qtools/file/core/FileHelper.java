package com.liumapp.qtools.file.core;

import java.net.URL;

/**
 * file FileHelper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public interface FileHelper {

    /**
     * read file content by nio
     * @param filePath absolute file path
     * @return bytes array
     */
    public byte[] readyBytesByFilePath(String filePath);

    /**
     * write bytes to file
     * @param bytes bytes array
     * @param filePath file URL
     */
    public void writeBytesToFile (byte[] bytes, String filePath);



}
