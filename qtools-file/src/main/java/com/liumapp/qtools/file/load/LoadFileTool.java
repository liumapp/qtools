package com.liumapp.qtools.file.load;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * author liumapp
 * file LoadFileTool.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/8
 */
public final class LoadFileTool {

    /**
     * all static method , plz do not initialize the class
     */
    private LoadFileTool() {
        throw new UnsupportedOperationException("not allowed to initialize");
    }

    /**
     * return byte arrays according to a file object
     * @param file File object
     * @return byte[]
     */
    public static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }

    /**
     * load input stream from resource path or file path
     * @param path resource path or absolute path
     * @return input stream
     * @throws IOException io exception
     */
    public static InputStream loadInputStream(String path) throws IOException {
        // 从Resource路径获取
        InputStream inputStream = LoadFileTool.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            // 从文件路径获取
            inputStream = new FileInputStream(path);
        }

        return inputStream;
    }

}
