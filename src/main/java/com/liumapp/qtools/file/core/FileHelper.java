package com.liumapp.qtools.file.core;

import com.liumapp.qtools.core.Helper;

/**
 * file FileHelper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public interface FileHelper extends Helper {

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
     * @return write length
     */
    public Integer writeBytesToFile (byte[] bytes, String filePath);

    /**
     * @return Base64Helper to solve base64 problem
     */
    public Base64Helper base64();

}
